package entities;


public class Room implements Comparable<Room> {

	private final String name;
	private boolean enemiesInRoom;
	private boolean itemsInRoom;
	private int totalEnemies;
	private int totalItems;

    public Room(String name) {
		this.name = name;
		this.totalEnemies = 0;
		this.totalItems = 0;
    }


	public String getName()  {
		return this.name;
	}

	public void setEnemies(boolean enemiesInRoom) {
		this.enemiesInRoom = enemiesInRoom;
	}

	public void setItemsInRoom(boolean itemsInRoom) {
		this.itemsInRoom = itemsInRoom;
	}

	public void addEnemy() {
		this.totalEnemies++;
	}

	public void removeEnemy(){
		this.totalEnemies--;
	}

	public void addItem() {
		this.totalItems++;
	}

	public void removeItem() {
		this.totalItems--;
	}

	public int getTotalEnemies() {
		return this.totalEnemies;
	}

	public int getTotalItems() {
		return this.totalItems;
	}

	public boolean hasItems() {
		return this.itemsInRoom;
	}

	public boolean hasEnemies() {
		return enemiesInRoom;
	}

	@Override
	public int compareTo(Room other) {
		return this.name.compareTo(other.name);
	}

	public String toString() {
		return this.name;
	}


}
