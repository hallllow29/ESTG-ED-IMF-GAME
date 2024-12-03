import entities.Enemy;
import entities.Item;
import entities.Room;
import entities.Target;
import lib.Graph;
import lib.LinkedList;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

public class Mission {

	private final String code;
	private final int version;
	private Target target;
	private Room entryPoint;

	private final Graph<Room> battlefield;
	private final LinkedList<Enemy> enemies;
	private final LinkedList<Item> items;
	private final LinkedList<Room> entry_exit_points;

	public Mission(String code, int version, Graph<Room> battlefield) {
		this.code = code;
		this.version = version;
		this.battlefield = battlefield;
		this.enemies = new LinkedList<>();
		this.items = new LinkedList<>();
		this.entry_exit_points = new LinkedList<>();
		this.target = null;
		this.bestPath = null;
	}

	public void setBestPath() {
		Iterator<Room> temp_path = null;


		for (Room entry_point : this.entry_exit_points) {
			try {

				if (!entry_point.hasEnemies()) {

					System.out.println("\nBEST PATH WITHOUT ENEMY");

					temp_path = this.battlefield.iteratorShortestPath(entry_point, target.getRoom());

					while (temp_path.hasNext()) {
						int enemy_counter = 0;

						Room tmp_room = temp_path.next();

						if (tmp_room.hasEnemies()) {

							for (Enemy enemy : enemies) {
								if (enemy.getCurrentPosition().getName().equals(tmp_room.getName())) {
									enemy_counter++;
								}
							}
							System.out.println("THIS ROOM " + tmp_room.getName() + " has " + enemy_counter);
						}
						System.out.println(tmp_room.getName());
					}
				}

			} catch (ElementNotFoundException e) {
				System.out.println(e.getMessage());
			}
			while (temp_path.hasNext()) {
				System.out.println(temp_path.next());
			}
		}

	}

	public void setEnemy(Enemy enemy) {
		this.enemies.add(enemy);
	}

	public void setItem(Item item) {
		this.items.add(item);
	}

	public void setEntryExitPoint(Room entry_exit_point) {
		this.entry_exit_points.add(entry_exit_point);
	}

	public void setTarget(Target target) {
		this.target = target;
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

	public Graph<Room> getBattlefield() {
		return this.battlefield;
	}

	public Target getTarget() {
		return this.target;
	}

	public void removeEnemy(Enemy enemy) throws EmptyCollectionException, ElementNotFoundException {
		this.enemies.remove(enemy);
	}

	public void removeItem(Item item) throws EmptyCollectionException, ElementNotFoundException {
		this.items.remove(item);
	}

}
