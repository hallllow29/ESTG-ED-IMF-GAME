package game;

import entities.*;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.graphs.CustomNetwork;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class ModeManager {
	private Mission missionImpl;

	public ModeManager() {
		this.missionImpl = null;
	}

	public Player createPlayer() {
		Scanner scanner = new Scanner(System.in);
		String createPlayerInfo = "";

		createPlayerInfo = Display.welcomeIMFbanner();
		createPlayerInfo += Display.enterYourNameMessage();
		System.out.print(createPlayerInfo);

		String playerName = scanner.nextLine();

		return new Player(playerName, 100, chooseBackPackSize(scanner));

	}

	public void selectMission() throws NotElementComparableException, IOException, ParseException {
		int choice;
		Scanner scanner = new Scanner(System.in);

		String selectMissionInfo = "";
		selectMissionInfo += Display.selectMessage("a mission");
		selectMissionInfo += Display.missionSelection();
		System.out.print(selectMissionInfo);

		choice = scanner.nextInt();

		switch (choice) {
			case 1:
				this.missionImpl = JsonSimpleRead.loadMissionFromJson("mission.json", new CustomNetwork<>());
				break;
			case 2:
				this.missionImpl = JsonSimpleRead.loadMissionFromJson("missao_rato_de_aco.json", new CustomNetwork<>());
				break;
			default:
				System.out.println("Please select a valid option!");
				break;
		}

	}

	public void startGame() throws NotElementComparableException, IOException, ParseException, ElementNotFoundException, EmptyCollectionException {
		Player newPlayer = createPlayer();

		this.selectMission();

		String startGameInfo = "";
		startGameInfo += Display.simulationModeSelection();
		startGameInfo += Display.selectMessage("the simulation");
		System.out.print(startGameInfo);

		Scanner scanner = new Scanner(System.in);
		int choice = scanner.nextInt();

		switch (choice) {
			case 1:
				this.displayMissionDetails();
				runAutomaticSimulation(newPlayer);
				break;
			case 2:
				this.displayMissionDetails();
				runManualSimulation(newPlayer);
				break;
			case 9:
				return;
			default:
				System.out.println("Please select a valid option");
				break;
		}
	}

	private void runAutomaticSimulation(Player player) throws ElementNotFoundException, EmptyCollectionException {
		Report report = new Report("Automatic", player, missionImpl);
		report.setBackPackSize(player.getBack_pack().getMaxCapacity());
		AutomaticMode autoMode = new AutomaticMode(missionImpl, player, report);

		autoMode.game();

		SaveToJsonFile.saveJsonFile(report);
	}

	private void runManualSimulation(Player player) throws EmptyCollectionException, ElementNotFoundException {
		Report report = new Report("Manual", player, missionImpl);
		report.setBackPackSize(player.getBack_pack().getMaxCapacity());
		ManualMode manualMode = new ManualMode(missionImpl, player, report);

		manualMode.game();

		SaveToJsonFile.saveJsonFile(report);
	}

	private void displayMissionDetails() {
		this.displayMission();
		this.displayRoomDetails(missionImpl.getBattlefield());
		this.displayAdjacentRoomDetails(missionImpl.getBattlefield());
		this.displayEnemyIntel();
		this.displayItems();
		this.displayEntryExitPoints();
		this.displayTarget();
	}

	private void displayMission() {
		System.out.println("\n\t=========  MISSAO  =========");
		System.out.println(this.missionImpl.getCode());
	}

	private void displayRoomDetails(CustomNetwork<Room> graph) {
		String result = ("\n\t========= DIVISOES =========");

		for (Room room : graph.getVertices()) {
			result += "\nRoom: " + room.getName();
		}
		System.out.println(result);
	}

	private void displayAdjacentRoomDetails(CustomNetwork<Room> graph) {
		// System.out.println("\n\t========= CONEXOES =========");

		String result = ("\n\t========= CONEXOES =========");

		for (Room room : graph.getVertices()) {
			for (Room connectedRoom : graph.getConnectedVertices(room)) {
				// System.out.printf("%-20s <-----> %-15s\n",
				// 	room.getName(), connectedRoom.getName());

				result += "\n[" + room.getName() + "] <-----> [" + connectedRoom.getName() + "]";
			}
		}
		System.out.println(result);
	}

	private void displayEnemyIntel() {
		String result = ("\n\t========= INIMIGOS =========");
		for (Enemy enemy : missionImpl.getEnemies()) {
			// System.out.printf("Name: %-10s Fire Power: %-4s Position: %s\n",
			// 	enemy.getName(), enemy.getFirePower(), enemy.getPosition());
			result += "\nName: " + enemy.getName() +
				"\tFire Power: " + enemy.getFirePower() +
				" Position: " + enemy.getPosition();
		}
		System.out.println(result);
	}

	private void displayItems() {
		String result = ("\n\t=========  ITEMS  =========");
		for (Item item : missionImpl.getItems()) {
			result += "\nItem: " + item.getName() +
				"\tValue: " + item.getItemValue() +
				"\tPosition: " + item.getPosition();
		}
		System.out.println(result);
	}

	private void displayEntryExitPoints() {
		System.out.println("\n===== Pontos de Entrada/Saída =====");
		for (Room roomObj : missionImpl.getEntryExitPoints()) {
			System.out.println(roomObj.getName());
		}
	}

	private void displayTarget() {
		System.out.println("\n==== TARGET ====");
		System.out.println(missionImpl.getTarget());
	}

	private BackPack chooseBackPackSize(Scanner scanner) {
		String backPackSizeInfo = "";
		backPackSizeInfo += Display.selectBackPackBanner();
		backPackSizeInfo += Display.backPackSelection();
		System.out.print(backPackSizeInfo);

		int choice = 0;
		BackPackSize backPackSize = null;
		choice = scanner.nextInt();

		switch (choice) {
			case 1:
				backPackSize = BackPackSize.SMALL;
				break;
			case 2:
				backPackSize = BackPackSize.MEDIUM;
				break;
			case 3:
				backPackSize = BackPackSize.LARGE;
				break;
			case 4:
				backPackSize = BackPackSize.TRY_HARD;
				break;
			default:
				System.out.println("Select a valid option");
				break;
		}

		return new BackPack(backPackSize.getCapacity());
	}

}
