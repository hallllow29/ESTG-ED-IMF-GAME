
package lib.interfaces;

import lib.exceptions.EmptyCollectionException;

public interface StackADT<T> {

    void push(T element);

    T pop() throws EmptyCollectionException;

    T peek() throws EmptyCollectionException;

    boolean isEmpty();

    int size();

    @Override
	String toString();
}
