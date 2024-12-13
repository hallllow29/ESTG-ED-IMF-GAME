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
 * The MainMenu class is responsible for providing the primary user interface for the
 * application. It manages the interaction with users and provides options to navigate
 * through the different core functionalities, including mission selection and report
 * management.
 */
public class MainMenu {

	/**
	 * Displays the main menu of the application and handles user interaction to navigate
	 * through various options such as mission selection, report management, or exiting
	 * the application. This method runs in a loop until the user chooses to exit.
	 *
	 * @throws NotElementComparableException if an operation requiring comparable elements
	 *                                       is performed on non-comparable elements
	 * @throws EmptyCollectionException      if an operation is performed on an empty
	 *                                       collection
	 * @throws ElementNotFoundException      if a required element is not found during
	 *                                       execution
	 * @throws IOException                   if an I/O error occurs
	 * @throws ParseException                if an error occurs while parsing inputs
	 */
	public static void mainMenu() throws NotElementComparableException, EmptyCollectionException, ElementNotFoundException, IOException, ParseException {
		MissionReportManager repManager = new MissionReportManager();
		boolean running = true;

		Scanner scanner = new Scanner(System.in);
		int choice = 0;
		String welcomeMenuInfo = "";

		while (running) {

			welcomeMenuInfo = Display.welcomeMenu();
			System.out.print(welcomeMenuInfo);

			boolean validSelection = false;
			while (!validSelection) {
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					switch (choice) {
						case 1:
							validSelection = true;
							ModeManager modeManager = new ModeManager(repManager);
							modeManager.startGame();
							break;
						case 2:
							showReportsMenu(scanner, repManager);
							validSelection = true;
							break;
						case 9:
							running = false;
							validSelection = true;
							break;
						default:
							System.out.println("\nInvalid option!");
							System.out.print("Option: ");
					}
				} else {
					System.out.println("\nInvalid option!");
					System.out.print("Option: ");
					scanner.next();
				}
			}
		}
	}

	/**
	 * Displays the reports menu and allows the user to interact with options such as
	 * listing available reports, visualizing a specific report, or going back to the main
	 * menu. The method runs in a loop until the user selects the "Back" option.
	 *
	 * @param scanner       the input scanner used to read user choices from the console
	 * @param reportManager the manager object responsible for handling report-related
	 *                      operations such as listing and visualizing reports
	 */
	private static void showReportsMenu(Scanner scanner, MissionReportManager reportManager) throws EmptyCollectionException, IOException {
		boolean running = true;
		String showReportMenuInfo = "";

		while (running) {

			showReportMenuInfo = Display.selectReportMenu();
			System.out.print(showReportMenuInfo);

			int choice;
			boolean validSelection = false;
			while (!validSelection) {
				if (scanner.hasNextInt()) {
					choice = scanner.nextInt();
					switch (choice) {
						case 1:
							System.out.println("Listing all reports available....");
							ModeManager.getRepManager().saveALlReports();
							validSelection = true;
							break;
						case 2:
							ModeManager.getRepManager().viewReports();
							validSelection = true;
							break;
						case 3:
							running = false;
							validSelection = true;
							break;
						default:
							System.out.println("\nInvalid option!");
							System.out.print("Option: ");
					}
				} else {
					System.out.println("\nInvalid option!");
					System.out.print("Option: ");
					scanner.next();
				}
			}
		}
	}
}
