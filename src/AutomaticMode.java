import entities.Player;
import entities.Room;
import entities.Turn;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

public class AutomaticMode extends Simulation {

    public AutomaticMode(Mission mission, Player player) {
        super(mission, player);
    }

    /*public void game() throws ElementNotFoundException, EmptyCollectionException {

        this.setEntryPoint(findBestEntryPoint());
        this.getPlayer().setPosition(this.getEntryPoint());
        this.setCurrentTurn(Turn.PLAYER);

        while (isGameOver()) {

            if (this.getCurrentTurn() == Turn.PLAYER) {

                playerTurn();

                // currentTurn = Turn.ENEMY;

            } else if (this.getCurrentTurn() == Turn.ENEMY) {

                enemyTurn();

                this.setCurrentTurn(Turn.PLAYER);
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

    /*protected void movePlayer(boolean toExtraction) throws ElementNotFoundException {
        Room playerPosition = this.getPlayer().getPosition();
        Room targetPosition = this.getMission().getTarget().getRoom();

        this.getBestPath();
        Iterator<Room> path = null;
        if (toExtraction) {
            // Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(playerPosition,bestExtractionPoint());
            path = this.getMission().getBattlefield().iteratorShortestPath(playerPosition, this.getExtractionPoint());

        } else {
            // Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(playerPosition, targetPosition);
            path = this.getMission().getBattlefield().iteratorShortestPath(playerPosition, targetPosition);
        }
        path.next();

        if (path.hasNext()) {

            Room toRoom = path.next();
            System.out.println("TO CRUZ plans to go from\t" + playerPosition.getName() + "\tto\t" + toRoom.getName() + "...");
            this.getPlayer().setPosition(toRoom);

        } else {
            if (this.getPlayer().isAlive() && this.getMission().isTargetSecured() && this.getPlayer().getPosition().equals(this.getEntryPoint())) {
                isGameOver();
            }
        }

    }
}*/
