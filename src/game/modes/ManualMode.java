package game.modes;

import entities.*;
import entities.enums.ScenarioNr;
import entities.enums.Turn;
import game.briefings.Report;
import game.io.Display;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.lists.ArrayUnorderedList;
import lib.lists.CircularDoubleLinkedList;
import lib.stacks.ArrayStack;

import java.util.Iterator;
import java.util.Scanner;

/**
 * The ManualMode class provides a simulation mode where players can manually interact
 * with the game environment and make decisions, such as selecting routes, using items, or
 * staying at the current position. It extends the Simulation class and incorporates
 * custom logic for user-driven gameplay.
 * <p>
 * The ManualMode gameplay is characterized by:
 * - Manual decision-making for the player's movements.
 * - Interactions with items and enemies within the game environment.
 * - Displaying detailed intel about the game environment (enemies, items, paths, etc.) to
 * the player.
 */
public class ManualMode extends Simulation {

	/**
	 * Constructs a ManualMode instance using the specified mission, player, and report.
	 * This class is responsible for managing the manual gameplay mode and its associated
	 * operations.
	 *
	 * @param missionImpl the mission implementation that defines the game's scenario and
	 *                    objectives
	 * @param player      the player participating in the manual gameplay mode
	 * @param report      the report instance used to log and display game-related
	 *                    information
	 */
	public ManualMode(Mission missionImpl, Player player, Report report) {
		super(missionImpl, player, report);
	}

	/**
	 * Executes the primary game flow for the manual mode. This method performs a series
	 * of steps to initiate and manage the simulation, including rendering manual
	 * simulation, displaying critical game intel, managing room entries, and setting the
	 * player's starting position. Finally, it triggers the overarching game flow logic.
	 *
	 * @throws ElementNotFoundException if a required element is not found during the
	 *                                  execution.
	 * @throws EmptyCollectionException if an operation is attempted on an empty
	 *                                  collection.
	 */
	@Override
	public void game() throws ElementNotFoundException, EmptyCollectionException {
		System.out.print(Display.initSimulation());
		renderManualSimulation(getMission().getTarget().getRoom());

		this.displaySophisticatedSpySystem();
		Room entryRoom = displayAllEntries();

		super.setEntryPoint(entryRoom);
		super.getPlayer().setPosition(entryRoom);

		getReport().addRoom(getPlayer().getPosition().getName());
		getReport().setEntryPoint(getPlayer().getPosition().getName());
		super.setNextTurn(Turn.PLAYER);

		super.gameFlow();
	}

	/**
	 * Moves the player within the game simulation by determining the next room based on
	 * the player's current position and the next objective. This method is invoked to
	 * handle player movement and update the game state accordingly.
	 * <p>
	 * The player's current position is evaluated to determine the next room for movement.
	 * Additionally, the method utilizes pathfinding and decision-making logic to manage
	 * the movement. If the player completes their mission, the game ends.
	 * <p>
	 * During execution:
	 * - The current player position is retrieved.
	 * - If the mission is not accomplished, the path to the next objective is displayed.
	 * - Advanced spy intelligence is rendered for player guidance.
	 * - The next room is selected based on the current position.
	 * - If the selected room is the same as the current position, the player's position
	 * remains unchanged; otherwise, the position updates to the next room.
	 * - The name of the room the player moves into is added to the game report.
	 * - If the mission is accomplished, the game is set as over.
	 *
	 * @throws ElementNotFoundException if an element required for room selection or
	 *                                  movement is missing.
	 * @throws EmptyCollectionException if the collection required for operation is empty,
	 *                                  indicating no valid movement paths are available.
	 */
	@Override
	public void movePlayer() throws ElementNotFoundException, EmptyCollectionException {
		Room currentRoom = getPlayer().getPosition();

		if (!isMissionAccomplished()) {

			System.out.println(Display.sophisticatedSpySystemBanner());

			super.displayPath(getPlayer().getPosition(), getNextObjective());

			Room nextRoom = this.selectNextRoom(currentRoom);

			// STAY
			if (nextRoom.equals(currentRoom)) {
				super.getPlayer().setPosition(currentRoom);
				super.setNextTurn(Turn.ENEMY);
			} else {
				super.getPlayer().setPosition(nextRoom);
				super.setNextTurn(Turn.PLAYER);
			}

			this.displaySophisticatedSpySystem();

			super.addRoomToReport(nextRoom.getName());

		} else {
			setGameOver(true);
		}
	}

	/**
	 * Selects the next room for the player to move into based on the current player
	 * position and user input. Provides options to stay, use a recovery item, or move to
	 * a connected room. Validates the user's input and updates the player's position
	 * appropriately.
	 *
	 * @param playerPosition the current position of the player in the game.
	 * @return the selected room for the player's next move.
	 */
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
		selectNextPositionInfo += "\n\nOption: ";

		// selectNextPositionInfo = "";

		while (true) {
			System.out.print(selectNextPositionInfo);

			if (scanner.hasNextInt()) {
				choice = scanner.nextInt();

				if (choice >= 0 && choice <= medicKitOption) {

					selectedRoom = decideNextMove(choice, lastSelection, possibleMoves);

					if (selectedRoom != null) {
						break;
					}
				}
			} else {
				System.out.println(Display.invalidOptionMessage());
				scanner.next();
			}
		}

		selectNextPositionInfo = Display.yourNextPositionMessage(selectedRoom.getName());
		System.out.print(selectNextPositionInfo);
		
		return selectedRoom;
	}

	/**
	 * Counts the number of possible positions (rooms) a player can move to based on the
	 * provided possible moves. The method builds and displays a message containing the
	 * possible move options and updates the provided list of display rooms with the
	 * possible rooms.
	 *
	 * @param possibleMoves the list of rooms representing possible moves the player can
	 *                      make.
	 * @param displayRooms  the list of rooms to be updated with the rooms from the
	 *                      possible moves.
	 * @return the total number of possible positions (rooms) a player can move to.
	 */
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

	/**
	 * Generates a formatted string for displaying a numbered option with its
	 * corresponding selection text. This is typically used to create interactive menu
	 * options or selections for the user.
	 *
	 * @param option    the number representing the option to display.
	 * @param selection the text or description associated with the option.
	 * @return a formatted string representing the numbered option and its corresponding
	 * selection.
	 */
	private String optionNrMessage(int option, String selection) {
		return "\n[" + option + "] " + selection;
	}

	/**
	 * Generates a message displaying the player's current health status. The message is
	 * formatted as "{currentHealth}/100", where "{currentHealth}" represents the player's
	 * current health value.
	 *
	 * @return a string representing the player's current health in the format
	 * "{currentHealth}/100"
	 */
	private String currentHealthMessage() {
		return getPlayer().getCurrentHealth() + "/100";
	}

	/**
	 * Determines the player's next move based on their choice, the last allowable option,
	 * and the list of possible moves. The method evaluates the player's selection and
	 * updates the game state accordingly. It handles staying in the current room, moving
	 * to a selected room, or using a medical kit if applicable.
	 *
	 * @param choice        the player's input representing their decision for the next
	 *                      move
	 * @param lastSelection the index of the last valid move option (e.g., the maximum
	 *                      choice for room selection or stay option)
	 * @param possibleMoves the list of rooms that the player can choose to move to
	 * @return the Room object representing the selected room for the next move, or the
	 * current room if the player chooses to stay or uses a medical kit
	 */
	private Room decideNextMove(int choice, int lastSelection, ArrayUnorderedList<Room> possibleMoves) {

		String decideNextMoveInfo = "";
		Room selectedRoom = null;
		int medicKitOption = lastSelection + 1;

		if (choice >= 0 && choice < lastSelection) {
			selectedRoom = possibleMoves.getElement(choice);
		} else if (choice == lastSelection) {
			decideNextMoveInfo += Display.youChooseStayMessage();
			selectedRoom = getPlayer().getPosition();
		} else if (choice == medicKitOption) {

			if (getPlayer().getBack_pack().isBackPackEmpty()) {
				decideNextMoveInfo += Display.noMediKitsBackPackMessage();
			} else if (getPlayer().playerNeedsRecoveryItem()) {
				useMedicKit();
				selectedRoom = getPlayer().getPosition();
			} else if (!getPlayer().playerNeedsRecoveryItem()) {
				decideNextMoveInfo += Display.playerHealthFullMessage();
			}
		} else {
			decideNextMoveInfo += Display.invalidOptionMessage();
		}

		System.out.println(decideNextMoveInfo);
		return selectedRoom;
	}

	/**
	 * Displays all possible entry points by iterating over the available rooms and
	 * presenting them to the user for selection. This method interacts with the user to
	 * select a preferred entry point or follows the recommended entry point identified by
	 * the system. The user can navigate through the options until a selection is made.
	 *
	 * @return the Room object representing the selected entry point.
	 *
	 * @throws ElementNotFoundException if any required element for displaying or
	 *                                  selecting entry points is missing.
	 */
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
							System.out.println("\nInvalid option!");
							System.out.print("Option: ");
						}
					} else {
						System.out.println("\nInvalid option!");
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

	/**
	 * Displays the sophisticated spy system's interface and renders various aspects of
	 * the game intel, such as enemy and item intelligence, the player's status, and
	 * upcoming scenarios. This method consolidates and outputs critical game information
	 * for the user to observe and analyze.
	 */
	private void displaySophisticatedSpySystem() throws ElementNotFoundException {
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
		System.out.println(gatheringIntelInfo);
	}

	/**
	 * Calculates the closest path to a room containing a medical kit based on the least
	 * amount of damage incurred along the path. It uses Breadth-First Search (BFS) to
	 * explore the battlefield and considers the shortest path to each room with items.
	 * The method determines the path that minimizes the damage and selects the
	 * destination room accordingly.
	 *
	 * @return the room containing a medical kit that is closest to the player's current
	 * position in terms of minimal damage along the path.
	 *
	 * @throws EmptyCollectionException if the battlefield or relevant paths are empty.
	 * @throws ElementNotFoundException if required elements, such as a room or medical
	 *                                  kit, cannot be located during the computation.
	 */
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

	/**
	 * Displays the medical kits available in the player's backpack. This method accesses
	 * the player's backpack (a stack structure containing MediKit objects), checks for
	 * its contents, and prepares a message detailing the medical kits present.
	 * <p>
	 * If the backpack is empty, a message indicating the absence of items is appended.
	 * Otherwise, the contents of the backpack (as a string representation of the stack)
	 * are included.
	 */
	private void displayMedicKits() {
		String displayMedicKitsInfo = "";

		ArrayStack<MediKit> stack = super.getPlayer().getBack_pack().getListItems();

		if (stack.isEmpty()) {
			displayMedicKitsInfo += Display.backPackNoItemsMessage();
		} else {
			displayMedicKitsInfo += Display.backPackContentMessage(stack.toString());
		}

		System.out.print(displayMedicKitsInfo);
	}

	/**
	 * Executes the action of using a medical kit during the manual gameplay mode. This
	 * method triggers the predefined Scenario QUATRO game logic by invoking the
	 * corresponding superclass method.
	 * <p>
	 * Scenario QUATRO includes the following sequence:
	 * - Displays messages related to the player's turn.
	 * - Simulates checking the player's backpack for available medical kits.
	 * - Attempts to utilize a medical kit if available. If no medical kit exists, an
	 * exception is handled gracefully without interrupting the game flow.
	 * - Updates the game state to set the subsequent turn for the enemy.
	 * <p>
	 * This method encapsulates the usage of a medical kit as part of the scenario and
	 * ensures proper handling of any
	 */
	private void useMedicKit() {
		super.scenarioQUATRO();
	}

	/**
	 * Displays information about the items available during the mission, including their
	 * names, values, and positions. It also determines and outputs the closest path to a
	 * medical kit if any are available, providing actionable intelligence to the player.
	 * <p>
	 * This method is crucial for providing the player with strategic insights into the
	 * items present in the game and facilitating informed decision-making during the
	 * mission.
	 */
	private void displayItemsIntel() {

		String gatheringItemsInfo = "";
		gatheringItemsInfo += Display.mediKitsKevlarsBanner();

		for (Item item : getMission().getItems()) {
			gatheringItemsInfo += Display.itemsIntelMessage(item.getName(), item.getItemValue(), item.getPosition().getName());
		}

		gatheringItemsInfo += Display.closestMediKitBanner();
		System.out.print(gatheringItemsInfo);
		gatheringItemsInfo = "";

		if (getPlayer().getPosition() != null) {
			try {
				Room toNextMediKit = calculateClosestPathToMedicKit();

				if (toNextMediKit != null) {
					displayPath(getPlayer().getPosition(), toNextMediKit);
				} else {
					gatheringItemsInfo += Display.noMediKitsLeftMessage();
				}
			} catch (EmptyCollectionException | ElementNotFoundException e) {
				System.err.println(e.getMessage());
			}

		}
		System.out.print(gatheringItemsInfo);
	}

	/**
	 * Gathers and displays intelligence information about all known enemies in the game
	 * simulation. This method consolidates enemy details, including their names,
	 * firepower, and positions, into a formatted message.
	 * <p>
	 * This is a utility method to provide players with critical intelligence about enemy
	 * status, aiding in strategic decision-making during gameplay.
	 */
	private void displayEnemiesIntel() {
		String gatheringEnemiesInfo = "";
		gatheringEnemiesInfo += Display.enemiesBanner();

		for (Enemy enemy : getEnemies()) {
			gatheringEnemiesInfo += Display.enemiesIntelMessage(enemy.getName(), enemy.getFirePower(), enemy.getPosition().getName());
		}
		System.out.print(gatheringEnemiesInfo);
	}

	/**
	 * Determines whether the mission is accomplished.
	 * <p>
	 * This method checks if the player is alive, the mission target is secured, and the
	 * player is positioned at the designated extraction point.
	 *
	 * @return true if the mission is accomplished; false otherwise
	 */
	@Override
	protected boolean isMissionAccomplished() {
		Room playerPosition = getPlayer().getPosition();
		Room extractionPoint = null;

		for (Room room : getMission().getEntryExitPoints()) {
			if (room.equals(playerPosition)) {
				extractionPoint = room;
			}
		}

		if (getPlayer().isAlive() && getMission().isTargetSecured() && playerPosition.equals(extractionPoint)) {

			setNextScenario(ScenarioNr.SIX);
			setGameOver(true);
			return true;
		}

		return false;
	}

}