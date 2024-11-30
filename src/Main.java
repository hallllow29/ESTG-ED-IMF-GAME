import entities.Enemy;
import entities.Room;
import lib.Graph;
import lib.Node;
import org.json.simple.parser.ParseException;

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
                    System.out.println("Divisao: " + roomObj.getName() + "\n" + enemyObj.toString());
                }
            }

        } catch (IOException e) {
            System.err.println("Erro ao ler o arquivo JSON: " + e.getMessage());
        } catch (ParseException e) {
            System.err.println("Erro ao processar o arquivo JSON: " + e.getMessage());
        } catch (Exception e) {
            System.err.println("Erro inesperado: " + e.getMessage());
        }
    }
}