import entities.Player;
import entities.Room;
import entities.Target;
import lib.ArrayUnorderedList;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

public interface Simulation {
    void renderAutomaticSimulation(Player player, Target target) throws ElementNotFoundException;
    void renderManualSimulation(Room target);
    void playerTurn() throws ElementNotFoundException, EmptyCollectionException;
    void enemyTurn() throws ElementNotFoundException, EmptyCollectionException;
    String gatherItems(Room room);
    Room findBestEntryPoint() throws ElementNotFoundException;
    void movePlayer() throws ElementNotFoundException, EmptyCollectionException;
    ArrayUnorderedList<Room> getPossibleMoves(Room room);
    void displayPath(Room start, Room end) throws ElementNotFoundException;
    MissionImpl getMission();

}
