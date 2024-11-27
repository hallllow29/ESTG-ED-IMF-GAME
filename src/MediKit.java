public class MediKit extends Item {

	/**
	 * The amount of health points that the MediKit can restore.
	 */
	private final int heal_power;

	/**
	 * Constructs a new MediKit with the specified name, position, and healing power.
	 *
	 * @param name the name of the MediKit.
	 * @param position the initial position of the MediKit as a Room object.
	 * @param heal_power the healing power of the MediKit.
	 */
	public MediKit(String name, Room position, int heal_power) {
		super(name, position);
		this.heal_power = heal_power;
	}

	/**
	 * Retrieves the healing power of the MediKit.
	 *
	 * @return the healing power of the MediKit as an integer.
	 */
	public int getHealPower(){
		return this.heal_power;
	}

}
