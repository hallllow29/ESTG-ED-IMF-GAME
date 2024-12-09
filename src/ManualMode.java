import entities.*;
import lib.lists.ArrayUnorderedList;
import lib.lists.CircularDoubleLinkedList;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Scanner;

public class ManualMode extends Simulation {

    public ManualMode(Mission missionImpl, Player player, Report report) {
        super(missionImpl, player, report);
    }

    @Override
    public void game() throws ElementNotFoundException, EmptyCollectionException {
        renderManualSimulation(getMission().getTarget().getRoom());
        this.displaySophisticatedSpySystem();
        Room entryRoom = displayAllEntries();

        super.setEntryPoint(entryRoom);
        super.getPlayer().setPosition(entryRoom);

        getReport().addRoom(getPlayer().getPosition().getName());
        getReport().setEntryPoint(getPlayer().getPosition().getName());

        super.gameFlow();
    }

    @Override
    public void movePlayer() throws ElementNotFoundException, EmptyCollectionException {
        Room currentRoom = getPlayer().getPosition();

        if (!isMissionAccomplished()) {
            super.displayPath(getPlayer().getPosition(), getNextObjective());
            this.displaySophisticatedSpySystem();
            Room nextRoom = this.selectNextRoom(currentRoom);

            super.getPlayer().setPosition(nextRoom);

            super.addRoomToReport(nextRoom.getName());

        } else {
            setGameOver(true);
        }
    }

    private Room selectNextRoom(Room currenRoom) {
        System.out.println("==== IMF - Possible Moves ====");
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        Room selectedRoom = null;
        int counter = 0;

        ArrayUnorderedList<Room> possibleMoves = getMission().getBattlefield().getConnectedVertices(currenRoom);

        for (int i = 0; i < possibleMoves.size(); i++) {
            System.out.println("[" + i + "] " + possibleMoves.getElement(i));
            counter++;
        }

        System.out.println("[" + counter + "] Stay" );

        while (true) {
            System.out.println("==== IMF - Choose your next move ====");
            if (scanner.hasNextInt()) {
                choice = scanner.nextInt();
                if (choice >= 0 && choice < counter) {
                    selectedRoom = possibleMoves.getElement(choice);
                    break;
                } else if (choice == counter) {
                    System.out.println("==== You choose to stay ====");
                    selectedRoom = currenRoom;
                    break;
                } else {
                    System.out.println("==== Invalid option ====");
                }
            } else {
                System.out.println("==== Please select a valid option ====");
                scanner.next();
            }
        }

        System.out.println("==== IMF - Your next move is " + selectedRoom.getName());

        return selectedRoom;
    }

    private Room displayAllEntries() throws ElementNotFoundException {
        System.out.println("==== IMF - All possible entries ====");
        Room selectedRoom = null;
        Room ourRecommendation = super.findBestEntryPoint();

        Iterator<Room> roomIterator = getMission().getEntryExitPoints().iterator();

        CircularDoubleLinkedList<Room> entryPoints = new CircularDoubleLinkedList<>();

        while (roomIterator.hasNext()) {
            Room entry = roomIterator.next();
            entryPoints.add(entry);
        }

        Scanner scanner = new Scanner(System.in);
        int choice = 2;

        while (choice != 1) {
            for (Room entry : entryPoints) {
                System.out.println("==== IMF - Select your entry point: ");
                System.out.println("Your best entry point to the target is " + ourRecommendation.getName());
                System.out.println("[1] Select this room -> " + entry.getName());
                System.out.println("[2] Next room ");
                choice = scanner.nextInt();
                if (choice == 1) {
                    selectedRoom = entry;
                    break;
                }
            }
        }
        return selectedRoom;
    }

    private void displaySophisticatedSpySystem() throws EmptyCollectionException, ElementNotFoundException {
        System.out.println("Getting intel...");
        System.out.println("==== SSS Sophisticated Spy System ====");
        System.out.println("==== ENEMIES INFORMATION ====");

        for (Enemy enemy : getEnemies()) {
            System.out.println(enemy.getName() + " Current Room -> " + enemy.getPosition().getName()
                    + " | Fire Power: " + enemy.getFirePower());
        }

        System.out.println("==== MEDIC KITS/KEVLARS ====");

        for (Item item : getMission().getItems()) {
            System.out.printf(item.getName() + " Current Room -> " + item.getPosition().getName()
                    + " | Heal Points: " + item.getItemValue() + "\n");
        }

        if (getPlayer().getPosition() != null) {
            System.out.println("==== BEST PATH TO CLOSEST MEDIC KIT ====");

            Room toPosition = calculateClosestPathToMedicKit();
            displayPath(getPlayer().getPosition(), toPosition);
        }

        System.out.println("==== SSS Sophisticated Spy System ====");
    }

    private Room calculateClosestPathToMedicKit() throws EmptyCollectionException, ElementNotFoundException {
        Room currentRoom = getPlayer().getPosition();
        Room destinationRoom = null;
        double minimalDamage = Double.MAX_VALUE;
        double calculatedDamage = 0;

        Iterator<Room> rooms = getMission().getBattlefield().iteratorBFS(currentRoom);

        while (rooms.hasNext()) {
            Room bestRoom = rooms.next();
            if (bestRoom.hasItems()) {
                Iterator<Room> paths = getMission().getBattlefield().iteratorShortestPath(currentRoom, bestRoom);
                calculatedDamage = calculatePathDamage(paths);
                if (calculatedDamage < minimalDamage) {
                    minimalDamage = calculatedDamage;
                    destinationRoom = bestRoom;
                }
            }
        }

        return destinationRoom;
    }
}
