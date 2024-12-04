package lib.interfaces;

import lib.BinaryTreeNode;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

public interface BinaryTreeADT<T> {

	T getRootElement();

	BinaryTreeNode<T> getRoot();

	boolean isEmpty();

	int size();

	boolean contains(T target) throws EmptyCollectionException, ElementNotFoundException;

	T find(T target) throws ElementNotFoundException;

	String toString();

	Iterator<T> iteratorInOrder();

	Iterator<T> iteratorPreOrder();

	Iterator<T> iteratorPostOrder();

	Iterator<T> iteratorLevelOrder() throws EmptyCollectionException;
}
