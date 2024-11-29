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
            Graph<String> graph = new Graph<>();
            Mission mission = JsonSimpleRead.loadMissionFromJson("mission.json", graph);

            System.out.println("===== Missão =====");
            System.out.println(mission);

            System.out.println("\n===== Pontos de Entrada/Saída =====");
            for (Room room : mission.getEntryPoints()) {
                System.out.println(room.getName());
            }

            System.out.println("\n===== Divisões no Grafo =====");
            for (String roomName : graph.getVertices()) {
                Room room = graph.getRoom(roomName);
                if (room != null) {
                    System.out.println(room);
                }
            }

            System.out.println("\n===== Conexões =====");
            for (String roomName : graph.getVertices()) {
                for (String connectedRoom : graph.getConnectedVertices(roomName)) {
                    System.out.println(roomName + " está conectado a " + connectedRoom);
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