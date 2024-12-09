package lib.interfaces;

import lib.exceptions.EmptyCollectionException;

public interface SmackStackADT<T> {

    public T smack() throws EmptyCollectionException;

    public void push(T element);

    public T pop() throws EmptyCollectionException;

    public T peek() throws EmptyCollectionException;

    public boolean isEmpty();

    public int size();

    @Override
    public String toString();
}