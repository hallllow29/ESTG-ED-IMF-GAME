import entities.*;
import lib.ArrayList;
import lib.ArrayUnorderedList;
import lib.LinkedList;
import lib.Network;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Random;

public class Simulation {

	private final Mission mission;
	private final Player player;
	private boolean gameOver;
	private Room entryPoint;
	private Room extractionPoint;
	private boolean returningToExit;
	private Turn currentTurn;
	private int currentScenario;
	private boolean missionAccomplished;
	private Room nextObjective;

	public Simulation(Mission mission, Player player) {
		this.mission = mission;
		this.player = player;
		this.gameOver = false;
		this.currentTurn = Turn.PLAYER;
		this.missionAccomplished = false;
		this.nextObjective = this.mission.getTarget().getRoom();
	}

	public boolean isGameOver() {
		return this.gameOver;
	}

	public void game() throws ElementNotFoundException, EmptyCollectionException {

		this.entryPoint = findBestEntryPoint();
		this.nextObjective = this.mission.getTarget().getRoom();
		this.player.setPosition(this.entryPoint);

		while (!isGameOver()) {

			playerTurn();

			if (this.player.getPosition().equals(this.extractionPoint)) {
				this.gameOver = true;
			}

			enemyTurn();

		}

		// I wish i could add some sleep for few ms, but it would
		// have a negative impact in performance or avaliation....
		System.out.println("TO CRUZ DIED !!!");
		System.out.println("JUST KIDDING...");
		System.out.println("IT WAS A SIMULATION...");
		System.out.println("...OR WASN'T IT...");
		System.out.println("GAME OVER!");

		Iterator<Enemy> enemies = this.mission.getEnemies().iterator();

		while (enemies.hasNext()) {
			System.out.println(enemies.next());
		}

		System.out.println(player.getCurrentHealth());



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

	public void playerTurn() throws ElementNotFoundException {
		boolean hasEnemies = this.player.getPosition().hasEnemies();
		Room playerPosition = this.player.getPosition();

		if (!this.missionAccomplished && this.player.isAlive() && !hasEnemies) {
			movePlayer(false);
		} else if (this.player.isAlive() && this.mission.isTargetSecured() && !hasEnemies) {
			movePlayer(true);
		}

		if (!playerPosition.equals(this.extractionPoint)) {

			System.out.println("TO CRUZ is moving...");
			if (!this.mission.getEnemies().isEmpty()) {
				System.out.println("BUT the enemies are somewhere...");
			}
			// "o jogo se me sequência de ações"
			scenariosSitiuations(Turn.PLAYER);
			scenariosCase(this.currentScenario);
		}
	}

	public void enemyTurn() throws EmptyCollectionException, ElementNotFoundException {

		// System.out.println("==== ENEMY TURN ====");

		System.out.println("Enemies are moving...");

		Iterator<Enemy> enemies = mission.getEnemies().iterator();

		while (enemies.hasNext()) {
			System.out.println(enemies.next());
		}

		// "o jogo se me sequência de ações"
		scenariosSitiuations(Turn.ENEMY);
		scenariosCase(this.currentScenario);
	}

	void scenariosSitiuations(Turn currentTurn) {
		Room playerPosition = this.player.getPosition();
		boolean playerPositionHasEnemies = playerPosition.hasEnemies();

		// System.out.println("TO CRUZ enters in " + playerPosition.getName() + "...");

		roomSituation(playerPosition, playerPositionHasEnemies);
	}

	void roomSituation(Room playerPosition, Boolean hasEnemies) {
		boolean atTarget = isAtTarget(playerPosition, this.nextObjective);

		if (hasEnemies && atTarget) {
			this.currentScenario = 5;
		} else if (hasEnemies && !atTarget) {

			/**
			 * DESPOLETADO
			 */
			// this.currentScenario = (this.currentTurn == Turn.PLAYER) ? 1 : 3;

			if (this.currentTurn == Turn.ENEMY) {
				this.currentScenario = 1;
			} else if (this.currentTurn == Turn.PLAYER) {
				this.currentScenario = 3;
			}

		} else if (!atTarget) {
			this.currentScenario = 2;
		} else {
			this.currentScenario = 6;
		}

	}

	void scenariosCase(int currentScenario) throws ElementNotFoundException {

		switch (currentScenario) {
			case 1:
				scenarioUM();
				this.currentTurn = Turn.PLAYER;

				// NAO SEI SE DEIXO AQUI, ou dentro do scenarioUM()...
				if (!this.player.isAlive()) {
					this.missionAccomplished = false;
					this.gameOver = true;
				}

				break;
			case 2:
				scenarioDOIS();
				this.currentTurn = Turn.PLAYER;
				break;
			case 3:
				scenarioTRES();
				this.currentTurn = Turn.PLAYER;
				break;
			case 4:
				scenarioQUATRO();
				this.currentTurn = Turn.ENEMY;
				break;
			case 5:
				scenarioCINCO();
				this.currentTurn = Turn.PLAYER;
				break;
			case 6:
				scenarioSEIS();
				this.currentTurn = Turn.PLAYER;
				break;
		}
	}

	private void scenarioUM() {
		Room playerPosition = this.player.getPosition();
		Iterator<Enemy> enemies = mission.getEnemies().iterator();
		boolean enemiesRemained = playerPosition.hasEnemies();

		System.out.println("[<< SCENARIO 1 START>> ]");

		System.out.println("==== PLAYER TURN ====");
		System.out.println("TO CRUZ enters in " + playerPosition.getName() + "...");
		System.out.println("TO CRUZ makes contact with ENEMIES...");
		System.out.println("AND has priority of attack over ENEMIES...");

		try {
			playerConfronts(enemies);
		} catch (EmptyCollectionException | ElementNotFoundException e) {
			System.out.println(e.getMessage());
		}
		System.out.println("==== ENEMY TURN ====");

		if (!enemiesRemained) {
			System.out.println("ENEMIES survived the attack.");
			System.out.println("ENEMIES are moving...");
			moveEnemiesNotInSameRoom();
		} else {
			System.out.println("TO CRUZ eliminated all ENEMIES in..." + playerPosition.getName() + "...");
		}

		System.out.println("[<< SCENARIO 1 END >>]");
	}

	private void scenarioDOIS() {
		Room playerPosition = this.player.getPosition();

		System.out.println("[<< SCENARIO 2 START >>]");

		System.out.println("==== PLAYER TURN =====");
		System.out.println("TO CRUZ enters in " + playerPosition.getName() + "...");
		System.out.println("AND the room TO CRUZ entered is clear...");

		if (!this.mission.getEnemies().isEmpty()) {
			System.out.println("BUT the enemies are somewhere...");
		}
		if (playerPosition.hasItems()) {
			System.out.println("AND checks if he needs to use the item immediately...");
			gatherItems(playerPosition);
		}

		System.out.println("==== ENEMY TURN ====");
		System.out.println("Enemies are moving...");
		moveEnemies();

		System.out.println("[<< SCENARIO 2 ENDED >>]");
	}

	private void scenarioTRES() {
		Room playerPosition = this.player.getPosition();
		Iterator<Enemy> enemies = this.mission.getEnemies().iterator();

		System.out.print("[<< SCENARIO 3 START >>]");

		System.out.println("==== ENEMY TURN ====");
		System.out.println("ENEMIES enter in " + playerPosition.getName() + "...");
		System.out.println("ENEMIES make contact with TO CRUZ...");
		System.out.println("AND have priority of attack over TO CRUZ...");

		enemiesConfronts(this.player);
		if (playerPosition.hasEnemies()) {
			try {
				playerConfronts(enemies);
			} catch (EmptyCollectionException | ElementNotFoundException e) {
				System.out.println(e.getMessage());
			}
		}
		System.out.println("[<< SCENARIO 3 ENDED >>]");
	}

	private void scenarioQUATRO() {

		System.out.println("[<< SCENARIO 4 START >>]");

		System.out.println("==== PLAYER TURN ====");
		System.out.println("TO CRUZ seems to be injured...");
		System.out.println("TO CRUZ halts and is checking BackPack...");
		try {
			System.out.println(this.player.useMediKit());
		} catch (EmptyCollectionException e) {
			System.err.println(e.getMessage());
		}

		System.out.println("[<< SCENARIO 4 END >>]");
	}

	private void scenarioCINCO() {
		Room playerPosition = this.player.getPosition();
		Iterator<Enemy> enemies = mission.getEnemies().iterator();

		System.out.println("[<< SCENARIO 5 START >>]");

		System.out.println("==== PLAYER TURN ====");
		System.out.println("TO CRUZ enters in " + playerPosition.getName() + "...");
		System.out.println("AND in that room there is the TARGET...");
		System.out.println("TO CRUZ makes contact with ENEMIES...");
		System.out.println("AND has priority of attack over ENEMIES...");
		try {
			playerConfronts(enemies);
		} catch (EmptyCollectionException | ElementNotFoundException e) {
			System.out.println(e.getMessage());
		}

		if (!playerPosition.hasEnemies()) {
			System.out.println("TO CRUZ eliminated all ENEMIES in..." + player.getName() + "...");
		}

		System.out.println("==== ENEMIES TURN ====");
		System.out.println("ENEMIES are moving...");
		moveEnemiesNotInSameRoom();

		System.out.println("[<< SCENARIO 5 END >>]");
	}

	private void scenarioSEIS() throws ElementNotFoundException {
		Room playerPosition = this.player.getPosition();

		System.out.println("[<< SCENARIO 6 START >>]");

		System.out.println("==== PLAYER TURN ====");
		System.out.println("TO CRUZ enters in " + playerPosition.getName() + "...");
		System.out.println("AND in that room there is the TARGET...");
		System.out.println("LOOK AT THAT, it is clear...");
		if (playerPosition.hasItems()) {
			System.out.println("AND checks if he needs to use the item immediately...");
			gatherItems(playerPosition);
		}
		boolean secured = true;
		this.mission.setTargetSecured(secured);

		this.returningToExit = true;
		this.extractionPoint = bestExtractionPoint();
		this.nextObjective = this.extractionPoint;
		movePlayer(returningToExit);

		System.out.println("[<< SCENARIO 6 END >>]");

	}

	private void movePlayer(boolean toExtraction) throws ElementNotFoundException {
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.nextObjective;
		Iterator<Room> path = null;

		this.getBestPath();

		if (toExtraction) {
			// Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(playerPosition,bestExtractionPoint());
			path = mission.getBattlefield().iteratorShortestPath(playerPosition, this.extractionPoint);

		} else {
			// Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(playerPosition, targetPosition);
			path = mission.getBattlefield().iteratorShortestPath(playerPosition, targetPosition);
		}
		path.next();

		if (path.hasNext()) {

			Room toRoom = path.next();
			System.out.println("TO CRUZ plans to go from\t" + playerPosition.getName() + "\tto\t" + toRoom.getName() + "...");
			this.player.setPosition(toRoom);

		} else {
			if (this.player.isAlive() && this.mission.isTargetSecured() && player.getPosition().equals(this.entryPoint)) {
				this.gameOver = true;
			}
		}
	}

	private boolean isAtTarget(Room playerPosition, Room targetPosition) {
		return playerPosition.equals(targetPosition);
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

	private void playerConfronts(Iterator<Enemy> enemies) throws EmptyCollectionException, ElementNotFoundException {
		final int ZERO = 0;
		ArrayUnorderedList<Enemy> enemiesDefeated = new ArrayUnorderedList<>();
		String playerName = this.player.getName();
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();
		int playerAttack = this.player.getFirePower();
		int totalEnemiesInRoom = 0;
		LinkedList<Enemy> enemiesToRemove = new LinkedList<>();

		for (Enemy enemy : mission.getEnemies()) {
			if (enemy.getPosition().equals(playerPosition)) {
				System.out.println();
				System.out.println(player.getName() + " attack " + enemy.getName() + " dealing " + playerAttack + " damage");
				enemy.takesDamageFrom(playerAttack);
			}

			if (!enemy.isAlive()) {
				System.out.println(enemy.getName() + " defeated");
				enemiesDefeated.addToRear(enemy);
			}

		}

		for (Enemy defeated : enemiesDefeated) {
			mission.getEnemies().remove(defeated);
			defeated.setPosition(null);
		}
















		/*while (enemies.hasNext()) {

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
				} else {
					totalEnemiesInRoom++;
				}
			}

		}*/

		// boolean stillHasEnemies = false;
		// Iterator<Enemy> checkEnemies = this.mission.getEnemies().iterator();
		//
		// while (checkEnemies.hasNext()) {
		// 	Enemy survivedEnemy = checkEnemies.next();
		//
		// 	if (survivedEnemy.getPosition().equals(playerPosition)) {
		// 		stillHasEnemies = true;
		// 		break;
		// 	}
		// }
		//
		// playerPosition.setEnemies(stillHasEnemies);

		// if (totalEnemiesInRoom <= ZERO) {
		// 	playerPosition.setEnemies(false);
		// }
	}

	private boolean playerNeedsRecoveryItem() {

		int playerCriticalHealth = 80;

		/**
		 * Se TO CRUZ decide usar um kit...
		 */
		return this.player.getCurrentHealth() <= playerCriticalHealth && this.player.hasRecoveryItem();
	}

	private void moveEnemies() {
		/**
		 * Movimentação dos inimigos: Os inimigos movimentam-se aleatoriamente
		 */

		ArrayList<Room> possible_moves;

		Random random = new Random();

		for (Enemy enemyObj : this.mission.getEnemies()) {

			possible_moves = getPossibleMoves(enemyObj.getPosition());

			if (!possible_moves.isEmpty()) {

				// For current Room
				Room enemyPosition = enemyObj.getPosition();
				enemyPosition.setEnemies(false);

				int random_index = random.nextInt(possible_moves.size());
				Room next_room = possible_moves.getElement(random_index);

				// For next Room
				enemyObj.setPosition(next_room);
				next_room.setEnemies(true);
			}
		}
	}

	private void moveEnemiesNotInSameRoom() {
		Room playerPosition = this.player.getPosition();
		for (Enemy enemyObj : this.mission.getEnemies()) {

			Room enemyPosition = enemyObj.getPosition();

			if (!enemyPosition.equals(playerPosition)) {
				moveEnemy(enemyObj);
			}
		}
	}

	private void moveEnemy(Enemy enemy) {
		Room enemyPosition = enemy.getPosition();
		ArrayList<Room> possibleMoves = getPossibleMoves(enemyPosition);

		if (!possibleMoves.isEmpty()) {

			// For current Room
			enemyPosition.setEnemies(false);

			Random random = new Random();
			int random_index = random.nextInt(possibleMoves.size());

			Room next_room = possibleMoves.getElement(random_index);

			// For next Room
			enemy.setPosition(next_room);
			next_room.setEnemies(true);

		}
	}

	private ArrayUnorderedList<Room> getPossibleMoves(Room fromRoom) {

		ArrayUnorderedList<Room> possibleMoves = new ArrayUnorderedList<>();

		try {
			Iterator<Room> bfsIterator = mission.getBattlefield().iteratorBFS(fromRoom);
			int bfsLevel = 0;

			while (bfsIterator.hasNext() && bfsLevel <= 2) {

				Room toRoom = bfsIterator.next();

				if (bfsLevel == 1 || bfsLevel == 2) {

					if (toRoom != null) {
						possibleMoves.addToRear(toRoom);
					}
				}

				bfsLevel++;
			}

		} catch (EmptyCollectionException e) {
			System.err.println(e.getMessage());
		}

		return possibleMoves;
	}

	private void gatherItems(Room room) {
		Iterator<Item> itemIterator = mission.getItems().iterator();
		while (itemIterator.hasNext()) {

			Item item = itemIterator.next();

			if (item == null) {
				itemIterator.remove();
				if (itemIterator.hasNext()) {
					item = itemIterator.next();
				} else {
					break;
				}
			}

			if (item.getPosition() != null && item.getPosition().equals(room)) {
				if (item instanceof MediKit) {
					this.player.addKitToBackPack((MediKit) item);
					System.out.println("TO CRUZ adds MediKit to his backPack...");
				} else if (item instanceof Kevlar) {
					this.player.equipKevlar((Kevlar) item);
					System.out.println("TO CRUZ equips Kevlar, current health " + this.player.getCurrentHealth() + "...");
				}
				itemIterator.remove();
			}
		}
		room.setItemInRoom(false);
	}

	private void enemiesConfronts(Player player) {
		ArrayUnorderedList<Enemy> enemiesDefeated = new ArrayUnorderedList<>();
		String playerName = player.getName();
		Room playerPosition = player.getPosition();

		for (Enemy enemy : mission.getEnemies()) {
			if (enemy.getPosition().equals(playerPosition)) {
				System.out.println(enemy.getName() + " attack " + player.getName() + " dealing " + enemy.getFirePower() + " damage");
				player.takesDamageFrom(enemy.getFirePower());
			}

		}





































		/*while (enemies.hasNext()) {


			Enemy enemy = enemies.next();
			String enemyName = enemy.getName();
			Room enemyPosition = enemy.getPosition();
			int enemyAttack = enemy.getFirePower();

			if (enemyPosition.equals(playerPosition)) {

				player.takesDamageFrom(enemyAttack);
				System.out.println("\n\n\n\n");
				System.out.println(enemyName + " is attacking " + playerName + " with " + enemy.getFirePower() + " damage.........................................................................");
				System.out.println("\n\n\n\n");

				// Scenario 4: O Tó Cruz utiliza kits de vida DURANTE
				// o combate consumindo a sua fase de jogador...
				if (playerNeedsRecoveryItem()) {
					scenarioQUATRO();
				}
				if (!player.isAlive()) {
					this.gameOver = true;
					Room purgatory = new Room("");
					this.player.setPosition(purgatory);
					System.out.println("HE DIED DAMN IT!!!");
				}
			}
		}*/

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
		} else {
			return weight;
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

	private void getBestPath() throws ElementNotFoundException {
		Room targetRoom;

		this.updateWeightsForEnemies();

		if (returningToExit) {
			targetRoom = bestExtractionPoint();
			System.out.println("Calculating best extraction point");
			this.extractionPoint = targetRoom;
		} else {
			targetRoom = mission.getTarget().getRoom();
			System.out.println("Calculating best path to target...");
		}

		System.out.println("Path to destiny: ");
		this.displayPath(player.getPosition(), targetRoom);

	}

	private void displayPath(Room from_Room, Room to_Room) throws ElementNotFoundException {
		Iterator<Room> bestPath = mission.getBattlefield().iteratorShortestPath(from_Room, to_Room);
		Room targetPosition = this.mission.getTarget().getRoom();
		Room extractionPosition = this.extractionPoint;
		// Room nextObjective = this.objective ???? yeah maybe later,

		Room nextRoom = bestPath.next();
		System.out.println("TO CRUZ\t->\t" + nextRoom.getName());

		while (bestPath.hasNext()) {
			nextRoom = bestPath.next();
			if (nextRoom.equals(this.entryPoint) || nextRoom.equals(extractionPosition) || nextRoom.equals(targetPosition)) {
				System.out.println("TARGET\t->\t" + nextRoom.getName());
			} else {
				System.out.println("\t\t\t" + nextRoom.getName());
			}
		}
		System.out.println();
	}
}