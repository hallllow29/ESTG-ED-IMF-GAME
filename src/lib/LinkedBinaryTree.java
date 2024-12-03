package lib;

import lib.interfaces.BinaryTreeADT;

import java.util.Iterator;


public class LinkedBinaryTree<T> implements BinaryTreeADT<T> {

	private final int count;
	private final BinaryTreeNode<T> root;

	public LinkedBinaryTree() {
		this.count = 0;
		root = null;
	}

	public LinkedBinaryTree(T element) {
		count = 1;
		root = new BinaryTreeNode<T>(element);
	}

	@Override
	public T getRoot() {
		return this.root.getElement();
	}

	@Override
	public boolean isEmpty() {
		return this.count == 0;
	}

	@Override
	public int size() {
		return this.count;
	}

	@Override
	public boolean contains(T targetElement) {
		return false;
	}

	@Override
	public T find(T targetElement) {
		return null;
	}

	@Override
	public Iterator<T> iteratorInOrder() {
		// Slides...
	}

	@Override
	public Iterator<T> iteratorPreOrder() {
		return null;
	}

	@Override
	public Iterator<T> iteratorPostOrder() {
		return null;
	}

	@Override
	public Iterator<T> iteratorLevelOrder() {
		return null;
	}


}
