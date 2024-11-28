package entities;

public class Player extends Character {

	/**
	 * Represents the current position of the player in the game.
	 * This variable holds a reference to the Room object where the player is currently located.
	 */
	private Room current_position;

	/**
	 * Represents the player's backpack.
	 * This variable holds a reference to the BackPack object containing
	 * the items the player has collected in the game.
	 */
	private BackPack back_pack;

	/**
	 * Constructs a new Player with the specified name, fire power, current position, and backpack.
	 *
	 * @param name the name of the player
	 * @param fire_power the fire power of the player
	 * @param current_position the current position of the player in the game
	 * @param back_pack the backpack of the player containing collected items
	 */
	public Player(String name, int fire_power, Room current_position, BackPack back_pack) {
		super(name, fire_power);
		this.current_position = current_position;
		this.back_pack = back_pack;
	}

	/**
	 * Retrieves the current position of the player in the room.
	 *
	 * @return the current position of the player in the room
	 */
	public Room getCurrentPosition() {
		return this.current_position;
	}

	/**
	 * Sets the current position of the player in the specified room.
	 *
	 * @param position the new room where the player will be positioned
	 */
	public void setCurrentPosition(Room position) {
		this.current_position = position;
	}

	/**
	 * Executes an attack on the specified character. The attack will reduce the health
	 * of the targeted character based on the attacker's fire power.
	 *
	 * @param character the character to attack
	 */
	@Override
	public void attack(Character character) {

	}

}
