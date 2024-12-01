import entities.Enemy;
import entities.Room;
import lib.Graph;
import org.json.simple.parser.ParseException;

import java.util.InputMismatchException;
import java.util.Scanner;

import java.io.IOException;

/**
 *
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

            System.out.println("\n===== Pontos de Entrada/Saída =====");
            for (Room room : mission.getEntryPoints()) {
                System.out.println(room.getName());
            }

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

            int option = 0;
            Scanner input = new Scanner(System.in);
			option = options("\n==== SIMULATION MODE =====\n[1] MANUAL\n[2] SIMULATION");



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
     * @return the integer value representing the user's selected option, or -1 if the input is invalid
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
		return option;
	}
}