import entities.Enemy;
import entities.Player;
import entities.Room;
import lib.ArrayList;
import lib.ArrayUnorderedList;
import lib.Graph;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Random;

public class Simulation {

	private final Mission mission;
	private final Graph<Room> graph;
	private final Player player;
	private final boolean gameOver;
	private final Room entry_point;

	public Simulation(Mission mission, Graph<Room> graph, Player player, Room entry_point) {
		this.mission = mission;
		this.graph = graph;
		this.player = player;
		this.gameOver = false;
		this.entry_point = entry_point;
	}

	private void playerTurn() {
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
	}

	private void enemyTurn() {
		System.out.println("Enemies turn!");

        /*for (String roomName: this.graph.getVertices()) {
            Room room = graph.getRoom(roomName);

            if (room != null && room.hasEnemies()) {
                //moverInimigos;
            }

        }*/
	}

	private void scenario1(Room room) {
		// TODO: Sala com inimigos TOM CRUISE CONFRONTA
	}

	private void scnario2(Room room) {
		// TODO: TO CRUZ ENTRE NA SALA MAS NAO TEM INIMIGOS
		if (!room.hasEnemies()) {
			moveEnemies();
		}

		// IF manual
		//  Entra noutra funcao que faz um turno...

		// IF automatico
		//  Fazer ountra funcao pra saber onde o jogador vai pra a seguir
		//  get.next.room.for.player.
	}

	private void scnario3(Room room) {
		// TODO: CASO OS INIMGOS ENTREM NUMA SALA QUE TO CRUZ ESTA, CENARIO 1 É DESPOLETADO MAS OS INIMIGOS FICAM COM PRIORIDADE
	}

	private void scnario4(Room room) {
		// TODO: TO CRUZ USA UM KIT DEVIDA PARA SE CURAR NAO PODE EFETUAR UMA MOVIMANTAÇÃO PERDE O TURNO
	}

	private void scnario5(Room room) {
		// TODO: TO CRUZ ENCONTRA A SALA COM O ALVO MAS A SALA TEM INIMIGOS, O TO DEVE MATAR OS INIMIGOS TODOS PRIMEIRO E SO DEPOIS E QUE PODE RESGATAR O ALVO
		// TODO: NA FASE DOS INIMIGOS OS INIMIGOS MOVEM-SE PARA A SALA QUE O TO ESTA
	}

	private void scnario6(Room room) {
		// TODO: O TO ENCONTRA O ALVO MAS NAO TEM INIMIGOS NA SALA, O TO RESGATA O ALVO E DEPOIS TEM DE SAIR DO EDIFICIO.
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

		for (Enemy enemyObj : mission.getEnemies()) {

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
			Iterator<Room> bfs_iterator = graph.iteratorBFS(from_room);
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
}
