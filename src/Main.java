import game.briefings.MissionReportManager;
import game.io.MainMenu;
import lib.exceptions.NotElementComparableException;
import org.json.simple.parser.ParseException;

import java.io.IOException;

/**
 * @author 8230068, 8230069
 */
public class Main {

	//private static Mission missionImpl;
	//private static Simulation simulationImpl;
	//private static CustomNetwork<Room> graph;
	//private static LinkedList<Enemy> enemies;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			// INIT GRAPH
			//System.out.println("Carregando ");
			//CustomNetwork<Room> graph = initGraph();
			//Mission missionImpl = Main.getMission();
			//setGraph(graph);

			//displayMissionDetails(graph);

			//displayRoomDetails(graph);

			//displayAdjacentRoomDetails(graph);

			//displayEnemyIntel(missionImpl);

			//displayItems(missionImpl);

			//displayEntryExitPoints(missionImpl);

			//simulationMenu();

			//displayTarget(missionImpl);

			//LinkedList<Room> entry_points_selection = missionImpl.getEntryExitPoints();

			//Iterator<Room> entry_points = entry_points_selection.iterator();
			//Room coco  = null;

			/*for (Room room : mission.getBattlefield().getVertices()) {
				if (room.getName().equals("Heliporto")) {
					coco = room;
				}
			}*/


			//Room entry_point = entry_points_selection.first();

			//BackPack mochila = new BackPack();

			//Player toCruz = new Player("TO CRUZ", 50, mochila);


			/*for (Room roomobj : mission.getBattlefield().getVertices()) {
				simulation.scnario2(roomobj);
			} */
			//ModeManager modeManager = new ModeManager();

			//modeManager.startGame();

			MainMenu.mainMenu();



			// manualSimulation.play();

			//simulation.game();
			// automaticSimulation.play();
			//simulation.playerTurn();
			//simulation.enemyTurn();
			//simulation.setBestPath();


			/*
				LinkedList has no add!
				The add gets extended to addToRear or addToFront in
				UnorderedLinkedList or OrderedLinkedList, right?

				Probably since we now need to change that LinkedList to Unordered
				a Concurrent Modifcation Detected Exception will happen..
				Just like I said... the exception got thrown... FUCK!!!!

				I will fix that tomorrow....

				Fuck it, it was fixed today!!!

				Run and you will see it works for now.

				So next objective is the weight Graph aka. the Network...


			 */






































		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("Erro ao processar o arquivo JSON: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro inesperado: " + e.getMessage());
		} catch (NotElementComparableException e) {
			throw new RuntimeException(e);
		}
	}

	/*public static void setMission(Mission missionImpl) {
		Main.missionImpl = missionImpl;
	}

	public static Mission getMission() {
		return Main.missionImpl;
	}

	public static void setSimulation(Simulation simulationImpl) {
		Main.simulationImpl = simulationImpl;
	}

	public static Simulation getSimulation() {
		return Main.simulationImpl;
	}

	public static void setGraph(CustomNetwork<Room> graph) {
		Main.graph = graph;
	}

	public static Network<Room> getGraph() {
		return Main.graph;
	}

	public static void setEnemies(LinkedList<Enemy> enemies) {
		Main.enemies = enemies;
	}

	public static LinkedList<Enemy> getEnemies() {
		return Main.enemies;
	}

	/**
	 * Displays a menu with options for user input and returns the selected option.
	 *
	 * @param menu the menu text to be displayed to the user
	 * @return the integer value representing the user's selected option, or -1 if the
	 * input is invalid
	 */
	/*public static int options(String menu) {
		Scanner input = new Scanner(System.in);
		int option = -1;

		String output = "---------------------"+
			"\n" + menu +
			"\n[9] GO BACK" +
			"\n[0] EXIT";
		System.out.println(output);
		try {
			option = input.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("SYMBOLS OR LETTERS ARE NO OPTION.");
		}
		// input.close();
		return option;
	}

	/**
	 * Displays a simulation menu to the user, allowing the selection between manual and
	 * automatic simulation modes. It continually prompts for input until a valid
	 * selection is made or the user chooses to exit. The menu provides the following
	 * options:
	 * - 1: Initiate manual simulation
	 * - 2: Initiate automatic simulation
	 * - 9: Exit the simulation menu and return to the previous menu
	 * - 0: Exit the program If an invalid input is received, an error message is
	 * displayed. The method loops until a valid exit condition (option 9 or 0).
	 */
	/*public static void simulationMenu() {
		int option = -1;
		while (true) {
			option = options("\n==== SIMULATION MODE =====\n[1] MANUAL\n[2] AUTOMATIC");
			switch (option) {
				case 1:
					// MANUAL SIMULATION

					// Escolher o ponto de entrada....
					try {
						selectEntryPoint(mission.getEntryPointsSelection());
					} catch (EmptyCollectionException e) {
						System.out.println(e.getMessage());
					} catch (ElementNotFoundException e) {
						throw new RuntimeException(e);
					}

					break;
				case 2:
					// AUTOMATIC SIMULATION
				case 9:
					return;
				case 0:
					System.exit(1);
				default:
					System.out.println("INVALID INPUT");
					break;
			}
		}
	} */

	/*private static CustomNetwork<Room> initGraph() throws IOException, ParseException, NotElementComparableException {
		CustomNetwork<Room> network = new CustomNetwork<>();
		Mission missionImpl = JsonSimpleRead.loadMissionFromJson("mission.json", network);
		setMission(missionImpl);
		return network;
	}

	private static void displayMissionDetails(Network<Room> graph) {
		String result = ("\n\t=========  MISSAO  =========");
		System.out.println(getMission());
	}

	private static void displayRoomDetails(CustomNetwork<Room> graph) {
		String result = ("\n\t========= DIVISOES =========");

		for (Room room : graph.getVertices()) {
			result += "\nRoom: " + room.getName();
		}
		System.out.println(result);
	}

	private static void displayAdjacentRoomDetails(CustomNetwork<Room> graph) {
		// System.out.println("\n\t========= CONEXOES =========");

		String result = ("\n\t========= CONEXOES =========");

		for (Room room : graph.getVertices()) {
			for (Room connectedRoom : graph.getConnectedVertices(room)) {
				// System.out.printf("%-20s <-----> %-15s\n",
				// 	room.getName(), connectedRoom.getName());

				result += "\n["+room.getName() + "] <-----> [" + connectedRoom.getName()+ "]";
			}
		}
		System.out.println(result);
	}

	private static void displayEnemyIntel(Mission missionImpl) {
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

	private static void displayItems(Mission missionImpl) {
		String result = ("\n\t=========  ITEMS  =========");
		for (Item item : missionImpl.getItems()) {
			result += "\nItem: " + item.getName() +
				"\tValue: " + item.getItemValue() +
				"\tPosition: " + item.getPosition();
		}
		System.out.println(result);
	}

	private static void displayEntryExitPoints(Mission missionImpl) {
		System.out.println("\n===== Pontos de Entrada/Saída =====");
		for (Room roomObj : missionImpl.getEntryExitPoints()) {
			System.out.println(roomObj.getName());
		}
	}

	private static void displayTarget(Mission missionImpl) {
		System.out.println("\n==== TARGET ====");
		System.out.println(missionImpl.getTarget());
	}

	public static int interactiveSelection(String entry_point_string) {
		Scanner input = new Scanner(System.in);
		int option = -1;
		System.out.println("---------------------");
		System.out.print("\n[2] PREV <-- [" + entry_point_string + "] --> NEXT [3]");
		System.out.println("\n[1] SELECT");
		System.out.println("[9] GO BACK");
		System.out.println("[0] EXIT");

		try {
			option = input.nextInt();
		} catch (InputMismatchException e) {
			System.err.println("SYMBOLS OR LETTERS ARE NO OPTION.");
		}
		return option;
	}

	/*public static void selectEntryPoint(CircularDoubleLinkedList<Room> entry_points_selection) throws EmptyCollectionException, ElementNotFoundException {
		Scanner input = new Scanner(System.in);
		int option = -1;


		while (true) {
			option = interactiveSelection(entry_point.getName());
			switch (option) {
				case 1:
					// SELECT
					// Porque nao passar para a simulation?
					// ou por Simulation abstract e para o modo que precisa entao instanciar
					// com entry_point.

					// OPAH NAO SEI VOU TENTAR
					for (Room roomObj : graph.getVertices()) {
						simulation.scnario2(roomObj);
					}
					displayEnemyIntel(graph);


					return;
				case 2:
					// entry_point = entry_points.previous();
					// option = interactiveSelection(entry_point.getName());
					break;
				case 3:
					// NEXT
					entry_point = entry_points.next();
					option = interactiveSelection(entry_point.getName());
					break;
				case 9:
					return;
				case 0:
					System.exit(1);
				default:
					System.out.println("INVALID INPUT");
					break;

			}
		}
	}
} */

}

