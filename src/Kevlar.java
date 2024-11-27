public class Kevlar extends Item {

	/**
	 * The amount of extra health points provided by the Kevlar item.
	 */
	private final int extra_hp;

	/**
	 * Constructs a new Kevlar item with the specified name, position, and extra health points.
	 *
	 * @param name the name of the Kevlar item.
	 * @param position the initial position of the Kevlar item as a Room object.
	 * @param extra_hp the extra health points provided by the Kevlar item.
	 */
	public Kevlar(String name, Room position, int extra_hp) {
		super(name, position);
		this.extra_hp = extra_hp;
	}

	/**
	 * Retrieves the extra health points provided by the Kevlar item.
	 *
	 * @return the extra health points as an integer.
	 */
	public int getExtraHp() {
		return extra_hp;
	}
}
