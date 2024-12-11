package game;

import entities.*;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.graphs.CustomNetwork;
import lib.lists.LinkedList;

public interface SimulationI {

    void movePlayer() throws ElementNotFoundException, EmptyCollectionException;
    void game() throws ElementNotFoundException, EmptyCollectionException;

    boolean isReturningToExit();
    boolean isGameOver();
    CustomNetwork<Room> getBattleField();
    LinkedList<Enemy> getEnemies();
    Player getPlayer();
    Mission getMission();
    Room getNextObjective();
    Turn getCurrentTurn();
    ScenarioNr getCurrentScenario();

    void setGameOver(boolean gameOver);
    void setEntryPoint(Room entryPoint);
    void setNextObjective(Room nextObjective);

}