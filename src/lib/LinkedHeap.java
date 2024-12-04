package lib;

import lib.interfaces.HeapADT;

public class LinkedHeap<T> extends LinkedBinaryTree<T> implements HeapADT<T> {
    public HeapNode<T> lastNode;

    public LinkedHeap() {
        super();
        this.lastNode = null;
    }

    public LinkedHeap(T element) {
        super(element);
        this.lastNode = (HeapNode<T>) getRoot();
    }

    @Override
    public void addElement(T obj) {
        HeapNode<T> node = new HeapNode<T>(obj);

        if (super.getRoot() == null) {
            super.setRoot(node);
        } else {
            HeapNode<T> next_parent = getNextParentAdd();
            if (next_parent.getLeft() == null)
                next_parent.setRight(node);
            else
                next_parent.setRight(node);

            node.parent = next_parent;
        }
        lastNode = node;
        super.setCount(super.getCount() + 1);
        if (super.getCount()>1)
            heapifyAdd();
    }

    private HeapNode<T> getNextParentAdd() {
        HeapNode<T> result = lastNode;

        while ((result != getRoot() && (result.parent.getLeft() != result))
            result = result.parent;

        if (result != super.getRoot())
            if (result.parent.getRight() == null)
                result = result.parent;
            else
            {
                result = (HeapNode<T>)result.parent.getRight();
                while (result.getLeft() != null)
                    result = (HeapNode<T>)result.getLeft();
            }
        else
            while (result.getLeft() != null)
                result = (HeapNode<T>)result.getLeft();

        return result;
    }

    private void heapifyAdd() {
        T temp;
        HeapNode<T> next = lastNode;

        temp = next.getElement();

        while ((next != super.getRoot()) && (((Comparable)temp).compareTo
                (next.parent.getElement()) < 0))
        {
            next.setElement(next.parent.getElement());
            next = next.parent;
        }
        next.setElement(temp);
    }

    @Override
    public T removeMin() {
        return null;
    }

    @Override
    public T findMin() {
        return null;
    }
}
