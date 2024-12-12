package game;

import entities.*;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.lists.ArrayUnorderedList;
import lib.lists.CircularDoubleLinkedList;
import lib.stacks.ArrayStack;

import javax.swing.text.Element;
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

			if (nextRoom.equals(currentRoom)) {
				super.getPlayer().setPosition(currentRoom);
			}
			super.getPlayer().setPosition(nextRoom);

			super.addRoomToReport(nextRoom.getName());

		} else {
			setGameOver(true);
		}
	}

	private Room selectNextRoom(Room playerPosition) {
		String selectNextPositionInfo = "";
		int choice = -1;
		Scanner scanner = new Scanner(System.in);
		Room selectedRoom = null;
		int lastSelection = 0;



		ArrayUnorderedList<Room> possibleMoves = getMission().getBattlefield().getConnectedVertices(getPlayer().getPosition());
		ArrayUnorderedList<Room> displayRooms = new ArrayUnorderedList<>();

		lastSelection = countPossiblePositions(possibleMoves, displayRooms);
		int stayOption = lastSelection;
		selectNextPositionInfo += optionNrMessage(stayOption, "Stay");

		int medicKitOption = lastSelection + 1;
		selectNextPositionInfo += optionNrMessage(medicKitOption, "Use MedicKit - HP" + currentHealthMessage());

		System.out.print(selectNextPositionInfo);
		selectNextPositionInfo = "";

		while (true) {

			if (scanner.hasNextInt()) {

				choice = scanner.nextInt();

				if (choice >= 0 && choice <= medicKitOption) {
					if (choice == medicKitOption && !getPlayer().hasRecoveryItem()) {

						System.out.println(Display.noMediKitsBackPackMessage());
						continue;
					}
					selectedRoom = decideNextMove(choice, lastSelection, possibleMoves);
					break;
				}

			} else {
				System.out.println(Display.invalidOptionMessage());
				scanner.next();
			}

		}

		selectNextPositionInfo += Display.yourNextPositionMessage(selectedRoom.getName());
		System.out.print(selectNextPositionInfo);

		return selectedRoom;
	}

	private int countPossiblePositions(ArrayUnorderedList<Room> possibleMoves, ArrayUnorderedList<Room> displayRooms) {
		int possiblePositions = 0;
		String possiblePositionInfo = "";
		possiblePositionInfo += Display.possibleMovesMessage();

		for (Room room : possibleMoves) {
			possiblePositionInfo += "\n[" + possiblePositions + "] " + possibleMoves.getElement(possiblePositions);
			displayRooms.addToRear(room);
			possiblePositions++;
		}

		System.out.print(possiblePositionInfo);

		return possiblePositions;
	}

	private String optionNrMessage(int option, String selection) {
		return "\n[" + option + "] " + selection;
	}

	private String currentHealthMessage() {
		return getPlayer().getCurrentHealth() + "/100";
	}

	private Room decideNextMove(int choice, int lastSelection, ArrayUnorderedList<Room> possibleMoves) {

		String decideNextMoveInfo = "";
		Room selectedRoom = null;
		int medicKitOption = lastSelection + 1;

		if (choice >= 0 && choice < lastSelection) {
			selectedRoom = possibleMoves.getElement(choice);

		} else if (choice == lastSelection) {

			decideNextMoveInfo += Display.youChooseStayMessage();
			selectedRoom = getPlayer().getPosition();
			moveEnemies();

		} else if (choice == medicKitOption) {
			if (getPlayer().getBack_pack().isBackPackEmpty()) {

				decideNextMoveInfo += Display.noMediKitsBackPackMessage();

			} else {
				useMedicKit();
				selectedRoom = getPlayer().getPosition();

			}
		} else {
			decideNextMoveInfo += Display.invalidOptionMessage();
		}

		System.out.println(decideNextMoveInfo);
		return selectedRoom;
	}

	private Room displayAllEntries() throws ElementNotFoundException {
		String displayAllEntiesInfo = "";
		displayAllEntiesInfo += Display.allPossibleEntriesBanner();
		System.out.print(displayAllEntiesInfo);
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

		String displayEntryPointInfo = "";

		while (choice != 1) {
			for (Room entryPoint : entryPoints) {

				displayEntryPointInfo +=
					Display.entryPointSelection(ourRecommendation.getName(), entryPoint.getName());
				System.out.print(displayEntryPointInfo);
				displayEntryPointInfo = "";
				boolean validSelection = false;
				while (!validSelection) {

					if (scanner.hasNextInt()) {
						choice = scanner.nextInt();
						if (choice == 1) {
							selectedRoom = entryPoint;
							validSelection = true;
						} else if (choice == 2) {
							validSelection = true;
						} else {
							System.out.println("Invalid option.");
							System.out.print("Option: ");
						}
					} else {
						System.out.print("Option: ");
						scanner.next();
					}
				}

				if (choice == 1) {
					break;
				}
			}
		}
		return selectedRoom;
	}

	private void displaySophisticatedSpySystem() {
		String gatheringIntelInfo = Display.collectingData();
		System.out.print(gatheringIntelInfo);
		gatheringIntelInfo = "";

		displayEnemiesIntel();

		displayItemsIntel();


		gatheringIntelInfo += Display.playerBanner();
		gatheringIntelInfo += Display.playerHealthStatusMessage(getPlayer().getCurrentHealth());

		System.out.print(gatheringIntelInfo);
		displayMedicKits();

		gatheringIntelInfo = Display.renderingNextSituationMessage();
		System.out.print(gatheringIntelInfo);
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
		String displayMedicKitsInfo = "";

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

	private void displayItemsIntel() {

		String gatheringItemsInfo = "";
		gatheringItemsInfo += Display.mediKitsKevlarsBanner();


		for (Item item : getMission().getItems()) {
			gatheringItemsInfo += Display.itemsIntelMessage(item.getName(), item.getItemValue(), item.getPosition().getName());
		}

		if (getPlayer().getPosition() != null) {
			gatheringItemsInfo += Display.closestMediKitBanner();
			try {

				Room toPosition = calculateClosestPathToMedicKit();
				if (toPosition != null) {
					displayPath(getPlayer().getPosition(), toPosition);
				} else {
					gatheringItemsInfo += Display.noMediKitsLeftMessage();
				}
			} catch (EmptyCollectionException| ElementNotFoundException e) {
				System.err.println(e.getMessage());
			}

		}

		System.out.print(gatheringItemsInfo);
	}

	private void displayEnemiesIntel() {
		String gatheringEnemiesInfo = "";
		gatheringEnemiesInfo += Display.enemiesBanner();

		for (Enemy enemy : getEnemies()) {
			gatheringEnemiesInfo += Display.enemiesIntelMessage(enemy.getName(), enemy.getFirePower(), enemy.getPosition().getName());
		}
		System.out.print(gatheringEnemiesInfo);
	}
}
