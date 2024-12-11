package entities;

import lib.exceptions.EmptyCollectionException;

public class Player extends Character {

	/**
	 * Represents the current position of the player in the game.
	 * This variable holds a reference to the entities.Room object where the player is currently located.
	 */
	private Room position;

	/**
	 * Represents the player's backpack.
	 * This variable holds a reference to the entities.BackPack object containing
	 * the items the player has collected in the game.
	 */
	private final BackPack back_pack;

	/**
	 * Constructs a new Player with the specified name, fire power, current position, and backpack.
	 *
	 * @param name the name of the player
	 * @param fire_power the fire power of the player
	 * @param position the current position of the player in the game
	 * @param back_pack the backpack of the player containing collected items
	 */

	private Kevlar kevlar;


	public Player(String name, int fire_power, BackPack back_pack) {
		super(name, fire_power);
		this.position = null;
		this.back_pack = back_pack;
		this.kevlar = null;
	}

	/**
	 * Retrieves the current position of the player in the room.
	 *
	 * @return the current position of the player in the room
	 */
	public Room getPosition() {
		return this.position;
	}

	/**
	 * Sets the current position of the player in the specified room.
	 *
	 * @param position the new room where the player will be positioned
	 */
	public void setPosition(Room position) {
		this.position = position;
	}

	/**
	 * Executes an attack on the specified character. The attack will reduce the health
	 * of the targeted character based on the attacker's fire power.
	 *
	 * @param //character the character to attack
	 */
	/*@Override
	public void attack(Character character) {

	}*/

	public String equipKevlar(Kevlar kevlar) {
		this.kevlar = kevlar;

		super.setCurrentHealth(super.getCurrentHealth() + this.kevlar.getExtraHp());

		return super.getName() + " equipped a kevlar! HP :" + super.getCurrentHealth();
	}

	public void addKitToBackPack(MediKit kit) {
		this.back_pack.addKit(kit);
	}

	public String useMediKit() throws EmptyCollectionException {
		boolean backpackEmpty = this.back_pack.isBackPackEmpty();

		if (backpackEmpty) {
			throw new EmptyCollectionException("There are no MediKits available to use!");
		}

		if (super.getCurrentHealth() >= 100 ) {
			return "You can't use medikits because your health is already full";
		}

		MediKit kit = back_pack.useKit();

		int newHealth = Math.min(super.getCurrentHealth() + kit.getHealPower(), 100);
		super.setCurrentHealth(newHealth);

		return "Medic kit used! HP: " + super.getCurrentHealth() + "/100";
 	}

	 public boolean hasRecoveryItem() {
		return !this.back_pack.isBackPackEmpty();
	 }

	 public BackPack  getBack_pack() {
		return this.back_pack;
	 }


}