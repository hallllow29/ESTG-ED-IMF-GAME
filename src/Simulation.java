import entities.*;
import lib.ArrayList;
import lib.ArrayUnorderedList;
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

	public Simulation(Mission mission, Player player) {
		this.mission = mission;
		this.player = player;
		this.gameOver = false;
		// this.entry_point;
	}

	public boolean isGameOver() {
		return this.gameOver;
	}

	public void game() throws ElementNotFoundException, EmptyCollectionException {

		// Maybe a setter in mission? Due to mission containing all the intelligence...
		this.entryPoint = findBestEntryPoint();
		this.player.setPosition(entryPoint);
		Turn currentTurn = Turn.PLAYER;

		while (!isGameOver()) {

			/**
			 * O turno é baseado em duas fases principais:
			 */
			if (currentTurn == Turn.PLAYER) {

				/**
				 * Fase do jogador (Tó Cruz)
				 */
				playerTurn();

				currentTurn = Turn.ENEMY;

			} else if (currentTurn == Turn.ENEMY) {

				/**
				 * Fase dos inimigos.
				 */
				enemyTurn();

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

	public void playerTurn() throws ElementNotFoundException {
		/**
		 * Fase do jogador (Tó Cruz)
		 */
		System.out.println("==== PLAYER TURN ====");
		System.out.println("TO CRUZ is moving...");

		if (this.mission.isTargetSecured()) {
			if (player.isAlive() && player.getPosition().equals(extractionPoint)) {
				this.gameOver = true;
			}
			movePlayer(true);
		} else {
			movePlayer(false);
		}
		scenariosCase(Turn.PLAYER);
	}

	public void enemyTurn() {
		/**
		 * Fase dos inimigos
		 */
		System.out.println("==== ENEMY TURN ====");
		System.out.println("Enemies are moving...");

		moveEnemies();

		scenariosCase(Turn.ENEMY);
	}

	private void movePlayer(boolean toExtraction) throws ElementNotFoundException {
		Room playerPosition = this.player.getPosition();
		Room targetPosition = this.mission.getTarget().getRoom();

		// TODO: Needs refactoring isto foi à trolha for testing
		Iterator<Room> path = null;
		if (toExtraction) {
			// Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(playerPosition,bestExtractionPoint());
			path = mission.getBattlefield().iteratorShortestPath(playerPosition, this.extractionPoint);

		} else {
			// Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(playerPosition, targetPosition);
			path = mission.getBattlefield().iteratorShortestPath(playerPosition, targetPosition);
		}
		path.next();

		if (path.hasNext()) {

			/**
			 * Quando o Tó Cruz se move para uma nova divisão,
			 */
			Room toRoom = path.next();
			System.out.println("TO CRUZ moved from: " + playerPosition.getName() + " to " + toRoom.getName());
			this.player.setPosition(toRoom);

		} else {
			if (this.player.isAlive() && this.mission.isTargetSecured() && player.getPosition().equals(this.entryPoint)) {
				this.gameOver = true;
			}
		}
	}

	void scenariosCase(Turn currentTurn) {
		/**
		 * O jogo segue uma sequência de ações conforme as condições da sala e a
		 * situação de combate.
		 */

		Room playerPosition = this.player.getPosition();
		Room nextObjective;
		boolean playerPositionHasEnemies = playerPosition.hasEnemies();

		System.out.println("TO CRUZ enters in: " + playerPosition.getName() + "...");

		// TODO: I am going refactor .getRoom() to .getPosition() in class Target

		if (this.mission.isTargetSecured()) {
			nextObjective = this.extractionPoint;
		} else {
			nextObjective = this.mission.getTarget().getRoom();
		}

		if (playerPositionHasEnemies) {

			roomWithEnemiesSituation(currentTurn, playerPosition, nextObjective);

		} else if (!playerPositionHasEnemies) {
			/**
			 * Cenário 2: Na fase do jogador, o Tó Cruz entra na sala e não encontra inimigo.
			 */
			roomWithoutEnemiesSituation(currentTurn, playerPosition, nextObjective);

		}
	}

	private boolean isAtTarget(Room playerPosition, Room targetPosition) {
		return playerPosition.equals(targetPosition);
	}

	// -------------------- ROOM WITH ENEMIES SITUATION ----------------------------------
	private void roomWithEnemiesSituation(Turn currentTurn, Room playerPosition, Room nextObjective) {

		// Ya man! dou um refactor... que consome tempo, mas tem que ser...man

		if (!isAtTarget(playerPosition, nextObjective)) {

			confrontationOccurs(currentTurn);

		} else if (isAtTarget(playerPosition, nextObjective)) {

			securingTargetPerimeter();

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
	private void roomWithoutEnemiesSituation(Turn currentTurn, Room playerPosition, Room nextObjective) {

		// O movimento dos inimigos ocorre sempre que Tó Cruz não
		// encontra inimigos

		// FODASSE AQUI OCCORRE
		// moveEnemies()
		// OU/E
		// moveEnemiesNotSameRoom()

		if (!isAtTarget(playerPosition, nextObjective)) {

			clearingRoom();

		} else if (isAtTarget(playerPosition, nextObjective)) {

			securingTarget();

		}
	}

	private void clearingRoom() {
		Room playerPosition = this.player.getPosition();
		System.out.println("AND the room TO CRUZ entered is clear...");
		System.out.println("TO CRUZ checks if there are items...");

		if (playerPosition.hasItems()) {
			System.out.println("AND notices there are items for him to use...");
			roomWithItemsSituation();
		}
		try {
			scenarioDOIS();

		} catch (EmptyCollectionException | ElementNotFoundException e) {
			System.out.println(e.getMessage());
		}

	}

	private void securingTarget() {
		// TO CRUZ encontra o alvo sem inimigos.
		System.out.println("TO CRUZ enters on a room...");
		System.out.println("AND in that room there is the TARGET...");
		System.out.println("LOOK AT THAT, it is clear...");
		try {
			scenarioSEIS();
		} catch (ElementNotFoundException e) {
			System.err.println(e.getMessage());
		}
	}

	// --------------------- ROOM WITH ITEMS SITUATION -----------------------------------
	private void roomWithItemsSituation() {
		Room playerPosition = this.player.getPosition();

		gatherItems(playerPosition);

		System.out.println("AND checks if he needs to use the item immediately...");
		try {
			if (playerNeedsRecoveryItem()) {
				scenarioQUATRO();
			} else {
				System.out.println("THIS is not the case...");
			}

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
		// System.out.println("MAN WHAT? I MEAN I HAVE THAT IN playerConfronts...");
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
		/**
		 * Cenário 2:
		 * Na fase do jogador, o Tó Cruz entra na sala e não encontra inimigo.
		 * Segue-se a fase dos inimigos, na qual estes se movem aleatoriamente.
		 * o Fim do turno: O turno termina e o próximo começa permitindo que o jogador escolha uma nova ação.
		 */
		System.out.println("BUT the enemies are somewhere...");
		/**
		 * Segue-se a fase dos inimigos...
		 */
		System.out.println("==== ENEMY TURN ====");
		System.out.println("Enemies are moving...");
		/**
		 * na qual estes se movem aleatoriamente.
		 */
		moveEnemies();
		/**
		 * Fim do turno
		 */
		System.out.println("SCENARIO 2 ENDED");
		/**
		 * e o proximo jogador escolhe uma nova ação.
		 */
		playerTurn();
	}

	private void scenarioTRES() {

		System.out.println("ENEMIES are confronting TO CRUZ...");
		enemiesConfronts(this.player);

		System.out.println("SCENARIO 3 ENDED");
	}

	private void scenarioQUATRO() throws EmptyCollectionException {

		if (playerNeedsRecoveryItem()) {
			System.out.println("TO CRUZ is using medic kit...");
			System.out.println(this.player.useMediKit());
			System.out.println("SCENARIO 4 ENDED");
		}
	}

	private void scenarioCINCO() throws EmptyCollectionException, ElementNotFoundException {
		Iterator<Enemy> enemies = this.mission.getEnemies().iterator();

		// PLAYER
		// O confronto é prioritário para o Tó.
		// O Tó Cruz deve lidar com os inimigos primeiro.
		System.out.println("TO CRUZ is confronting enemies firstly...");
		playerConfronts(enemies);

		if (!checkEnemiesInPlayerRoom()) {
			scenarioSEIS();
		}

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

	private void scenarioSEIS() throws ElementNotFoundException {
		// TO CRUZ pode interagir com o alvo
		// e conclui a missao com sucesso
		// caso consiga sair do edificio com vida.
		this.mission.setTargetSecured(true);

		// updateBestPath(true);

		this.extractionPoint = bestExtractionPoint();

		// movePlayer(true);

		if (player.isAlive() && player.getPosition().equals(this.extractionPoint)) {
			this.gameOver = true;
		}
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
			try {
				scenarioSEIS();
			} catch (ElementNotFoundException e) {
				System.err.println(e.getMessage());
			}
			/*// MOVE TO EXTRACTION POINT, RIGHT ?
			updateWeightsForEnemies();
			try {
				Room extractionPoint = bestExtractionPoint();
				System.out.println("TO CRUZ has found a way to extraction point...");
				// MOVE PLAYER ??

			} catch (ElementNotFoundException e) {
				System.err.println(e.getMessage());
			}*/
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
		// Movimentação dos inimigos: Os inimigos movimentam-se aleatoriamente até duas
		// divisões a partir da sua posição.
		Room playerPosition = this.player.getPosition();

		for (Enemy enemyObj : this.mission.getEnemies()) {
			Room enemyPosition = enemyObj.getPosition();

			if (!enemyPosition.equals(playerPosition)) {
				moveEnemy(enemyObj);
			}
		}
	}

	private ArrayUnorderedList<Room> getPossibleMoves(Room fromRoom) {

		/**
		 * Os inimigos movimentam-se aleatoriamente até duas divisões a partir da
		 * sua posição.
		 */

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
				continue;
			}

			if (item.getPosition() != null && item.getPosition().equals(room)) {
				if (item instanceof MediKit) {
					this.player.addKitToBackPack((MediKit) item);
					System.out.println("TO CRUZ adds MediKit backPack...");
				} else if (item instanceof Kevlar) {
					this.player.equipKevlar((Kevlar) item);
					System.out.println("TO CRUZ equips Kevlar, current health..." + this.player.getCurrentHealth());
				}

				item.setPosition(null);
				room.setItemInRoom(false);
				itemIterator.remove();
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
					this.gameOver = true;
					break;
				}
			}
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
















































































	/*private void playerUnderAttackBy(Enemy enemy) {
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

}