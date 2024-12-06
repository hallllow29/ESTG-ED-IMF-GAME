package entities;

public abstract class Item implements Comparable<Item> {

	/**
	 * The name of the item.
	 */
	private final String name;

	/**
	 * The current position of the item represented as a entities.Room object.
	 */
	private Room position;

	/**
	 * Constructs a new entities.Item with the specified name and position.
	 *
	 * @param name the name of the item.
	 * @param position the initial position of the item as a entities.Room object.
	 */
	public Item(String name, Room position) {
		this.name = name;
		this.position = position;
	}

	/**
	 * Retrieves the name of the item.
	 *
	 * @return the name of the item as a String.
	 */
	public abstract String getName();

	/**
	 * Retrieves the current position of the item.
	 *
	 * @return the current position of the item as a entities.Room object.
	 */
	public Room getPosition() {
		return this.position;
	}

	/**
	 * Sets the current position of the item.
	 *
	 * @param position the new position of the item represented as a entities.Room object.
	 */
	public void setPosition(Room position) {
		this.position = position;
	}

	public abstract int getItemValue();

	public int compareTo(Item other) {
		return Integer.compare(this.getItemValue(), other.getItemValue());
	}

	public String toString() {
		// return "Name: " + this.name +
		// 		"\tPosition: " + this.position;
		return "Name: " + this.name + " " + this.position;


	}


}
