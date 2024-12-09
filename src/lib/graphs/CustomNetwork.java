package lib.graphs;

import entities.Room;
import lib.lists.ArrayUnorderedList;
import lib.interfaces.CustomNetworkADT;

public class CustomNetwork<T> extends Network<T> implements CustomNetworkADT<T> {

    public CustomNetwork() {
        super();
    }

    public ArrayUnorderedList<T> getConnectedVertices(T vertex) {
        int index = super.getVertexIndex(vertex);

        if (index == -1) {
            throw new IllegalArgumentException("Graph");
        }

        ArrayUnorderedList<T> connectedVertices = new ArrayUnorderedList<>();

        for (int i = 0; i < this.numVertices; i++) {
            if (this.getWeightMatrix()[index][i] != Double.POSITIVE_INFINITY) {
                connectedVertices.addToRear(vertices[i]);
            }
        }

        return connectedVertices;
    }

    public Room getRoom(String name) {
        for (T vertex : vertices) {
            if (((Room) vertex).getName().equals(name)) {
                return (Room) vertex;
            }
        }
        return null;
    }

    public ArrayUnorderedList<T> getVertices() {
        ArrayUnorderedList<T> verticesList = new ArrayUnorderedList<>();
        // Object vertex;

        for (int i = 0; i < numVertices; i++) {
            // vertex = this.vertices[i];

            verticesList.addToRear(this.vertices[i]);
        }

        return verticesList;
    }

}
