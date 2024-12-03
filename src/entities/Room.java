package entities;

import lib.DoubleLinkedOrderedList;
import lib.LinkedList;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

public class Room implements Comparable<Room> {

	private final String name;
	//private LinkedList<Enemy> enemies;
	private final DoubleLinkedOrderedList<Item> items;

    public Room(String name) {
		this.name = name;
		this.enemies = new LinkedList<Enemy>();
		this.items = new DoubleLinkedOrderedList<Item>();
    }

	public String getName() {
		return this.name;
	}

	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}

	public void removeEnemy(Enemy enemy) throws EmptyCollectionException, ElementNotFoundException {
		this.enemies.remove(enemy);
	}

	public LinkedList<Enemy> getEnemies() {
		return this.enemies;
	}

	public void addItem(Item item) {
		this.items.add(item);
	}

	public DoubleLinkedOrderedList<Item> getItems() {
		return this.items;
	}


	@Override
	public int compareTo(Room other) {
		return this.name.compareTo(other.name);
	}

	public String toString() {
		return "Room name: " + this.name;
	}

	public boolean hasEnemies() {
		return !this.enemies.isEmpty();
	}
}
