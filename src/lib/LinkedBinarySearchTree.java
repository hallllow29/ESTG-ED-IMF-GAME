package lib;

import lib.interfaces.BinarySearchTreeADT;

import lib.exceptions.EmptyCollectionException;
import lib.exceptions.ElementNotFoundException;

import java.util.Iterator;

public class LinkedBinarySearchTree<T> extends LinkedBinaryTree<T> implements BinarySearchTreeADT<T> {

    public LinkedBinarySearchTree() {
        super();
    }

    @Override
    public void addElement(T element) {
        BinaryTreeNode<T> temp = new BinaryTreeNode<T>(element);
        Comparable<T> comparableElement = (Comparable<T>) element;

        if (this.isEmpty()) {
            this.setRoot(temp);
        } else {
            // BinaryTreeNode<T> current = this.root;
            BinaryTreeNode<T> current = (BinaryTreeNode<T>) this.getRoot();
            // TODO: ASK THIS.

            boolean added = false;
            while (!added) {
                /* O comparableElement.compareTo(current.element) < 0 é usado para decidir em que direção a árvore deve crescer.
                Se compareTo retornar um valor negativo , isso significa que element é menor do que current.element, e o código deve
                posiciona-lo na subárvore esquerda. Se retornar um valor positivo, ele vai para a subárvore direita.
                 */
                if (comparableElement.compareTo(current.getElement()) < 0) {
                    if (current.getLeft() == null) {
                        current.setLeft(temp);
                        added = true;
                    } else {
                        current = current.getLeft();
                    }
                } else {
                    if (current.getRight() == null) {
                        current.setRight(temp);
                        added = true;
                    } else {
                        current = current.getRight();
                    }
                }
            }
        }

        // this.getCount++;

    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {

        T result = null;

        if (!this.isEmpty()) {
            if (targetElement.equals(this.root.element)) {
                result = this.root.element;
                this.root = this.replacement(root);
                this.count--;
            } else {
                BinaryTreeNode<T> current, parent = this.root;
                boolean found = false;

                if (((Comparable) targetElement).compareTo(root.element) < 0) {
                    current = this.root.left;
                } else {
                    current = this.root.right;
                }
                while (current != null && !found) {
                    if (targetElement.equals(current.element)) {
                        found = true;
                        this.count--;
                        result = current.element;
                        if (current == parent.left) {
                            parent.left = this.replacement(current);
                        } else {
                            parent.right = this.replacement(current);
                        }
                    } else {
                        parent = current;
                        if (((Comparable) targetElement).compareTo(current.element) < 0) {
                            current = current.left;
                        } else {
                            current = current.right;
                        }
                    }
                }
                if (!found) {
                    throw new ElementNotFoundException("binary search tree");
                }
            }
        }

        return result;

    }

    protected BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> result = null;

        if ((node.left == null) && (node.right == null)) {
            result = null;
        } else if ((node.left != null) && (node.right == null)) {
            result = node.left;
        } else if ((node.left == null) && (node.right != null)) {
            result = node.right;
        } else {
            BinaryTreeNode<T> current = node.right;
            BinaryTreeNode<T> parent = node;
            while (current.left != null) {
                parent = current;
                current = current.left;
            }
            if (node.right == current) {
                current.left = node.left;
            } else {
                parent.left = current.right;
                current.right = node.right;
                current.left = node.left;
            }
            result = current;
        }

        return result;

    }

    @Override
    public void removeAllOccurrences(T targetElement) throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("binare tree");
        }

        Comparable<T> compareElement = (Comparable<T>) targetElement;
        Iterator<T> iter = super.iteratorInOrder();
        int numberOfElements = 0;

        while (iter.hasNext()) {
            if (compareElement.compareTo(iter.next()) == 0) {
                numberOfElements++;
            }
        }

        for (int i = 0; i < numberOfElements; i++) {
            try {
                this.removeElement(targetElement);
            } catch (ElementNotFoundException ex) {
            }
        }
    }

    @Override
    public T removeMin() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("binary tree");
        }

        T min = this.findMin();

        try {
            this.removeElement(min);
        } catch (ElementNotFoundException ex) {
            Logger.getLogger(LinkedBinarySearchTree.class.getName()).log(Level.SEVERE, null, ex);
        }

        return min;
    }

    @Override
    public T removeMax() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("binary tree");
        }

        T max = this.findMax();

        try {
            this.removeElement(max);
        } catch (ElementNotFoundException ex) {
            Logger.getLogger(LinkedBinarySearchTree.class.getName()).log(Level.SEVERE, null, ex);
        }

        return max;
    }

    @Override
    public T findMin() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Binary tree");
        }

        BinaryTreeNode<T> current = this.root;

        while(current.left != null) {
            current = current.left;
        }

        return current.element;
    }

    @Override
    public T findMax() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("binary tree");
        }

        BinaryTreeNode<T> current = this.root;

        while(current.right != null) {
            current = current.right;
        }

        return current.element;
    }

}
