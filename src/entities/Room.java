package entities;

import lib.DoubleLinkedOrderedList;
import lib.ArrayUnorderedList;
import lib.DoubleNode;

public class Room extends DoubleNode<Room> implements Comparable<Room> {

	private final String name;
	private final ArrayUnorderedList<Enemy> enemies;
	private final DoubleLinkedOrderedList<Item> items;

    public Room(String name) {
        super(null);
		this.name = name;
		this.enemies = new ArrayUnorderedList<Enemy>();
		this.items = new DoubleLinkedOrderedList<Item>();
    }

	public String getName() {
		return this.name;
	}

	public void addEnemy(Enemy enemy) {
		this.enemies.addToRear(enemy);
	}

	public ArrayUnorderedList<Enemy> getEnemies() {
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
