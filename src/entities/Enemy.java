package entities;

public class Enemy extends Character implements Comparable<Enemy> {

	private Room position;

	/**
	 * Constructs a new Enemy with the specified name, fire power, and current position.
	 *
	 * @param name the name of the enemy
	 * @param fire_power the fire power of the enemy
	 * @param position the current position of the enemy as a entities.Room object
	 */
	public Enemy(String name, int fire_power, Room position) {
		super(name, fire_power);
		this.position = position;
	}

	/**
	 * Retrieves the current position of the enemy.
	 *
	 * @return the current position of the enemy as a entities.Room object
	 */
	public Room getPosition() {
		return this.position;
	}

	public void setPosition(Room position) {
		this.position = position;
	}

	@Override
	public int compareTo(Enemy other) {
		return Integer.compare(super.getFirePower(), other.getFirePower());
	}

	/**
	 * Returns a string representation of the enemy, including the name and fire power.
	 *
	 * @return a string containing the name and fire power of the enemy
	 */
	@Override
	public String toString(){
		return "Name: " + super.getName() + " Fire Power: " + super.getFirePower() + " Position: " + this.getPosition();
	}
}
