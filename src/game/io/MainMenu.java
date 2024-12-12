package game.io;

import game.briefings.MissionReportManager;
import game.modes.ModeManager;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import org.json.simple.parser.ParseException;

import java.io.IOException;
import java.util.Scanner;

/**
 * The MainMenu class is responsible for providing the primary user interface
 * for the application. It manages the interaction with users and provides options
 * to navigate through the different core functionalities, including mission selection
 * and report management.
 */
public class MainMenu {

    /**
     * Displays the main menu of the application and handles user interaction to navigate
     * through various options such as mission selection, report management, or exiting
     * the application. This method runs in a loop until the user chooses to exit.
     *
     * @throws NotElementComparableException if an operation requiring comparable elements is performed on non-comparable elements
     * @throws EmptyCollectionException if an operation is performed on an empty collection
     * @throws ElementNotFoundException if a required element is not found during execution
     * @throws IOException if an I/O error occurs
     * @throws ParseException if an error occurs while parsing inputs
     */
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

    /**
     * Displays the reports menu and allows the user to interact with options such as listing available reports,
     * visualizing a specific report, or going back to the main menu. The method runs in a loop until the user
     * selects the "Back" option.
     *
     * @param scanner the input scanner used to read user choices from the console
     * @param reportManager the manager object responsible for handling report-related operations such as listing and visualizing reports
     */
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
