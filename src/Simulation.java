import entities.Enemy;
import entities.Room;
import lib.*;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Random;

public class Simulation {

	private final Mission mission;
	private final Graph<Room> graph;
	private LinkedList<Enemy> enemies;


	public Simulation(Mission mission, Graph<Room> graph, Room entry_point) {
		this.mission = mission;
		this.graph = graph;
	}

	public LinkedList<Enemy> getEnemies(){
		return this.mission.getEnemies();
	}


	/*public void scnario2(Room room) throws EmptyCollectionException, ElementNotFoundException {
		// TODO: TO CRUZ ENTRE NA SALA MAS NAO TEM INIMIGOS
		if (!room.hasEnemies()) {
			moveEnemies(room);
		}

	}*/

	/*private void moveEnemies(Room room) {

		ArrayUnorderedList<Room> possible_moves;

		Random random = new Random();

		for (Enemy enemyObj : mission.getEnemies()) {

			possible_moves = getPossibleMoves(enemyObj.getCurrentPosition());

			if (!possible_moves.isEmpty()) {

				int random_index = random.nextInt(possible_moves.size());



				Room next_room = possible_moves.getElement(random_index);

				// MAS getEnemies.... vai ter lá uma loop a rodar pelo o graph constnat...
				// Nao é eficiente..
				if (room.getEnemies().size() != 0) {
					try {
						room.removeEnemy(enemyObj);
						next_room.addEnemy(enemyObj);
					} catch (ElementNotFoundException | EmptyCollectionException e) {
						System.err.println(e.getStackTrace());
					}
				}
				enemyObj.setCurrentPosition(next_room);


			}
		}
	}*/


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
