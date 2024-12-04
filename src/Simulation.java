import entities.*;
import lib.ArrayList;
import lib.ArrayUnorderedList;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Random;

public class Simulation {

	private final Mission mission;
	private final Player player;
	private boolean gameOver;
	// private final Room entry_point;

	public Simulation(Mission mission, Player player) {
		this.mission = mission;
		this.player = player;
		this.gameOver = false;
		// this.entry_point = entry_point;
	}

	/*public void game() throws EmptyCollectionException, ElementNotFoundException {
		Turn currentTurn = Turn.PLAYER;
		boolean gameOver = false;

		while(!gameOver) {
			switch(currentTurn) {
				case PLAYER:
					System.out.println("Player's turn");

			}
		}
	}*/
	/*private void playerTurn() {
		Room currentRoom = player.getCurrentPosition();
		System.out.println("Tó Cruz current position is " + currentRoom);

		if (currentRoom.hasEnemies()) {
			// cenario1;
		} else if (currentRoom.equals(mission.getTarget().getRoom())) {
			if (!currentRoom.hasEnemies()) {
				// cenario6
			} else {
				// cenario5
			}
		} else {
			// cenario2
		}
	}*/

	public void game() throws ElementNotFoundException, EmptyCollectionException {
		System.out.println("Game start");

		Room bestEntry = this.findBestEntryPoint();
		player.setCurrentPosition(bestEntry);
		System.out.println("Player enters at: " + bestEntry.getName());

		boolean foundTarget = false;

		while (!gameOver) {
			System.out.println("=== New Turn ===");

			playerTurn();

			if (!foundTarget && player.getCurrentPosition().equals(mission.getTarget().getRoom())) {
				System.out.println("Player reached the target room!");
				foundTarget = true;
			}

			if (foundTarget && mission.getEntryExitPoints().contains(player.getCurrentPosition())) {
				System.out.println("Player exited the building!");
				gameOver = true;
				break;
			}

			enemyTurn();

			if (!gameOver) {
				updateBestPath(foundTarget);
			}
		}

		System.out.println(player.getCurrentHealth() > 0 ? "Mission accomplished!" : "Mission failed");
	}
	public void enemyTurn() {
		System.out.println("Enemies turn!");

		Room playerCurrentPosition = this.player.getCurrentPosition();
		boolean enemiesEnteredPlayerRoom = false;

		for (Enemy enemy : mission.getEnemies()) {
			Room enemyCurrentPosition = enemy.getCurrentPosition();

			if (enemyCurrentPosition.equals(playerCurrentPosition)) {
				enemiesEnteredPlayerRoom = true;
				break;
			}
		}

		if (enemiesEnteredPlayerRoom) {
			scenario3();
		} else {
			moveEnemies();
		}
	}

	public void playerTurn() throws EmptyCollectionException, ElementNotFoundException {
		Room currentRoom = player.getCurrentPosition();
		System.out.println("Players turn. Current room " + currentRoom.getName());


		if (currentRoom.equals(mission.getTarget().getRoom())) {
			if (currentRoom.hasEnemies()) {
				scenario5();
			} else {
				scenario6();
			}
			return;
		}

		if (currentRoom.hasEnemies()) {
			scenario1();
		} else {
			if (playerUseRecoveryItem()) {
				scenario4();
			} else {
				scenario2();
			}
		}

	}

	/*private boolean checkEnemiesInPlayerRoom() {
		for (Enemy enemy : mission.getEnemies()) {
			if (enemy.getCurrentPosition().equals(player.getCurrentPosition())) {
				return true;
			}
		}

		return false;
	} */

	private boolean playerUseRecoveryItem() {
		int healthMissing = 30;

		return player.getCurrentHealth() <= healthMissing && player.hasRecoveryItem();
	}

	private void movePlayer() throws ElementNotFoundException {
		System.out.println("Next player move");

		Room currentRoom = player.getCurrentPosition();
		Room targetRoom = mission.getTarget().getRoom();

		Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(currentRoom, targetRoom);

		if (path.hasNext()) {
			path.next();
			if (path.hasNext()) {
				Room nextRoom = path.next();
				player.setCurrentPosition(nextRoom);
				System.out.println("Player moved to: " + nextRoom.getName());
			} else {
				System.out.println("No valid moves available from current path.");
			}
		} else {
			System.out.println("No valid path found.");
		}
	}

	public void attackEnemies(Iterator<Enemy> enemies) {
		String player_name = this.player.getName();
		Room player_current_position = this.player.getCurrentPosition();
		int player_damage = this.player.getFirePower();

		while (enemies.hasNext()) {
			Enemy current_enemy = enemies.next();
			Room enemy_current_position = current_enemy.getCurrentPosition();
			String current_enemy_name = current_enemy.getName();

			if (enemy_current_position.equals(player_current_position)) {
				current_enemy.takeDamage(player_damage);

				if (current_enemy.getCurrentHealth() <= 0) {
					System.out.println(player_name + " is attacking " + current_enemy_name);
					current_enemy.takeDamage(player_damage);
					enemies.remove();
					System.out.println("Enemy: " + current_enemy_name + " is dead");
				}
			}
		}
	}

	private void enemyAttack(Enemy enemy) {
		String enemy_current_name = enemy.getName();
		String player_name = this.player.getName();
		int player_current_health = this.player.getCurrentHealth();
		int enemy_damage = enemy.getFirePower();

		System.out.println("Enemy " + enemy_current_name + " is attacking " + player_name);
		this.player.setCurrentHealth(player_current_health - enemy_damage);
		System.out.println("Enemy deal " + enemy_damage + " damage " + player_name + " current HP is " + player_current_health);

		if (player_current_health <= 0) {
			this.gameOver = true;
		}
	}

	public void scenario1() {
		System.out.println("Tó cruz confronting enemies");

		attackEnemies(mission.getEnemies().iterator());

		if (!player.getCurrentPosition().hasEnemies()) {
			System.out.println("All enemies in the room are defeated");
		} else {
			System.out.println("Enemies remain. End player turn");
		}

	}

	public void scenario2() throws EmptyCollectionException, ElementNotFoundException {
		System.out.println("Room is clear. Checking for items....");
		this.checkForItems(player.getCurrentPosition());
		movePlayer();
	}

	private void scenario3() {
		System.out.println("Enemies have entered on the players room");

		for (Enemy enemy : mission.getEnemies()) {
			if (enemy.getCurrentPosition().equals(player.getCurrentPosition())) {
				enemyAttack(enemy);
			}
		}
		if (player.getCurrentHealth() <= 0) {
			System.out.println("Tó Cruz has been defeated. Game over.");
			gameOver = true;
		} else {
			System.out.println("Tó Cruz survived the attack. Player's turn will continue.");
		}
	}

	private void scenario4() throws EmptyCollectionException {
		System.out.println("To cruz is using medic kit ");
		player.useMediKit();

	}

	private void scenario5() throws EmptyCollectionException, ElementNotFoundException {
		System.out.println("To cruz as reached the target room" + mission.getTarget().getRoom().getName());

		attackEnemies(mission.getEnemies().iterator());

		if (!player.getCurrentPosition().hasEnemies()) {
			System.out.println("All enemies in the target room are defeated");
		} else {
			System.out.println("Enemies reamin in the room. Turn ends");
		}

	}

	private void scenario6() {
		System.out.println("Target found! Preparing to exit the building");
	}

	/**
	 * Moves each enemy in the mission to a new random room from the list of possible
	 * adjacent rooms.
	 * <p>
	 * This method iterates through all enemies, retrieves their current positions, and
	 * determines the possible rooms they can move to. Each enemy is then moved to one of
	 * the possible rooms chosen at random. The method handles the case where the current
	 * position stack is empty by catching an EmptyCollectionException.
	 */
	private void moveEnemies() {
		ArrayList<Room> possible_moves;

		Random random = new Random();

		for (Enemy enemyObj : this.mission.getEnemies()) {

			possible_moves = getPossibleMoves(enemyObj.getCurrentPosition());

			if (!possible_moves.isEmpty()) {

				int random_index = random.nextInt(possible_moves.size());

				Room next_room = possible_moves.getElement(random_index);

				enemyObj.setCurrentPosition(next_room);
			}
		}
	}

	/**
	 * Retrieves a list of possible rooms that can be moved to from the given room within
	 * a limited distance of 2 BFS levels.
	 * <p>
	 * This method performs a breadth-first search starting from the specified room and
	 * collects rooms at BFS levels 1 and 2, indicating possible adjacent rooms and those
	 * one step further away from the start room.
	 *
	 * @param from_room the starting room from which possible moves are to be determined
	 * @return an ArrayUnorderedList containing rooms that are reachable within 2 BFS
	 * levels
	 */
	private ArrayUnorderedList<Room> getPossibleMoves(Room from_room) {

		ArrayUnorderedList<Room> possible_moves = new ArrayUnorderedList<>();

		try {
			Iterator<Room> bfs_iterator = mission.getBattlefield().iteratorBFS(from_room);
			int bfs_level = 0;

			while (bfs_iterator.hasNext() && bfs_level <= 2) {

				Room to_room = bfs_iterator.next();

				if (bfs_level == 1 || bfs_level == 2) {

					if (to_room != null) {
						possible_moves.addToRear(to_room);
					}
				}

				bfs_level++;
			}

		} catch (EmptyCollectionException e) {
			System.err.println(e.getMessage());
		}

		return possible_moves;
	}

	private void checkForItems(Room room) throws EmptyCollectionException, ElementNotFoundException {
		System.out.println("Checking if the room has items.....");

		if (room.hasItems()) {
			for (Item item : mission.getItems()) {
				if (item.getPosition() != null && item.getPosition().equals(room)) {
					if (item instanceof MediKit) {
						this.player.addKitToBackPack((MediKit) item);
						item.setPosition(null);
						mission.removeItem(item);
						room.setItemInRoom(false);
						System.out.println("Medikit added to the backPack");
					} else if (item instanceof Kevlar) {
						this.player.equipKevlar((Kevlar) item);
						item.setPosition(null);
						mission.removeItem(item);
						room.setItemInRoom(false);
						System.out.println("Kevlar equipped, current health" + this.player.getCurrentHealth());
					}
				}
			}
		}
	}

	public void setBestPath() {
		Iterator<Room> temp_path = null;

		for (Room entry_point : mission.getEntryExitPoints()) {
			try {

				if (!entry_point.hasEnemies()) {

					System.out.println("\nBEST PATH WITHOUT ENEMY");

					temp_path = this.mission.getBattlefield().iteratorShortestPath(entry_point, mission.getTarget().getRoom());

					while (temp_path.hasNext()) {
						int enemy_counter = 0;

						Room tmp_room = temp_path.next();

						if (tmp_room.hasEnemies()) {
							for (Enemy enemy : mission.getEnemies()) {
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

	public void updateBestPath(boolean returning) throws ElementNotFoundException {
		updateWeightsForEnemies();
		System.out.println("Getting information intel for the best current path to the target..");

		Room targetRoom = returning ? bestExtractionPoint() : mission.getTarget().getRoom();
		Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(player.getCurrentPosition(), targetRoom);

		while(path.hasNext()) {
			System.out.println("-> " + path.next().getName());
		}

 	}

	private void updateWeightsForEnemies() {
		for (Room room : mission.getBattlefield().getVertices()) {
			for (Room connectedRoom : mission.getBattlefield().getConnectedVertices(room)){
				double newWeight = calculateWeight(connectedRoom);
				mission.getBattlefield().addEdge(room,connectedRoom, newWeight );
			}

		}
	}

	private double calculateWeight(Room room) {
		double weight = 0.0;

		if (room.hasEnemies()) {
			int totalDamage = 0;
			for (Enemy enemy : mission.getEnemies()) {
				if (enemy.getCurrentPosition().equals(room)) {
					totalDamage += enemy.getFirePower();
				}
			}
			weight += totalDamage;
		}
		return weight;
	}

	public Room bestExtractionPoint() throws ElementNotFoundException {
		Room bestExtractionPoint = null;
		double lowestDamage = Double.MAX_VALUE;

		for (Room exit : mission.getEntryExitPoints()) {
			Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(mission.getTarget().getRoom(), exit);
			double damage = this.calculatePathDamage(path);

			if (damage < lowestDamage) {
				lowestDamage = damage;
				bestExtractionPoint = exit;
			}
		}

		System.out.println("Best exit room is: " + bestExtractionPoint);

		return bestExtractionPoint;

	}

	public Room findBestEntryPoint() throws ElementNotFoundException {
		Room bestEntry = null;
		double lowestDamage = Double.MAX_VALUE;

		for (Room entry : mission.getEntryExitPoints()) {
			Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(entry, mission.getTarget().getRoom());
			double damage = this.calculatePathDamage(path);

			if (damage < lowestDamage) {
				lowestDamage = damage;
				bestEntry = entry;
			}
		}

		System.out.println("Best entry room is: " + bestEntry.getName());

		return bestEntry;
	}

	private double calculatePathDamage(Iterator<Room> path) {
		double totalDamage = 0;
		int playerHealth = player.getCurrentHealth();
		int maxHealth = 100;

		while(path.hasNext()) {
			Room room = path.next();
			if (room.hasEnemies()) {
				for (Enemy enemy : mission.getEnemies()) {
					if (enemy.getCurrentPosition().equals(room)) {
						totalDamage += enemy.getFirePower();

					}
				}
			}

			if (room.hasItems()) {
				for (Item item : mission.getItems()) {
					if (item.getPosition() != null && item.getPosition().equals(room)) {
						if (item instanceof MediKit) {
							playerHealth = Math.min(maxHealth, playerHealth + ((MediKit) item).getHealPower());
						} else if (item instanceof Kevlar) {
							playerHealth += ((Kevlar) item).getExtraHp();
						}
					}
				}
			}
		}

		return totalDamage;
 	}
}
