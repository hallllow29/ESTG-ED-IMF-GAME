import entities.Enemy;
import entities.Room;
import lib.CircularDoubleLinkedList;
import lib.Graph;
import lib.exceptions.EmptyCollectionException;
import lib.interfaces.IteratorADT;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.rmi.server.RMIClassLoader;
import java.util.InputMismatchException;
import java.util.Iterator;
import java.util.Scanner;

/**
 * @author 8230068, 8230069
 */
public class Main {
	/**
	 * @param args the command line arguments
	 */
	public static void main(String[] args) {
		try {
			Graph<Room> graph = new Graph<>();
			Mission mission = JsonSimpleRead.loadMissionFromJson("mission.json", graph);

			System.out.println("===== Missão =====");
			System.out.println(mission);

			System.out.println("\n===== Divisões no Grafo =====");
			for (Room roomObj : graph.getVertices()) {
				Room room = graph.getRoom(roomObj.getName());
				if (room != null) {
					System.out.println(room);
				}
			}

			System.out.println("\n===== Conexões =====");
			for (Room roomObj : graph.getVertices()) {
				for (Room connectedRoom : graph.getConnectedVertices(roomObj)) {
					System.out.println(roomObj.getName() + " está conectado a " + connectedRoom.getName());
				}
			}

			System.out.println("\n==== Inimigos ====");
			for (Room roomObj : graph.getVertices()) {
				for (Enemy enemyObj : roomObj.getEnemies()) {
					System.out.println(enemyObj);
				}
			}

			System.out.println("\n===== Pontos de Entrada/Saída =====");
			for (Room roomObj : mission.getEntryPoints()) {
				System.out.println(roomObj.getName());
			}

			simulationMenu(mission);

			// new Simulation(Mission mission, Graph<Room> graph, Player player, )


		} catch (IOException e) {
			System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
		} catch (ParseException e) {
			System.err.println("Erro ao processar o arquivo JSON: " + e.getMessage());
		} catch (Exception e) {
			System.err.println("Erro inesperado: " + e.getMessage());
		}
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
	public static void simulationMenu(Mission mission) {
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

	public static Room selectEntryPoint(CircularDoubleLinkedList<Room> entry_points_selection) throws EmptyCollectionException {
		Scanner input = new Scanner(System.in);
		int option = -1;

		Iterator<Room> entry_points = entry_points_selection.iterator();
		option = interactiveSelection(entry_points.next().getName());
		while (true) {
			switch (option) {
				case 1:
					// SELECT

					break;
				case 2:
					// option = interactiveSelection(entry_points.)
					break;
				case 3:
					// NEXT
					option = interactiveSelection(entry_points.next().getName());
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

	public static void entryPointMenu() {

	}
}