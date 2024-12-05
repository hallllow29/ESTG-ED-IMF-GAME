import entities.BackPack;
import entities.Player;
import lib.Network;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class SimulationManager {
    private final Mission mission;

    public SimulationManager(String jsonFilePath) throws NotElementComparableException, IOException, ParseException {
        this.mission = JsonSimpleRead.loadMissionFromJson(jsonFilePath, new Network<>());

    }

    public void startGame() throws EmptyCollectionException, ElementNotFoundException {
        System.out.println("Choose the simulation mode: ");
        System.out.println("1. Automatic");
        System.out.println("2. Manual");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        GameMode gameMode;
        switch (choice) {
            case 1:
                runAutomaticSimulation();
                break;
            case 2:
                //gameMode = new ManualSimulation(mission);
                break;
            default:
                System.out.println("Invalid choice");
        }

    }


    private void runAutomaticSimulation() throws EmptyCollectionException, ElementNotFoundException {
        BackPack backPack = new BackPack();
        Player player = new Player("To cruz ", 100, backPack);

        AutomaticSimulation simulation = new AutomaticSimulation(mission, player);

        simulation.play();

    }

}
