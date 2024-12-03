package lib;

import lib.exceptions.ElementNotFoundException;
import lib.interfaces.NetworkADT;

import java.util.Iterator;

public class Network<T extends Comparable<T>> extends Graph<T> implements NetworkADT<T> {

    private double[][] weightMatrix;

    public Network() {
        super();
        this.weightMatrix = new double[DEFAULT_CAPACITY][DEFAULT_CAPACITY];
    }

    @Override
    public void addEdge(T vertex1, T vertex2, double weight) {
        int index1 = super.getVertexIndex(vertex1);
        int index2 = super.getVertexIndex(vertex2);

        if (super.indexIsValid(index1) && super.indexIsValid(index2)) {
            super.addEdge(vertex1, vertex2);
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
    public void removeVertex(T vertex) throws ElementNotFoundException {
        int index = super.getVertexIndex(vertex);
        super.removeVertex(vertex);

        if (index != -1 ) {
            for (int i = index; i < super.numVertices; i++) {
                System.arraycopy(this.weightMatrix[i + 1], 0, this.weightMatrix[i], 0, super.numVertices);
            }
            for (int i = 0; i < super.numVertices; i++) {
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
                if (!visitados[j] && (maisProximo == -1  || distancias[j] < distancias[maisProximo])) {
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
        while(atual != -1 ) {
            caminho.addToFront(super.vertices[atual]);
            atual = (int) anteriores[atual];
        }

        if (distancias[targetIndex] == INFINITO) {
            return new ArrayUnorderedList<T>().iterator();
        }

        return caminho.iterator();

    }


    @Override
    public double shortestPathWeight(T startVertex, T targetVertex) throws ElementNotFoundException {
        Iterator<T> shortPath = iteratorShortestPath(startVertex, targetVertex);

        if (!shortPath.hasNext()) {
            return -1;
        }

        double totalWeight = 0.0;
        T current = shortPath.next();

        while(shortPath.hasNext()) {
            T next = shortPath.next();
            int index1 = super.getVertexIndex(current);
            int index2 = super.getVertexIndex(next);

            totalWeight += this.weightMatrix[index1][index2];

            current = next;
        }

        return totalWeight;
    }

    public Network<T> mstNetwork() {
        int x, y;
        int index;
        double weight;
        int[] edge = new int[2];
        LinkedHeap<Double> minHeap = new LinkedHeap<Double>();
        Network<T> resultGraph = new Network<T>();

        if (isEmpty() || !isConnected()) {
            return resultGraph;
        }
        resultGraph.adjMatrix = new double[numVertices][numVertices];
        for (int i = 0; i < numVertices; i++) {
            for (int j = 0; j < numVertices; i++) {
                resultGraph.adjMatrix[i][j] = Double.POSITIVE_INFINITY;
            }
            resultGraph.vertices = (T[]) (new Object[numVertices]);
        }
        boolean[] visited = new boolean[numVertices];
        for (int i = 0; i < numVertices; i++) {
            visited[i] = false;
        }
        edge[0] = 0;
        resultGraph.vertices[0] = this.vertices[0];
        resultGraph.numVertices++;
        visited[0] = true;

        /**
         * Add all edges, which are adjacent to the starting vertex, to the heap
         */
        for (int i = 0; i < numVertices; i++) {
            minHeap.addElement(adjMatrix[0][i]);
        }

        while ((resultGraph.size() < this.size()) && !minHeap.isEmpty()) {
            /**
             * Get the edge with the smallest weight that has exactly one vertex
             * already in the resultGraph
             */
            do {
                weight = minHeap.removeMin();
                edge = getEdgeWithWeightOf(weight, visited);
            } while (!indexIsValid(edge[0]) || !indexIsValid(edge[1]));

            x = edge[0];
            y = edge[1];
            if (!visited[x]) {
                index = x;
            } else {
                index = y;
            }

            /**
             * Add the new edge and vertex to the resultGraph
             */
            resultGraph.vertices[index] = this.vertices[index];
            visited[index] = true;
            resultGraph.numVertices++;

            resultGraph.adjMatrix[x][y] = this.adjMatrix[x][y];
            resultGraph.adjMatrix[y][x] = this.adjMatrix[y][x];

            /**
             * Add all edges, that are adjacent to the newly added vertex, to
             * the heap
             */
            for (int i = 0; i < numVertices; i++) {
                if (!visited[i] && (this.adjMatrix[i][index]
                        < Double.POSITIVE_INFINITY)) {
                    edge[0] = index;
                    edge[1] = i;
                    minHeap.addElement(adjMatrix[index][i]);
                }
            }
        }
        return resultGraph;
    }

}
