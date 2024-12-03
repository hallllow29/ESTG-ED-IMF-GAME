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
	//private final Room entry_point;


	public Simulation(Mission mission, Player player) {
		this.mission = mission;
		this.player = player;
		this.gameOver = false;
		//this.entry_point = entry_point;
	}

	public void game() throws EmptyCollectionException, ElementNotFoundException {
		Turn currentTurn = Turn.PLAYER;
		boolean gameOver = false;

		while(!gameOver) {
			switch(currentTurn) {
				case PLAYER:
					System.out.println("Player's turn");
					
			}
		}
	}
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

	public void enemyTurn() {
		System.out.println("Enemies turn!");

		for (Enemy enemy : this.mission.getEnemies()) {
			if (enemy.getCurrentPosition().equals(player.getCurrentPosition())) {
				this.enemyAttack(enemy);
			}
		}
	}

	public void playerTurn() throws EmptyCollectionException, ElementNotFoundException {
		System.out.println("Player turn");

		if (!this.player.getCurrentPosition().hasEnemies()) {
			this.scnario2(this.player.getCurrentPosition());
		} else if (this.player.getCurrentPosition().hasEnemies()) {
			for (Enemy enemy : mission.getEnemies()) {
				if (enemy.getCurrentPosition().equals(this.player.getCurrentPosition())) {
					this.playerAttack(enemy);
				}
			}
		}

	}

	private String removeEnemy(Enemy enemyToRemove) {
		try {
			mission.removeEnemy(enemyToRemove);
			return "Enemy: " + enemyToRemove.getName() + " is dead";
		} catch (EmptyCollectionException e) {
			return "Enemy list is empty";
		} catch (ElementNotFoundException e) {
			return "Enemy " + enemyToRemove.getName() + " was not found";
		}

	}

	private void playerAttack(Enemy enemy) throws EmptyCollectionException, ElementNotFoundException {
		System.out.println(this.player.getName() + " is attacking " + enemy.getName());

		enemy.takeDamage(this.player.getFirePower());

		if (enemy.getCurrentHealth() <= 0) {
			System.out.println(removeEnemy(enemy));
		}


	}
	private void enemyAttack(Enemy enemy) {
		System.out.println("Enemy " + enemy.getName() + " is attacking " + this.player.getName());

		this.player.setCurrentHealth(this.player.getCurrentHealth() - enemy.getFirePower());

		System.out.println("Enemy deal " + enemy.getFirePower() + " damage " + this.player.getName() + " current HP is " + this.player.getCurrentHealth());

		if (this.player.getCurrentHealth() <= 0) {
			this.gameOver = true;
		}
	}

	public void scnario2(Room room) throws EmptyCollectionException, ElementNotFoundException {
			this.checkForItems(room);
			this.moveEnemies();


		// IF manual
		//  Entra noutra funcao que faz um turno...

		// IF automatico
		//  Fazer ountra funcao pra saber onde o jogador vai pra a seguir
		//  get.next.room.for.player.
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
	 * Retrieves a list of possible rooms that can be moved to from the given room
	 * within a limited distance of 2 BFS levels.
	 *
	 * This method performs a breadth-first search starting from the specified room
	 * and collects rooms at BFS levels 1 and 2, indicating possible adjacent rooms
	 * and those one step further away from the start room.
	 *
	 * @param from_room the starting room from which possible moves are to be determined
	 * @return an ArrayUnorderedList containing rooms that are reachable within 2 BFS levels
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
						System.out.println("Medikit added to the backPack");
					} else if (item instanceof Kevlar) {
						this.player.equipKevlar((Kevlar) item);
						item.setPosition(null);
						mission.removeItem(item);
						System.out.println("Kevlar equipped, current health" + this.player.getCurrentHealth());
					}
				}
			}
		}
	}
}
