package entities;

import lib.DoubleLinkedOrderedList;
import lib.LinkedList;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

public class Room implements Comparable<Room> {

	private final String name;
	private boolean enemies_in_room;

    public Room(String name) {
		this.name = name;

    }

	public String getName()  {
		return this.name;
	}

	public void setEnemies(boolean enemies_in_room) {
		this.enemies_in_room = enemies_in_room;
	}

	public boolean hasEnemies() {
		return enemies_in_room;
	}

	@Override
	public int compareTo(Room other) {
		return this.name.compareTo(other.name);
	}

	public String toString() {
		return "Room name: " + this.name;
	}

}
