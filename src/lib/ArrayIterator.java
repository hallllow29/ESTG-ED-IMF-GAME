
package lib;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author pedro
 */
public class ArrayIterator<T> implements Iterator<T> {

    private int size;
    private int current;
    private T[] items;

    public ArrayIterator(T[] items, int size) {
        this.items = items;
        this.size = size;
        this.current = 0;
    }
    @Override
    public boolean hasNext() {
        return this.current < this.size;
    }

    @Override
    public T next() {
        if (!hasNext()) {
            throw new NoSuchElementException("No more elements to iterate");
        }

        return this.items[this.current++];
    }

}
