package lib;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.IteratorADT;
import lib.interfaces.OrderedListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayOrderedList<T> extends ArrayList<T> implements OrderedListADT<T> {

    @Override
    public void add(T element) {
        if (!(element instanceof Comparable)) {
            throw new IllegalArgumentException("Element must be Comparable.");
        }

        if (this.count == this.list.length) {
            this.expandCapacity();
        }

        Comparable<T> comparableElement = (Comparable<T>) element;

        int position = 0;
        while (position < this.count && comparableElement.compareTo(this.list[position]) > 0) {
            position++;
        }

        for (int i = this.count; i > position; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[position] = element;
        this.count++;
    }

}