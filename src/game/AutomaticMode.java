package game;

import entities.*;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;

public class AutomaticMode extends Simulation {

    public AutomaticMode(Mission missionImpl, Player player, Report report) {
        super(missionImpl, player, report);
    }

    @Override
    public void game() throws ElementNotFoundException, EmptyCollectionException {

        renderAutomaticSimulation(this.getPlayer(), this.getMission().getTarget());

       String simulationStartInfo =  Display.playerStartsInEntryPoint(getPlayer().getName(), getMission().getCode(), getEntryPoint().getName());
       System.out.println(simulationStartInfo);


        this.getReport().addRoom(getPlayer().getPosition().getName());
        this.getReport().setEntryPoint(getPlayer().getPosition().getName());

        super.gameFlow();

    }


    @Override
    public void movePlayer() throws ElementNotFoundException {
        StringBuilder movePlayerOutput = new StringBuilder();
        Room playerPosition = this.getPlayer().getPosition();
        Room nextObjective = this.getNextObjective();
        Iterator<Room> path;

        updateWeightsForEnemies();
        nextMissionStageInfo(isReturningToExit());
        // getBestPath();

        // Pode ficar aqui
        this.displayPath(playerPosition, nextObjective);

        path = this.getBattlefield().iteratorShortestPath(playerPosition, nextObjective);

        path.next();

        if (path.hasNext()) {

            Room nextPosition = path.next();

            movePlayerOutput.append("\n" + this.getPlayer().getName() + " plans to move from...").append
                (String.format("\n\t[%s] ---> [%s]\n", playerPosition.getName(), nextPosition.getName()));
            this.getPlayer().setPosition(nextPosition);

            super.addRoomToReport(nextPosition.getName());

        } else {
            if (isMissionAccomplished()) {
                this.setGameOver(true);
            }
        }
        System.out.print(movePlayerOutput);
    }

}
