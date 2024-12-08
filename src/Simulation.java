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
	private Network<Room> battlefield;
	private final Player player;
	private boolean gameOver;
	private Room entryPoint;
	private Room extractionPoint;
	private boolean returningToExit;
	private Turn currentTurn;
	private ScenarioNr currentScenario;
	private boolean missionAccomplished;
	private Room nextObjective;
	private LinkedList<Enemy> enemies;

	public Simulation(Mission mission, Player player) {
		this.mission = mission;
		this.battlefield = new Network<>();
		this.battlefield = this.mission.getBattlefield();
		this.player = player;
		this.currentTurn = Turn.PLAYER;
		this.missionAccomplished = false;
		this.enemies = new LinkedList<>();
		this.enemies = this.mission.getEnemies();
	}

	public void renderSimulation(Player player, Target target) throws ElementNotFoundException {
		setNextObjective(target.getRoom());
		setEntryPoint(findBestEntryPoint());
		player.setPosition(entryPoint);
		this.missionAccomplished = false;
		this.currentTurn = Turn.PLAYER;
		this.gameOver = false;
	}

	public void setEntryPoint(Room entryPoint) {
		this.entryPoint = entryPoint;
	}

	public void setNextObjective(Room nextObjective) {
		this.nextObjective = nextObjective;
	}

	public boolean isGameOver() {
		return this.gameOver;
	}

	public boolean firstStage() {
		return player.isAlive() && !missionAccomplished;
	}

	public boolean finalStage() {
		return player.isAlive() && mission.isTargetSecured();
	}

	public void game() throws ElementNotFoundException, EmptyCollectionException {

		renderSimulation(this.player, this.mission.getTarget());

		while (!isGameOver()) {

			if (this.currentTurn == Turn.PLAYER) {
				playerTurn();
			}

			if (isMissionAccomplished()) {
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

		Iterator<Enemy> enemies = this.enemies.iterator();

		while (enemies.hasNext()) {
			System.out.println(enemies.next());
		}

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

	public void setNextTurn(Turn nextTurn) {
		this.currentTurn = nextTurn;
	}

	public Turn getCurrentTurn() {
		return this.currentTurn;
	}

	public void playerTurn() throws ElementNotFoundException {
		Room playerPosition = player.getPosition();

		// if (firstStage()) {
		// 	movePlayer(false);
		// } else if (finalStage()) {
		// 	movePlayer(true);
		// }

		movePlayer();

		System.out.println("TO CRUZ is moving...");

		if (!enemies.isEmpty()) {
			System.out.println("BUT the enemies are somewhere...");
		}
		System.out.println("\nTO CRUZ leaves " + playerPosition.getName() + "...");

		// "o jogo se me sequência de ações"
		scenariosSituations();
		scenariosCase(this.currentScenario);
	}

	public void enemyTurn() throws ElementNotFoundException {

		if (player.getPosition().hasEnemies()) {
			moveEnemiesNotInSameRoom();
		} else {
			moveEnemies();
		}
		System.out.println("Enemies are moving...");

		String enemyTurnOutput = "";

		Iterator<Enemy> enemies = this.enemies.iterator();
		while (enemies.hasNext()) {
			enemyTurnOutput += "\n" + enemies.next();
		}
		System.out.println(enemyTurnOutput);

		scenariosSituations();
		scenariosCase(this.currentScenario);
	}

	void scenariosSituations() {
		Room playerPosition = player.getPosition();
		boolean playerPositionHasEnemies = playerPosition.hasEnemies();
		roomSituation(playerPosition, playerPositionHasEnemies);
	}

	void roomSituation(Room playerPosition, Boolean hasEnemies) {

		boolean atTarget = isAtTarget(playerPosition, this.nextObjective);

		if (hasEnemies && atTarget) {
			setNextScenario(ScenarioNr.FIVE);

		} else if (hasEnemies && !atTarget) {

			/**
			 * DESPOLETADO
			 */
			setNextScenario(getCurrentTurn() == Turn.PLAYER ? ScenarioNr.ONE : ScenarioNr.THREE);

		} else if (!hasEnemies && !atTarget) {
			setNextScenario(ScenarioNr.TWO);
		} else {
			setNextScenario(ScenarioNr.SIX);
		}
	}

	public void setNextScenario(ScenarioNr nextScenario) {
		this.currentScenario = nextScenario;
	}

	public void scenariosCase(ScenarioNr nextScenario) throws ElementNotFoundException {

		switch (nextScenario) {
			case TWO:
				scenarioDOIS();
				setNextTurn(Turn.PLAYER);
				break;
			case ONE:
				scenarioUM();
				// setNextTurn(Turn.PLAYER);

				// NAO SEI SE DEIXO AQUI, ou dentro do scenarioUM()...
				if (!this.player.isAlive()) {
					this.missionAccomplished = false;
					this.gameOver = true;
				}
				break;
			case THREE:
				scenarioTRES();
				break;
			case FOUR:
				scenarioQUATRO();
				setNextTurn(Turn.ENEMY);
				break;
			case FIVE:
				scenarioCINCO();
				setNextTurn(Turn.PLAYER);
				break;
			case SIX:
				scenarioSEIS();
				setNextTurn(Turn.PLAYER);
				break;
		}
	}

	private void scenarioUM() {
		Room playerPosition = player.getPosition();
		boolean enemiesRemained = playerPosition.hasEnemies();
		String scenarioUMstart = "";

		scenarioUMstart +=
			"\nTO CRUZ enters in " + playerPosition.getName() + "..." +
				"\n\n[<< SCENARIO 1 START >>]" +
				"\nTO CRUZ makes contact with ENEMIES..." +
				"\nAND has priority of attack over ENEMIES..." +
				"\n===== PLAYER  TURN =====";
		System.out.println(scenarioUMstart);

		playerConfronts();


		if (enemiesRemained) {
			System.out.println("=====  ENEMY TURN  =====");
			System.out.println("ENEMIES in " + playerPosition.getName() + " survived the attack...");
			System.out.println("ENEMIES not in " + playerPosition.getName() + " are moving...");

			if (!this.enemies.isEmpty()) {
				System.out.println("BUT the enemies are somewhere...");
			}

			setNextTurn(Turn.ENEMY);
		} else {
			System.out.println("TO CRUZ eliminated all ENEMIES in..." + playerPosition.getName() + "...");

			// RECOLHE ITEMS.
			if (playerPosition.hasItems()) {
				gatherItems(playerPosition);
			}

			setNextTurn(Turn.PLAYER);
		}
		System.out.println("[<< SCENARIO 1 END >>]");

	}

	private void scenarioDOIS() {
		Room playerPosition = this.player.getPosition();

		System.out.println("TO CRUZ enters in " + playerPosition.getName() + "...");
		System.out.println("\n[<< SCENARIO 2 START >>]");
		System.out.println("AND the room TO CRUZ entered is clear...");
		System.out.println("===== PLAYER  TURN =====");

		// RECOLHE ITEMS...
		if (playerPosition.hasItems()) {
			gatherItems(playerPosition);

			// PODE USAR ITEMS???---
		}

		if (!this.enemies.isEmpty()) {
			System.out.println("BUT the enemies are somewhere...");
		}
		System.out.println("=====  ENEMY TURN  =====");
		System.out.println("[<< SCENARIO 2 ENDED >>]");
	}

	private void scenarioTRES() {
		Room playerPosition = player.getPosition();

		System.out.println("ENEMIES enter in " + playerPosition.getName() + "...");
		System.out.print("\n[<< SCENARIO 3 START >>]");
		System.out.println("ENEMIES make contact with TO CRUZ...");
		System.out.println("AND have priority of attack over TO CRUZ...");
		System.out.println("==== ENEMY TURN ====");

		// Trigger para o scenario 4 se o to cruz precisar de items...
		enemiesConfronts(player);




		/*if (enemiesRemained) {
			System.out.println("=====  ENEMY TURN  =====");
			System.out.println("ENEMIES in " + playerPosition.getName() + " survived the attack...");
			System.out.println("ENEMIES not in " + playerPosition.getName() + " are moving...");

			if (!this.enemies.isEmpty()) {
				System.out.println("BUT the enemies are somewhere...");
			}

			setNextTurn(Turn.ENEMY);
		} else {
			System.out.println("TO CRUZ eliminated all ENEMIES in..." + playerPosition.getName() + "...");

			// RECOLHE ITEMS.
			if (playerPosition.hasItems()) {
				gatherItems(playerPosition);
			}

			setNextTurn(Turn.PLAYER);
		}*/












		System.out.println("SCENARIO 3 ENDED");
	}

	private void scenarioQUATRO() {

		String scenarioQUATROstart = "";

		scenarioQUATROstart += "\n[<< SCENARIO 4 START >>]" +
		"\nTO CRUZ seems to be injured..." +
		"\n===== PLAYER  TURN =====" +
		"\nTO CRUZ halts and is checking BackPack...";

		System.out.println(scenarioQUATROstart);

		try {
			System.out.println(this.player.useMediKit());
		} catch (EmptyCollectionException e) {
			System.err.println(e.getMessage());
		}
		setNextTurn(Turn.ENEMY);
		System.out.println("[<< SCENARIO 4 END >>]");

	}

	private void scenarioCINCO() {
		Room playerPosition = player.getPosition();

		String scenarioCINCOstart = "";
		scenarioCINCOstart +=
			"\nTO CRUZ enters in " + playerPosition.getName() + "..." +
				"\n[<< SCENARIO 5 START >>]" +
				"\nTO CRUZ makes contact with ENEMIES...\n" +
				"\nAND in that room there is the TARGET...\n" +
				"\nAND has priority of attack over ENEMIES..." +
				"\n===== PLAYER  TURN =====";

		System.out.println(scenarioCINCOstart);

		playerConfronts();

		String scenarioCINCOend = "";
		if (!playerPosition.hasEnemies()) {
			scenarioCINCOend +=
				"\nTO CRUZ eliminated all ENEMIES in..." + player.getName() + "...";
		}

		scenarioCINCOend +=
			"\n=====  ENEMY TURN  =====" +
			"\nENEMIES are moving..." +
			"\n[<< SCENARIO 5 END >>]";
		System.out.println(scenarioCINCOend);
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
		Room extractionPoint = bestExtractionPoint(player.getPosition());
		setNextObjective(extractionPoint);
		movePlayer();

		System.out.println("[<< SCENARIO 6 END >>]");

	}

	private void movePlayer() throws ElementNotFoundException {
		String movePlayerOutput = "";
		Room playerPosition = this.player.getPosition();
		Room nextObjective = this.nextObjective;
		Iterator<Room> path;

		this.getBestPath();

		path = this.battlefield.iteratorShortestPath(playerPosition, nextObjective);

		path.next();

		if (path.hasNext()) {

			Room nextPosition = path.next();

			movePlayerOutput += player.getName() + "plans to go from [" +
				playerPosition.getName() + "] -----> [" + nextPosition.getName() + "]";
			this.player.setPosition(nextPosition);

		} else {
			if (isMissionAccomplished()) {
				this.gameOver = true;
			}
		}

		System.out.println(movePlayerOutput);
	}

	private boolean isAtTarget(Room playerPosition, Room targetPosition) {
		return playerPosition.equals(targetPosition);
	}

	private boolean isMissionAccomplished() {
		Room playerPosition = player.getPosition();
		return player.isAlive() && mission.isTargetSecured() && playerPosition.equals(this.nextObjective);
	}

	public Room findBestEntryPoint() throws ElementNotFoundException {
		Network<Room> battlefield = mission.getBattlefield();
		Iterator<Room> entryPoints = mission.getEntryExitPoints().iterator();
		double minimalDamage = Double.MAX_VALUE;
		Room bestEntryPoint = null;

		while (entryPoints.hasNext()) {

			Room entryPoint = entryPoints.next();
			Iterator<Room> entryPointsPaths = battlefield.iteratorShortestPath(entryPoint, this.nextObjective);
			double calculatedDamage = calculatePathDamage(entryPointsPaths);
			if (calculatedDamage < minimalDamage) {
				minimalDamage = calculatedDamage;
				bestEntryPoint = entryPoint;
			}
		}
		return bestEntryPoint;
	}

	private void playerConfronts() {
		String playerConfrontsOutput = "";
		final int NONE = 0;
		Iterator<Enemy> enemies = this.enemies.iterator();
		String playerName = player.getName();
		Room playerPosition = player.getPosition();
		int playerAttack = player.getFirePower();

		while (enemies.hasNext()) {

			Enemy enemy = enemies.next();
			Room enemyPosition = enemy.getPosition();

			if (enemyPosition.equals(playerPosition)) {

				enemy.takesDamageFrom(playerAttack);

				playerConfrontsOutput += playerName +
					" is attacking " + enemy.getName() + "...";

				if (!enemy.isAlive()) {
					playerConfrontsOutput += "ENEMY " + enemy.getName() +
						" suffered " + playerAttack +
						"AND is now DEAD";
					enemies.remove();
					enemyPosition.removeEnemy();
				}
			}

		}

		if (playerPosition.getTotalEnemies() <= NONE) {
			playerPosition.setEnemies(false);
		}

		System.out.println(playerConfrontsOutput);

	}

	private boolean playerNeedsRecoveryItem() {

		int playerCriticalHealth = 80;

		/**
		 * Se TO CRUZ decide usar um kit...
		 */
		return this.player.getCurrentHealth() <= playerCriticalHealth && this.player.hasRecoveryItem();
	}

	private void moveEnemies() {
		for (Enemy enemy : this.enemies) {
			moveEnemy(enemy);
		}
	}

	private void moveEnemiesNotInSameRoom() {
		Room playerPosition = player.getPosition();
		for (Enemy enemy : enemies) {
			Room enemyPosition = enemy.getPosition();
			if (!enemyPosition.equals(playerPosition)) {
				moveEnemy(enemy);
			}
		}
	}

	private void moveEnemy(Enemy enemy) {
		Room enemyPosition = enemy.getPosition();
		ArrayList<Room> possibleMoves = getPossibleMoves(enemyPosition);
		final int NONE = 0;

		if (!possibleMoves.isEmpty()) {

			System.out.println("\nENEMY " + enemy.getName() + " leaves " + enemyPosition.getName() + "...");
			enemyPosition.removeEnemy();

			if (enemyPosition.getTotalEnemies() <= NONE) {
				enemyPosition.setEnemies(false);
			}

			Random random = new Random();
			int random_index = random.nextInt(possibleMoves.size());

			Room nextPosition = possibleMoves.getElement(random_index);

			System.out.println("AND enters in " + nextPosition.getName() + "...");
			enemy.setPosition(nextPosition);
			nextPosition.addEnemy();
			nextPosition.setEnemies(true);
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
		final int NONE = 0;
		String gatherItemsOutput = "";
		Room playerPosition = player.getPosition();
		Iterator<Item> itemIterator = mission.getItems().iterator();

		while (itemIterator.hasNext()) {

			Item item = itemIterator.next();

			// if (item == null) {
			// 	itemIterator.remove();
			// 	if (itemIterator.hasNext()) {
			// 		item = itemIterator.next();
			// 	} else {
			// 		break;
			// 	}
			// }

			if (item.getPosition() != null && item.getPosition().equals(room)) {
				if (item instanceof MediKit) {
					this.player.addKitToBackPack((MediKit) item);
					gatherItemsOutput += player.getName() +
						" adds " + item.getName() +
						" to his BackPack...";
				} else if (item instanceof Kevlar) {
					this.player.equipKevlar((Kevlar) item);
					gatherItemsOutput += player.getName() +
						" equips " + item.getName() +
						" , current health " + player.getCurrentHealth() + "...";
				}
				playerPosition.removeItem();
				itemIterator.remove();
			}
		}

		if (playerPosition.getTotalItems() <= NONE) {
			playerPosition.setItemsInRoom(false);
		}

		System.out.println(gatherItemsOutput);
	}

	private void enemiesConfronts(Player player) {
		String enemiesConfronts = "";
		Iterator<Enemy> enemies = this.enemies.iterator();
		String playerName = player.getName();
		Room playerPosition = player.getPosition();

		while (enemies.hasNext()) {

			Enemy enemy = enemies.next();
			Room enemyPosition = enemy.getPosition();
			int enemyAttack = enemy.getFirePower();

			if (enemyPosition.equals(playerPosition)) {

				player.takesDamageFrom(enemyAttack);
				enemiesConfronts += "\n" + enemy.getName() +
					" is attacking " + playerName +
					" with " + enemyAttack + " damage...";

				// ScenarioNr 4: O Tó Cruz utiliza kits de vida DURANTE
				// o combate consumindo a sua fase de jogador...
				if (playerNeedsRecoveryItem()) {
					scenarioQUATRO();
					return;
				}

				if (!player.isAlive()) {
					this.gameOver = true;
					Room purgatory = new Room("");
					// setNextObjective(purgatory);
				}
			}

		}

		System.out.println(enemiesConfronts);
	}

	private Room bestExtractionPoint(Room playerPosition) throws ElementNotFoundException {
		Room bestExtractionPoint = null;
		Iterator<Room> extractionPoints = mission.getEntryExitPoints().iterator();
		double minimalDamage = Double.MAX_VALUE;
		double calculatedDamage = 0.0;

		while (extractionPoints.hasNext()) {

			Room extractionPoint = extractionPoints.next();
			Iterator<Room> extractionPointsPaths = battlefield.iteratorShortestPath(playerPosition, extractionPoint);
			calculatedDamage = calculatePathDamage(extractionPointsPaths);

			if (calculatedDamage < minimalDamage) {
				minimalDamage = calculatedDamage;
				bestExtractionPoint = extractionPoint;

			}
		}
		return bestExtractionPoint;
	}

	private double calculatePathDamage(Iterator<Room> path) {
		double totalDamage = 0;
		int playerHealth = player.getCurrentHealth();
		int playerMaxHealth = 100;

		while (path.hasNext()) {

			Room room = path.next();

			if (room.hasEnemies()) {

				for (Enemy enemy : enemies) {
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
		// TODO: Stack issue? need to ? this...

		for (Room room : this.battlefield.getVertices()) {
			for (Room connectedRoom : this.battlefield.getConnectedVertices(room)) {
				double newWeight = calculateWeight(connectedRoom);
				this.battlefield.addEdge(room, connectedRoom, newWeight);
			}
		}
	}

	private double calculateWeight(Room room) {
		double weight = 0.0;
		int totalDamage = 0;

		if (room.getTotalEnemies() > 0) {

			for (Enemy enemy : this.enemies) {
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
		Room playerPosition = this.player.getPosition();
		String bestPathOutput = "";

		this.updateWeightsForEnemies();

		if (returningToExit) {
			this.nextObjective = bestExtractionPoint(playerPosition);
			bestPathOutput += "\nCalculating best path to EXTRACTION POINT...";
		} else {
			bestPathOutput += "\nCalculating best path to OBJECTIVE...";
		}
		bestPathOutput += "\nPath to OBJECTIVE";

		// System.out.println(bestPathOutput);

		this.displayPath(playerPosition, this.nextObjective);

		System.out.println(bestPathOutput);
	}

	private void displayPath(Room fromPosition, Room toPosition) throws ElementNotFoundException {
		Iterator<Room> bestPath = this.battlefield.iteratorShortestPath(fromPosition, toPosition);

		Room nextRoom = bestPath.next();
		String displayPathOutput = "TO CRUZ\t\t->\t" + nextRoom.getName();

		while (bestPath.hasNext()) {
			nextRoom = bestPath.next();
			if (!nextRoom.equals(this.nextObjective)) {
				displayPathOutput += "\n\t\t\t\t" + nextRoom.getName();
			} else {
				displayPathOutput += "\nOBJECTIVE\t->\t" + this.nextObjective.getName();
			}
		}

		System.out.println(displayPathOutput + "\n");
	}

}