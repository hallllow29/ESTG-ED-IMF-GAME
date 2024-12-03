package lib;

public class BinaryTreeNode <T> {
	private T element;
	private final BinaryTreeNode<T> left;
	private final BinaryTreeNode<T> right;

	public BinaryTreeNode(T element) {
		this.element = element;
		this.left = null;
		this.right = null;
	}

	public T getElement() {
		return this.element;
	}

	public void setElement(T element) {
		this.element = element;
	}

	public int numChildren() {
		int children = 0;
		if (this.left != null) {
			children = 1 + this.left.numChildren();
		}

		if (this.right != null) {
			children = children + 1 + this.right.numChildren();
		}
		return children;
	}

}
