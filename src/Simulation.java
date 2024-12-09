import entities.*;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.graphs.CustomNetwork;
import lib.graphs.Network;
import lib.lists.ArrayList;
import lib.lists.ArrayUnorderedList;
import lib.lists.LinkedList;

import java.util.Iterator;
import java.util.Random;

public abstract class Simulation {

	private final Mission mission;
	private CustomNetwork<Room> battlefield;
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
	private Room closestItem;
	private Report report;

	public Simulation(Mission mission, Player player, Report report) {
		this.mission = mission;
		this.battlefield = new CustomNetwork<>();
		this.battlefield = this.mission.getBattlefield();
		this.player = player;
		this.currentTurn = Turn.PLAYER;
		this.missionAccomplished = false;
		this.enemies = new LinkedList<>();
		this.enemies = this.mission.getEnemies();
		this.report = report;
	}

	protected abstract void movePlayer() throws ElementNotFoundException, EmptyCollectionException;

	public abstract void game() throws ElementNotFoundException, EmptyCollectionException;

	protected void gameFlow() throws EmptyCollectionException, ElementNotFoundException {
		while (!isGameOver()) {

			if (this.getCurrentTurn() == Turn.PLAYER) {
				playerTurn();
			}

			if (isMissionAccomplished()) {
				this.setGameOver(true);
			}


			enemyTurn();


		}

		addStatusToReport();

		System.out.println("TO CRUZ DIED !!!");
		System.out.println("JUST KIDDING...");
		System.out.println("IT WAS A SIMULATION...");
		System.out.println("...OR WASN'T IT...");
		System.out.println("GAME OVER!");

		Iterator<Enemy> enemies = this.getEnemies().iterator();

		while (enemies.hasNext()) {
			Enemy enemy = enemies.next();
			System.out.println(enemy);
			this.getReport().addEnemy(enemy.getName());
		}
	}

	protected void playerTurn() throws ElementNotFoundException, EmptyCollectionException {
		Room playerPosition = this.getPlayer().getPosition();

		String playerTurnOutput = "";
		if (!playerPosition.hasEnemies() || this.getCurrentScenario() == ScenarioNr.TWO) {
			playerTurnOutput +=
					"\n" + this.getPlayer().getName() + " is moving..." +
							"\n" + this.getPlayer().getName() + " leaves " + playerPosition.getName() + "...";

			movePlayer();
		} else {
			playerConfronts();
		}

		System.out.println(playerTurnOutput);

		// "o jogo se me sequência de ações"
		scenariosSituations();
		scenariosCase(this.getCurrentScenario());
	}

	public boolean isReturningToExit() {
		return this.returningToExit;
	}

	public void renderAutomaticSimulation(Player player, Target target) throws ElementNotFoundException {
		setNextObjective(target.getRoom());
		setEntryPoint(findBestEntryPoint());
		player.setPosition(entryPoint);
		this.missionAccomplished = false;
		this.currentTurn = Turn.PLAYER;
		this.gameOver = false;
	}

	public void renderManualSimulation(Room target) {
		setNextObjective(target);
		this.missionAccomplished = false;
		this.currentTurn = Turn.PLAYER;
		this.gameOver = false;
	}

	public Report getReport() {
		return this.report;
	}

	public Network<Room> getBattlefield() {
		return this.battlefield;
	}

	public void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	public ScenarioNr getCurrentScenario() {
		return this.currentScenario;
	}

	public LinkedList<Enemy> getEnemies() {
		return this.enemies;
	}

	public Player getPlayer() {
		return this.player;
	}

	public Mission getMission() {
		return this.mission;
	}

	public Room getNextObjective() {
		return this.nextObjective;
	}

	public void setEntryPoint(Room entryPoint) {
		this.entryPoint = entryPoint;
	}

	public void setNextObjective(Room nextObjective) {
		this.nextObjective = nextObjective;
	}

	protected boolean isGameOver() {
		return this.gameOver;
	}

	protected void setNextTurn(Turn nextTurn) {
		this.currentTurn = nextTurn;
	}

	protected Room getEntryPoint() {
		return this.entryPoint;
	}

	protected Turn getCurrentTurn() {
		return this.currentTurn;
	}

	protected void enemyTurn() throws ElementNotFoundException, EmptyCollectionException {
		if (this.currentScenario == ScenarioNr.TWO) {
			movePlayer();
			setNextTurn(Turn.ENEMY);
			// NAO FAZ SENTIDO, mas é que eu entendo!
		}
		scenariosSituations();
		scenariosCase(this.currentScenario);
	}

	protected void scenariosSituations() {
		Room playerPosition = player.getPosition();
		boolean playerPositionHasEnemies = playerPosition.hasEnemies();
		roomSituation(playerPosition, playerPositionHasEnemies);
	}

	protected void roomSituation(Room playerPosition, Boolean hasEnemies) {

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

	protected void setNextScenario(ScenarioNr nextScenario) {
		this.currentScenario = nextScenario;
	}

	protected void scenariosCase(ScenarioNr nextScenario) throws ElementNotFoundException, EmptyCollectionException {

		switch (nextScenario) {
			case TWO:
				scenarioDOIS();
				setNextTurn(Turn.ENEMY);
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
				setNextTurn(Turn.PLAYER);
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
		boolean enemiesRemained;

		String scenarioUMstart =
			"\nTO CRUZ is in " + playerPosition.getName() + "..." +
				"\n|========== [<< SCENARIO 1 START >>] ==========" +
				"\n|\tTO CRUZ makes contact with ENEMIES..." +
				"\n|\tAND has priority of attack over ENEMIES..." +
				"\n|" +
				"\n|\t-------------- PLAYER  TURN --------------";
		System.out.print(scenarioUMstart);

		playerConfronts();

		enemiesRemained = playerPosition.hasEnemies();
		String scenarioUMend = "";

		if (enemiesRemained) {
			scenarioUMend +=
				"\n|" +
					"\n|\t--------------  ENEMY TURN  --------------" +
					"\n|\tENEMIES in " + playerPosition.getName() + " survived the attack...";

			if (!this.enemies.isEmpty()) {
				scenarioUMend +=
					"\n|\tBUT the enemies are somewhere..." +
						"\n|\tENEMIES not in " + playerPosition.getName() + " are moving...";
			}
			setNextTurn(Turn.ENEMY);

		} else {
			scenarioUMend +=
				"\n|\tTO CRUZ eliminated all ENEMIES in " + playerPosition.getName() + "...";

			// RECOLHE ITEMS.
			if (playerPosition.hasItems()) {
				scenarioUMend += gatherItems(playerPosition);
			}

			setNextTurn(Turn.PLAYER);
		}
		scenarioUMend +=
			"\n|" +
				"\n|========== [<< SCENARIO 1  END  >>] ==========";
		System.out.println(scenarioUMend);
	}

	private void scenarioDOIS() {
		Room playerPosition = player.getPosition();

		String scenarioDOISstart =
			"\n" + player.getName() + " is in " + playerPosition.getName() + "..." +
				"\n|========== [<< SCENARIO 2 START >>] ==========" +
				"\n|\tAND the room " + player.getName() + " entered is clear..." +
				"\n|" +
				"\n|\t-------------- PLAYER  TURN --------------";
		System.out.print(scenarioDOISstart);

		// RECOLHE ITEMS...
		String scenarioDOISend =
			"\n|\t"
				+ player.getName() + " searches the " + player.getPosition() + "...";
		if (playerPosition.hasItems()) {
			scenarioDOISend += gatherItems(playerPosition);
			// PODE USAR ITEMS???---
		}

		scenarioDOISend +=
			"\n|" +
				"\n|\t-------------- ENEMY  TURN  --------------";

		if (!this.enemies.isEmpty()) {
			scenarioDOISend += "\n|\tBUT the enemies are somewhere..." +
				"\n|\tEnemies are moving...";

			moveEnemies();

		} else {
			scenarioDOISend += "\n|\tBUT there are no enemies left...";
		}
		scenarioDOISend +=
			"\n|" +
				"\n|========== [<< SCENARIO 2  END  >>] ==========";

		System.out.println(scenarioDOISend);
	}

	private void scenarioTRES() {
		Room playerPosition = player.getPosition();

		String scenarioTRESstart =
			"\nENEMIES enter in " + playerPosition.getName() + "..." +
				"\n|========== [<< SCENARIO 3 START >>] ==========" +
				"\n|\tENEMIES make contact with " + player.getName() + "..." +
				"\n|\tAND have priority of attack over " + player.getName() + "..." +
				"\n|" +
				"\n|  --------------  ENEMY TURN  --------------";
		System.out.print(scenarioTRESstart);

		// Trigger para o scenario 4 se o to cruz precisar de items...
		enemiesConfronts(player);

		String scenarioTRESend = "";
		while (playerPosition.hasEnemies() && player.isAlive()) {

			if (this.currentScenario != ScenarioNr.FOUR &&
				this.currentScenario == ScenarioNr.THREE && player.isAlive()) {
				String scenarioTRESendSpecial =
					"\n|\t-------------- PLAYER  TURN --------------";
				System.out.print(scenarioTRESendSpecial);
				playerConfronts();
			}

			setNextScenario(ScenarioNr.THREE);
			scenarioTRESend =
				"\n|" +
					"\n|  --------------  ENEMY TURN  --------------" +
					"\n|\tENEMIES in " + playerPosition.getName() + " survived the attack..." +
					"\n|\tENEMIES not in " + playerPosition.getName() + " are moving..." +
					"\n|";
			System.out.print(scenarioTRESend);
			moveEnemiesNotInSameRoom();
			enemiesConfronts(player);
			playerPosition = player.getPosition();
		}

		if (!playerPosition.hasEnemies() && player.isAlive()) {
			scenarioTRESend = "\n|\t-------------- PLAYER  TURN --------------";
			scenarioTRESend += "\n|\tTO CRUZ eliminated all ENEMIES in..." + playerPosition.getName() + "...";
			setNextTurn(Turn.PLAYER);

		} else if (!player.isAlive()) {
			scenarioTRESend = "\n|\t-------------- PLAYER  TURN --------------";
			scenarioTRESend += "\n|\t" + player.getName() + " DIED DAMN IT!!!!!";
			this.gameOver = true;
		}

		scenarioTRESend +=
			"\n|========== [<< SCENARIO 3  END  >>] ==========";
		System.out.println(scenarioTRESend);
	}

	private void scenarioQUATRO() {

		String scenarioQUATROstart =
			"\n|========== [<< SCENARIO 4 START >>] ==========" +
				"\n|\tTO CRUZ seems to be injured..." +
				"\n|" +
				"\n|\t-------------- PLAYER  TURN --------------" +
				"\n|\t" + player.getName() + " halts and is checking BackPack...";
		System.out.print(scenarioQUATROstart);

		String scenarioQUATROend = "";
		try {
			scenarioQUATROend += "\n|\t" + this.player.useMediKit();
		} catch (EmptyCollectionException e) {
			System.err.println(e.getMessage());
		}
		setNextTurn(Turn.ENEMY);

		scenarioQUATROend +=
			"\n|" +
				"\n|========== [<< SCENARIO 4  END  >>] ==========";
		System.out.print(scenarioQUATROend);
	}

	private void scenarioCINCO() {
		Room playerPosition = player.getPosition();
		boolean enemiesRemained;

		String scenarioCINCOstart =
			"\n" + player.getName() + " is in " + playerPosition.getName() + "..." +
				"\n|========== [<< SCENARIO 5 START >>] ==========" +
				"\n|\tTO CRUZ makes contact with ENEMIES..." +
				"\n|\tAND has priority of attack over ENEMIES..." +
				"\n|\tAND in that room there is the TARGET..." +
				"\n|" +
				"\n|\t-------------- PLAYER  TURN --------------";
		System.out.print(scenarioCINCOstart);

		playerConfronts();

		enemiesRemained = playerPosition.hasEnemies();
		String scenarioCINCOend = "";
		if (enemiesRemained) {
			scenarioCINCOend +=
				"\n|" +
					"\n|\t--------------  ENEMY TURN  --------------" +
					"\n|\tENEMIES in " + playerPosition.getName() + " survived the attack..." +
					"\n|\tENEMIES not in " + playerPosition.getName() + " are moving...";

			if (!this.enemies.isEmpty()) {
				scenarioCINCOend +=
					"\n|\tBUT the enemies are somewhere..." +
						"\n|\tENEMIES not in " + playerPosition.getName() + " are moving...";
			}
		}

		scenarioCINCOend +=
			"\n|" +
				"\n|========== [<< SCENARIO 5  END  >>] ==========";
		System.out.println(scenarioCINCOend);
	}

	private void scenarioSEIS() throws ElementNotFoundException, EmptyCollectionException {
		Room playerPosition = this.player.getPosition();
		String scenarioSEISstart =
			"\n" + player.getName() + " is in " + playerPosition.getName() + "..." +
				"\n|========== [<< SCENARIO 6 START >>] ==========" +
				"\n|\tAND in that room there is the TARGET..." +
				"\n|\tLOOK AT THAT, it is clear..." +
				"\n|" +
				"\n|\t-------------- PLAYER  TURN --------------";
		System.out.print(scenarioSEISstart);

		String scenarioSEISend = "";
		if (playerPosition.hasItems()) {
			scenarioSEISend += gatherItems(playerPosition);
		}

		if (isMissionAccomplished()) {
			this.setGameOver(true);
			return;
		} else {

			boolean secured = true;
			this.mission.setTargetSecured(secured);
			this.returningToExit = true;
			Room extractionPoint = bestExtractionPoint(player.getPosition());
			this.extractionPoint = extractionPoint;
			setNextObjective(extractionPoint);
		}

		scenarioSEISend +=
			"\n|\tTARGET is now secured..." +
				"\n|\tWELL DONE " + player.getName() + " return to " + this.nextObjective.getName() + "..." +
				"\n|" +
				"\n|========== [<< SCENARIO 6  END  >>] ==========";
		System.out.println(scenarioSEISend);

		// movePlayer();

	}

	private boolean isAtTarget(Room playerPosition, Room targetPosition) {
		return playerPosition.equals(targetPosition);
	}

	protected boolean isMissionAccomplished() {
		Room playerPosition = player.getPosition();
		return player.isAlive() && mission.isTargetSecured() && playerPosition.equals(this.nextObjective);
	}

	protected Room findBestEntryPoint() throws ElementNotFoundException {
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

	protected void playerConfronts() {
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

				playerConfrontsOutput +=
					"\n|\t" +
						"\n|\t" + playerName + " is attacking " + enemy.getName() + "...";

				if (!enemy.isAlive()) {
					playerConfrontsOutput += "\n|\tENEMY " + enemy.getName() +
						" suffered " + playerAttack + " of attack..." +
						"\n|\tAND is now DEAD!!!";
					enemies.remove();
					enemyPosition.removeEnemy();
				} else {
					playerConfrontsOutput += "\n|\tENEMY " + enemy.getName() +
						" endured " + playerAttack + " of attack...";
				}
			}

		}

		if (playerPosition.getTotalEnemies() <= NONE) {
			playerPosition.setEnemies(false);
		}

		System.out.print(playerConfrontsOutput);

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

			enemyPosition.removeEnemy();
			if (enemyPosition.getTotalEnemies() <= NONE) {
				enemyPosition.setEnemies(false);
			}

			Random random = new Random();
			int random_index = random.nextInt(possibleMoves.size());

			Room nextPosition = possibleMoves.getElement(random_index);

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

	private String gatherItems(Room room) {
		final int NONE = 0;
		String gatherItemsOutput = "";
		Room playerPosition = player.getPosition();
		Iterator<Item> itemIterator = mission.getItems().iterator();

		while (itemIterator.hasNext()) {

			Item item = itemIterator.next();

			if (item.getPosition() != null && item.getPosition().equals(room)) {
				gatherItemsOutput += "\n|\tAND spots some items in the room...";

				if (item instanceof MediKit) {
					this.player.addKitToBackPack((MediKit) item);
					gatherItemsOutput +=
						"\n|\t" + player.getName() + " adds " + item.getName() + " to his BackPack...";

				} else if (item instanceof Kevlar) {
					this.player.equipKevlar((Kevlar) item);
					gatherItemsOutput +=
						"\n|\t" + player.getName() + " equips " + item.getName() +
							", current health " + player.getCurrentHealth() + "...";
				}
				playerPosition.removeItem();
				itemIterator.remove();
			}
		}

		if (playerPosition.getTotalItems() <= NONE) {
			playerPosition.setItemsInRoom(false);
		}

		return gatherItemsOutput;
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

				enemiesConfronts =
					"\n|\t" + enemy.getName() + " is attacking " + playerName +
						"\n|\t" + "with " + enemyAttack + " damage..." + "\n|";
				// ScenarioNr 4: O Tó Cruz utiliza kits de vida DURANTE
				// o combate consumindo a sua fase de jogador...
				System.out.print(enemiesConfronts);
				if (playerNeedsRecoveryItem()) {
					setNextScenario(ScenarioNr.FOUR);
					scenarioQUATRO();
					return;
				}

			}

		}

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

	protected double calculatePathDamage(Iterator<Room> path) {
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

	protected void getBestPath() throws ElementNotFoundException {
		Room playerPosition = this.player.getPosition();
		String bestPathOutput = "";

		this.updateWeightsForEnemies();

		if (returningToExit) {
			bestPathOutput += "\nCalculating best path to EXTRACTION POINT...";
			bestPathOutput += "\nPath to EXTRACTION POINT";

		} else {
			bestPathOutput += "\nCalculating best path to OBJECTIVE...";
			bestPathOutput += "\nPath to OBJECTIVE";
		}

		System.out.println(bestPathOutput);

		this.displayPath(playerPosition, this.nextObjective);

	}

	protected void displayPath(Room fromPosition, Room toPosition) throws ElementNotFoundException {
		Iterator<Room> bestPath = this.battlefield.iteratorShortestPath(fromPosition, toPosition);

		Room nextRoom = bestPath.next();
		StringBuilder displayPathOutput = new StringBuilder();
		displayPathOutput.append(String.format("\n%-25s <--- %-20s", nextRoom.getName(), player.getName()));

		while (bestPath.hasNext()) {
			nextRoom = bestPath.next();
			if (!nextRoom.equals(this.nextObjective)) {
				displayPathOutput.append("\n  |\t").append(nextRoom.getName());

			} else if (!nextRoom.equals(this.nextObjective) && !nextRoom.equals(this.closestItem.getName())) {
				displayPathOutput.append(String.format("\n%-26s <--- %-20s", this.closestItem, "ITEM"));

			} else if (returningToExit) {
				displayPathOutput.append(String.format("\n%-25s <--- %-20s", this.nextObjective.getName(), "EXTRACTION POINT"));
			} else {
				displayPathOutput.append(String.format("\n%-25s <--- %-20s", this.nextObjective.getName(), "OBJECTIVE"));
			}
		}
		System.out.println(displayPathOutput);
		// String print = displayPathOutput.toString();
	}

	protected void addRoomToReport(String roomName) {
		if (!this.mission.isTargetSecured()) {
			getReport().addRoom(roomName);
		} else {
			getReport().addRoomToExtraction(roomName);
		}
	}

	protected void addStatusToReport() {
		if (player.isAlive() && isGameOver()) {
			report.setMissionStatus("Mission Accomplished");
		} else {
			report.setMissionStatus("Mission Failed");
		}
	}

}