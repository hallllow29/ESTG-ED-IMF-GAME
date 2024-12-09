import entities.BackPack;
import entities.Player;
import lib.CustomNetwork;
import lib.Network;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

public class ModeManager {
    private Mission missionImpl;

    public ModeManager() {
        this.missionImpl = null;
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
                this.missionImpl = JsonSimpleRead.loadMissionFromJson("mission.json", new CustomNetwork<>());
                break;
            case 2:
                this.missionImpl = JsonSimpleRead.loadMissionFromJson("missao_rato_de_aco.json", new CustomNetwork<>());
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
        Report report = new Report("Automatic", player, missionImpl);
        AutomaticMode autoMode = new AutomaticMode(missionImpl, player, report);

        autoMode.game();

        SaveToJsonFile.saveJsonFile(report);
    }

    private void runManualSimulation(Player player) throws EmptyCollectionException, ElementNotFoundException {
        Report report = new Report("Manual", player, missionImpl);
        ManualMode manualMode = new ManualMode(missionImpl, player, report);

        manualMode.game();

        SaveToJsonFile.saveJsonFile(report);
    }
}
