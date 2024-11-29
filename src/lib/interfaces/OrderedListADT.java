package lib.interfaces;/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */


/**
 *
 * @author pedro
 */
public interface OrderedListADT<T> extends ListADT<T> {

    /**
     * Adds the specified element to this list at the proper location
     *
     * @param element the element to be added to this list
     */
    public void add(T element);
}
