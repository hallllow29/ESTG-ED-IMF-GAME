package entities;

public class Kevlar extends Item {

	/**
	 * The amount of extra health points provided by the entities.Kevlar item.
	 */
	private final int extra_hp;

	/**
	 * Constructs a new entities.Kevlar item with the specified name, position, and extra health points.
	 *
	 * @param name the name of the entities.Kevlar item.
	 * @param position the initial position of the entities.Kevlar item as a entities.Room object.
	 * @param extra_hp the extra health points provided by the entities.Kevlar item.
	 */
	public Kevlar(String name, Room position, int extra_hp) {
		super(name, position);
		this.extra_hp = extra_hp;
	}

	/**
	 * Retrieves the extra health points provided by the entities.Kevlar item.
	 *
	 * @return the extra health points as an integer.
	 */
	public int getExtraHp() {
		return extra_hp;
	}

	@Override
	public int getItemValue() {
		return this.extra_hp;
	}
}
