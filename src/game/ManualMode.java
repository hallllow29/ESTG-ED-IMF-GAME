package game;

import entities.*;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.lists.ArrayUnorderedList;
import lib.lists.CircularDoubleLinkedList;
import lib.stacks.ArrayStack;

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

    private Room selectNextRoom(Room playerPosition) {
        String selectNextRoomInfo = "";

        // Aqui podemos mexer na parte visual.
        // Ai que nostalgia de LP man lembras-te? <3
        final String POSSIBLE_MOVES         = "\n\t==== IMF - Possible Moves ====";
        final String CHOOSE_NEXT_MOVE       = "\n==== IMF - Choose your next move ====";
        final String CHOOSE_TO_STAY         = "==== You choose to stay ====";
        final String NO_MEDICKITS_BACKPACK  = "==== NO MEDICKITS AVAILABLE IN YOUR BACKPACK! ====";
        final String INVALID_OPTION         = "==== Invalid option ====";
        final String SELECT_VALID_OPTION    = "==== Please select a valid option ====";
        final String YOUR_NEXT_MOVE         = "==== IMF - Your next move is =====";

        System.out.print(POSSIBLE_MOVES);
        int choice = -1;
        Scanner scanner = new Scanner(System.in);
        Room selectedRoom = null;
        int lastSelection = 0;

        ArrayUnorderedList<Room> possibleMoves = getMission().getBattlefield().getConnectedVertices(getPlayer().getPosition());
        ArrayUnorderedList<Room> displayRooms = new ArrayUnorderedList<>();

        // ArrayUnorderedList<Room> possibleMoves = getMission().getBattlefield().getConnectedVertices(playerPosition);
        // ArrayUnorderedList<Room> displayRooms = new ArrayUnorderedList<>();
        //
        // for (Room room : possibleMoves) {
        //     System.out.println("[" + possibleSelection + "] " + possibleMoves.getElement(possibleSelection));
        //     displayRooms.addToRear(room);
        //     possibleSelection++;
        // }

        lastSelection = countPossiblePositions(possibleMoves, displayRooms);

        int stayOption = lastSelection;
        selectNextRoomInfo += optionNrMessage(stayOption,"Stay");

        int medicKitOption = lastSelection + 1;
        selectNextRoomInfo += optionNrMessage(medicKitOption,"Use MedicKit - HP" + currentHealthMessage());
        System.out.println(selectNextRoomInfo);

        while (true) {

            // Display.chooseNextMoveMessage();
            System.out.println(CHOOSE_NEXT_MOVE);

            if (scanner.hasNextInt()) {

                choice = scanner.nextInt();

               if (choice >= 0 && choice <= medicKitOption) {
                   if (choice == medicKitOption && !getPlayer().hasRecoveryItem()) {

                       // Display.noMediKitsBackPackMessage();
                       System.out.println(NO_MEDICKITS_BACKPACK);
                       continue;
                   }
                    selectedRoom = decideNextMove(choice, lastSelection, possibleMoves);
                    break;
               }

            } else {
                // Display.selectValidOptionMessage();
                System.out.println(SELECT_VALID_OPTION);
                scanner.next();
            }

        }

        // Display.selectYourNextMoveMessage();
        System.out.println(YOUR_NEXT_MOVE);
        // Display.selectedPositionMessage();
        System.out.println(selectedRoom.getName());

        return selectedRoom;
    }

    private int countPossiblePositions(ArrayUnorderedList<Room> possibleMoves, ArrayUnorderedList<Room> displayRooms) {
        int possiblePositions = 0;
        String possiblePositionInfo = "";

        for (Room room : possibleMoves) {
            possiblePositionInfo += "\n[" + possiblePositions + "] " + possibleMoves.getElement(possiblePositions);
            displayRooms.addToRear(room);
            possiblePositions++;
        }

        System.out.print(possiblePositionInfo);

        return possiblePositions;
    }

    private String optionNrMessage (int option, String selection) {
        return "\n[" + option + "] " + selection;
    }

    private String currentHealthMessage() {
        return getPlayer().getCurrentHealth() + "/100";
    }

    private Room decideNextMove (int choice, int lastSelection, ArrayUnorderedList<Room> possibleMoves ) {
        Room selectedRoom = null;
        int medicKitOption = lastSelection + 1;

        final String CHOOSE_TO_STAY         = "==== You choose to stay ====";
        final String NO_MEDICKITS_BACKPACK  = "No MediKits in BackPack!";
        final String INVALID_OPTION         = "==== Invalid option ====";

        if (choice >= 0 && choice < lastSelection) {
            selectedRoom = possibleMoves.getElement(choice);

        } else if (choice == lastSelection) {

            // Display.chooseToStayMessage();
            System.out.println(CHOOSE_TO_STAY);
            selectedRoom = getPlayer().getPosition();

        } else if (choice == medicKitOption) {
            if (getPlayer().getBack_pack().isBackPackEmpty()) {

                // Display.noMediKitsInBackpackMessage();
                System.out.println(NO_MEDICKITS_BACKPACK);

            } else {
                useMedicKit();
                selectedRoom = getPlayer().getPosition();
            }
        } else {
            // Display.invalidOptionMessage();
            System.out.println(INVALID_OPTION);
        }
        return selectedRoom;
    }


    private Room displayAllEntries() throws ElementNotFoundException {
        String displayAllEntiesInfo = "";
        displayAllEntiesInfo += Display.allPossibleEntriesBanner();
        System.out.println(displayAllEntiesInfo);
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
       String gatheringIntelInfo = Display.collectingData();

	   gatheringIntelInfo += Display.enemiesBanner();

	   for (Enemy enemy : getEnemies()) {
           gatheringIntelInfo += Display.enemiesIntelMessage(enemy.getName(),enemy.getFirePower(), enemy.getPosition().getName());
        }

        gatheringIntelInfo += Display.mediKitsKevlarsBanner();

        for (Item item : getMission().getItems()) {
			gatheringIntelInfo += Display.itemsIntelMessage(item.getName(), item.getItemValue(), item.getPosition().getName());
		}

        if (getPlayer().getPosition() != null) {
           gatheringIntelInfo += Display.closestMediKitBanner();

            Room toPosition = calculateClosestPathToMedicKit();
            if (toPosition != null) {
                displayPath(getPlayer().getPosition(), toPosition);
            } else {
                gatheringIntelInfo += Display.noMediKitsLeftMessage();
            }

        }


		gatheringIntelInfo += Display.playerBanner();
		gatheringIntelInfo += Display.playerHealthStatusMessage(getPlayer().getCurrentHealth());

		System.out.print(gatheringIntelInfo);
        displayMedicKits();

		gatheringIntelInfo = Display.renderingNextSituationMessage();
		System.out.println(gatheringIntelInfo);
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

    private void displayMedicKits() {
        String displayMedicKitsInfo ="";

        ArrayStack<MediKit> stack = super.getPlayer().getBack_pack().getListItems();

        if (stack.isEmpty()) {
            displayMedicKitsInfo += Display.backPackNoItemsMessage();
        }

        displayMedicKitsInfo += Display.backPackContentMessage(stack.toString());
        System.out.print(displayMedicKitsInfo);
    }

    private void useMedicKit() {
        super.scenarioQUATRO();
    }
}
