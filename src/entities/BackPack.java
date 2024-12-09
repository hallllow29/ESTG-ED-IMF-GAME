package entities;

import lib.stacks.ArrayStack;
import lib.exceptions.EmptyCollectionException;

public class BackPack {


    private final ArrayStack<MediKit> back_pack;


    public BackPack() {
        this.back_pack = new ArrayStack<>();
    }

    public void addKit(MediKit kit) {
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

}
