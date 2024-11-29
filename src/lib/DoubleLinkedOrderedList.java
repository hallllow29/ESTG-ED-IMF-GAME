package lib;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import lib.exceptions.EmptyCollectionException;
import lib.interfaces.OrderedListADT;

import java.util.Iterator;
import java.util.NoSuchElementException;

/**
 *
 * @author pedro
 */
public class DoubleLinkedOrderedList<T extends Comparable<T>> implements OrderedListADT<T> {

    private int count;
    private DoubleNode<T> head;
    private DoubleNode<T> tail;

    public DoubleLinkedOrderedList() {
        this.head = this.tail = null;
        this.count = 0;
    }

    @Override
    public void add(T element) {
        DoubleNode<T> newNode = new DoubleNode<>(element);

        if (this.head == null) { // Lista vazia
            this.head = newNode;
            this.tail = newNode;
        } else {
            DoubleNode<T> current = head;

            while (current != null && current.getElement().compareTo(element) < 0) {
                current = current.getNext();
            }

            // Se for para adicionar no início da lista
            if (current == this.head) {
                newNode.setNext(this.head);
                this.head.setPrev(newNode);
                this.head = newNode;

                // Se for para adicionar no fim
            } else if (current == null) {
                this.tail.setNext(newNode);
                newNode.setPrev(this.tail);
                this.tail = newNode;

                // Se for para adicionar no meio
            } else {
                newNode.setNext(current);
                newNode.setPrev(current.getPrev());
                if (current.getPrev() != null) {
                    current.getPrev().setNext(newNode);
                }
                current.setPrev(newNode);
            }
        }

        this.count++;
    }

    @Override
    public T removeFirst() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        T data = this.head.getElement();

        this.head = head.getNext();

        if (this.head != null) {
            this.head.setPrev(null);
        } else {
            this.tail = null;
        }

        this.count--;

        return data;

    }

    @Override
    public T removeLast() throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        T data = this.tail.getElement();

        this.tail = this.tail.getPrev();

        if (this.tail != null) {
            this.tail.setNext(null);
        } else {
            this.head = null;
        }

        this.count--;

        return data;
    }

    @Override
    public T remove(T element) throws EmptyCollectionException {
        if (this.isEmpty()) {
            throw new EmptyCollectionException("List is empty");
        }

        DoubleNode<T> current = this.head;

        while (current != null && !current.getElement().equals(element)) {
            current = current.getNext();
        }

        if (current == null) {
            throw new NoSuchElementException("Element not found");
        }

        if (current.getPrev() != null) {
            current.getPrev().setNext(current.getNext());

        } else {
            this.head = current.getNext();
        }

        if (current.getNext() != null) {
            current.getNext().setPrev(current.getPrev());
        } else {
            this.tail = current.getPrev();
        }

        this.count--;

        return current.getElement();

    }

    @Override
    public T first() {
        return this.head.getElement();
    }

    @Override
    public T last() {
        return this.tail.getElement();
    }

    @Override
    public boolean contains(T target) {
        DoubleNode<T> current = this.head;

        while (current != null) {
            if (current.getElement().equals(target)) {
                return true;
            }

            current = current.getNext();

        }

        return false;
    }

    @Override
    public boolean isEmpty() {
        return this.count == 0;
    }

    @Override
    public int size() {
        return this.count;
    }

    @Override
    public Iterator<T> iterator() {
        return new DoubleLinkedListIterator<>(this.head);
    }

    public DoubleLinkedOrderedList<T> reverse() {
        DoubleLinkedOrderedList<T> newList = new DoubleLinkedOrderedList<>();

        DoubleNode<T> current = this.tail;

        while (current != null) {
            newList.add(current.getElement());
            current = current.getPrev();
        }

        return newList;
    }

    public String toString() {
        StringBuilder string = new StringBuilder();

        DoubleNode<T> current = head;

        if (current == null) {
            return "Lista vazia";
        }

        while (current != null) {
            string.append(current.getElement().toString());

            current = current.getNext();

            if (current != null) {
                string.append(" -> ");
            }
        }

        return string.toString();

    }
}
