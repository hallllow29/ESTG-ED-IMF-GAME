package game;

import com.sun.security.jgss.GSSUtil;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import org.json.simple.parser.ParseException;

import javax.management.modelmbean.ModelMBean;
import java.io.IOException;
import java.util.Scanner;

public class MainMenu {

    public static void mainMenu() throws NotElementComparableException, EmptyCollectionException, ElementNotFoundException, IOException, ParseException {
        MissionReportManager repManager = new MissionReportManager();
        boolean running = true;

        Scanner scanner = new Scanner(System.in);
        int choice = 0;

        while(running) {
            System.out.println("==== WELCOME TO IMPROBABLE MISSION FORCE ====");
            System.out.println("[1] Mission Select");
            System.out.println("[2] Report Menu");
            System.out.println("[9] Exit");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    ModeManager modeManager = new ModeManager();
                    modeManager.startGame();
                    break;
                case 2:
                    showReportsMenu(scanner, repManager);
                    break;
                case 9:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option!");

            }
        }

    }

    private static void showReportsMenu(Scanner scanner, MissionReportManager reportManager) {
        boolean running = true;

        while(running) {
            System.out.println("\n==== REPORTS MENU ====");
            System.out.println("[1] List Available Reports");
            System.out.println("[2] Visualize Report");
            System.out.println("[3] Back");

            int choice = scanner.nextInt();

            switch (choice) {
                case 1 :
                    System.out.println("Listing all reports available....");
                    reportManager.listReports();
                    break;
                case 2:
                    reportManager.visualizeReport();
                    break;
                case 3:
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option!");
            }
        }
    }
}
