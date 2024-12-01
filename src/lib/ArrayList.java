package lib;


import lib.exceptions.EmptyCollectionException;
import lib.interfaces.ListADT;
import java.util.Iterator;

public abstract class ArrayList<T> implements ListADT<T> {

    private static final int INITIAL_CAPACITY = 10;

    private static final int EXPANSION_FACTOR = 10;

    protected T[] list;
    protected int count;

    public ArrayList() {
        this.list = (T[]) (new Object[INITIAL_CAPACITY]);
        this.count = 0;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The List Is Empty");
        }

        T element = this.list[0];

        for (int i = 0; i < this.count - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[--this.count] = null;
        return element;
    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The List Is Empty");
        }

        T element = this.list[this.count - 1];
        this.list[--this.count] = null;

        return element;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (isEmpty()) {
            throw new EmptyCollectionException("The List Is Empty");
        }

        int index = this.find(element);

        if (index == -1) {
            throw new EmptyCollectionException("The Element Does Not Exists");
        }

        T elementToRemove = this.list[index];

        for (int i = index; i < this.count - 1; i++) {
            this.list[i] = this.list[i + 1];
        }

        this.list[this.count--] = null;
        return elementToRemove;
    }

    @Override
    public T first() {
        return this.list[0];
    }

    @Override
    public T last() {
        return this.list[this.count - 1];
    }

    @Override
    public boolean contains(T target) {
        boolean contains = false;

        for (T element : this.list) {
			if (element.equals(target)) {
				contains = true;
				break;
			}
        }

        return contains;
    }

    public T getElement(int index) {
        T element = null;

        for (int i = 0; i < this.count; i++) {
            if (i == index) {
                element = this.list[i];
                break;
            }
        }

        return element;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    private int find(T element) {
        int position = -1;

        for (int i = 0; i < this.count; i++) {
            if (this.list[i].equals(element)) {
                position = i;
                break;
            }
        }

        return position;
    }


    @Override
    public Iterator<T> iterator() {
        return new ArrayIterator<>(this.list, this.count);
    }

    protected void expandCapacity() {
        T[] temp = (T[]) (new Object[this.count * EXPANSION_FACTOR]);
        System.arraycopy(this.list, 0, temp, 0, this.count);
        this.list = temp;
    }

    public String toString() {
        String s = "";

        for (int i = 0; i < this.count; i++) {
            s += this.list[i] + "\n";
        }

        return s;
    }
}
