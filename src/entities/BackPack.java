package entities;

import lib.stacks.ArrayStack;
import lib.exceptions.EmptyCollectionException;

public class BackPack {

    private final int maxCapacity;
    private final ArrayStack<MediKit> back_pack;

    public BackPack(int maxCapacity) {
        this.back_pack = new ArrayStack<>(maxCapacity);
        this.maxCapacity = maxCapacity;
    }

    public void addKit(MediKit kit) {
        if (back_pack.size() >= maxCapacity) {
            System.out.println("BackPack is full! Can't add more kits!");
            return;
        }

        this.back_pack.push(kit);
    }

    public MediKit useKit() throws EmptyCollectionException {
        return this.back_pack.pop();
    }

    public boolean isBackPackEmpty() {
        return this.back_pack.isEmpty();
    }

    public int numberOfKits() {
        return this.back_pack.size();
    }

    public String toString() {
        return "BackPack:\n" + back_pack;
    }

    public ArrayStack<MediKit> getListItems() {
        return this.back_pack;
    }

    public boolean isBackPackFull() {
        return this.back_pack.size() == maxCapacity;
    }

    public int getMaxCapacity() {
        return this.maxCapacity;
    }

}
