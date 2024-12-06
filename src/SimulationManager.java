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
    private Mission mission;

    public SimulationManager() throws NotElementComparableException, IOException, ParseException {
        //this.mission = JsonSimpleRead.loadMissionFromJson(jsonFilePath, new Network<>());
        this.mission = null;
    }

    public void chooseMission() throws NotElementComparableException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        int choice;
        System.out.println("Choose a mission you want to do: ");
        System.out.println("[1] Pata de Coelho");
        System.out.println("[2] Rato de Aço");
        System.out.println("[3] Exit");

        choice = scanner.nextInt();

        switch(choice) {
            case 1:
                this.mission = JsonSimpleRead.loadMissionFromJson("mission.json", new Network<>());
                break;
            case 2:
                this.mission = JsonSimpleRead.loadMissionFromJson("missao_rato_de_aco.json", new Network<>());
                break;
            case 3:
                return;
            default:
                System.out.println("Please select a valid option");
                break;
        }
    }

    public void startGame() throws EmptyCollectionException, ElementNotFoundException, NotElementComparableException, IOException, ParseException {
        System.out.println("Choose the simulation mode: ");
        System.out.println("1. Automatic");
        System.out.println("2. Manual");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                chooseMission();
                runAutomaticSimulation();
                break;
            case 2:
                chooseMission();
                runManualSimulation();
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
    private  void runManualSimulation() throws EmptyCollectionException, ElementNotFoundException {
        BackPack backPack = new BackPack();
        Player player = new Player("To cruz", 100, backPack);

        ManualSimulation simulation = new ManualSimulation(mission, player);

        simulation.play();
    }

}
