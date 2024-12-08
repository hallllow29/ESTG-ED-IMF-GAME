import entities.BackPack;
import entities.Player;
import lib.Network;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class ModeManager {
    private Mission mission;

    public ModeManager() {
        this.mission = null;
    }

    public Player createPlayer() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("=== Welcome to Improbable Mission Force (IMF) ===");
        System.out.println("Please enter your name: ");
        String name = scanner.nextLine();
        BackPack backPack = new BackPack();

        return new Player(name, 100, backPack);

    }

    public void selectMission() throws NotElementComparableException, IOException, ParseException {
        Scanner scanner = new Scanner(System.in);
        int choice;

        System.out.println("==== IMF - Mission Select");
        System.out.println("[1] Pata de coelho");
        System.out.println("[2] Rato de Aço");
        System.out.println("[9] Exit");

        choice = scanner.nextInt();

        switch (choice) {
            case 1:
                this.mission = JsonSimpleRead.loadMissionFromJson("mission.json", new Network<>());
                break;
            case 2:
                this.mission = JsonSimpleRead.loadMissionFromJson("missao_rato_de_aco.json", new Network<>());
                break;
            case 9 :
                return;
            default:
                System.out.println("Please select a valid option");
                break;
        }
    }

    public void startGame() throws NotElementComparableException, IOException, ParseException, ElementNotFoundException, EmptyCollectionException {
        Player newPlayer = createPlayer();
        System.out.println("==== IMF - Simulation Mode");
        System.out.println("[1] Automatic");
        System.out.println("[2] Manual");

        Scanner scanner = new Scanner(System.in);
        int choice = scanner.nextInt();

        switch (choice) {
            case 1:
                selectMission();
                runAutomaticSimulation(newPlayer);
                break;
            case 2:
                selectMission();
                runManualSimulation(newPlayer);
                break;
            case 9:
                return;
            default:
                System.out.println("Please select a valid option");
                break;
        }
    }


    private void runAutomaticSimulation(Player player) throws ElementNotFoundException, EmptyCollectionException {
        AutomaticMode autoMode = new AutomaticMode(mission, player);

        autoMode.game();
    }

    private void runManualSimulation(Player player) throws EmptyCollectionException, ElementNotFoundException {
        ManualMode manualMode = new ManualMode(mission, player);

        manualMode.game();
    }
}
