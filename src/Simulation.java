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

	public void game_AAAA() throws ElementNotFoundException, EmptyCollectionException {
		Room bestEntry = this.findBestEntryPoint();
		player.setCurrentPosition(bestEntry);
		this.updateBestPath(this.bestExtractionPoint(mission.getTarget().getRoom()));

		playerTurn();
		enemyTurn();

		if (player.getCurrentHealth() > 0) {
			System.out.println("Mission done");
		} else {
			System.out.println("Mission failed");
		}
	}

	public void enemyTurn_AAAAA() {
		System.out.println("Enemies turn!");

		Room player_current_position = this.player.getCurrentPosition();
		Iterator<Enemy> enemies = mission.getEnemies().iterator();

		while (enemies.hasNext()) {
			Enemy enemy_current = enemies.next();
			Room enemy_current_position = enemy_current.getCurrentPosition();

			if (enemy_current_position.equals(player_current_position)) {
				this.enemyAttack(enemy_current);
			}
		}
	}

	public void playerTurn_AAAA() throws EmptyCollectionException, ElementNotFoundException {
		System.out.println("Player turn");
		boolean player_sees_enemies = this.player.getCurrentPosition().hasEnemies();
		Room player_current_position = this.player.getCurrentPosition();

		if (!player_sees_enemies) {
			this.scenario2(player_current_position);
		} else if (player_sees_enemies) {
			Iterator<Enemy> enemies = mission.getEnemies().iterator();
			attackEnemies(enemies);
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
				System.out.println(player_name + " is attacking " + current_enemy_name);

				if (current_enemy.getCurrentHealth() <= 0) {
					current_enemy.takeDamage(player_damage);
					enemies.remove();
					System.out.println("Enemy: " + current_enemy_name + " is dead");
				}
			}
		}

		// TODO: check if gameover is not false.
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

	public void scenario1_AAAA(Room currentRoom) {
		System.out.println("Tó cruz confronting enemies");

		Iterator<Enemy> enemies = mission.getEnemies().iterator();
		attackEnemies(enemies);

		if (currentRoom.hasEnemies()) {
			System.out.println("Not all enemies are dead! End player turn. Enemies will move");
			enemyTurn();
		}
	}

	public void scenario2_AAAAA(Room room) throws EmptyCollectionException, ElementNotFoundException {
		System.out.println("Room is clear. Checking for items....");
		this.checkForItems(room);

		System.out.println("Moving enemies...");
		this.moveEnemies();

		System.out.println("Player turn ended");
		// IF manual
		//  Entra noutra funcao que faz um turno...

		// IF automatico
		//  Fazer ountra funcao pra saber onde o jogador vai pra a seguir
		//  get.next.room.for.player.
	}

	private void scenario3_AAAAA(Enemy enemy) {
		System.out.println("Enemy " + enemy.getName() + " is on To cruz room");
		System.out.println("Enemy has priority attack");
		this.enemyAttack(enemy);

		if (!player.isAlive()) {
			System.out.println("To cruz is dead");
			this.gameOver = true;
		}
	}

	private void scenario4_AAAAA() throws EmptyCollectionException {
		System.out.println("To cruz is using medic kit ");

		player.useMediKit();

	}

	private void scenario5_AAAAA() throws EmptyCollectionException, ElementNotFoundException {

		System.out.println("To cruz as reached the target room" + mission.getTarget().getRoom().getName
			());
		// Tó Cruz encontra o alvo, mas há inimigos na sala
		// Se o Tó Cruz entra numa sala onde está o alvo
		Room targetRoom = mission.getTarget().getRoom();
		if (this.player.getCurrentPosition().equals(targetRoom) && targetRoom.hasEnemies()) {

			// FASE DO JOGADOR: ESTA MAL
			if (playerTurn()) {
				// TO CRUZ ATACA (Confronto Prioritario)
				// Ele nao pode resgatar o alvo EQUANTO
				// inimigos na sala seja eliminados
				Iterator<Enemy> enemies = mission.getEnemies().iterator();
				attackEnemies(enemies);

				// Depois vai para o exit point.
				// Calcula o trajeto outra vez MAS do target room para o exit room
				// Ver qual é o melhor desses trajetos.
				updateWeightsForEnemies();  // Os inimigos movem-se nao estao fixo.
				Room exitRoom = bestExtractionPoint(this.player.getCurrentPosition());
				Iterator<Room> exit = mission.getBattlefield().iteratorShortestPath(mission.getTarget().getRoom(), exitRoom);
				// se houver um
				// Usa esse trajeto em que ele perde menos vida.

				// TEST....
				System.out.println("Best exit: ");
				while (exit.hasNext()) {
					System.out.println("-> " + exit.next().getName());
				}

				// Game over???

				// FASE DO INIMIGO
			} else (enemyTurn()) {
				// MAS aqueles que nao estao no target room,
				// andam aleatoriamente.
				Iterator<Enemy> enemies = mission.getEnemies().iterator();
				while (enemies.hasNext()) {
					Enemy current_enemy = enemies.next();
					if (!current_enemy.getCurrentPosition().equals(targetRoom)) {
						moveEnemy(current_enemy);
					}
				}

			}

			// CASO ESPECIAL
			if (!targetRoom.hasEnemies()) {
				// APANHA NO ALVO
				// TURNO ACABA PARA O TO CRUZ
				// VEZ DOS INIMIGOS.
			} else if (targetRoom.hasEnemies()) {
				// MATA OS INIMIGOS
			}
		}
	}

	void scenario6_AAAAAA() {
		// TO CRUZ encontra o alvo...
		if (this.player.getCurrentPosition().equals(mission.getTarget().getRoom())) {
			// ... e nao há inimigos presentes.
			if (!this.player.getCurrentPosition().hasEnemies()) {
				// TO CRUZ pode interagir com o alvo
					// Maybe um Sout qualquer...
				// e conclui a missao com sucesso
				// caso consiga sair do edificio com vida.
			}
		}
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

	private void moveEnemy(Enemy enemy) {
		ArrayList<Room> possible_moves;

		Random random = new Random();

		possible_moves = getPossibleMoves(enemy.getCurrentPosition());

		if (!possible_moves.isEmpty()) {

			int random_index = random.nextInt(possible_moves.size());

			Room next_room = possible_moves.getElement(random_index);

			enemy.setCurrentPosition(next_room);
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

	private void checkForItems(Room room) throws
		EmptyCollectionException, ElementNotFoundException {
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

	public void updateBestPath_AAAAA(Room exitRoom) throws ElementNotFoundException {
		updateWeightsForEnemies();
		System.out.println("Getting information intel for the best current path to the target..");

		Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(player.getCurrentPosition(), mission.getTarget().getRoom());
		Iterator<Room> exit = mission.getBattlefield().iteratorShortestPath(mission.getTarget().getRoom(), exitRoom);
		System.out.println("Best path is: ");

		while (path.hasNext()) {
			System.out.println("-> " + path.next().getName());
		}

		System.out.println("Best exit: ");
		while (exit.hasNext()) {
			System.out.println("-> " + exit.next().getName());
		}
	}

	private void updateWeightsForEnemies_AAAA() {
		for (Room room : mission.getBattlefield().getVertices()) {
			for (Room connectedRoom : mission.getBattlefield().getConnectedVertices(room)) {
				double newWeight = calculateWeight(connectedRoom);
				mission.getBattlefield().addEdge(room, connectedRoom, newWeight);
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

	public Room bestExtractionPoint_AAAA(Room currentRoom) throws ElementNotFoundException {
		Room bestExtractionPoint = null;
		double lowestDamage = Double.MAX_VALUE;

		for (Room exit : mission.getEntryExitPoints()) {
			Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(currentRoom, exit);
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

		while (path.hasNext()) {
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
