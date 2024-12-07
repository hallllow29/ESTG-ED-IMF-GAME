import entities.*;
import lib.LinkedList;
import lib.Network;
import lib.exceptions.NotElementComparableException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author 8230068, 8230069
 */
public class Main {

	private static Mission mission;
	private static Simulation simulation;
	private static Network<Room> graph;
	private static LinkedList<Enemy> enemies;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			// INIT GRAPH
			System.out.println("Carregando ");
			Network<Room> graph = initGraph();
			Mission mission = Main.getMission();
			setGraph(graph);

			displayMissionDetails(graph);

			displayRoomDetails(graph);

			displayAdjacentRoomDetails(graph);

			displayEnemyIntel(mission);

			displayItems(mission);

			displayEntryExitPoints(mission);

			//simulationMenu();

			displayTarget(mission);

			LinkedList<Room> entry_points_selection = mission.getEntryExitPoints();

			//Iterator<Room> entry_points = entry_points_selection.iterator();
			Room coco  = null;

			/*for (Room room : mission.getBattlefield().getVertices()) {
				if (room.getName().equals("Heliporto")) {
					coco = room;
				}
			}*/


			//Room entry_point = entry_points_selection.first();

			BackPack mochila = new BackPack();

			Player toCruz = new Player("Tó cruz", 1000, mochila);

			Main.setSimulation(new Simulation(getMission(), toCruz));

			/*for (Room roomobj : mission.getBattlefield().getVertices()) {
				simulation.scnario2(roomobj);
			} */
			AutomaticSimulation automaticSimulation = new AutomaticSimulation(mission, toCruz);
			ManualSimulation manualSimulation = new ManualSimulation(mission, toCruz);

			// manualSimulation.play();

			simulation.game();
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

			Iterator<Enemy> enemies = mission.getEnemies().iterator();
		int counter = 0;
			while (enemies.hasNext() && counter != 3) {
			System.out.println(enemies.next());
			enemies.remove();
			counter++;
		}

			Iterator<Enemy> enemyIterator = mission.getEnemies().iterator();


			while (enemies.hasNext()) {
				System.out.println("ENTRA");
				System.out.println(enemies.next());
				enemies.remove();
			}








































		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("Erro ao processar o arquivo JSON: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro inesperado: " + e.getCause());
		} catch (NotElementComparableException e) {
			throw new RuntimeException(e);
		}
	}

	public static void setMission(Mission mission) {
		Main.mission = mission;
	}

	public static Mission getMission() {
		return Main.mission;
	}

	public static void setSimulation(Simulation simulation) {
		Main.simulation = simulation;
	}

	public static Simulation getSimulation() {
		return Main.simulation;
	}

	public static void setGraph(Network<Room> graph) {
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
	public static int options(String menu) {
		Scanner input = new Scanner(System.in);
		int option = -1;
		System.out.println("---------------------");
		System.out.println(menu);
		System.out.println("\n[9] GO BACK");
		System.out.println("[0] EXIT");

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

	private static Network<Room> initGraph() throws IOException, ParseException, NotElementComparableException {
		Network<Room> network = new Network<>();
		Mission mission = JsonSimpleRead.loadMissionFromJson("mission.json", network);
		setMission(mission);
		return network;
	}

	private static void displayMissionDetails(Network<Room> graph) {
		System.out.println("==== MISSAO ====");
		System.out.println(getMission());
	}

	private static void displayRoomDetails(Network<Room> graph) {
		System.out.println("==== DIVISOES ====");
		for (Room room : graph.getVertices()) {
			System.out.println(room);
		}
	}

	private static void displayAdjacentRoomDetails(Network<Room> graph) {
		System.out.println("\n===== Conexões =====");
		for (Room roomObj : graph.getVertices()) {
			for (Room connectedRoom : graph.getConnectedVertices(roomObj)) {
				System.out.println(roomObj.getName() + " está conectado a " + connectedRoom.getName());
			}
		}
	}

	private static void displayEnemyIntel(Mission mission) {
		System.out.println("\n==== INIMIGOS ====");
		for (Enemy enemyObj : mission.getEnemies()) {
			System.out.println(enemyObj);
		}
	}

	private static void displayItems(Mission mission) {
		System.out.println("\n==== ITEMS ====");
		for (Item itemObj : mission.getItems()) {
			System.out.println(itemObj);
		}
	}

	private static void displayEntryExitPoints(Mission mission) {
		System.out.println("\n===== Pontos de Entrada/Saída =====");
		for (Room roomObj : mission.getEntryExitPoints()) {
			System.out.println(roomObj.getName());
		}
	}

	private static void displayTarget(Mission mission) {
		System.out.println("\n==== TARGET ====");
		System.out.println(mission.getTarget());
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

