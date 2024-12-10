package game;

import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

public interface GameMode {
    public void play() throws ElementNotFoundException, EmptyCollectionException;
}
