package lib.interfaces;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T> {

	void addElement(T element);

	T removeElement(T target) throws ElementNotFoundException;

	void removeAllOccurences(T targetElement) throws EmptyCollectionException, ElementNotFoundException;

	T removeMin() throws EmptyCollectionException, ElementNotFoundException;

	T removeMox() throws EmptyCollectionException, ElementNotFoundException;

	T findMin() throws EmptyCollectionException;

	T findMax() throws EmptyCollectionException;


}
