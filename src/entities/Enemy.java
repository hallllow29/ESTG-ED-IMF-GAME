package entities;

public class Enemy extends Character implements Comparable<Enemy> {

	private Room current_position;

	/**
	 * Constructs a new Enemy with the specified name, fire power, and current position.
	 *
	 * @param name the name of the enemy
	 * @param fire_power the fire power of the enemy
	 * @param current_position the current position of the enemy as a entities.Room object
	 */
	public Enemy(String name, int fire_power, Room current_position) {
		super(name, fire_power);
		this.current_position = current_position;
	}

	/**
	 * Retrieves the current position of the enemy.
	 *
	 * @return the current position of the enemy as a entities.Room object
	 */
	public Room getCurrentPosition() {
		return this.current_position;
	}

	public void setCurrentPosition(Room current_position) {
		this.current_position = current_position;
	}

	/**
	 * Executes an attack on the specified character, reducing their current health by the fire power
	 * of the attacking enemy. If the resulting health is less than 0, it will be set to 0.
	 *
	 * @param character the character to be attacked
	 */
	@Override
	public void attack(Character character) {

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
		return "Name: " + super.getName() + "\tFire Power: " + super.getFirePower() + "\t" + this.getCurrentPosition();
	}
}
