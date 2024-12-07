import entities.Enemy;
import entities.Item;
import entities.Room;
import entities.Target;
import lib.Graph;
import lib.LinkedList;
import lib.Network;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;

import java.util.Iterator;

public class Mission {

	private final String code;
	private final int version;
	private Target target;
	private Room entryPoint;
	private boolean targetSecured;

	private final Network<Room> battlefield;
	private final LinkedList<Enemy> enemies;
	private final LinkedList<Item> items;
	private final LinkedList<Room> entry_exit_points;

	public Mission(String code, int version, Network<Room> battlefield) {
		this.code = code;
		this.version = version;
		this.battlefield = battlefield;
		this.enemies = new LinkedList<>();
		this.items = new LinkedList<>();
		this.entry_exit_points = new LinkedList<>();
		this.target = null;
		this.targetSecured = false;
	}

	public void setEnemy(Enemy enemy) throws NotElementComparableException {
		this.enemies.add(enemy);
	}

	public void setItem(Item item) throws NotElementComparableException {
		this.items.add(item);
	}

	public void setEntryExitPoint(Room entry_exit_point) throws NotElementComparableException {
		this.entry_exit_points.add(entry_exit_point);
	}

	public void setTarget(Target target) {
		this.target = target;
	}

	public void setTargetSecured(boolean targetSecured) {
		this.targetSecured = targetSecured;
	}

	public LinkedList<Enemy> getEnemies() {
		return this.enemies;
	}

	public LinkedList<Item> getItems() {
		return this.items;
	}

	public LinkedList<Room> getEntryExitPoints() {
		return this.entry_exit_points;
	}

	public Network<Room> getBattlefield() {
		return this.battlefield;
	}

	public Target getTarget() {
		return this.target;
	}

	public boolean isTargetSecured() {
		return this.targetSecured;
	}

	public void removeEnemy(Enemy enemy) throws EmptyCollectionException, ElementNotFoundException {

	}

	public void removeItem(Item item) throws EmptyCollectionException, ElementNotFoundException {
		this.items.remove(item);
	}

}
