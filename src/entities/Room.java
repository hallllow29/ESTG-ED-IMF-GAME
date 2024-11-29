package entities;

import lib.DoubleLinkedOrderedList;

public class Room implements Comparable<Room> {

	private final String name;
	private DoubleLinkedOrderedList<Enemy> enemies;
	private DoubleLinkedOrderedList<Item> items;


    public Room(String name) {
        this.name = name;
		this.enemies = new DoubleLinkedOrderedList<Enemy>();
		this.items = new DoubleLinkedOrderedList<Item>();
    }

	public String getName() {
		return this.name;
	}

	public void addEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}

	public DoubleLinkedOrderedList<Enemy> getEnemies() {
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
}
