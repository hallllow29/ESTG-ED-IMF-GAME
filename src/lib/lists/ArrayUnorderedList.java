/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package lib.lists;

import lib.interfaces.UnorderedListADT;

import java.util.NoSuchElementException;

/**
 *
 * @author pedro
 */
public class ArrayUnorderedList<T> extends ArrayList<T> implements UnorderedListADT<T> {

    @Override
    public void addToFront(T element) {
        if (this.count == this.list.length) {
            this.expandCapacity();
        }

        for (int i = this.count; i > 0; i--) {
            this.list[i] = this.list[i - 1];
        }

        this.list[0] = element;

        this.count++;
    }

    @Override
    public void addToRear(T element) {
        if (this.count == this.list.length) {
            this.expandCapacity();
        }

        this.list[this.count++] = element;
    }

    @Override
    public void addAfter(T element, T target) {
        if (this.count == this.list.length) {
            this.expandCapacity();
        }

        int index = -1;

        for (int i = 0; i < this.count; i++) {
            if (this.list[i].equals(target)) {
                index = i;
                break;
            }
        }

        if (index == -1) {
            throw new NoSuchElementException("Element no found");
        }

        for (int j = this.count; j > index + 1; j--) {
            this.list[j] = this.list[j - 1];
        }

        this.list[index + 1] = element;

        this.count++;

    }

}
