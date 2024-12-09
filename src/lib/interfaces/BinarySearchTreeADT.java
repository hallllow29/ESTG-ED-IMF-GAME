package lib.interfaces;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T> {

	void addElement(T element);

	T removeElement(T target) throws ElementNotFoundException;

	void removeAllOccurrences(T targetElement) throws EmptyCollectionException, ElementNotFoundException;

	T removeMin() throws EmptyCollectionException, ElementNotFoundException;

	T removeMax() throws EmptyCollectionException, ElementNotFoundException;

	T findMin() throws EmptyCollectionException;

	T findMax() throws EmptyCollectionException;


}
