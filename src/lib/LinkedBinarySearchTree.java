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
            BinaryTreeNode<T> current = this.getRoot();
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
        this.setCount(getCount()+1);
    }

    @Override
    public T removeElement(T targetElement) throws ElementNotFoundException {

        T result = null;

        if (!this.isEmpty()) {
            if (targetElement.equals(this.getRootElement())) {
                result = this.getRootElement();
                this.setRoot(this.replacement(getRoot()));
                this.setCount(getCount()-1);
            } else {
                BinaryTreeNode<T> current, parent = this.getRoot();
                boolean found = false;

                if (((Comparable) targetElement).compareTo(this.getRootElement()) < 0) {
                    current = this.getRoot().getLeft();
                } else {
                    current = this.getRoot().getRight();
                }
                while (current != null && !found) {
                    if (targetElement.equals(current.getElement())) {
                        found = true;
                        this.setCount(getCount()-1);
                        result = current.getElement();
                        if (current == parent.getLeft()) {
                            parent.setLeft(this.replacement(current));
                        } else {
                            parent.setRight(this.replacement(current));
                        }
                    } else {
                        parent = current;
                        if (((Comparable) targetElement).compareTo(current.getElement()) < 0) {
                            current = current.getLeft();
                        } else {
                            current = current.getRight();
                        }
                    }
                }
                if (!found) {
                    throw new ElementNotFoundException("Element not in Binary Search Tree");
                }
            }
        }
        return result;
    }

    protected BinaryTreeNode<T> replacement(BinaryTreeNode<T> node) {
        BinaryTreeNode<T> result = null;

        if ((node.getLeft() == null) && (node.getRight() == null)) {
            result = null;
        } else if ((node.getLeft() != null) && (node.getRight() == null)) {
            result = node.getLeft();
        } else if ((node.getLeft() == null) && (node.getRight() != null)) {
            result = node.getRight();
        } else {
            BinaryTreeNode<T> current = node.getRight();
            BinaryTreeNode<T> parent = node;
            while (current.getLeft() != null) {
                parent = current;
                current = current.getLeft();
            }
            if (node.getRight() == current) {
                current.setLeft(node.getLeft());
            } else {
               parent.setLeft(current.getRight());
               current.setRight(node.getRight());
               current.setLeft(node.getLeft());
            }
            result = current;
        }

        return result;

    }

    public void removeAllOccurences(T targetElement) throws EmptyCollectionException, ElementNotFoundException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Binary Search Tree is empty");
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
                throw new ElementNotFoundException("Element not in Binary Search Tree");
            }
        }
    }

    @Override
    public T removeMin() throws EmptyCollectionException, ElementNotFoundException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Binary Tree is empty");
        }

        T min = this.findMin();


        try {
            this.removeElement(min);
        } catch (ElementNotFoundException e) {
            // Logger.getLogger(LinkedBinarySearchTree.class.getName()).log(Level.SEVERE, null, ex);
            // System.err.println(e.getMessage());
            throw new ElementNotFoundException("Element not in Binary Search Tree");
        }

        return min;
    }

    @Override
    public T removeMax() throws EmptyCollectionException, ElementNotFoundException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Binary Tree is empty");
        }

        T max = this.findMax();

        try {
            this.removeElement(max);
        } catch (ElementNotFoundException ex) {
            // Logger.getLogger(LinkedBinarySearchTree.class.getName()).log(Level.SEVERE, null, ex);
            throw new ElementNotFoundException("Element not in Binary Search Tree");
        }

        return max;
    }

    @Override
    public T findMin() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Binary tree");
        }

        BinaryTreeNode<T> current = this.getRoot();

        while(current.getLeft() != null) {
            current = current.getLeft();
        }

        return current.getElement();
    }

    @Override
    public T findMax() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("Binary Search Tree is empty");
        }

        BinaryTreeNode<T> current = this.getRoot();

        while(current.getRight() != null) {
            current = current.getRight();
        }

        return current.getElement();
    }

}
