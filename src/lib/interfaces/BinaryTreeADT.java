package lib.interfaces;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

public interface BinaryTreeADT<T> {

	/**
	 * Retrieves the root element of the binary tree.
	 *
	 * @return the root element of the binary tree of type T
	 */
	T getRoot();

	/**
	 * Determines if the binary tree is empty.
	 *
	 * @return true if the binary tree contains no elements, false otherwise
	 */
	boolean isEmpty();

	/**
	 * Returns the number of elements in the binary tree.
	 *
	 * @return the number of elements in the binary tree as an integer
	 */
	int size();

	/**
	 * Determines whether the binary tree contains the specified element.
	 *
	 * @param targetElement the element being searched for in the binary tree
	 * @return true if the element is found in the binary tree, false otherwise
	 */
	boolean contains(T targetElement) throws EmptyCollectionException, ElementNotFoundException;

	/**
	 * Searches for the specified element in the binary tree.
	 *
	 * @param targetElement the element to be located in the binary tree
	 * @return the target element if it is found within the binary tree
	 * @throws NoSuchElementException if the element is not found in the tree
	 */
	T find(T targetElement) throws ElementNotFoundException;

	/**
	 * Returns a string representation of the binary tree.
	 *
	 * @return a string that represents the binary tree
	 */
	String toString();

	/**
	 * Returns an iterator over the elements in this binary tree in in-order sequence.
	 *
	 * @return an iterator positioned at the first element in in-order traversal of the binary tree
	 */
	Iterator<T> iteratorInOrder();

	/**
	 * Returns an iterator over the elements in this binary tree in pre-order sequence.
	 *
	 * @return an iterator positioned at the first element in pre-order traversal of the binary tree
	 */
	Iterator<T> iteratorPreOrder();

	/**
	 * Returns an iterator over the elements in this binary tree in post-order sequence.
	 *
	 * @return an iterator positioned at the first element in post-order traversal of the binary tree
	 */
	Iterator<T> iteratorPostOrder();

	/**
	 * Returns an iterator over the elements in this binary tree in level-order sequence.
	 *
	 * @return an iterator positioned at the first element in level-order traversal of the binary tree
	 */
	Iterator<T> iteratorLevelOrder() throws EmptyCollectionException;
}
