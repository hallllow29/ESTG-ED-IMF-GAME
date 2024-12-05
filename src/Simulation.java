import entities.*;
import lib.ArrayList;
import lib.ArrayUnorderedList;
import lib.LinkedList;
import lib.Network;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Random;
import java.util.Scanner;

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

	public void game() throws EmptyCollectionException, ElementNotFoundException {
		Turn currentTurn = Turn.PLAYER;

		boolean gameOver = false;

		while (!gameOver) {
			switch (currentTurn) {

				case PLAYER:
					System.out.println("Player's turn");
					scenariosCase(currentTurn);
					movePlayer();
					currentTurn = Turn.ENEMY;
					break;

				case ENEMY:
					System.out.println("Enemy's turn");
					System.out.println("Enemies are moving...");
					// O movimento dos inimigos ocorre sempre...
					// logo de seguida à fase de jogador
					moveEnemies();

					// Na fase dos inimigos apos a sua movimentacao...
					//

					scenariosCase(currentTurn);
					currentTurn = Turn.PLAYER;
			}
		}

		// I wish i could add some sleep for few ms, but it would
		// have a negative impact in performance or avaliation....
		System.out.println("TO CRUZ DIED !!!");
		System.out.println("JUST KIDDING...");
		System.out.println("IT WAS A SIMULATION...");
		System.out.println("...OR WASN'T IT...");
		System.out.println("GAME OVER!");


		/*
			Look at me
			I Just can't believe
			What they've done to me
			We could never get free
			I just wanna be

			Look at me
			I Just can't believe
			What they've done to me
			We could never get free
			I just wanna be
			I just wanna dream
		 */
	}

	void scenariosCase(Turn currentTurn) {
		Room playerPosition = this.player.getPosition();
		System.out.println("PlayerPosition->>>>>>>" + playerPosition);
		// TODO: I am going refactor .getRoom() to .getPosition() in class Target
		Room targetPosition = this.mission.getTarget().getRoom();

		if (playerPosition.hasEnemies()) {

			roomWithEnemiesSituation(currentTurn, playerPosition, targetPosition);

			/*if (!playerPosition.equals(targetPosition)) {
				switch (currentTurn) {
					case PLAYER:
						// TO CRUZ entra na sala e encontra os inimigos.
						System.out.println("TO CRUZ enters in a room and faces enemies...");
						System.out.println("AND has priority of attack over enemies...");
						scenarioUM(currentTurn);
						break;
					case ENEMY:
						// INIMIGOS entram apos movimentacao,
						System.out.println("ENEMIES enter in a room and face TO CRUZ");
						System.out.println("AND have priority of attack over TO CRUZ");
						scenarioTRES(currentTurn);
						break;
				}

			} else if (playerPosition.equals(targetPosition)) {
				// TO CRUZ encontra alvo MAS há enimigos na sala.
				System.out.println("TO CRUZ enters in a room and faces enemies...");
				System.out.println("AND in that room there is the TARGET");

				try {
					scenarioCINCO(currentTurn);
				} catch (EmptyCollectionException | ElementNotFoundException e) {
					System.err.println(e.getMessage());
				}

			}*/

		} else if (playerPosition.hasItems()) {

			roomWithItemsSituation();

			/*// TO CRUZ utiliza items de recuperacao
			System.out.println("TO CRUZ enters in a room...");
			System.out.println("AND the room has items for him to use...");
			try {
				scenarioQUATRO(currentTurn);
			} catch (EmptyCollectionException e) {
				System.out.println(e.getMessage());
			}*/

		} else if (!playerPosition.hasEnemies()) {

			roomWithoutEnemiesSituation(currentTurn, playerPosition, targetPosition);

			/*// O movimento dos inimigos ocorre sempre que Tó Cruz não
			// encontra inimigos

			// FODASSE AQUI OCCORRE
			// moveEnemies()
			// OU/E
			// moveEnemiesNotSameRoom()

			// TO CRUZ entra na sala e nao encontra os inimigos.
			System.out.println("TO CRUZ enters in a room...");
			System.out.println("AND the room TO CRUZ entered is clear...");

			try {
				scenarioDOIS(currentTurn);
			} catch (EmptyCollectionException | ElementNotFoundException e) {
				System.out.println(e.getMessage());
			}

			// TO CRUZ encontra o alvo sem inimigos
			System.out.println("TO CRUZ enters on a room...");
			System.out.println("AND in that room there is the TARGET...");
			System.out.println("LOOK AT THAT, it is clear...");
			scenarioSEIS(currentTurn);*/
		}
	}

	private boolean isAtTarget(Room playerPosition, Room targetPosition) {
		return playerPosition.equals(targetPosition);
	}

	// -------------------- ROOM WITH ENEMIES SITUATION ----------------------------------
	private void roomWithEnemiesSituation(Turn currentTurn, Room playerPosition, Room targetPosition) {

		// Ya man! dou um refactor... que consome tempo, mas tem que ser...man

		/* if (!isAtTarget(Room playerPosition, Room targetPosition) {
			playerAtTargetSituation
			} else if (itAtTarget(...) {
			duel ou battle- ou confrontation-Situation
			}
		 */

		if (!isAtTarget(playerPosition, targetPosition)) {

			confrontationOccurs(currentTurn);

			/*switch (currentTurn) {
				case PLAYER:
					// TO CRUZ entra na sala e encontra os inimigos.
					System.out.println("TO CRUZ enters in a room and faces enemies...");
					System.out.println("AND has priority of attack over enemies...");
					scenarioUM(currentTurn);
					break;
				case ENEMY:
					// INIMIGOS entram apos movimentacao,
					System.out.println("ENEMIES enter in a room and face TO CRUZ");
					System.out.println("AND have priority of attack over TO CRUZ");
					scenarioTRES(currentTurn);
					break;
			}*/

		} else if (isAtTarget(playerPosition, targetPosition)) {

			securingTargetPerimeter();

			/*// TO CRUZ encontra alvo MAS há enimigos na sala.
			System.out.println("TO CRUZ enters in a room and faces enemies...");
			System.out.println("AND in that room there is the TARGET");

			try {
				scenarioCINCO(currentTurn);
			} catch (EmptyCollectionException | ElementNotFoundException e) {
				System.err.println(e.getMessage());
			}*/

		}
	}

	private void confrontationOccurs(Turn currentTurn) {

		if (currentTurn == Turn.PLAYER) {
			// TO CRUZ entra na sala e encontra os inimigos.
			System.out.println("TO CRUZ enters in a room and faces enemies...");
			System.out.println("AND has priority of attack over enemies...");
			scenarioUM();
		} else if (currentTurn == Turn.ENEMY) {
			// INIMIGOS entram apos movimentacao,
			System.out.println("ENEMIES enter in a room and face TO CRUZ");
			System.out.println("AND have priority of attack over TO CRUZ");
			scenarioTRES();
		}
	}

	private void securingTargetPerimeter() {
		// TO CRUZ encontra alvo MAS há inimigos na sala.
		System.out.println("TO CRUZ enters in a room and faces enemies...");
		System.out.println("AND in that room there is the TARGET");

		try {
			scenarioCINCO();
		} catch (EmptyCollectionException | ElementNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	// ------------------ ROOM WITHOUT ENEMIES SITUATION --------------------------------
	private void roomWithoutEnemiesSituation(Turn currentTurn, Room playerPosition, Room targetPosition) {

		// O movimento dos inimigos ocorre sempre que Tó Cruz não
		// encontra inimigos

		// FODASSE AQUI OCCORRE
		// moveEnemies()
		// OU/E
		// moveEnemiesNotSameRoom()

		if (!isAtTarget(playerPosition, targetPosition)) {

			clearingRoom();

			/*// TO CRUZ entra na sala e nao encontra os inimigos.
			System.out.println("TO CRUZ enters in a room...");
			System.out.println("AND the room TO CRUZ entered is clear...");
			System.out.println("BUT the enemies are somewhere...");
			try {
				scenarioDOIS();
			} catch (EmptyCollectionException | ElementNotFoundException e) {
				System.out.println(e.getMessage());
			}*/

		} else if (isAtTarget(playerPosition, targetPosition)) {

			securingTarget(currentTurn);

			/*// TO CRUZ encontra o alvo sem inimigos.
			System.out.println("TO CRUZ enters on a room...");
			System.out.println("AND in that room there is the TARGET...");
			System.out.println("LOOK AT THAT, it is clear...");
			scenarioSEIS(currentTurn);*/
		}
	}

	private void clearingRoom() {
		// TO CRUZ entra na sala e nao encontra os inimigos.
		System.out.println("TO CRUZ enters in a room...");
		System.out.println("AND the room TO CRUZ entered is clear...");
		System.out.println("BUT the enemies are somewhere...");
		try {
			scenarioDOIS();
		} catch (EmptyCollectionException | ElementNotFoundException e) {
			System.out.println(e.getMessage());
		}
	}

	private void securingTarget(Turn currentTurn) {
		// TO CRUZ encontra o alvo sem inimigos.
		System.out.println("TO CRUZ enters on a room...");
		System.out.println("AND in that room there is the TARGET...");
		System.out.println("LOOK AT THAT, it is clear...");
		scenarioSEIS(currentTurn);
	}

	// --------------------- ROOM WITH ITEMS SITUATION -----------------------------------
	private void roomWithItemsSituation() {
		try {
			// TO CRUZ utiliza items de recuperacao
			System.out.println("TO CRUZ enters in a room...");
			System.out.println("AND the room has items for him to use...");
			scenarioQUATRO();
		} catch (EmptyCollectionException e) {
			System.out.println(e.getMessage());
		}
	}






	public void scenarioUM() {
		Iterator<Enemy> enemies = this.mission.getEnemies().iterator();
		Room playerPosition = this.player.getPosition();

		// PLAYER
		// O dano causado por Tó Cruz é aplicado simultaneamente
		// a todos os inimigos presentes na sala considerando o seu pode de ataque.
		System.out.println("TO CRUZ is confronting enemies...");

		playerConfronts(enemies);

		// Se o Tó Cruz perder todos os pontos de vida, o jogo termina.
		if (!this.player.isAlive()) {
			this.gameOver = true;
			return;
		}

		// ENEMY
		// O(s) inimigo(s) é(são) eliminado(s) e o turno termina.
		// Nenhum movimento adicional dos inimigos ocorre neste turno.
		System.out.println("MAN WHAT? I MEAN I HAVE THAT IN playerConfronts...");
		// TODO: Analyse this case, so if all ENVOLVED in the confront are dead it should end the enemy turn? And what about the movements of the ones who are not ENVOLVED ?

		// Os inimigos na sala atual em que o Tó Cruz se encontra não podem
		// mover-se até o final do combate. Apenas os inimigos de outras
		// divisões podem-se movimentar.
		// Se os inimigos não forem eliminados após o confronto,
		// todos os outros inimigos no edifício movem-se aleatoriamente.
		moveEnemiesNotInSameRoom();

		if (!playerPosition.hasEnemies()) {
			System.out.println("All enemies in the room are defeated...");
		} else {
			System.out.println("Enemies remain. End player turn...");
		}

		System.out.println("SCENARIO 1 ENDED");
	}

	public void scenarioDOIS() throws EmptyCollectionException, ElementNotFoundException {
		Room playerPosition = this.player.getPosition();

		// PLAYER
		checkForItems(playerPosition);

		// ENEMY
		moveEnemies();

		System.out.println("SCENARIO 2 ENDED");
	}

	private void scenarioTRES() {

		System.out.println("ENEMIES are confronting TO CRUZ...");
		enemiesConfronts(this.player);

		System.out.println("SCENARIO 3 ENDED");
	}

	private void scenarioQUATRO() throws EmptyCollectionException {
		// PLAYER
		// O Tó Cruz pode apanhar kits de recuperação de vida que permitem
		// recuperar um determinado número de pontos até ao limite máximo permitido.
		if (playerNeedsRecoveryItem()) {
			System.out.println("TO CRUZ is using medic kit...");
			System.out.println(this.player.useMediKit());
		}

		System.out.println("SCENARION 4 ENDED");
	}

	private void scenarioCINCO() throws EmptyCollectionException, ElementNotFoundException {
		Iterator<Enemy> enemies = this.mission.getEnemies().iterator();

		// PLAYER
		// O confronto é prioritário para o Tó.
		// O Tó Cruz deve lidar com os inimigos primeiro.
		System.out.println("TO CRUZ is confronting enemies firstly...");
		playerConfronts(enemies);

		// ENEMY
		// Os outros inimigos no edificio movem-se conforme as regras de
		// movimentacao...
		moveEnemiesNotInSameRoom();
		System.out.println("SCENARIO 5 ENDED");


		/*
			Where is my mind?
			Where is my mind?
			Where is my mind?
			Way out in the water
			See it swimming

			With your feet on the air and your head on the ground
			Try this trick and spin it, yeah
		*/
	}

	private void scenarioSEIS(Turn currentTurn) {
		// TO CRUZ pode interagir com o alvo
		// e conclui a missao com sucesso
		// caso consiga sair do edificio com vida.

	}

	private Room findBestEntryPoint() throws ElementNotFoundException {
		Network<Room> battlefield = mission.getBattlefield();
		Room targetPosition = mission.getTarget().getRoom();
		Room bestEntryPoint = null;
		Iterator<Room> entryPoints = mission.getEntryExitPoints().iterator();
		double minimalDamage = Double.MAX_VALUE;

		while (entryPoints.hasNext()) {

			Room entryPoint = entryPoints.next();
			Iterator<Room> entryPointsPaths = battlefield.iteratorShortestPath(entryPoint, targetPosition);
			double calculatedDamage = calculatePathDamage(entryPointsPaths);
			if (calculatedDamage < minimalDamage) {
				minimalDamage = calculatedDamage;
				bestEntryPoint = entryPoint;
			}
		}
		return bestEntryPoint;

	}

	private void playerConfronts(Iterator<Enemy> enemies) {
		final int ZERO = 0;
		String playerName = this.player.getName();
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();
		int playerAttack = this.player.getFirePower();
		int totalEnemiesInRoom = 0;

		while (enemies.hasNext()) {

			Enemy enemy = enemies.next();
			String enemyName = enemy.getName();
			Room enemyPosition = enemy.getPosition();

			if (enemyPosition.equals(playerPosition)) {

				totalEnemiesInRoom++;

				enemy.takesDamageFrom(playerAttack);

				System.out.println(playerName + " is attacking " + enemyName + "...");

				if (!enemy.isAlive()) {
					System.out.println("Enemy: " + enemyName + " is dead");
					enemies.remove();
					totalEnemiesInRoom--;

				} else if (enemy.isAlive()) {
					// O movimento dos inimigos ocorre sempre que Tó Cruz não
					// elimina um inimigo ao entrar numa sala

					// FODASSE AQUI OCCORRE
					// moveEnemies()
					// OU/E
					// moveEnemiesNotSameRoom()
				}
			}
		}

		if (totalEnemiesInRoom <= ZERO) {
			playerPosition.setEnemies(false);
		}

		// TO CRUZ resgata alvo só quando TODOS os inimigos no targetPosition forem
		// eliminados
		if (totalEnemiesInRoom <= ZERO && playerPosition.equals(targetPosition)) {
			System.out.println("TO CRUZ eliminated all enemies in..." + targetPosition.getName() + "...");
			System.out.println("AND heads on to the extraction point...");

			// NO ENEMIES IN THE TARGET ROOM
			playerPosition.setEnemies(false);

			// MOVE TO EXTRACTION POINT, RIGHT ?
			updateWeightsForEnemies();
			try {
				Room extractionPoint = bestExtractionPoint();
				System.out.println("TO CRUZ has found a way to extraction point...");
				// MOVE PLAYER ??

			} catch (ElementNotFoundException e) {
				System.err.println(e.getMessage());
			}
		}
	}

	private void moveEnemies() {
		// Movimentação dos inimigos: Os inimigos movimentam-se aleatoriamente
		// até duas divisões a partir da sua posição.

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

	private void moveEnemiesNotInSameRoom() {
		// Movimentação dos inimigos: Os inimigos movimentam-se aleatoriamente até duas
		// divisões a partir da sua posição.

		Room playerPosition = this.player.getPosition();
		ArrayList<Room> possible_moves;
		Random random = new Random();

		for (Enemy enemyObj : this.mission.getEnemies()) {

			Room enemyPosition = enemyObj.getPosition();

			if (!enemyPosition.equals(playerPosition)) {

				possible_moves = getPossibleMoves(enemyObj.getPosition());

				if (!possible_moves.isEmpty()) {

					int random_index = random.nextInt(possible_moves.size());

					Room next_room = possible_moves.getElement(random_index);

					enemyObj.setPosition(next_room);
				}
			}
		}
	}

	private ArrayUnorderedList<Room> getPossibleMoves(Room from_room) {
		// Movimentação dos inimigos: Os inimigos movimentam-se aleatoriamente até duas
		// divisões a partir da sua posição.

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
			Iterator<Item> itemIterator = mission.getItems().iterator();
			while (itemIterator.hasNext()) {
				Item item = itemIterator.next();
				if (item == null) {
					continue;
				}

				if (item.getPosition() != null && item.getPosition().equals(room)) {
					if (item instanceof MediKit) {
						this.player.addKitToBackPack((MediKit) item);
						System.out.println("Medikit added to the backPack");
					} else if (item instanceof Kevlar) {
						this.player.equipKevlar((Kevlar) item);
						System.out.println("Kevlar equipped, current health" + this.player.getCurrentHealth());
					}

					item.setPosition(null);
					room.setItemInRoom(false);
					itemIterator.remove();
				}
			}
		}
	}

	private void enemiesConfronts(Player player) {
		Iterator<Enemy> enemies = this.mission.getEnemies().iterator();
		String playerName = player.getName();
		Room playerPosition = player.getPosition();

		while (enemies.hasNext()) {

			Enemy enemy = enemies.next();
			String enemyName = enemy.getName();
			Room enemyPosition = enemy.getPosition();
			int enemyAttack = enemy.getFirePower();

			if (enemyPosition.equals(playerPosition)) {

				player.takesDamageFrom(enemyAttack);
				System.out.println(enemyName + " is attacking " + playerName + "...");

				// Scenario 4: O Tó Cruz utiliza kits de vida DURANTE
				// o combate consumindo a sua fase de jogador...
				try {
					scenarioQUATRO();

				} catch (EmptyCollectionException e) {
					System.err.println(e.getMessage());
				}

				if (!player.isAlive()) {
					this.gameOver = false;
					break;
				}
			}
		}
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

	private boolean playerNeedsRecoveryItem() {
		// O Tó Cruz pode apanhar kits de recuperação de vida que permitem
		// recuperar um determinado número de pontos até ao limite máximo permitido.

		int playerCriticalHealth = 30;

		return this.player.getCurrentHealth() <= playerCriticalHealth && this.player.hasRecoveryItem();
	}

	private Room bestExtractionPoint() throws ElementNotFoundException {
		Network<Room> battlefield = mission.getBattlefield();
		Room bestExtractionPoint = null;
		Iterator<Room> extractionPoints = mission.getEntryExitPoints().iterator();
		double minimalDamage = Double.MAX_VALUE;

		while (extractionPoints.hasNext()) {

			Room extractionPoint = extractionPoints.next();
			Iterator<Room> extractionPointsPaths = battlefield.iteratorShortestPath(mission.getTarget().getRoom(), extractionPoint);
			double calculatedDamage = calculatePathDamage(extractionPointsPaths);
			if (calculatedDamage < minimalDamage) {
				minimalDamage = calculatedDamage;
				bestExtractionPoint = extractionPoint;
			}
		}
		return bestExtractionPoint;
	}

	private double calculatePathDamage(Iterator<Room> path) {
		double totalDamage = 0;
		int playerHealth = this.player.getCurrentHealth();
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
			int totalDamage = 0;
			for (Enemy enemy : mission.getEnemies()) {
				if (enemy.getPosition().equals(room)) {
					totalDamage += enemy.getFirePower();
				}
			}
			weight += totalDamage;
		}
		return weight;
	}




	/*
		Where is my mind?
		Where is my mind?
		Where is my mind?
		Way out in the water
		See it swimming

		With your feet on the air and your head on the ground
		Try this trick and spin it, yeah
	*/

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

	/*public void game_MNM() throws ElementNotFoundException, EmptyCollectionException {
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
			//updateBestPath(bestExtractionPoint);

			playerTurn();
			enemyTurn();

			if (!gameOver) {
				updateBestPath(targetFound);
			}
		}

		System.out.println(this.player.getCurrentHealth() > 0 ? "Mission accomplished!" : "Mission failed");
	}*/

	/*public void enemyTurn() {
		Room playerPosition = this.player.getPosition();
		boolean enemiesEnteredPlayerRoom = false;
		Turn currentTurn = Turn.ENEMY;

		System.out.println("Enemies turn!");

		Iterator<Enemy> enemies = mission.getEnemies().iterator();

		while (enemies.hasNext()) {
			Enemy enemy_current = enemies.next();
			Room enemy_current_position = enemy_current.getPosition();

			if (enemy_current_position.equals(playerPosition)) {
				this.playerUnderAttackBy(enemy_current);
			}
		}

		if (enemiesEnteredPlayerRoom) {
			scenarioTRES(currentTurn);
		} else {
			moveEnemies();
		}
	}*/

	/*public void playerTurn() throws EmptyCollectionException, ElementNotFoundException {
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();

		System.out.println("Players turn. Current room " + playerPosition.getName());

		if (playerPosition.equals(targetPosition)) {
			if (playerPosition.hasEnemies()) {
				scenarioCINCO(Turn.PLAYER);
			} else {
				scenarioSEIS(Turn.PLAYER);
			}
			return;
		}

		if (playerPosition.hasEnemies()) {
			scenarioUM(Turn.PLAYER);
		} else {

			if (playerNeedsRecoveryItem()) {
				scenarioQUATRO(Turn.PLAYER);
			} else {
				scenarioDOIS(Turn.PLAYER);
			}
		}

	}*/

	/*// TODO: Change method name. maybe "enemiesInSameRoom()" ?
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
	}*/

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

	/*private void scenario6() {
		System.out.println("Target found! Preparing to exit the building");
	}*/

	/*private void moveEnemy(Enemy enemy) {
		ArrayList<Room> possible_moves;

		Random random = new Random();

		possible_moves = getPossibleMoves(enemy.getPosition());

		if (!possible_moves.isEmpty()) {

			int random_index = random.nextInt(possible_moves.size());

			Room next_room = possible_moves.getElement(random_index);

			enemy.setPosition(next_room);
		}
	}*/

	/*public void setBestPath() {

		// TODO: READ ME
		*//*
			Deve levar se calhar o caminho do findBestEntryPoint
			+
			o caminho do findBestExtractionPoint
			ou
			estou a pesar bem ou nao faz sentido?

			Quase 2 da manha... Tugas vai dormir man...



		 *//*

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

	}*/

	/*public void updateBestPath(boolean returning) throws ElementNotFoundException {
		updateWeightsForEnemies();
		System.out.println("Getting information intel for the best current path to the target..");

		Room targetRoom = returning ? bestExtractionPoint() : mission.getTarget().getRoom();
		Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(player.getPosition(), targetRoom);

		System.out.println("\nBest path: ");
		while (path.hasNext()) {
			System.out.println("-> " + path.next().getName());

		}
	}*/

	/*public void gameManual() throws ElementNotFoundException, EmptyCollectionException {
		System.out.println("Manual Game");

		Room besEntry = this.findBestEntryPoint();
		player.setPosition(besEntry);
		System.out.println("Player enters at: " + besEntry.getName());

		boolean targetFound = false;

		while (!gameOver) {
			System.out.println("\nNew turn");

			this.displayMap();

			displayBestPath(this.mission.getTarget().getRoom());

			manualPlayerTurn();
			if (!targetFound && player.getPosition().equals(mission.getTarget().getRoom())) {
				System.out.println("Player as reached the target");
				targetFound = true;
			}

			if (targetFound && mission.getEntryExitPoints().contains(player.getPosition())) {
				System.out.println("Player exited the buidling");
				gameOver = true;
				break;
			}

			enemyTurn();

			if (!gameOver) {
				updateBestPath(targetFound);
			}
		}
	}*/

	/*private void manualPlayerTurn() throws ElementNotFoundException, EmptyCollectionException {
		Room curentRoom = player.getPosition();
		System.out.println("\nPlayer turn. Current room: " + curentRoom.getName());
		if (curentRoom.hasEnemies()) {
			scenarioUM(Turn.PLAYER);
		} else {
			System.out.println("Room is clear. Checking for item...");
			checkForItems(curentRoom);

			System.out.println("\nChoose next action: ");
			System.out.println("1. Move to other room");
			System.out.println("2. Use Medic Kit");
			System.out.println("3. Stay in current room");

			Scanner scanner = new Scanner(System.in);
			int choice = scanner.nextInt();

			switch (choice) {
				case 1:
					manualMovePlayer();
					break;
				case 2:
					if (player.hasRecoveryItem()) {
						scenarioQUATRO(Turn.PLAYER);
					} else {
						System.out.println("You don't have available medic kits");
					}
					break;
				case 3:
					System.out.println("Staying in current position..");
					break;
				default:
					System.out.println("Invalid choice! Turn skkiped");
					break;
			}

		}

		displayBestPath(mission.getTarget().getRoom());

		System.out.println("Choose an action: ");

	}*/

	/*private void displayMap() {
		System.out.println("\n==== Building Map =====");

		for (Room room : mission.getBattlefield().getVertices()) {
			System.out.println(room.getName());

			if (room.equals(player.getPosition())) {
				System.out.println(" Your position");
			} else if (room.equals(mission.getTarget().getRoom())) {
				System.out.println(" Your target");
			}

			boolean hasEnemies = false;

			for (Enemy enemy : mission.getEnemies()) {
				if (enemy.getPosition().equals(room)) {
					hasEnemies = true;
					break;
				}
			}

			if (hasEnemies) {
				System.out.println(" Enemies in the room");
			}

			System.out.println(" -> ");
			for (Room connectedRoom : mission.getBattlefield().getConnectedVertices(room)) {
				System.out.println(connectedRoom.getName() + " ");
			}

			System.out.println();

		}

		System.out.println("============================================");
	}*/

	/*private void displayBestPath(Room targetRoom) throws ElementNotFoundException {
		System.out.println("\n==== Best Path =====");

		Room currentRoom = player.getPosition();
		Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(currentRoom, targetRoom);

		double totalDamage = 0.0;
		System.out.println("Path: ");
		while (path.hasNext()) {
			Room room = path.next();
			System.out.println(room.getName());

			if (room.hasEnemies()) {
				double roomDamage = calculateWeight(room);
				totalDamage += roomDamage;
				System.out.println(" [Damage: " + roomDamage + "]");
			}

			if (path.hasNext()) {
				System.out.println(" ->");
			}
		}

		System.out.println("Total Expected Damage: " + totalDamage);
		System.out.println("----------------------------------------");

	}*/

	/*private void manualMovePlayer() {
		Room currentRoom = player.getPosition();
		System.out.println("\n Your current position is " + currentRoom.getName());

		System.out.println("You can move to the next rooms: ");
		ArrayUnorderedList<Room> connectedRooms = this.mission.getBattlefield().getConnectedVertices(currentRoom);
		for (int i = 0; i < connectedRooms.size(); i++) {
			Room room = connectedRooms.getElement(i);
			System.out.println((i + 1) + ". " + room.getName());
		}

		System.out.println("Choose the number of them room: ");
		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();

		if (choice < 1 || choice > connectedRooms.size()) {
			System.out.println("Invalid option");
		} else {
			Room roomSelected = connectedRooms.getElement(choice - 1);
			player.setPosition(roomSelected);
			System.out.println("Player moved to: " + roomSelected.getName());
		}

	}*/

}
