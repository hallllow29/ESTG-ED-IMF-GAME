package lib;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.NetworkADT;

import java.util.Iterator;

public class Network<T> extends Graph<T> implements NetworkADT<T> {

    private double[][] weightMatrix;

    public Network() {
        numVertices = 0;
        this.weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
        this.vertices = (T[]) (new Object[DEFAULT_CAPACITY]);
    }

    public double[][] getWeightMatrix() {
        return this.weightMatrix;
    }
    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        int index1 = super.getVertexIndex(vertex1);
        int index2 = super.getVertexIndex(vertex2);

        if (super.indexIsValid(index1) && super.indexIsValid(index2)) {
            this.weightMatrix[index1][index2] = weight;
            this.weightMatrix[index2][index1] = weight;
        }
    }

    @Override
    public void removeEdge(T vertex1, T vertex2) throws ElementNotFoundException {
        int index1 = super.getVertexIndex(vertex1);
        int index2 = super.getVertexIndex(vertex2);

        if (super.indexIsValid(index1) && super.indexIsValid(index2)) {
            this.weightMatrix[index1][index2] = Double.MAX_VALUE;
            this.weightMatrix[index2][index1] = Double.MAX_VALUE;
        }

    }

    @Override
    public void addVertex(T vertex) {
        if (vertex == null) {
            throw new IllegalArgumentException("Network");
        }

        if (numVertices == super.vertices.length) {
            this.expandCapacity();
        }
        this.vertices[numVertices] = vertex;

        for (int i = 0; i <= numVertices; i++) {
            this.weightMatrix[numVertices][i] = Double.POSITIVE_INFINITY;
            this.weightMatrix[i][numVertices] = Double.POSITIVE_INFINITY;
        }

        this.numVertices++;
    }

    @Override
    public void removeVertex(T vertex) throws ElementNotFoundException {
        int index = super.getVertexIndex(vertex);
        this.removeVertex(vertex);

        if (index != -1) {
            for (int i = index; i < this.numVertices; i++) {
                System.arraycopy(this.weightMatrix[i + 1], 0, this.weightMatrix[i], 0, this.numVertices);
            }
            for (int i = 0; i < this.numVertices; i++) {
                this.weightMatrix[i][index] = Double.MAX_VALUE;
            }
        }
    }

    @Override
    public Iterator<T> iteratorShortestPath(T startVertex, T targetVertex) throws ElementNotFoundException {
        int startIndex = getVertexIndex(startVertex);
        int targetIndex = getVertexIndex(targetVertex);

        if (startIndex == -1 || targetIndex == -1) {
            throw new ElementNotFoundException("Network");
        }

        final double INFINITO = Double.MAX_VALUE;
        double[] distancias = new double[super.numVertices];
        double[] anteriores = new double[super.numVertices];
        boolean[] visitados = new boolean[super.numVertices];

        for (int i = 0; i < super.numVertices; i++) {
            distancias[i] = INFINITO;
            anteriores[i] = -1;
            visitados[i] = false;
        }

        distancias[startIndex] = 0;

        for (int i = 0; i < super.numVertices; i++) {
            int maisProximo = -1;
            for (int j = 0; j < super.numVertices; j++) {
                if (!visitados[j] && (maisProximo == -1 || distancias[j] < distancias[maisProximo])) {
                    maisProximo = j;
                }
            }

            if (maisProximo == -1) {
                break;
            }
            visitados[maisProximo] = true;

            for (int vizinho = 0; vizinho < super.numVertices; vizinho++) {
                if (this.weightMatrix[maisProximo][vizinho] != INFINITO && !visitados[vizinho]) {
                    double novaDistancia = distancias[maisProximo] + this.weightMatrix[maisProximo][vizinho];
                    if (novaDistancia < distancias[vizinho]) {
                        distancias[vizinho] = novaDistancia;
                        anteriores[vizinho] = maisProximo;
                    }
                }
            }
        }

        ArrayUnorderedList<T> caminho = new ArrayUnorderedList<>();
        int atual = targetIndex;
        while (atual != -1) {
            caminho.addToFront(super.vertices[atual]);
            atual = (int) anteriores[atual];
        }

        if (distancias[targetIndex] == INFINITO) {
            return new ArrayUnorderedList<T>().iterator();
        }

        return caminho.iterator();

    }

    public Iterator<T> iteratorDFS(T startVertex) throws EmptyCollectionException {
        int vertexIndex = super.getVertexIndex(startVertex);
        return iteratorDFS(vertexIndex);
    }

    public Iterator<T> iteratorDFS(int startIndex) throws EmptyCollectionException {
        Integer x;
        boolean found;
        LinkedStack<Integer> traversalStack = new LinkedStack<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();
        boolean[] visited = new boolean[numVertices];

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalStack.push(startIndex);
        resultList.addToRear(vertices[startIndex]);
        visited[startIndex] = true;

        while (!traversalStack.isEmpty()) {
            x = traversalStack.peek();
            found = false;

            for (int i = 0; (i < numVertices) && !found; i++) {
                if ((this.weightMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalStack.push(i);
                    resultList.addToRear(vertices[i]);
                    visited[i] = true;
                    found = true;
                }
            }
            if (!found && !traversalStack.isEmpty()) {
                traversalStack.pop();
            }
        }
        return resultList.iterator();
    }

    public Iterator<T> iteratorBFS(T startVertex) throws EmptyCollectionException {
        int vertexIndex = getVertexIndex(startVertex);
        return iteratorBFS(vertexIndex);
    }

    public Iterator<T> iteratorBFS(int startIndex) throws EmptyCollectionException {
        Integer x;
        LinkedQueue<Integer> traversalQueue = new LinkedQueue<Integer>();
        ArrayUnorderedList<T> resultList = new ArrayUnorderedList<T>();

        if (!indexIsValid(startIndex)) {
            return resultList.iterator();
        }

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }

        traversalQueue.enqueue(startIndex);
        visited[startIndex] = true;

        while (!traversalQueue.isEmpty()) {
            x = traversalQueue.dequeue();
            resultList.addToRear(vertices[x]);

            for (int i = 0; i < numVertices; i++) {
                if ((this.weightMatrix[x][i] < Double.POSITIVE_INFINITY) && !visited[i]) {
                    traversalQueue.enqueue(i);
                    visited[i] = true;
                }
            }
        }
        return resultList.iterator();
    }

    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) throws ElementNotFoundException {
        Iterator<T> shortPath = iteratorShortestPath(startVertex, targetVertex);

        if (!shortPath.hasNext()) {
            return -1;
        }

        double totalWeight = 0.0;
        T current = shortPath.next();

        while (shortPath.hasNext()) {
            T next = shortPath.next();
            int index1 = super.getVertexIndex(current);
            int index2 = super.getVertexIndex(next);

            totalWeight += this.weightMatrix[index1][index2];

            current = next;
        }

        return totalWeight;
    }

    public Network mstNetwork() throws EmptyCollectionException {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
        Network<T> resultGraph = new Network<T>();

        if (isEmpty() || !isConnected()) {
            return resultGraph;
        }

        resultGraph.weightMatrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++)
            for (int j = 0; j < numVertices; j++)
                resultGraph.weightMatrix[i][j] = Double.POSITIVE_INFINITY;

        resultGraph.vertices = (T[]) (new Object[numVertices]);

        boolean[] visited = new boolean[numVertices];

        for (int i = 0; i < numVertices; i++)
            visited[i] = false;
        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;

        /** Add all edges, which are adjacent to the starting vertex,
         to the heap */
        for (int i = 0; i < numVertices; i++)
            minHeap.addElement((weightMatrix[0][i]));

        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {

            /** Get the edge with the smallest weight that has exactly
             * one vertex already in the resultGraph */
            do {
                weight = (minHeap.removeMin()).doubleValue();
                edge = getEdgeWithWeightOf(weight, visited);
            } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));
            x = edge[0];
            y = edge[1];

            if (!visited[x])
                index = x;
            else
                index = y;
            /** Add the new edge and vertex to the resultGraph */

            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;
            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
            resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];

            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && (this.weightMatrix[i][index] <
                        Double.POSITIVE_INFINITY)) {
                    edge[0] = index;
                    edge[1] = i;
                    minHeap.addElement(this.weightMatrix[index][i]);
                }
            }
        }
        return resultGraph;
    }

    private int[] getEdgeWithWeightOf(double weight, boolean[] visited) {
        for (int i = 0; i < super.numVertices - 1; i++) {
            for (int j = 0; j < super.numVertices; j++) {
                if (this.weightMatrix[i][j] == weight) {
                    if ((visited[i] && !visited[j]) || (!visited[i] || visited[j])) {
                        return new int[]{i, j}; //Retorna a aresta encontrada
                    }
                }
            }
        }

        return new int[]{-1, -1}; //Nenhuma aresta encontrada
    }

    private void expandCapacity() {
        int newCapacity = super.vertices.length * 2;

        double[][] newWeightedMatrix = new double[newCapacity][newCapacity];

        for (int i = 0; i < super.numVertices; i++) {
            System.arraycopy(this.weightMatrix[i], 0, newWeightedMatrix[i], 0, super.numVertices);
        }

        this.weightMatrix = newWeightedMatrix;

        T[] newVertices = (T[]) new Comparable[newCapacity];

        if (super.numVertices >= 0) {
            System.arraycopy(super.vertices, 0, newVertices, 0, super.numVertices);
        }

        super.vertices = newVertices;
    }

    public ArrayUnorderedList<T> getConnectedVertices(T vertex) {
        int index = super.getVertexIndex(vertex);

        if (index == -1) {
            throw new IllegalArgumentException("Graph");
        }

        ArrayUnorderedList<T> connectedVertices = new ArrayUnorderedList<>();

        for (int i = 0; i < this.numVertices; i++) {
            if (this.weightMatrix[index][i] != Double.POSITIVE_INFINITY) {
                connectedVertices.addToRear(vertices[i]);
            }
        }

        return connectedVertices;
    }
}

