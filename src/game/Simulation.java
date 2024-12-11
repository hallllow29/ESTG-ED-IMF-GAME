package game;

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
	private boolean returningToExtraction;
	private Turn currentTurn;
	private ScenarioNr currentScenario;
	private boolean missionAccomplished;
	private Room nextObjective;
	private LinkedList<Enemy> enemies;
	private Room closestItem;
	private final Report report;

	public Simulation(Mission mission, Player player, Report report) {
		this.mission = mission;
		this.battlefield = new CustomNetwork<>();
		this.battlefield = mission.getBattlefield();
		this.player = player;
		this.currentTurn = Turn.PLAYER;
		this.missionAccomplished = false;
		this.enemies = new LinkedList<>();
		this.enemies = mission.getEnemies();
		this.report = report;
	}

	public abstract void movePlayer() throws ElementNotFoundException, EmptyCollectionException;

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

		System.out.println("TO CRUZ DIED !!!"); System.out.println("JUST KIDDING...");
		System.out.println("IT WAS A SIMULATION...");
		System.out.println("...OR WASN'T IT..."); System.out.println("GAME OVER!");

		Iterator<Enemy> enemies = getEnemies().iterator();

		while (enemies.hasNext()) {
			Enemy enemy = enemies.next(); System.out.println(enemy);
			this.getReport().addEnemy(enemy.getName());
		}
	}

	protected void playerTurn() throws ElementNotFoundException, EmptyCollectionException {
		Room playerPosition = this.getPlayer().getPosition();

		String playerTurnOutput = "";
		if (!playerPosition.hasEnemies() || this.getCurrentScenario() == ScenarioNr.TWO) {
			playerTurnOutput += "\n" + this.getPlayer().getName() + " is moving..." + "\n" + this.getPlayer().getName() + " leaves " + playerPosition.getName() + "...";

			movePlayer();
		} else {
			scenarioUM();
			// playerConfronts();
		}

		System.out.println(playerTurnOutput);

		// "o jogo se me sequência de ações"
		scenariosSituations();
		scenariosCase(this.getCurrentScenario());
	}

	public boolean isReturningToExit() {
		return this.returningToExtraction;
	}

	public void renderAutomaticSimulation(Player player, Target target) throws ElementNotFoundException {
		setNextObjective(target.getRoom());
		entryPoint = findBestEntryPoint();
		setEntryPoint(entryPoint);
		player.setPosition(entryPoint);
		missionAccomplished = false;
		currentTurn = Turn.PLAYER;
		gameOver = false;
	}

	public void renderManualSimulation(Room target) {
		setNextObjective(target);
		missionAccomplished = false;
		currentTurn = Turn.PLAYER;
		gameOver = false;
	}

	public Report getReport() {
		return this.report;
	}

	public CustomNetwork<Room> getBattlefield() {
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

	public boolean isGameOver() {
		return this.gameOver;
	}

	protected void setNextTurn(Turn nextTurn) {
		this.currentTurn = nextTurn;
	}

	protected Room getEntryPoint() {
		return this.entryPoint;
	}

	public Turn getCurrentTurn() {
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

	protected void setMissionAccomplished(boolean missionAccomplished) {
		this.missionAccomplished = missionAccomplished;
	}

	protected void scenariosCase(ScenarioNr nextScenario) throws ElementNotFoundException, EmptyCollectionException {

		switch (nextScenario) {
			case TWO:
				scenarioDOIS(); setNextTurn(Turn.ENEMY); break;
			case ONE:
				scenarioUM();
				// setNextTurn(Turn.PLAYER);

				// NAO SEI SE DEIXO AQUI, ou dentro do scenarioUM()...
				if (!this.player.isAlive()) {
					setMissionAccomplished(false);
					setGameOver(true);
				} break;
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
		Room playerPosition = player.getPosition(); boolean enemiesRemained;

		String scenarioUMinfo = Display.scenarioUMstartMessage(player.getName());
		scenarioUMinfo += Display.playerTurnMessage();
		System.out.print(scenarioUMinfo);

		playerConfronts();

		enemiesRemained = playerPosition.hasEnemies();

		scenarioUMinfo = "";

		if (enemiesRemained) {
			scenarioUMinfo += Display.enemyTurnMessage();

			if (getEnemies().isEmpty()) {
				scenarioUMinfo += Display.enemiesAreMovingMessage();
				moveEnemiesNotInSameRoom();
			} setNextTurn(Turn.ENEMY);

		} else {
			scenarioUMinfo += Display.playerEliminatedAllEnemiesInPositionMessage(player.getName(), playerPosition.getName());
			// RECOLHE ITEMS.
			if (playerPosition.hasItems()) {
				scenarioUMinfo += gatherItems(playerPosition);
			}
			setNextTurn(Turn.PLAYER);
		}

		scenarioUMinfo += Display.scenarioUMendMessage();
		System.out.print(scenarioUMinfo);

	}

	private void scenarioDOIS() {
		Room playerPosition = player.getPosition();

		String scenarioDOISinfo = Display.playerEntersInMessage(player.getName(), playerPosition.getName());
			scenarioDOISinfo += Display.scenarioDOISstartMessage(playerPosition.getName());
		scenarioDOISinfo += Display.playerTurnMessage();
		scenarioDOISinfo += Display.playerSearchsMessage(player.getName(), playerPosition.getName());
		System.out.print(scenarioDOISinfo);
		// RECOLHE ITEMS...

		scenarioDOISinfo = "";
		if (playerPosition.hasItems()) {
			scenarioDOISinfo = gatherItems(playerPosition);

			// TODO: Entra o cenario 4 ?? Que dizes?

		}

		scenarioDOISinfo += Display.enemyTurnMessage();

		if (!getEnemies().isEmpty()) {

			scenarioDOISinfo += Display.enemiesAreMovingMessage();
			moveEnemies();

		} else {

			scenarioDOISinfo += Display.noEnemiesLeftMessage();
		}

		scenarioDOISinfo += Display.scenarioDOISendMessage();

		System.out.println(scenarioDOISinfo);
	}

	private void scenarioTRES() {
		Room playerPosition = player.getPosition();

		String scenarioTRESinfo = Display.scenarioTRESstartMessage(player.getName());
		System.out.print(scenarioTRESinfo);

		// Tem um trigger para o scenario 4 se o to cruz precisar de items...
		enemiesConfronts(player);

		scenarioTRESinfo = "";

		while (playerPosition.hasEnemies() && player.isAlive()) {

			if (this.currentScenario != ScenarioNr.FOUR &&
				this.currentScenario == ScenarioNr.THREE && player.isAlive()) {

				scenarioTRESinfo = Display.playerTurnMessage();
				System.out.print(scenarioTRESinfo);

				playerConfronts();
			}

			setNextScenario(ScenarioNr.THREE);
			scenarioTRESinfo += Display.enemyTurnMessage();
			scenarioTRESinfo = Display.enemiesAreMovingMessage();
			System.out.println(scenarioTRESinfo);

			moveEnemiesNotInSameRoom();
			enemiesConfronts(player);

		}

		if (!playerPosition.hasEnemies() && player.isAlive()) {

			scenarioTRESinfo += Display.playerEliminatedAllEnemiesInPositionMessage(player.getName(), playerPosition.getName());

		} else if (!player.isAlive()) {

			scenarioTRESinfo += Display.playerDiedMessage(player.getName());
			setGameOver(true);
		}

		scenarioTRESinfo += Display.scenarioTRESendMessage();
		System.out.println(scenarioTRESinfo);
	}

	protected void scenarioQUATRO() {

		String scenarioQUATROinfo = Display.scenarioQUATROstartMessage(player.getName());

		try {
			scenarioQUATROinfo += "\n|\t" + this.player.useMediKit();
		} catch (EmptyCollectionException e) {
			System.err.println(e.getMessage());
		}

		scenarioQUATROinfo += Display.scenarioQUATROendMessage();
		System.out.print(scenarioQUATROinfo);

		setNextTurn(Turn.ENEMY);
	}

	private void scenarioCINCO() {
		Room playerPosition = player.getPosition();
		boolean enemiesRemained;

		String scenarioCINCOinfo = Display.playerEntersInMessage(player.getName(), playerPosition.getName());
		scenarioCINCOinfo += Display.scenarioCINCOstartMessage(player.getName());
		scenarioCINCOinfo += Display.playerTurnMessage();
		System.out.print(scenarioCINCOinfo);

		playerConfronts();

		scenarioCINCOinfo = "";

		enemiesRemained = playerPosition.hasEnemies(); if (enemiesRemained) {
			scenarioCINCOinfo += Display.enemyTurnMessage();
			scenarioCINCOinfo += Display.enemiesSurvivedAttackMessage(playerPosition.getName());

			if (!this.enemies.isEmpty()) {
				scenarioCINCOinfo += Display.enemiesAreMovingMessage();

			}
		}

		scenarioCINCOinfo += Display.scenarioCINCOendMessage();
		System.out.println(scenarioCINCOinfo);
	}

	private void scenarioSEIS() throws ElementNotFoundException {
		Room playerPosition = this.player.getPosition();

		String scenarioSEISinfo = Display.playerEntersInMessage(player.getName(), playerPosition.getName());
		scenarioSEISinfo += Display.scenarioSEISstartMessage();
		scenarioSEISinfo += Display.playerTurnMessage(); System.out.println(scenarioSEISinfo);

		if (playerPosition.hasItems()) {
			scenarioSEISinfo += gatherItems(playerPosition);
		}

		if (isMissionAccomplished()) {
			scenarioSEISinfo += Display.targetInExtractionPointMessage(player.getName());
			setGameOver(true);

		} else {

			playerReachedTarget();
			scenarioSEISinfo += Display.targetIsSecuredMessage(player.getName(), nextObjective.getName());
		}

		scenarioSEISinfo += Display.scenarioSEISendMessage();
		System.out.println(scenarioSEISinfo);
	}

	private void playerReachedTarget() throws ElementNotFoundException {
		mission.setTargetSecured(true); setReturningToExtraction(true);
		extractionPoint = bestExtractionPoint(player.getPosition());
		setNextObjective(extractionPoint);

	}

	private boolean isAtTarget(Room playerPosition, Room targetPosition) {
		return playerPosition.equals(targetPosition);
	}

	protected boolean isMissionAccomplished() {
		Room playerPosition = player.getPosition();
		return player.isAlive() && mission.isTargetSecured() && playerPosition.equals(this.nextObjective);
	}

	public Room findBestEntryPoint() throws ElementNotFoundException {
		Network<Room> battlefield = mission.getBattlefield();
		double minimalDamage = Double.MAX_VALUE;
		Room bestEntryPoint = null;

		for (Room entryPoint : mission.getEntryExitPoints()) {
			Iterator<Room> entryPointsPaths = battlefield.iteratorShortestPath(entryPoint, nextObjective);
			double calculatedDamage = calculatePathDamage(entryPointsPaths);

			if (calculatedDamage < minimalDamage) {
				minimalDamage = calculatedDamage;
				bestEntryPoint = entryPoint;
			}
		}
		return bestEntryPoint;

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

			enemy.setPosition(nextPosition); nextPosition.addEnemy();
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
		String gatherItemsOutput = "";
		Room playerPosition = player.getPosition();
		Iterator<Item> itemIterator = mission.getItems().iterator();

		gatherItemsOutput += Display.itemsSpottedMessage();
		while (itemIterator.hasNext()) {

			Item item = itemIterator.next();

			if (item.getPosition() != null && item.getPosition().equals(room)) {


				if (item instanceof MediKit) {
					addMediKitToBackPack((MediKit) item);

				} else if (item instanceof Kevlar) {
					playerWearsKevlar((Kevlar) item);
				}
				oneItemGetsPicked(playerPosition, itemIterator);
			}
		}

		allItemsInPositionCollected(playerPosition);

		return gatherItemsOutput;
	}

	private void oneItemGetsPicked(Room playerPosition, Iterator<Item> items) {
		playerPosition.removeItem();
		items.remove();
	}

	private void playerWearsKevlar(Kevlar item) {
		String playerWarsKevlarInfo = "";

		player.equipKevlar(item);
		playerWarsKevlarInfo +=
			Display.playerEquipsItemMessage(player.getName(), item.getName(), player.getCurrentHealth());

		System.out.println(playerWarsKevlarInfo);
	}

	private void addMediKitToBackPack(MediKit item) {
		String addMediKitToBackPackInfo = "";

		player.addKitToBackPack(item);
		addMediKitToBackPackInfo += Display.playerAddsItemMessage(player.getName(), item.getName());

		System.out.println(addMediKitToBackPackInfo);
	}


	private void allItemsInPositionCollected(Room playerPosition) {
		if (playerPosition.getTotalItems() <= 0) {
			playerPosition.setItemsInRoom(false);
		}
	}

	private void enemiesConfronts(Player player) {
		Room playerPosition = player.getPosition(); Room enemyPosition;
		boolean playerWantsToRecover;

		for (Enemy enemy : getEnemies()) {
			enemyPosition = enemy.getPosition();

			if (enemyPosition.equals(playerPosition)) {
				enemyAttacksPlayer(enemy, player);
				playerWantsToRecover = playerNeedsRecoveryItem();

				if (playerWantsToRecover) {
					playerDecidesToRecover();
					return;
				}

				if (!player.isAlive()) {
					setGameOver(true);
					break;
				}
			}
		}
	}

	protected void playerConfronts() {
		String playerConfrontsInfo = "";
		Iterator<Enemy> enemies = getEnemies().iterator();
		final int PLAYER_ATTACK = player.getFirePower();

		while (enemies.hasNext()) {
			Enemy enemy = enemies.next();
			Room enemyPosition = enemy.getPosition();

			if (enemyPosition.equals(player.getPosition())) {
				playerAttacksEnemy(player, enemy, PLAYER_ATTACK);

				if (!enemy.isAlive()) {

					report.addEnemyKilled(enemy.getName());

					oneEnemyDies(enemy, enemies);



				} else {
					playerConfrontsInfo += Display.enemyEnduredAttackMessage(enemy.getName(), PLAYER_ATTACK);
				}
			}
		}

		noEnemiesInPlayerPosition(player.getPosition());

		System.out.print(playerConfrontsInfo);

	}

	private void noEnemiesInPlayerPosition(Room playerPosition) {
		if (playerPosition.getTotalEnemies() <= 0) {
			playerPosition.setEnemies(false);
		}
	}

	private void playerAttacksEnemy(Player player, Enemy enemy, int playerAttack) {
		String playerAttacksEnemyInfo = "";

		enemy.takesDamageFrom(playerAttack);

		playerAttacksEnemyInfo += Display.playerIsAttackingMessage(player.getName(), enemy.getName(), playerAttack);
		playerAttacksEnemyInfo += Display.enemySufferedAttackMessage(enemy.getName(), playerAttack);

		System.out.println(playerAttacksEnemyInfo);
	}

	private void oneEnemyDies(Enemy enemy, Iterator<Enemy> enemies) {
		String oneEnemyDiesInfo = "";

		oneEnemyDiesInfo += Display.enemyIsDeadMessage();

		enemies.remove();
		enemy.getPosition().removeEnemy();

		System.out.println(oneEnemyDiesInfo);
	}

	private void playerDecidesToRecover() {
		setNextScenario(ScenarioNr.FOUR);
		scenarioQUATRO();
	}

	private void enemyAttacksPlayer(Enemy enemy, Player player) {
		String enemyAttacksPlayerInfo = "";

		player.takesDamageFrom(enemy.getFirePower());

		enemyAttacksPlayerInfo = Display.enemyIsAttackingMessage(enemy.getName(), player.getName(), enemy.getFirePower());

		System.out.print(enemyAttacksPlayerInfo);
	}

	public Room bestExtractionPoint(Room playerPosition) throws ElementNotFoundException {
		Room bestExtractionPoint = null;
		Iterator<Room> extractionPoints = mission.getEntryExitPoints().iterator();
		double minimalDamage = Double.MAX_VALUE;
		double calculatedDamage = 0.0;

		while (extractionPoints.hasNext()) {

			Room extractionPoint = extractionPoints.next();
			Iterator<Room> extractionPointsPaths = battlefield.iteratorShortestPath(playerPosition, extractionPoint);
			calculatedDamage = calculatePathDamage(extractionPointsPaths);

			if (calculatedDamage < minimalDamage) {
				minimalDamage = calculatedDamage; bestExtractionPoint = extractionPoint;

			}
		} return bestExtractionPoint;
	}

	public double calculatePathDamage(Iterator<Room> path) {
		final int PLAYER_MAX_HEALTH = 100;
		double totalDamage = 0;
		int playerHealth = player.getCurrentHealth();

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
							playerHealth = Math.min(PLAYER_MAX_HEALTH, playerHealth + ((MediKit) item).getHealPower());
						} else if (item instanceof Kevlar) {
							playerHealth += ((Kevlar) item).getExtraHp();
						}
					}
				}
			}
		}

		return totalDamage;
	}

	protected void updateWeightsForEnemies() {
		double newWeight = 0.0;

		for (Room room : getBattlefield().getVertices()) {
			for (Room connectedRoom : getBattlefield().getConnectedVertices(room)) {
				newWeight = calculateWeight(connectedRoom);
				getBattlefield().addEdge(room, connectedRoom, newWeight);
			}
		}
	}

	private double calculateWeight(Room room) {
		final double NONE = 0.0; double weight = 0.0; int totalDamage = 0;

		if (room.getTotalEnemies() > 0) {

			for (Enemy enemy : this.enemies) {
				if (enemy.getPosition().equals(room)) {
					totalDamage += enemy.getFirePower();
				}
			} weight += totalDamage;
		} else {
			return weight += NONE;
		} return weight;
	}

	protected void nextMissionStageInfo(boolean isReturningToExit) {

		String getBestPathOutput = "";

		// updateWeightsForEnemies();

		if (isReturningToExit) {
			getBestPathOutput += Display.bestPathExtractionMessage();
		} else {
			getBestPathOutput += Display.bestPathObjectiveMessage();
		}

		System.out.println(getBestPathOutput);
		// this.displayPath(playerPosition, this.nextObjective);
	}

	protected void displayPath(Room fromPosition, Room toPosition) throws ElementNotFoundException {
		Iterator<Room> bestPath = getBattlefield().iteratorShortestPath(fromPosition, toPosition);
		Room nextRoom;

		StringBuilder displayPathOutput = appendPlayerPositionInfo(bestPath.next());
		while (bestPath.hasNext()) {
			nextRoom = bestPath.next();
			displayPathOutput = appendNextRoomInfo(displayPathOutput, nextRoom);
		} System.out.println(displayPathOutput);
	}

	protected StringBuilder appendNextRoomInfo(StringBuilder pathOutput, Room nextRoom) {

		if (!nextRoom.equals(this.nextObjective)) {

			pathOutput.append("\n  |\t").append(nextRoom.getName());

		} else if (!nextRoom.equals(this.nextObjective) && !nextRoom.equals(this.closestItem.getName())) {
			pathOutput.append(String.format("\n%-26s <--- %-20s", this.closestItem, "ITEM"));

		} else if (returningToExtraction) {

			pathOutput.append(String.format("\n%-25s <--- %-20s", this.nextObjective.getName(), "EXTRACTION POINT"));
		} else {

			pathOutput.append(String.format("\n%-25s <--- %-20s", this.nextObjective.getName(), "OBJECTIVE"));
		}

		return pathOutput;
	}

	protected StringBuilder appendPlayerPositionInfo(Room playerPosition) {
		StringBuilder pathOutput = new StringBuilder();
		pathOutput.append(String.format("\n%-25s <--- %-20s", playerPosition.getName(), player.getName()));
		return pathOutput;
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
			getReport().setMissionStatus("game.Mission Accomplished");
		} else {
			report.setMissionStatus("game.Mission Failed");
		}
	}

		public void setReturningToExtraction(boolean returningToExtraction) {
			this.returningToExtraction = returningToExtraction;
	}


}