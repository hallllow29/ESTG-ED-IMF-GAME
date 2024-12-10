import entities.*;
import game.Mission;
import game.Report;
import game.Simulation;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.graphs.CustomNetwork;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertFalse;

import java.util.Iterator;

import static org.junit.jupiter.api.Assertions.*;


public class SimulationTest {

    private Mission mission;
    private Player player;
    private Report report;
    private CustomNetwork<Room> battlefield;
    private Room room1, room2, room3, extractionPoint, targetRoom;
    private Simulation simulation;

    @BeforeEach
    public void gameSetup() throws NotElementComparableException {
        battlefield = new CustomNetwork<>();
        mission = new Mission("Mission001", 1, battlefield);
        BackPack mochila = new BackPack();
        player = new Player("Tó Cruz", 100, mochila);
        report = new Report("Teste", this.player, this.mission);

        // Criar as salas
        room1 = new Room("Room 1");
        room2 = new Room("Room 2");
        room3 = new Room("Room 3");
        extractionPoint = new Room("Extraction Point");
        targetRoom = new Room ("Target room");

        //Adicionar salas ao mapa
        battlefield.addVertex(room1);
        battlefield.addVertex(room2);
        battlefield.addVertex(room3);
        battlefield.addVertex(extractionPoint);
        battlefield.addVertex(targetRoom);

        //Adicionar conexoes ao mapa
        battlefield.addEdge(room1, room2, 1);
        battlefield.addEdge(room2, targetRoom, 2);
        battlefield.addEdge(targetRoom, extractionPoint, 3);

        //Definir o target e o entry point
        mission.setTarget(new Target(targetRoom, "Professor Ricardo"));
        mission.setEntryExitPoint(extractionPoint);

         simulation = new Simulation(mission, player, report) {
             @Override
             public void movePlayer() throws ElementNotFoundException, EmptyCollectionException {

             }

             @Override
             public void game() throws ElementNotFoundException, EmptyCollectionException {

             }
         };
    }

    @Test
    public void testSetupEntryPoints() throws EmptyCollectionException {
        assertFalse(mission.getEntryExitPoints().isEmpty(), "Os pontos de entrada/saída devem estar configurados!");
        Room firstEntry = mission.getEntryExitPoints().first();
        assertEquals("Extraction Point", firstEntry.getName());
    }


    @Test
    public void testMove() throws ElementNotFoundException {
        Room currentRoom = player.getPosition();
        Room targetRoom = mission.getTarget().getRoom();

        Iterator<Room> path = battlefield.iteratorShortestPath(currentRoom, targetRoom);

        assertTrue(path.hasNext(), "O caminho deveria de existir!");
        assertEquals(currentRoom, path.next(), "A primeira sala deve ser a posição atual do jogador");

        if (path.hasNext()) {
            Room nextRoom = path.next();
            player.setPosition(nextRoom);

            assertEquals(nextRoom, player.getPosition(), "O jogador deveria de estar na próxima sala");
            System.out.println("Jogador moveu-se para " + nextRoom.getName());
        } else {
            fail("Nenhuma sala próxima encontrada no caminho");
        }
    }

    @Test
    public void testReturnToExtraction() throws EmptyCollectionException, ElementNotFoundException {
        Room currentRoom = player.getPosition();
        Room extractionRoom = mission.getEntryExitPoints().first();

        Iterator<Room> path = battlefield.iteratorShortestPath(currentRoom, extractionRoom);

        assertTrue(path.hasNext(), "O caminho deveria de existir");
        assertEquals(currentRoom, path.next(), "A primeira sala deve ser a posição atual do jogador");

        if (path.hasNext()) {
            Room nextRoom = path.next();
            player.setPosition(nextRoom);

            assertEquals(nextRoom, player.getPosition(), "O jogador deveria de estar na proxima sala");
            System.out.println("Jogador moveu-se para " + nextRoom.getName());
        } else {
            fail("Nenuk caminho de volta para extraction");
        }

    }

    @Test
    public void testEnemiesMove() throws NotElementComparableException, ElementNotFoundException {
        Enemy enemy = new Enemy("Pedro",10, room1);
        mission.setEnemy(enemy);

        Room currentRoom = enemy.getPosition();

        for (Room room : battlefield.getConnectedVertices(currentRoom)) {
            enemy.setPosition(room);
        }

        assertNotEquals("Room 1", enemy.getPosition().getName());

    }

    @Test
    public void testBackPack() throws EmptyCollectionException {
        BackPack mochila = new BackPack();

        MediKit kit1 = new MediKit("Small kit", null, 20);
        MediKit kit2 = new MediKit("Big kit", null, 50);
        mochila.addKit(kit1);
        mochila.addKit(kit2);

        assertEquals(2, mochila.numberOfKits());
        assertEquals(kit2, mochila.useKit());
        assertEquals(1, mochila.numberOfKits());

    }

    @Test
    public void testReport() {
        report.addRoom("Room 1");
        report.addRoom("Room 2");
        report.setMissionStatus("Accomplished");
        report.addEnemy("BadGuy");

        assertEquals("Accomplished", report.getMissionStatus());
        assertEquals(2, report.getTrajectoryToTarget().size());
        assertEquals(1, report.getEnemiesSurvived().size());

    }

    @Test
    public void testCalculatePathDamage() throws NotElementComparableException, ElementNotFoundException {
        Enemy pedro = new Enemy("Pedro", 10, room1);
        Enemy ruben = new Enemy("Ruben", 10, room2);
        mission.setEnemy(pedro);
        mission.setEnemy(ruben);

        double damage = this.simulation.calculatePathDamage(battlefield.iteratorShortestPath(room1, targetRoom));
        assertEquals(20, damage);

    }

    @Test
    public void testPlayerDie() {
        player.takesDamageFrom(100);
        assertFalse(player.isAlive());
    }

    @Test
    public void testExtractionPointSelection() throws ElementNotFoundException {
        Room bestExtractionPoint = simulation.bestExtractionPoint(player.getPosition());
        assertEquals("Extraction Point", bestExtractionPoint.getName());

    }

}
