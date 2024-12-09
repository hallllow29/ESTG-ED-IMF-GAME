/**
 * @author 8230069
 * @file CircularDoubleLinkedList.java
 * @copyright ESTG IPP
 * @brief ED, Ficha Prática 6, Exercicio 5
 * @date 2024/11/01
 **/

package lib.lists;

import lib.interfaces.ListADT;
import java.util.Iterator;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.ConcurrentModificationException;
import java.util.NoSuchElementException;

public class CircularDoubleLinkedList <T> implements ListADT<T> {

	private DoubleNode<T> front;
	private DoubleNode<T> rear;
	private int size;
	private int modCount;

	public CircularDoubleLinkedList() {
		this.front = null;
		this.rear = null;
		this.size = 0;
		this.modCount = 0;
	}

	/**
	 * Adds a specified element to the circular linked list.
	 *
	 * @param element the element to be added to the list
	 */
	public void add(T element) {

		DoubleNode<T> newNode = new DoubleNode<>(element);
		if (isEmpty()) {
			this.front = newNode;
			this.rear = newNode;
			this.front.setNext(this.front);
			this.front.setPrevious(this.front);
		} else {
			this.rear.setNext(newNode);
			newNode.setPrevious(this.rear);
			this.rear = newNode;
			this.rear.setNext(this.front);
		}

		this.size++;
		this.modCount++;
	}

	/**
	 * Removes and returns the specified element from the collection.
	 *
	 * @param target the element to be removed from the collection
	 * @return the removed element
	 * @throws EmptyCollectionException if the collection is empty
	 * @throws ElementNotFoundException if the element is not found in the collection
	 */
	@Override
	public T remove(T target) throws EmptyCollectionException, ElementNotFoundException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty");
		}

		DoubleNode<T> currentNode = this.front;
		DoubleNode<T> previousNode = null;

		boolean found = false;

		while (currentNode.getNext() != this.front) {
			if (currentNode.getElement().equals(target)) {
				found = true;
				break;
			}
			currentNode = currentNode.getNext();
		}

		previousNode = currentNode.getPrevious();
		previousNode.setNext(currentNode.getNext());
		currentNode.getNext().setPrevious(previousNode);

		DoubleNode<T> remove = currentNode;

		currentNode = null;
		this.size--;
		this.modCount++;

		return remove.getElement();
	}
	/**
	 * Returns the first element in the collection.
	 *
	 * @return the first element in the collection
	 * @throws EmptyCollectionException if the collection is empty
	 */
	@Override
	public T first() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty");
		}
		return this.front.getElement();
	}
	/**
	 * Returns the last element in the collection.
	 *
	 * @return the last element in the collection
	 * @throws EmptyCollectionException if the collection is empty
	 */
	@Override
	public T last() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty");
		}
		return rear.getElement();
	}
	/**
	 * Removes and returns the first element from the collection.
	 *
	 * @return the first element that was removed from the collection
	 * @throws EmptyCollectionException if the collection is empty
	 */
	@Override
	public T removeFirst() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		DoubleNode<T> first = this.front;

		if (this.front.getNext() == this.front) {
			this.front = null;
		} else {
			this.front = this.front.getNext();
			this.front.setPrevious(this.rear);
			this.rear.setNext(this.front);
		}
		this.size--;
		this.modCount++;

		return first.getElement();
	}
	/**
	 * Removes and returns the last element from the collection.
	 *
	 * @return the last element that was removed from the collection
	 * @throws EmptyCollectionException if the collection is empty
	 */
	@Override
	public T removeLast() throws EmptyCollectionException {
		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		DoubleNode<T> last = this.rear;

		if (this.rear.getNext() == this.rear) {
			this.rear = null;
			this.front = null;
		} else {
			this.rear = this.rear.getPrevious();
			this.rear.setPrevious(this.rear.getPrevious());
			this.rear.setNext(this.front);
		}
		this.size--;
		this.modCount++;

		return last.getElement();

	}
	/**
	 * Checks if the specified element is present in the collection.
	 *
	 * @param element the element to be checked for presence in the collection
	 * @return true if the element is found in the collection, false otherwise
	 * @throws EmptyCollectionException if the collection is empty
	 */
	@Override
	public boolean contains(T element) throws EmptyCollectionException {

		if (isEmpty()) {
			throw new EmptyCollectionException("List is empty.");
		}

		DoubleNode<T> currentNode = this.front;

		while (currentNode.getNext() != this.front) {
			if (currentNode.getElement().equals(element)) {
				return true;
			}
			currentNode = currentNode.getNext();
		}
		return false;
	}
	/**
	 * Checks if the collection is empty.
	 *
	 * @return true if the collection has no elements, false otherwise
	 */
	@Override
	public boolean isEmpty() {
		return this.front == null;
	}
	/**
	 * Returns the number of elements in the collection.
	 *
	 * @return the number of elements in the collection
	 */
	@Override
	public int size() {
		return this.size;
	}

	private DoubleNode<T> getFront() {
		return this.front;
	}

	private int getModCount() {
		return this.modCount;
	}

	@Override
	public String toString() {
		String result = "";
		Iterator<T> iterator = this.iterator();
		int counter = 0;
		while (iterator.hasNext() && counter < size()) {
			result += iterator.next();
			if (iterator.hasNext()) {
				result += " <-> ";
			}
			counter++;
		}

		if (size() == 0) {
			return result;
		}

		return result + "HEAD";

	}

	/**
	 * Returns an iterator over elements of type {@code T}.
	 *
	 * @return an {@code IteratorADT<T>} object for iterating over the elements in this collection
	 */
	@Override
	public Iterator<T> iterator() {
		return new CircularLinkedListIterator();
	}

	private class CircularLinkedListIterator implements Iterator<T> {

		private DoubleNode<T> currentNode;

		private final int exceptedModCount;

		/**
		 * Constructs an iterator for a double linked list.
		 * Initializes the iterator's current node to the front of the list and
		 * sets the expected modification count to the list's current modification count.
		 */
		public CircularLinkedListIterator() {
			this.currentNode = getFront();
			this.exceptedModCount = getModCount();
		}

		/**
		 * Returns true if the iteration has more elements.
		 * Ensures concurrent modification check is performed before checking the next element.
		 *
		 * @return true if the iteration has more elements
		 */
		@Override
		public boolean hasNext() {
			checkForCurrentModification();
			return this.currentNode != null;
		}


		public boolean hasPrevious() {
			checkForCurrentModification();
			return this.currentNode != null;
		}
		/**
		 * Returns the next element in the iteration.
		 * Advances the iterator to the next element in the sequence.
		 * Checks for concurrent modification before proceeding.
		 *
		 * @return the next element in the iteration
		 * @throws NoSuchElementException          if the iteration has no more elements
		 * @throws ConcurrentModificationException if the list has been modified concurrently
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

		public T previous() {
			checkForCurrentModification();
			if (!hasPrevious()) {
				throw new NoSuchElementException("No more elements in the iteration.");
			}
			T element = currentNode.getElement();
			this.currentNode = currentNode.getPrevious();
			return element;
		}

		@Override
		public void remove() {
			throw new UnsupportedOperationException("Remove operation is not supported.");
		}

		/**
		 * Checks for concurrent modification by comparing the current modification count with
		 * the expected modification count. If they do not match, it indicates that the list has
		 * been modified concurrently and throws a ConcurrentModificationException.
		 *
		 * @throws ConcurrentModificationException if the list has been modified concurrently
		 */
		private void checkForCurrentModification() {
			if (modCount != exceptedModCount) {
				throw new ConcurrentModificationException("Concurrent Modification Detected");
			}
		}
	}

	private class DoubleNode<T> {

		private DoubleNode<T> previous;
		private DoubleNode<T> next;
		private T element;


		public DoubleNode() {
			this.previous = null;
			this.next = null;
			this.element = null;
		}


		public DoubleNode(T element) {
			this.previous = null;
			this.next = null;
			this.element = element;
		}


		public DoubleNode<T> getNext() {
			return this.next;
		}

		public DoubleNode<T> getPrevious() {return this.previous;}


		public void setNext(DoubleNode<T> dnode) {
			this.next = dnode;
		}

		public void setPrevious(DoubleNode<T> dnode) {
			this.previous = dnode;
		}

		/**
		 * Retrieves the element stored in this node.
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
}
