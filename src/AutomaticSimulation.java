import entities.*;
import lib.Network;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

public class AutomaticSimulation extends SimulationBase implements GameMode {

    public AutomaticSimulation(Mission mission, Player player) {
        super(mission, player);
    }

    @Override
    public void play() throws ElementNotFoundException, EmptyCollectionException {
        System.out.println("\n==== AUTOMATIC SIMULATION ====");

        Room bestEntry = super.findBestEntryPoint();
        System.out.println("Best entry point found " + bestEntry.getName());
        super.getPlayer().setPosition(bestEntry);

        boolean exiting = false;

        while (!super.isGameOver()) {
            System.out.println("==== NEW TURN ====");

            playerTurn();

            checkVictoryConditions();
            if(isGameOver() ) {
                break;
            }

            updateBestPath();

            enemyTurn();

            checkVictoryConditions();

        }

    }

    protected void movePlayer(Room destination) throws ElementNotFoundException {
        Room playerPosition = getPlayer().getPosition();

        Iterator<Room> path = getMission().getBattlefield().iteratorShortestPath(playerPosition, destination);

        if (path.hasNext()) {
            path.next();
            if (path.hasNext()) {
                Room nextRoom = path.next();
                getPlayer().setPosition(nextRoom);
                System.out.println("Player moved to: " + nextRoom.getName());
            } else {
                System.out.println("No valid moves available from current path.");
            }

        } else {
            System.out.println("No valid path found.");
        }
    }

}
