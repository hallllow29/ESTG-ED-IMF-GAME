package lib;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.IteratorADT;
import lib.interfaces.OrderedListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayOrderedList <T> implements OrderedListADT<T> {

    private static final int INITIAL_CAPACITY = 4;
    private T[] array;
    private int size;
    private T front;
    private T rear;

    public ArrayOrderedList() {
        this.array = (T[]) new Object[INITIAL_CAPACITY];
        this.size = 0;
        this.front = null;
        this.rear = null;
    }

    @Override
    public void add(T element) {
        if (isFull()) {
            int newCapacity = this.size() * 2;
            T[] newArray = (T[]) new Object[newCapacity];

            for (int counter = 0; counter < size; counter++) {
                newArray[counter] = this.array[counter];
            }

            this.array = null;
            this.array = newArray;
        }

        if (isEmpty()) {
            this.front = element;
            this.rear = element;
            this.array[0] = element;
        } else {
            int insert = 0;
            while (insert < size && ((Comparable<T>) this.array[insert]).compareTo(element) < 0) {
                insert++;
            }

            // Shifting.
            for (int counter = size; counter > insert; counter--) {
                this.array[counter] = this.array[counter - 1];
            }

            this.array[insert] = element;
            front = array[0];
            rear = array[size];
        }

        size++;
    }
    @Override
    public T remove(T element) throws EmptyCollectionException, ElementNotFoundException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty.");
        }

        T remove = null;
        boolean found = false;
        int removeIndex = -1;

        // Loop to find element to be removed.
        for (int counter = 0; counter < size; counter++) {
            if (this.array[counter].equals(element)) {
                remove = this.array[counter];
                found = true;
                removeIndex = counter;
                break;
            }
        }

        if (!found) {
            throw new ElementNotFoundException("Element not found in the list.");
        }

        // Shifting.
        for (int counter = removeIndex; counter < size - 1; counter++) {
            this.array[counter] = this.array[counter + 1];
        }

        // Now trough shifting the last element should be the removed one
        this.array[size - 1] = null;

        if (isEmpty()) {
            this.front = null;
            this.rear = null;
            this.size = 0;
        }

        this.size--;
        return remove;

    }
    @Override
    public T first() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty.");
        }
        return this.array[0];
    }
    @Override
    public T last() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty.");
        }
        return this.array[size - 1];
    }
    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty.");
        }

        T first = this.front;

        // Same procedure as in T remove(T element)

        // Shifting.
        for (int counter = 0; counter < size - 1; counter++) {
            this.array[counter] = this.array[counter + 1];
        }

        this.array[size - 1] = null;
        this.size--;
        this.front = this.array[0];
        return first;
    }
    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty.");
        }

        T last = this.rear;

        // No shifting

        this.array[size - 1] = null;
        this.size--;
        this.rear = this.array[size - 1];
        return last;
    }
    @Override
    public boolean contains(T element) throws EmptyCollectionException {

        if (isEmpty()) {
            throw new EmptyCollectionException("List is empty.");
        }

        for (T t : array) {
            if (t.equals(element)) {
                return true;
            }
        }
        return false;
    }
    @Override
    public boolean isEmpty() {
        return size <= 0;
    }

    private boolean isFull() {
        return size == this.array.length;
    }

    @Override
    public int size() {
        return this.size;
    }

    @Override
    public String toString() {
        if (isEmpty()) {
            return "List is empty.";
        }

        String result = "ArrayOrderedList: [";
        for (int counter = 0; counter < size; counter++) {
            result += array[counter];
            if (counter < size - 1) {
                result += ", ";
            }
        }
        result += "]";
        return result;
    }


    public Iterator<T> iterator() {
        return new ArrayIterator(this.array, this.size);
    }

    private class ArrayListIterator implements IteratorADT<T> {
        private int currentPosition = 0;

        @Override
        public boolean hasNext() {
            return this.currentPosition < size();
        }

        @Override
        public T next() {
            if (!hasNext()) {
                throw new NoSuchElementException("No more elements in the iteration.");
            }
            return array[currentPosition++];
        }

        @Override
        public void remove() {
            throw new UnsupportedOperationException("Remove operation is not supported.");
        }
    }

}