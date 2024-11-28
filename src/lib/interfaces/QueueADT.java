package lib.interfaces;

import lib.exceptions.EmptyCollectionException;

public interface QueueADT <T>
{
	void enqueue(T element);

	public T dequeue() throws EmptyCollectionException;

	public T first() throws EmptyCollectionException;

	boolean isEmpty();

	int size();

	String toString();
}
