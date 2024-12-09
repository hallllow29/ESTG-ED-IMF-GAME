import entities.Enemy;
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

    public void game() throws ElementNotFoundException, EmptyCollectionException {

        renderSimulation(this.getPlayer(), this.getMission().getTarget());

        System.out.println("TO CRUZ starts mission in " + getEntryPoint().getName());

        while (!isGameOver()) {

            if (this.getCurrentTurn() == Turn.PLAYER) {
                playerTurn();
            }

            if (isMissionAccomplished()) {
                this.setGameOver(true);
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

        Iterator<Enemy> enemies = this.getEnemies().iterator();

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


    @Override
    protected void movePlayer() throws ElementNotFoundException {
        String movePlayerOutput = "";
        Room playerPosition = this.getPlayer().getPosition();
        Room nextObjective = this.getNextObjective();
        Iterator<Room> path;

        this.getBestPath();

        path = this.getBattlefield().iteratorShortestPath(playerPosition, nextObjective);

        path.next();

        if (path.hasNext()) {

            Room nextPosition = path.next();

            movePlayerOutput += "\n" + this.getPlayer().getName() + " plans to go from [" +
                    playerPosition.getName() + "] -----> [" + nextPosition.getName() + "]";
            this.getPlayer().setPosition(nextPosition);

        } else {
            if (isMissionAccomplished()) {
                this.setGameOver(true);
            }
        }
        System.out.print(movePlayerOutput);
    }
}
