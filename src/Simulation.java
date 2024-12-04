import entities.*;
import lib.ArrayList;
import lib.ArrayUnorderedList;
import lib.LinkedList;
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

	public void game_A() throws EmptyCollectionException, ElementNotFoundException {
		Turn currentTurn = Turn.PLAYER;

		boolean gameOver = false;

		while (!gameOver) {
			switch (currentTurn) {

				case PLAYER:
					System.out.println("Player's turn");
					scenariosCase(currentTurn);
					currentTurn = Turn.ENEMY;
					break;

				case ENEMY:
					System.out.println("Enemy's turn");
					scenariosCase(currentTurn);
					currentTurn = Turn.PLAYER;
					break;
			}
		}
	}



	void scenariosCase(Turn currentTurn) {
		Room playerPosition = this.player.getPosition();



		if (playerPosition.hasEnemies()) {
			// TO CRUZ entra na sala e encontra os inimigos.
			System.out.println("TO CRUZ enters in a room and faces enemies...");
			scenarioUM();
		}



	}

	public void scenarioUM() {
		// PLAYER




		Room playerPosition = this.player.getPosition();
		Iterator<Enemy> enemies = this.mission.getEnemies().iterator();

		System.out.println("Tó cruz confronting enemies");

		playerConfronts(enemies);

		if (!playerPosition.hasEnemies()) {
			System.out.println("All enemies in the room are defeated");
		} else {
			System.out.println("Enemies remain. End player turn");
		}
	}
















	private void playerTurn_A() {
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();

		System.out.println("Tó Cruz current position is " + playerPosition);

		if (playerPosition.hasEnemies()) {
			// cenario1;
		} else if (playerPosition.equals(targetPosition)) {
			if (!playerPosition.hasEnemies()) {
				// cenario6
			} else {
				// cenario5
			}
		} else {
			// cenario2
		}
	}

	public void game() throws ElementNotFoundException, EmptyCollectionException {
		Room playerPosition = this.player.getPosition();
		Room bestEntryPoint = this.findBestEntryPoint();
		Room targetPosition = this.mission.getTarget().getRoom();
		LinkedList<Room> entryPoints = this.mission.getEntryExitPoints();

		System.out.println("Game start");

		this.player.setPosition(bestEntryPoint);

		System.out.println("Player enters at: " + bestEntryPoint.getName());

		boolean targetFound = false;

		while (!this.gameOver) {
			System.out.println("=== New Turn ===");

			playerTurn();

			if (!targetFound && playerPosition.equals(targetPosition)) {
				System.out.println("Player reached the target room!");
				targetFound = true;
			}

			if (targetFound && entryPoints.contains(playerPosition)) {
				System.out.println("Player exited the building!");
				gameOver = true;
				break;
			}

			enemyTurn();

			// TODO: Mudar a call... ( ( ( ) ) )
			updateBestPath(bestExtractionPoint(targetPosition));

			playerTurn();
			enemyTurn();

			if (!gameOver) {
				updateBestPath(targetFound);
			}
		}

		System.out.println(this.player.getCurrentHealth() > 0 ? "Mission accomplished!" : "Mission failed");
	}

	public void enemyTurn() {
		Room playerPosition = this.player.getPosition();
		boolean enemiesEnteredPlayerRoom = false;

		System.out.println("Enemies turn!");

		Iterator<Ennez>

		for (Enemy enemy : mission.getEnemies()) {
			Room enemyCurrentPosition = enemy.getPosition();

			if (enemyCurrentPosition.equals(playerPosition)) {
				enemiesEnteredPlayerRoom = true;
				break;

				while (enemies.hasNext()) {
					Enemy enemy_current = enemies.next();
					Room enemy_current_position = enemy_current.getPosition();

					if (enemy_current_position.equals(playerPosition)) {
						this.playerUnderAttackBy(enemy_current);
					}
				}

				if (enemiesEnteredPlayerRoom) {
					scenario3();
				} else {
					moveEnemies();
				}
			}
		}
	}

	public void playerTurn() throws EmptyCollectionException, ElementNotFoundException {
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();

		System.out.println("Players turn. Current room " + playerPosition.getName());

		if (playerPosition.equals(targetPosition)) {
			if (playerPosition.hasEnemies()) {
				scenario5();
			} else {
				scenario6();
			}
			return;
		}

		if (playerPosition.hasEnemies()) {
			scenario1();
		} else {

			if (playerUseRecoveryItem()) {
				scenario4();
			} else {
				scenario2();
			}
		}

	}

	// TODO: Change method name. maybe "enemiesInSameRoom()" ?
	private boolean checkEnemiesInPlayerRoom() {

		Room playerPosition = this.player.getPosition();

		Iterator<Enemy> enemyIterator = this.mission.getEnemies().iterator();

		while (enemyIterator.hasNext()) {

			Enemy enemy = enemyIterator.next();
			Room enemyPosition = enemy.getPosition();

			if (enemyPosition.equals(playerPosition)) {
				return true;
			}
		}
		return false;
	}

	// TODO: Nao sei o que os 30 significa.
	private boolean playerUseRecoveryItem() {
		int healthMissing = 30;

		return this.player.getCurrentHealth() <= healthMissing && this.player.hasRecoveryItem();
	}

	private void movePlayer() throws ElementNotFoundException {
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();

		System.out.println("Next player move");

		Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(playerPosition, targetPosition);

		if (path.hasNext()) {
			path.next();

			if (path.hasNext()) {
				Room nextRoom = path.next();
				this.player.setPosition(nextRoom);

				System.out.println("Player moved to: " + nextRoom.getName());
			} else {
				System.out.println("No valid moves available from current path.");
			}

		} else {
			System.out.println("No valid path found.");
		}
	}

	public void playerConfronts(Iterator<Enemy> enemies) {
		final int ZERO = 0;

		String playerName = this.player.getName();
		Room playerPosition = this.player.getPosition();
		int playerAttack = this.player.getFirePower();

		while (enemies.hasNext()) {

			Enemy enemy = enemies.next();
			String enemyName = enemy.getName();
			Room enemyPosition = enemy.getPosition();
			int enemyHealth = enemy.getCurrentHealth();

			if (enemyPosition.equals(playerPosition)) {
				enemy.takesDamageFrom(playerAttack);
				System.out.println(playerName + " is attacking " + enemyName);

				if (enemyHealth <= ZERO) {
					enemy.takesDamageFrom(playerAttack);
					enemies.remove();
					System.out.println("Enemy: " + enemyName + " is dead");
				}
			}
		}

		// TODO: check if gameover is not false.
	}

	private void playerUnderAttackBy(Enemy enemy) {
		final int ZERO = 0;

		String playerName = this.player.getName();
		int playerHealth = this.player.getCurrentHealth();

		String enemyName = enemy.getName();
		int enemyAttack = enemy.getFirePower();

		System.out.println("Enemy " + enemyName + " is attacking " + playerName);
		this.player.setCurrentHealth(playerHealth - enemyAttack);

		System.out.println("Enemy deal " + enemyAttack + " damage " + playerName + " current HP is " + playerHealth);

		if (playerHealth <= ZERO) {
			this.gameOver = true;
		}
	}



	public void scenario2() throws EmptyCollectionException, ElementNotFoundException {
		Room playerPosition = this.player.getPosition();

		System.out.println("Room is clear. Checking for items....");

		checkForItems(playerPosition);

		movePlayer();

		// TODO: room, but which one? I need to read it again, I am refactoring.
		checkForItems(room);

		System.out.println("Moving enemies...");

		moveEnemies();

		System.out.println("Player turn ended");

		// IF manual
		//  Entra noutra funcao que faz um turno...

		// IF automatico
		//  Fazer ountra funcao pra saber onde o jogador vai pra a seguir
		//  get.next.room.for.player.
	}

	private void scenario3() {
		final int ZERO = 0;
		Room playerPosition = this.player.getPosition();
		int playerHealth = this.player.getCurrentHealth();

		System.out.println("Enemies have entered on the players room");

		for (Enemy enemy : mission.getEnemies()) {

			Room enemyPosition = enemy.getPosition();

			if (enemyPosition.equals(playerPosition)) {

				playerUnderAttackBy(enemy);
			}

		}

		if (playerHealth <= ZERO) {
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
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();
		Iterator<Enemy> enemies = this.mission.getEnemies().iterator();

		System.out.println("To cruz as reached the target room" + targetPosition.getName());

		playerConfronts(enemies);

		if (!playerPosition.hasEnemies()) {
			System.out.println("All enemies in the target room are defeated");
		} else {
			System.out.println("Enemies reamin in the room. Turn ends");

			System.out.println("To cruz as reached the target room" + targetPosition.getName());
			// Tó Cruz encontra o alvo, mas há inimigos na sala
			// Se o Tó Cruz entra numa sala onde está o alvo
			if (this.player.getPosition().equals(targetPosition) && targetPosition.hasEnemies()) {

				// FASE DO JOGADOR: ESTA MAL
				if (playerTurn()) {
					// TO CRUZ ATACA (Confronto Prioritario)
					// Ele nao pode resgatar o alvo EQUANTO
					// inimigos na sala seja eliminados
					Iterator<Enemy> enemies = mission.getEnemies().iterator();
					playerConfronts(enemies);

					// Depois vai para o exit point.
					// Calcula o trajeto outra vez MAS do target room para o exit room
					// Ver qual é o melhor desses trajetos.
					updateWeightsForEnemies();  // Os inimigos movem-se nao estao fixo.
					Room extractionPoint = bestExtractionPoint(playerPosition);
					Iterator<Room> exit = mission.getBattlefield().iteratorShortestPath(targetPosition, extractionPoint);
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
					while (enemies.hasNext()) {
						Enemy current_enemy = enemies.next();
						if (!current_enemy.getPosition().equals(targetPosition)) {
							moveEnemy(current_enemy);
						}
					}

				}

				// CASO ESPECIAL
				if (!targetPosition.hasEnemies()) {
					// APANHA NO ALVO
					// TURNO ACABA PARA O TO CRUZ
					// VEZ DOS INIMIGOS.
				} else if (targetPosition.hasEnemies()) {
					// MATA OS INIMIGOS
				}

			}
		}
	}

	void scenario6() {
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();

		// TO CRUZ encontra o alvo...
		if (playerPosition.equals(targetPosition)) {
			// ... e nao há inimigos presentes.
			if (!playerPosition.hasEnemies()) {
				// TO CRUZ pode interagir com o alvo
				// e conclui a missao com sucesso
				// caso consiga sair do edificio com vida.
			}
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

			possible_moves = getPossibleMoves(enemyObj.getPosition());

			if (!possible_moves.isEmpty()) {

				int random_index = random.nextInt(possible_moves.size());

				Room next_room = possible_moves.getElement(random_index);

				enemyObj.setPosition(next_room);
			}
		}
	}

	private void moveEnemy(Enemy enemy) {
		ArrayList<Room> possible_moves;

		Random random = new Random();

		possible_moves = getPossibleMoves(enemy.getPosition());

		if (!possible_moves.isEmpty()) {

			int random_index = random.nextInt(possible_moves.size());

			Room next_room = possible_moves.getElement(random_index);

			enemy.setPosition(next_room);
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
								if (enemy.getPosition().getName().equals(tmp_room.getName())) {
									enemy_counter++;

								}
							}
							System.out.println("THIS ROOM " + tmp_room.getName() + " has " + enemy_counter);
						} System.out.println(tmp_room.getName());
					}
				}

			} catch (ElementNotFoundException e) {
				System.out.println(e.getMessage());
			} while (temp_path.hasNext()) {
				System.out.println(temp_path.next());
			}
		}

	}

	public void updateBestPath(boolean returning) throws ElementNotFoundException {
		updateWeightsForEnemies();
		System.out.println("Getting information intel for the best current path to the target..");

		Room targetRoom = returning ? bestExtractionPoint() : mission.getTarget().getRoom();
		Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(player.getPosition(), targetRoom);

		while (path.hasNext()) {
			System.out.println("-> " + path.next().getName());
		}

		System.out.println("Best exit: "); while (exit.hasNext())

			System.out.println("-> " + exit.next().getName());
	}

	private void updateWeightsForEnemies() {
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
			int totalDamage = 0; for (Enemy enemy : mission.getEnemies()) {
				if (enemy.getPosition().equals(room)) {
					totalDamage += enemy.getFirePower();
				}
			} weight += totalDamage;
		} return weight;
	}

	public Room bestExtractionPoint() throws ElementNotFoundException {
		Room bestExtractionPoint = null; double lowestDamage = Double.MAX_VALUE;

		for (Room exit : mission.getEntryExitPoints()) {
			Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(mission.getTarget().getRoom(), exit);
			double damage = this.calculatePathDamage(path);

			if (damage < lowestDamage) {
				lowestDamage = damage; bestExtractionPoint = exit;
			}
		}

		System.out.println("Best exit room is: " + bestExtractionPoint);

		return bestExtractionPoint;

	}

	public Room findBestEntryPoint() throws ElementNotFoundException {
		Room bestEntry = null; double lowestDamage = Double.MAX_VALUE;

		for (Room entry : mission.getEntryExitPoints()) {
			Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(entry, mission.getTarget().getRoom());
			double damage = this.calculatePathDamage(path);

			if (damage < lowestDamage) {
				lowestDamage = damage; bestEntry = entry;
			}
		}

		System.out.println("Best entry room is: " + bestEntry.getName());

		return bestEntry;
	}

	private double calculatePathDamage(Iterator<Room> path) {
		double totalDamage = 0;
		int playerHealth = player.getCurrentHealth();
		int playerMaxHealth = 100;

		while (path.hasNext()) {

			Room room = path.next();

			if (room.hasEnemies()) {

				for (Enemy enemy : mission.getEnemies()) {
					if (enemy.getPosition().equals(room)) {

						totalDamage += enemy.getFirePower();

					}
				}
			}

			if (room.hasItems()) {
				for (Item item : mission.getItems()) {
					if (item.getPosition() != null && item.getPosition().equals(room)) {
						if (item instanceof MediKit) {
							playerHealth = Math.min(playerMaxHealth, playerHealth + ((MediKit) item).getHealPower());
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
