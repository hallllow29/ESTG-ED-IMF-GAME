import com.sun.security.jgss.GSSUtil;
import entities.Player;
import entities.Room;
import lib.ArrayUnorderedList;
import lib.CircularDoubleLinkedList;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Scanner;

public class ManualSimulation extends SimulationBase implements GameMode {

    public ManualSimulation(Mission mission, Player player) {
        super(mission, player);
    }

    @Override
    protected void movePlayer(Room room) throws ElementNotFoundException {
        room = getPlayer().getPosition();

        Room nextRoom = this.slectNextRoom(room);

        super.getPlayer().setPosition(nextRoom);

    }

    @Override
    public void play() throws ElementNotFoundException, EmptyCollectionException {
        System.out.println("\n==== MANUAL SIMULATION ====");
        Room entryPointSelected = displayBestEntries();

        System.out.println("You choose " + entryPointSelected.getName());

        getPlayer().setPosition(entryPointSelected);

        boolean exiting = false;

        while (!super.isGameOver()) {
            System.out.println("==== NEW TURN ====");

            this.displayClosestKit();
            playerTurn();

            checkVictoryConditions();
            if (isGameOver()) {
                break;
            }

            updateBestPath();

            enemyTurn();

            checkVictoryConditions();

        }

    }

    private Room displayBestEntries() throws ElementNotFoundException {
        System.out.println("Displaying all possible entries");
        Room selectedRoom = null;
        Room ourRecommendation = findBestEntryPoint();

        Iterator<Room> roomIterator = getMission().getEntryExitPoints().iterator();

        CircularDoubleLinkedList<Room> entryExitPoint = new CircularDoubleLinkedList<>();

        while (roomIterator.hasNext()) {
            Room entry = roomIterator.next();
            entryExitPoint.add(entry);
        }

        Scanner scanner = new Scanner(System.in);
        int choice = 2;

        while (choice != 1) {
            for (Room entry : entryExitPoint) {
                System.out.println("Select your entry point (our recommendation is " + ourRecommendation.getName());
                System.out.println("[1] Select this room ->" + entry.getName());
                System.out.println("[2] Next room");
                choice = scanner.nextInt();
                if (choice == 1) {
                    selectedRoom = entry;
                    break;
                }
            }
        }
        return selectedRoom;
    }

    private Room slectNextRoom(Room currentRoom) {
        System.out.println("Your possible moves are: ");
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        Room selectedRoom = null;

        ArrayUnorderedList<Room> possibleMoves = getMission().getBattlefield().getConnectedVertices(currentRoom);

        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println(i + ". " + possibleMoves.getElement(i));
        }

        while(true) {
            System.out.println("Choose an option");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 0 && choice <= possibleMoves.size()) {
                    selectedRoom = possibleMoves.getElement(choice);
                    break;
                } else {
                    System.out.println("Invalid option");
                }
            } else {
                System.out.println("Please select a valid option: ");
                scanner.next();
            }
        }
        System.out.println("You choose " + selectedRoom.getName());

        return selectedRoom;
    }

    private void displayClosestKit() {
        Room currentRoom = getPlayer().getPosition();

        for (Room room : getMission().getBattlefield().getVertices()) {
            if (room.hasItems()) {
                System.out.println(room.getName() + " has a recuperation item");
            }
        }

    }
}