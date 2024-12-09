package lib.interfaces;

import entities.Room;
import lib.ArrayUnorderedList;

public interface CustomNetworkADT<T> {

    ArrayUnorderedList<T> getConnectedVertices(T vertex);
    public Room getRoom(String name);
    public ArrayUnorderedList<T> getVertices();


}
