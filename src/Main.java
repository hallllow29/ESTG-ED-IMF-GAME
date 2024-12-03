import entities.Enemy;
import entities.Room;
import lib.CircularDoubleLinkedList;
import lib.Graph;
import lib.LinkedList;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
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
	private static Graph<Room> graph;
	private static LinkedList<Enemy> enemies;

	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			// INIT GRAPH
			Graph<Room> graph = initGraph();

			setGraph(graph);

			displayMissionDetails(graph);

			displayRoomDetails(graph);

			displayAdjacentRoomDetails(graph);

			displayEnemyIntel(mission);

			// displayEntryExitPoints(graph);




			// simulationMenu();



			// CircularDoubleLinkedList<Room> entry_points_selection =  mission.getEntryPointsSelection();

			// Iterator<Room> entry_points = entry_points_selection.iterator();

			// Room entry_point = entry_points.next();

			// Main.setSimulation(new Simulation(getMission(), getGraph(), entry_point));

			/*
			Nos nao tamos a ser eficazes...

			Cada Enemy tem só um Room

			Se percorremos a lista de Rooms...

			Por que nao marcar cada Room com o boolean se tem Enemy ou nao?

			Se o Room tem Enemy (boolean = true)

			Entao aí procuramos qual enemy currentPosition bate com o current room nna loop.

			Ou SEJA Tugas... tu percorres a lista de enemies e nao de ROOMS que é onde
			tá a dar a bronca toda

			PORQUE NOS SO PRECISAMOS DE MUDAR A POSICAO DO ENEMY...

			FODASSE nem escrever consigo hoje.

			Vou Zzz
			
















			 */

























































































		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("Erro ao processar o arquivo JSON: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro inesperado: " + e.getMessage());
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

	public static void setGraph(Graph<Room> graph) {
		Main.graph = graph;
	}

	public static Graph<Room> getGraph() {
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
	}*/

	private static Graph<Room> initGraph() throws IOException, ParseException {
		Graph<Room> graph = new Graph<>();
		Mission mission = JsonSimpleRead.loadMissionFromJson("mission.json", graph);
		setMission(mission);
		return graph;
	}

	private static void displayMissionDetails(Graph<Room> graph) {
		System.out.println("==== MISSAO ====");
		System.out.println(getMission());
	}

	private static void displayRoomDetails(Graph<Room> graph) {
		System.out.println("==== DIVISOES ====");
		for (Room room : graph.getVertices()) {
			System.out.println(room);
		}
	}

	private static void displayAdjacentRoomDetails(Graph<Room> graph) {
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

	/*private static void displayEntryExitPoints(Graph<Room> graph) {
		System.out.println("\n===== Pontos de Entrada/Saída =====");
		for (Room roomObj : mission.getEntryPoints()) {
			System.out.println(roomObj.getName());
		}
	}*/

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
	}*/
}

