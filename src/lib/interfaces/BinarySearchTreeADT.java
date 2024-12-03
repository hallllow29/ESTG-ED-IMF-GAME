package lib.interfaces;

public interface BinarySearchTreeADT<T> extends BinaryTreeADT<T> {

	void addElement(T element);

	T removeElement(T target);

	void removeAllOccurences(T targetElement);

	T removeMin();

	T removeMox();

	T findMin();

	T findMax();


}
