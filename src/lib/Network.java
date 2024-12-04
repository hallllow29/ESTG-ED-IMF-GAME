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



}
