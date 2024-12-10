/**
 * @author 8230068, 8230069
**/

package lib.interfaces;

import lib.exceptions.EmptyCollectionException;

/**
 * QueueADT defines the interface for a generic queue collection.
 * A queue is a linear data structure that follows the FIFO (First In, First Out) principle,
 * where elements are inserted at the rear and removed from the front.
 *
 * @param <T> the type of elements held in this queue
 */
public interface QueueADT <T>
{
	/**
	 * Adds the specified element to the rear of the queue.
	 *
	 * @param element the element to be added to the queue
	 */
	void enqueue(T element);

	/**
	 * Removes and returns the element from the front of the queue.
	 *
	 * @return the element removed from the front of the queue
	 * @throws EmptyCollectionException if the queue is empty
	 */
	T dequeue() throws EmptyCollectionException;

	/**
	 * Returns the first element in the queue without removing it.
	 *
	 * @return the first element in the queue
	 * @throws EmptyCollectionException if the queue is empty
	 */
	T first() throws EmptyCollectionException;

	/**
	 * Determines whether the queue is empty.
	 *
	 * @return true if the queue contains no elements, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements currently in the queue.
	 *
	 * @return the integer count of elements in the queue
	 */
	int size();

	/**
	 * Returns a string representation of the queue.
	 *
	 * @return a string that represents the current state of the queue
	 */
	String toString();
}
