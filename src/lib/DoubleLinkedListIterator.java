package lib;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author pedro
 */
public class DoubleLinkedListIterator<T> implements Iterator<T> {

    private DoubleNode<T> current;

    public DoubleLinkedListIterator(DoubleNode<T> head) {
        this.current = head;
    }

    @Override
    public boolean hasNext() {
        return current != null;
    }

    @Override
    public T next() {
        if (!this.hasNext()) {
            throw new NoSuchElementException("No more elements on the list");
        }

        T element = this.current.getElement();

        current = current.getNext();

        return element;
    }


}
