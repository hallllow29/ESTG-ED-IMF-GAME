package entities;

public abstract class Character {

	/**
	 * The name of the character.
	 * This variable stores the unique identifier or title designated to the character.
	 */
	private final String name;

	/**
	 * Represents the current health of the character.
	 * This value indicates how much health the character has remaining.
	 */
	private int current_health;

	/**
	 * Represents the offensive capability or attack strength of a character.
	 * This value determines the damage a character can inflict on opponents.
	 */
	private int fire_power;

	/**
	 * Constructs a new Character with the specified name and fire power.
	 *
	 * @param name the name of the character
	 * @param fire_power the fire power of the character
	 */
	public Character(String name, int fire_power) {
		this.name = name;
		this.current_health = 100;
		this.fire_power = fire_power;
	}

	/**
	 * Retrieves the name of the character.
	 *
	 * @return the name of the character
	 */
	public String getName() {
		return this.name;
	}

	/**
	 * Retrieves the current health of the character.
	 *
	 * @return the current health value of the character
	 */
	public int getCurrentHealth() {
		return this.current_health;
	}

	/**
	 * Retrieves the fire power of the character.
	 *
	 * @return the fire power value of the character
	 */
	public int getFirePower() {
		return this.fire_power;
	}


	/**
	 * Sets the current health of the character.
	 *
	 * @param current_health the new current health value to set
	 */
	public void setCurrentHealth(int current_health) {
		this.current_health = current_health;
	}

	/**
	 * Sets the fire power of the character.
	 *
	 * @param fire_power the new fire power value to set
	 */
	public void setFirePower(int fire_power) {
		this.fire_power = fire_power;
	}

	/**
	 * Reduces the current health of the character based on the damage received.
	 * If the resulting health is less than 0, it will be set to 0.
	 *
	 * @param damage the amount of damage to inflict on the character
	 */
	public void takeDamage(int damage) {
		this.current_health -= damage;

		if (this.current_health < 0) {
			this.current_health = 0;
		}
	}

	/**
	 * Checks if the character is alive based on their current health.
	 *
	 * @return true if the character's current health is greater than 0, otherwise false
	 */
	public boolean isAlive() {
		return this.current_health > 0;
	}

	/**
	 * Executes an attack on the specified character. This method should be
	 * implemented by subclasses to define the specific attack behavior.
	 *
	 * @param character the character to attack
	 */
	public abstract void attack(Character character);


}
