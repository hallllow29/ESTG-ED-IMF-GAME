/**
 * @author 8230069
 * @file LinkedList.java
 * @copyright ESTG IPP
 * @brief ED, Ficha Prática 7, Exercicio 8
 * @date 2024/11/12
 **/

package lib;
import lib.interfaces.IteratorADT;
import lib.interfaces.ListADT;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class LinkedList <T> implements ListADT<T> {

	private LinearNode<T> front;
	private LinearNode<T> rear;
	private int size;
	private int modCount;

	public LinkedList() {
		this.front = null;
		this.rear = null;
		this.size = 0;
		this.modCount = 0;
	}

	public void add(T element) {
		LinearNode<T> newNode = new LinearNode<>(element);

		if (isEmpty()) {
			this.front = newNode;
			this.rear = newNode;
		} else {
			this.rear.setNext(newNode);
			this.rear = newNode;
		}
		this.size++;
		this.modCount++;
	}

	/**
	 * Removes the specified target element from this LinkedList.
	 *
	 * @param target the element to be removed from the list
	 * @return the removed element from the list
	 *
	 * @throws EmptyCollectionException if the list is empty
	 * @throws ElementNotFoundException if the element is not found in the list
	 */
	@Override
	public T remove(T target) throws EmptyCollectionException, ElementNotFoundException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> previousNode = null;
		LinearNode<T> currentNode = this.front;
		boolean found = false;

		while (currentNode != null) {
			if (currentNode.getElement().equals(target)) {
				found = true;
				break;
			}
			previousNode = currentNode;
			currentNode = currentNode.getNext();
		}

		if (!found) {
			throw new ElementNotFoundException("Element not in the list.");
		}
		LinearNode<T> remove = currentNode;
		T removedElement = remove.getElement();
		previousNode.setNext(currentNode.getNext());
		currentNode = null;

		this.size--;
		this.modCount--;
		return removedElement;
	}

	/**
	 * Returns the first element in the LinkedList.
	 *
	 * @return the first element in this LinkedList
	 *
	 * @throws EmptyCollectionException if the LinkedList is empty
	 */
	@Override
	public T first() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> first = this.front;
		return first.getElement();
	}

	/**
	 * Returns the last element of this LinkedList.
	 *
	 * @return the last element in this LinkedList
	 *
	 * @throws EmptyCollectionException if the LinkedList is empty
	 */
	@Override
	public T last() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> last = this.rear;
		return last.getElement();
	}

	/**
	 * Removes and returns the first element from this LinkedList.
	 *
	 * @return the first element from this LinkedList
	 *
	 * @throws EmptyCollectionException if the LinkedList is empty
	 */
	@Override
	public T removeFirst() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> first = this.front;

		if (size() == 1) {
			this.front = this.rear = null;
		} else {
			this.front = this.front.getNext();
		}
		this.size--;
		this.modCount--;

		return first.getElement();
	}

	/**
	 * Removes and returns the last element from this LinkedList.
	 *
	 * @return the last element from this LinkedList
	 *
	 * @throws EmptyCollectionException if the LinkedList is empty
	 */
	@Override
	public T removeLast() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> lastNode = this.rear;

		if (size() == 1) {
			this.front = this.rear = null;
		} else {
			LinearNode<T> currentNode = this.front;
			while (currentNode.getNext() != this.rear) {
				currentNode = currentNode.getNext();
			}
			this.rear = null;
			this.rear = currentNode;
			this.rear.setNext(null);

		}
		this.size--;
		this.modCount--;
		return lastNode.getElement();
	}

	/**
	 * Checks if the list contains the specified element.
	 *
	 * @param element the element to be checked for containment
	 * @return true if the list contains the specified element, false otherwise
	 *
	 * @throws EmptyCollectionException if the list is empty
	 */
	@Override
	public boolean contains(T element) throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> currentNode = this.front;

		while (currentNode != null) {
			if (currentNode.getElement().equals(element)) {
				return true;
			}
			currentNode = currentNode.getNext();
		}
		return false;
	}

	/**
	 * Checks if the linked list is empty.
	 *
	 * @return true if the linked list contains no elements, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.size == 0;
	}

	/**
	 * Returns the number of elements in this LinkedList.
	 *
	 * @return the number of elements in this LinkedList
	 */
	@Override
	public int size() {
		return this.size;
	}

	/**
	 * Returns an iterator over the elements in this LinkedList. The iterator provides
	 * sequential access to the elements of the list.
	 *
	 * @return an iterator over the elements in this LinkedList
	 */
	@Override
	public Iterator<T> iterator() {
		return new LinkedListIterator();
	}

	@Override
	public String toString() {
		String result = "";
		Iterator<T> iterator = this.iterator();
		while (iterator.hasNext()) {
			result += iterator.next();
			if (iterator.hasNext()) {
				result += " -> ";
			}
		}

		if (size() == 0) {
			return result;
		}

		return result + " -> NULL";
	}

	/**
	 * Recursively prints the elements of the linked list starting from the given node.
	 * Each element is followed by " -> " and the sequence ends with a newline character.
	 *
	 * @param lnode the starting node of the linked list to be printed
	 */
	private void printListRecursive(LinearNode<T> lnode) {
		if (lnode == null) {
			System.out.println();
			return;
		}
		System.out.print(lnode.getElement() + " -> ");
		printListRecursive(lnode.getNext());
	}

	public void printListRecursive() {
		printListRecursive(this.front);
	}


	public void reverseListNonRecursive() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		LinearNode<T> currentNode = this.front;
		LinearNode<T> previousNode = null;
		LinearNode<T> tempNode;

		while (currentNode != null) {
			// Store next
			tempNode = currentNode.getNext();

			// Reverse next pointer
			currentNode.setNext(previousNode);

			// Move Pointer one position
			previousNode = currentNode;
			currentNode = tempNode;
		}
		// Update the the front and rear pointer
		tempNode = this.front;
		this.front = this.rear;
		this.rear = tempNode;
	}
	/**
	 * Returns the front node of the linked list.
	 *
	 * @return the front node of the linked list
	 */
	private LinearNode<T> getFront() {
		return this.front;
	}

	/**
	 * Returns the modification count of the LinkedList.
	 *
	 * @return the number of modifications made to the list
	 */
	private int getModCount() {
		return this.modCount;
	}

	/**
	 * Represents a node in a linked data structure where each node contains an element
	 * and a reference to the next node in the sequence.
	 *
	 * @param <T> the type of element held in this node
	 */
	private class LinearNode <T> {

		private LinearNode<T> next;
		private T element;

		/**
		 * Constructs an empty LinearNode with no element and no next node.
		 */
		public LinearNode() {
			this.next = null; this.element = null;
		}

		/**
		 * Constructs a new LinearNode with the specified element.
		 *
		 * @param element the element to be stored in this node
		 */
		public LinearNode(T element) {
			this.next = null; this.element = element;
		}

		/**
		 * Returns the next node in the linked list.
		 *
		 * @return the next node in the linked list, or null if there is no next node
		 */
		public LinearNode<T> getNext() {
			return this.next;
		}

		/**
		 * Sets the next node in the linked list.
		 *
		 * @param lnode the node to set as the next node
		 */
		public void setNext(LinearNode<T> lnode) {
			this.next = lnode;
		}

		/**
		 * Returns the element stored in this node.
		 *
		 * @return the element stored in this node
		 */
		public T getElement() {
			return this.element;
		}

		/**
		 * Sets the element stored in this node.
		 *
		 * @param element the element to be stored in this node
		 */
		public void setElement(T element) {
			this.element = element;
		}

	}

	private class LinkedListIterator implements Iterator<T> {

		private LinearNode<T> currentNode;

		private final int exceptedModCount;

		/**
		 * Constructs an iterator for a linked list. Initializes the iterator's current
		 * node to the front of the list and sets the expected modification count to the
		 * list's current modification count.
		 */
		public LinkedListIterator() {
			this.currentNode = getFront();
			this.exceptedModCount = getModCount();
		}

		/**
		 * Returns true if the iteration has more elements. Ensures concurrent
		 * modification check is performed before checking the next element.
		 *
		 * @return true if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			checkForCurrentModification();
			return this.currentNode != null;
		}

		/**
		 * Returns the next element in the iteration. Advances the iterator to the next
		 * element in the sequence. Checks for concurrent modification before proceeding.
		 *
		 * @return the next element in the iteration
		 *
		 * @throws NoSuchElementException          if the iteration has no more elements
		 * @throws ConcurrentModificationException if the list has been modified
		 *                                         concurrently
		 */
		@Override
		public T next() {
			checkForCurrentModification();
			if (!hasNext()) {
				throw new NoSuchElementException("No more elements in the iteration.");
			}
			T element = currentNode.getElement();
			this.currentNode = currentNode.getNext();
			return element;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove operation is not supported.");
		}

		/**
		 * Checks for concurrent modification by comparing the current modification count
		 * with the expected modification count. If they do not match, it indicates that
		 * the list has been modified concurrently and throws a
		 * ConcurrentModificationException.
		 *
		 * @throws ConcurrentModificationException if the list has been modified
		 *                                         concurrently
		 */
		private void checkForCurrentModification() {
			if (modCount != exceptedModCount) {
				throw new ConcurrentModificationException("Concurrent Modification Detected");
			}
		}
	}
}
