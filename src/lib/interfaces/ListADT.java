package lib.interfaces;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import java.util.Iterator;


public interface ListADT<T> extends Iterable<T> {

	/**
	 * Removes the specified element from the list.
	 *
	 * @param target the element to be removed from the list
	 * @return the removed element
	 * @throws EmptyCollectionException if the list is empty
	 * @throws ElementNotFoundException if the target element is not found in the list
	 */
	T remove(T target) throws EmptyCollectionException, ElementNotFoundException;

	/**
	 * Returns the first element in the list.
	 *
	 * @return the first element in the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	T first() throws EmptyCollectionException;

	/**
	 * Returns the last element in the list.
	 *
	 * @return the last element in the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	T last() throws EmptyCollectionException;

	/**
	 * Removes and returns the first element from the list.
	 *
	 * @return the first element from the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	T removeFirst() throws EmptyCollectionException;

	/**
	 * Removes and returns the last element from the list.
	 *
	 * @return the last element from the list
	 * @throws EmptyCollectionException if the list is empty
	 */
	T removeLast() throws EmptyCollectionException;

	/**
	 * Checks if the specified element is present in the list.
	 *
	 * @param element the element to be checked for presence in the list
	 * @return true if the list contains the specified element, false otherwise
	 *
	 * @throws EmptyCollectionException if the list is empty
	 */
	boolean contains(T element) throws EmptyCollectionException;

	/**
	 * Checks if the list is empty.
	 *
	 * @return true if the list contains no elements, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the list.
	 *
	 * @return the number of elements in the list.
	 */
	int size();

	Iterator<T> iterator();

	String toString();

}
