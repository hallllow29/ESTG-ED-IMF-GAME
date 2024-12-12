package game.io;

/**
 * The Display class provides a collection of static methods to generate descriptive
 * messages for various scenarios, player actions, enemy interactions, target events,
 * and path-related updates in the game's narrative. These methods can be utilized
 * to facilitate dynamic gameplay messaging and logging.
 */
public class Display {

	//------------------------------SCENARIOS---------------------------------------------

	/**
	 * Generates a message indicating the start of Scenario 1 where the player makes contact
	 * with enemies and has the priority of attack over them. This method provides a
	 * formatted string to be displayed during the gameplay.
	 *
	 * @param playerName The name of the player engaging in Scenario 1.
	 * @return A formatted message string indicating the start of Scenario 1 and providing
	 *         key details about the player's interaction and priority of attack.
	 */
	public static String scenarioUMstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 1 START >>] ==========" +
			"\n|\t" + playerName + " makes contact with ENEMIES..." +
			"\n|\tAND has priority of attack over ENEMIES...";
	}

	/**
	 * Generates a formatted message indicating the end of Scenario 1.
	 * This message serves as a closure marker for the Scenario 1 gameplay sequence.
	 *
	 * @return A string message marking the end of Scenario 1.
	 */
	public static String scenarioUMendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 1  END  >>] ==========";
	}

	/**
	 * Generates a message indicating the start of Scenario 2, where the room the player
	 * entered is described as clear. This method provides a formatted string to be
	 * presented during the gameplay sequence.
	 *
	 * @param playerName The name of the player entering the room during Scenario 2.
	 * @return A formatted string containing the start message of Scenario 2.
	 */
	// Scenario 2
	public static String scenarioDOISstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 2 START >>] ==========" +
			"\n|\tAND the room " + playerName + " entered is clear...";
	}

	/**
	 * Generates a formatted message indicating the end of Scenario 2.
	 * This message serves as a closure marker for the Scenario 2 gameplay sequence.
	 *
	 * @return A string message marking the end of Scenario 2.
	 */
	public static String scenarioDOISendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 2  END  >>] ==========";
	}

	/**
	 * Generates a message indicating the start of Scenario 3. This scenario involves
	 * the player, represented by their name, making contact with enemies who have
	 * the priority of attack over the player.
	 *
	 * @param playerName The name of the player engaging in Scenario 3.
	 * @return A formatted string message indicating the start of Scenario 3, providing
	 *         details about the player's interaction and the enemies' attack priority.
	 */
	// Scenario 3
	public static String scenarioTRESstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 3 START >>] ==========" +
			"\n|\tENEMIES make contact with " + playerName + "..." +
			"\n|\tAND have priority of attack over " + playerName + "...";
	}

	/**
	 * Generates a formatted message indicating the end of Scenario 3.
	 * This message serves as a closure marker for the Scenario 3 gameplay sequence.
	 *
	 * @return A string message marking the end of Scenario 3.
	 */
	public static String scenarioTRESendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 3  END  >>] ==========";
	}

	/**
	 * Generates a message indicating the start of Scenario 4.
	 * This scenario involves the player, represented by their name, appearing injured.
	 * The message is formatted for display during the gameplay sequence.
	 *
	 * @param playerName The name of the player engaging in Scenario 4.
	 * @return A formatted message string indicating the start of Scenario 4,
	 *         highlighting the player's injured status.
	 */
	// Scenario 4
	public static String scenarioQUATROstartMessage(String playerName) {
		return "\n|" + "\n|========== [<< SCENARIO 4 START >>] ==========" +
			"\n|\t" + playerName + " seems to be injured...";
	}

	/**
	 * Generates a formatted message signaling the end of Scenario 4.
	 * This message is designed to serve as a closure marker for the Scenario 4 gameplay sequence.
	 *
	 * @return A string containing a formatted message marking the end of Scenario 4.
	 */
	public static String scenarioQUATROendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 4  END  >>] ==========";
	}

	/**
	 * Generates a message indicating the start of Scenario 5.
	 * In this scenario, the player engages with enemies, has the priority
	 * of attack over them, and encounters the target in the same room.
	 *
	 * @param playerName The name of the player engaging in Scenario 5.
	 * @return A formatted string message indicating the start of Scenario 5,
	 *         detailing the player's interaction with enemies and the target in the room.
	 */
	// Scenario 5
	public static String scenarioCINCOstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 5 START >>] ==========" +
			"\n|\t" + playerName + " makes contact with ENEMIES..." +
			"\n|\tAND has priority of attack over ENEMIES..." +
			"\n|\tAND in that room there is the TARGET...";
	}

	/**
	 * Generates a formatted message signaling the end of Scenario 5.
	 * This message serves as a closure marker for the Scenario 5 gameplay sequence.
	 *
	 * @return A string containing a formatted message marking the end of Scenario 5.
	 */
	public static String scenarioCINCOendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 5  END  >>] ==========";
	}

	/**
	 * Generates a message indicating the start of Scenario 6. This scenario involves
	 * the player entering a room where the target is present, with a focus on emphasizing
	 * the distinct environment and clarity of the situation.
	 *
	 * @return A formatted string message marking the beginning of Scenario 6,
	 *         describing the player's surroundings and the presence of the target.
	 */
	// Scenario 6
	public static String scenarioSEISstartMessage() {
		return "\n|========== [<< SCENARIO 6 START >>] ==========" +
			"\n|\tAND in that room there is the TARGET..." +
			"\n|\tLOOK AT THAT, it is clear...";
	}

	/**
	 * Generates a formatted message signaling the end of Scenario 6.
	 * This message serves as a closure marker for the Scenario 6 gameplay sequence.
	 *
	 * @return A string containing a formatted message marking the end of Scenario 6.
	 */
	public static String scenarioSEISendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 6  END  >>] ==========";
	}

	/**
	 * Generates a formatted message indicating the end of the specified scenario.
	 * This message serves as a visual marker for the conclusion of a scenario
	 * during the gameplay sequence.
	 *
	 * @param scenarioNumber The number of the scenario being concluded.
	 * @return A formatted string message marking the end of the specified scenario.
	 */
	public static String scenarioNRendMessage(int scenarioNumber) {
		return "\n|" + "\n|========== [<< SCENARIO " + scenarioNumber + "  END  >>] ==========";
	}

	/**
	 * Generates a formatted message indicating the start of a specific scenario.
	 * This message serves as a visual marker for the beginning of a scenario during the gameplay sequence.
	 *
	 * @param scenarioNumber The number of the scenario being started.
	 * @return A formatted string message marking the start of the specified scenario.
	 */
	public static String scenarioNRstartMessage(int scenarioNumber) {
		return "\n|========== [<< SCENARIO " + scenarioNumber + " START >>] ==========";
	}

	//-----------------------------PLAYER-------------------------------------------------

	/**
	 * Generates a message indicating that a player starts a mission at a specific entry point.
	 * This method provides a formatted string to be displayed during the gameplay sequence.
	 *
	 * @param playerName The name of the player starting the mission.
	 * @param missionName The name of the mission being started.
	 * @param entryPointName The name of the entry point where the player starts.
	 * @return A formatted string message indicating the player's start location, mission, and the entry point.
	 */
	public static String playerStartsInEntryPoint(String playerName, String missionName, String entryPointName) {
		return "\n" + playerName + " starts mission " + missionName + " in" +
			"\n\t [" + entryPointName + "]";
	}

	/**
	 * Generates a formatted message indicating the start of the player's turn.
	 * This message serves as a visual indicator in the gameplay sequence.
	 *
	 * @return A string containing the formatted message for the player's turn.
	 */
	public static String playerTurnMessage() {
		return "\n|" + "\n|\t-------------- PLAYER  TURN --------------";
	}

	/**
	 * Generates a message indicating that a player is attacking an enemy with a specified amount of damage.
	 *
	 * @param playerName the name of the player who is attacking
	 * @param enemyName the name of the enemy being attacked
	 * @param playerAttack the amount of damage dealt by the player's attack
	 * @return a formatted string message describing the player's attack on the enemy
	 */
	public static String playerIsAttackingMessage(String playerName, String enemyName, int playerAttack) {
		return "\n|\t" + playerName + " is attacking " + enemyName + "..." +
			"\n|\twith " + playerAttack + " damage...";
	}

	/**
	 * Constructs a message indicating that a player has died.
	 *
	 * @param playerName the name of the player who has died
	 * @return a formatted string containing the player's death message
	 */
	public static String playerDiedMessage(String playerName) {
		return "\n|\t" + playerName + " DIED DAMN IT!!!!!";
	}

	/**
	 * Generates a message indicating that the specified player is checking their backpack.
	 *
	 * @param playerName the name of the player who is checking their backpack
	 * @return a formatted message stating that the player is checking their backpack
	 */
	public static String playerChecksBackPackMessage(String playerName) {
		return "\n|\t" + playerName + " halts and is checking BackPack...";
	}

	/**
	 * Generates a message indicating that a player has added an item to their backpack.
	 *
	 * @param playerName the name of the player adding the item
	 * @param itemName the name of the item being added by the player
	 * @return a formatted message indicating the addition of the item to the player's backpack
	 */
	public static String playerAddsItemMessage(String playerName, String itemName) {
		return "\n|" + "\n|\t" + playerName + " adds " + itemName + " to his BackPack...";
	}

	/**
	 * Generates a message string indicating a player has equipped an item and displays their current health value.
	 *
	 * @param playerName the name of the player equipping the item
	 * @param itemName the name of the item being equipped
	 * @param givenItemValue the item's associated value representing the player's current health
	 * @return a formatted string message indicating the player equips the item and their current health status
	 */
	public static String playerEquipsItemMessage(String playerName, String itemName, int givenItemValue) {
		return "\n|" + "\n|\t" + playerName + " equips " + itemName + ", current health " + givenItemValue + "...";
	}

	/**
	 * Generates a message indicating that a player is searching a specific position.
	 *
	 * @param playerName the name of the player performing the search
	 * @param playerPosition the position being searched by the player
	 * @return a formatted string describing the player's search activity
	 */
	public static String playerSearchsMessage(String playerName, String playerPosition) {
		return "\n|" + "\n|\t" + playerName + " searches the " + playerPosition + "...";
	}

	/**
	 * Constructs a message indicating that the specified player has eliminated all enemies
	 * in the specified position.
	 *
	 * @param playerName the name of the player who eliminated all enemies
	 * @param playerPositionName the name of the position where the enemies were eliminated
	 * @return a formatted message indicating the player and the position of the elimination
	 */
	public static String playerEliminatedAllEnemiesInPositionMessage(String playerName, String playerPositionName) {
		return "\n|" +
			"\n|\t" + playerName + " eliminated all ENEMIES..." +
			"\n|\tin " + playerPositionName + "...";
	}

	/**
	 * Constructs a message indicating that a player has entered in a specified position.
	 *
	 * @param playerName the name of the player entering
	 * @param playerPositionName the name of the position the player is entering
	 * @return a formatted message stating that the player has entered in the specified position
	 */
	public static String playerEntersInMessage(String playerName, String playerPositionName) {
		return "\n" + playerName + " enters in " + playerPositionName + "...";
	}

	/**
	 * Constructs a message indicating the player's current position.
	 *
	 * @param playerName the name of the player
	 * @param playerPositionName the name of the position where the player is situated
	 * @return a message stating that the player is in the specified position
	 */
	public static String playerIsInMessage(String playerName, String playerPositionName) {
		return "\n" + playerName + " is in " + playerPositionName + "...";
	}

	/**
	 * Generates a message indicating that an enemy has endured a certain amount of attack damage.
	 *
	 * @param enemyName the name of the enemy who endured the attack
	 * @param playerAttack the amount of attack damage endured by the enemy
	 * @return a formatted string message describing the endured attack
	 */
	// ------------------------------ENEMY------------------------------------------------
	public static String enemyEnduredAttackMessage(String enemyName, int playerAttack) {
		return "\n|\tENEMY " + enemyName + " endured " + playerAttack + " of attack..." + "\n|";
	}

	/**
	 * Generates a message indicating that an enemy suffered an attack.
	 *
	 * @param enemyName the name of the enemy that was attacked
	 * @param playerAttack the amount of attack points inflicted on the enemy
	 * @return a formatted string message indicating the enemy name and the attack damage dealt
	 */
	public static String enemySufferedAttackMessage(String enemyName, int playerAttack) {
		return "\n|" + "\n|\tENEMY " + enemyName + " suffered " + playerAttack + " of attack...";
	}

	/**
	 * Generates a message indicating that the enemy is dead.
	 *
	 * @return A string containing the message that the enemy is dead.
	 */
	public static String enemyIsDeadMessage() {
		return "\n|\tAND is now DEAD!!!";
	}

	/**
	 * Generates a message indicating that an enemy is attacking a player with a specific damage amount.
	 *
	 * @param enemyName The name of the enemy initiating the attack.
	 * @param playerName The name of the player being attacked.
	 * @param enemyAttack The amount of damage caused by the enemy's attack.
	 * @return A formatted string describing the attack details.
	 */
	public static String enemyIsAttackingMessage(String enemyName, String playerName, int enemyAttack) {
		return "\n|" + "\n|\t" + enemyName + " is attacking " + playerName + "..." +
			"\n|\twith " + enemyAttack + " damage...";
	}

	/**
	 * Generates a message indicating that enemies in a specified position survived the attack
	 * and that enemies not in the specified position are moving.
	 *
	 * @param playerPositionName the name of the position where the player is located
	 * @return a message describing the status of the enemies after the attack
	 */
	public static String enemiesSurvivedAttackMessage(String playerPositionName) {
		return "\n|\tENEMIES in " + playerPositionName + " survived the attack..." +
			"\n|\tENEMIES not in " + playerPositionName + " are moving...";
	}

	/**
	 * Constructs a message regarding the positions of enemies relative to the specified player's position.
	 *
	 * @param playerPositionName the name of the player's current position
	 * @return a message indicating the behavior of enemies based on their position compared to the player's position
	 */
	public static String enemiesNotIsTheSamePositionMessage(String playerPositionName) {
		return
			"\n|\tENEMIES in " + playerPositionName + " engaging in confront..." +
				"\n|\tENEMIES not in " + playerPositionName + " are moving...";
	}

	/**
	 * Generates a message indicating that the enemies are active and moving.
	 *
	 * @return A string message indicating enemy movement.
	 */
	public static String enemiesAreMovingMessage() {
		return "\n|\tBUT the enemies are somewhere..." +
			"\n|\tEnemies are moving...";
	}

	/**
	 * Provides a message indicating that there are no enemies remaining.
	 *
	 * @return A string message stating that there are no enemies left.
	 */
	public static String noEnemiesLeftMessage() {
		return "\n|\tBUT there are no enemies left...";
	}

	/**
	 * Displays a message indicating the start of the enemy's turn in the game.
	 *
	 * @return A formatted string representing the enemy's turn message.
	 */
	public static String enemyTurnMessage() {
		return "\n|" + "\n|\t-------------- ENEMY  TURN  --------------";
	}

	/**
	 * Simulates the engagement and confrontation of enemies at a specified player position.
	 *
	 * @param playerPositionName the name of the player's position where the enemies will engage in confrontation
	 * @return a formatted string indicating that enemies are engaging and confronting at the specified location
	 */
	public static String enemiesEngageConfront(String playerPositionName) {
		return "\n|\tENEMIES engage confront in " + playerPositionName + "...";
	}

	/**
	 * Constructs a message indicating that the target has reached the extraction point
	 * and congratulates the specified player.
	 *
	 * @param playerName the name of the player who is returning to base
	 * @return a formatted message notifying that the target is in the extraction point
	 *         and congratulating the player
	 */
	//------------------------------TARGET------------------------------------------------
	public static String targetInExtractionPointMessage(String playerName) {
		return
			"\n|" +
				"\n|\tTARGET is in EXTRACTION POINT..." +
				"\n|\tWELL DONE " + playerName + " is returning to base...";
	}

	/**
	 * Generates a message indicating that the target is secured and informs the player of their next objective.
	 *
	 * @param playerName the name of the player who secured the target.
	 * @param nextObjectiveName the name of the next objective the player should return to.
	 * @return a formatted message indicating the secured target and the next objective.
	 */
	public static String targetIsSecuredMessage(String playerName, String nextObjectiveName) {
		return "\n|" + "\n|\tTARGET is now secured..." +
			"\n|\tWELL DONE " + playerName + " return to " + nextObjectiveName + "...";
	}

	/**
	 * Generates a message indicating the calculation of the best path to the objective.
	 *
	 * @return A string message that includes a notice about calculating and the result of the best path to the objective.
	 */
	//------------------------------PATH--------------------------------------------------
	public static String bestPathObjectiveMessage() {
		return "\nCalculating best path to OBJECTIVE..." +
			"\nPath to OBJECTIVE";
	}

	/**
	 * Generates a message indicating the initiation of calculating the best path
	 * to the extraction point followed by the path description.
	 *
	 * @return A message string about the best path to the extraction point.
	 */
	public static String bestPathExtractionMessage() {
		return "\nCalculating best path to EXTRACTION POINT..." +
			"\nPath to EXTRACTION POINT";
	}

	/**
	 * Generates a message indicating that some items are spotted in the room.
	 *
	 * @return A descriptive string message about spotting items in the room.
	 */
	//-----------------------------ITEM---------------------------------------------------
	public static String itemsSpottedMessage() {
		return "\n|\tAND spots some items in the room...";
	}

	/**
	 * Initializes the simulation process for the Sophisticated Spy System (SSS).
	 * This method prepares the system for a simulation by returning a message
	 * indicating the initialization and intel gathering process.
	 *
	 * @return A string message confirming the start of the simulation process
	 * and the gathering of necessary intel.
	 */
	//----------------------------BANNERS-------------------------------------------------
	public static String initSimulation() {
		return
			"\n==== SSS Sophisticated Spy System ====" +
				"\n|Initializing Simulation..." +
				"\n|Gathering Intel for Simulation...";
	}

	/**
	 * Collects and returns a formatted string representing the initialization
	 * message for a simulation environment in the "Sophisticated Spy System" (SSS).
	 *
	 * @return A string message indicating the start of data collection in the simulation environment.
	 */
	public static String collectingData() {
		return "\n==== SSS Sophisticated Spy System ====" +
			"\n|Collecting Simulation Environment Data...";
	}

	/**
	 * Generates and returns a banner string for the "enemies" section.
	 *
	 * @return A formatted string acting as a banner for enemies, containing decorative elements and the word "INIMIGOS".
	 */
	public static String enemiesBanner() {
		return "\n\n\t========= INIMIGOS =========";
	}

	/**
	 * Constructs a formatted message containing information about an enemy's name, attack power, and position.
	 *
	 * @param enemyName the name of the enemy
	 * @param enemyAttack the attack power level of the enemy
	 * @param enemyPositionName the name of the enemy's position
	 * @return a formatted string containing the enemy's name, attack power, and position
	 */
	public static String enemiesIntelMessage(String enemyName, int enemyAttack, String enemyPositionName) {
		return "\n|Name: " + enemyName + "\tFire Power: " + enemyAttack + "\tPosition: " + enemyPositionName;

	}

	/**
	 * Generates and returns a banner string that serves as a heading or divider
	 * for items such as medi-kits and kevlar vests in the display or output.
	 *
	 * @return A banner string indicating the section for items.
	 */
	public static String mediKitsKevlarsBanner() {
		return "\n\n\t=========  ITEMS  =========";
	}

	/**
	 * Constructs a formatted message containing details about an item.
	 *
	 * @param itemName the name of the item
	 * @param itemValue the value associated with the item
	 * @param itemPositionName the positional descriptor of the item
	 * @return a formatted string combining the item name, value, and positional descriptor
	 */
	public static String itemsIntelMessage(String itemName, int itemValue, String itemPositionName) {
		return "\n|Item: " + itemName + "\tValue: " + itemValue + "\tPosition: " + itemPositionName;
	}

	/**
	 * Provides a banner indicating the best path to the closest medical kit.
	 *
	 * @return A string containing the banner text for directing to the closest medical kit.
	 */
	public static String closestMediKitBanner() {
		return "\n\n==== BEST PATH TO CLOSEST MEDIC KIT ====";
	}

	/**
	 * Provides a message indicating that no medical kits are left in the building.
	 *
	 * @return A string message stating that there are no more medical kits available.
	 */
	public static String noMediKitsLeftMessage() {
		return "\n" +
			"There are no more medic kits available on the building!";
	}

	/**
	 * Generates and returns a formatted banner string for the player.
	 *
	 * @return A string representing a formatted "PLAYER" banner.
	 */
	public static String playerBanner() {
		return "\n\n\t========  PLAYER  =========";
	}

	/**
	 * Constructs a health status message for a player based on their current health value.
	 *
	 * @param playerHealth the current health of the player, represented as an integer
	 * @return a string message displaying the player's health in the format "|HP: currentHealth/100"
	 */
	public static String playerHealthStatusMessage(int playerHealth) {
		return "\n|HP: " + playerHealth + "/100";
	}

	/**
	 * Constructs a message describing the content of a backpack.
	 *
	 * @param backPackContent the content of the backpack as a string
	 * @return a formatted string representing the backpack content
	 */
	public static String backPackContentMessage(String backPackContent) {
		return "\n" + backPackContent;
	}

	/**
	 * Returns a message indicating that the backpack has no items.
	 *
	 * @return A string message stating that the backpack is empty.
	 */
	public static String backPackNoItemsMessage() {
		return "\n|BackPack has no items";
	}

	/**
	 * Renders and returns a string message indicating the status of the next situation.
	 *
	 * @return A string message representing the initiation of rendering the next situation in the environment.
	 */
	public static String renderingNextSituationMessage() {
		return "\n\t======== ENVIRONMENT =========" +
			"\n|Rendering next situation...";
	}

	/**
	 * Generates a banner string for the IMF entry point selection.
	 *
	 * @return A formatted string representing the entry point selection banner.
	 */
	public static String allPossibleEntriesBanner() {
		return "\n\n\t==== IMF - ENTRY POINT SELECTION ====";
	}

	/**
	 * Generates and returns a welcome banner for the IMF Create Player screen.
	 *
	 * @return A string representation of the Create Player welcome banner.
	 */
	public static String welcomeIMFbanner() {
		return "\n\n\t==== CREATE PLAYER ====";
	}

	/**
	 * Generates a message prompting the user to enter their codename.
	 *
	 * @return A string message asking the user to enter their codename.
	 */
	public static String enterYourNameMessage() {
		return "\nPlease enter your codename: ";
	}

	/**
	 * Displays a menu for selecting a backpack size.
	 *
	 * This method returns a formatted string representation of the backpack size selection menu,
	 * including options for small, medium, large, or no backpack, along with their respective capacities.
	 *
	 * @return A string containing the formatted backpack selection menu.
	 */
	public static String selectBackPackMenu() {
		return
			"\n\n\t==== SELECT BACKPACK SIZE ====" +
				"\n[1] SMALL \t[max 1 item ]" +
				"\n[2] MEDIUM\t[max 2 items]" +
				"\n[3] LARGE \t[max 5 items]" +
				"\n[4] NONE \t[survival]" +
				"\n\n Option: ";
	}

	/**
	 * Generates a string representing the mission selection menu options.
	 *
	 * @return A string containing the mission selection menu
	 */
	public static String selectMissionMenu() {
		return
			"\n\n\t==== SELECT MISSION MODE ====" +
				"\n[1] NORMAL MISSION" +
				"\n[2] RATO DE ACO" +
				"\n[3] CUSTOM" +
				"\n\n Option: ";
	}

	/**
	 * Generates a standard message indicating an invalid option has been selected.
	 *
	 * @return A string containing the invalid option message.
	 */
	public static String invalidOptionMessage() {
		return "\nINVALID OPTION.";
	}

	/**
	 * Generates and returns a string representing the simulation mode selection menu.
	 *
	 * @return a formatted string displaying the simulation mode selection options.
	 */
	public static String selectSimulationMenu() {
		return
			"\n\n\t==== SELECT SIMULATION MODE ====" +
				"\n[1] AUTOMATIC" +
				"\n[2] MANUAL" +
				"\n\nOption: ";
	}

	/**
	 * Constructs a formatted message with the given option number and selection text.
	 *
	 * @param option the numerical option to be included in the message
	 * @param selection the text associated with the given option
	 * @return a formatted string combining the option number and the selection text
	 */
	public static String optionNrMessage(int option, String selection) {
		return "\n[" + option + "] " + selection;
	}

	/**
	 * Constructs a message prompting the user to select a specified item.
	 *
	 * @param thing the name or description of the item to be selected
	 * @return a formatted string prompting the user to select the specified item
	 */
	public static String selectMessage(String thing) {
		return "\nPlease select " + thing + ":";
	}

	/**
	 * Provides a formatted string representing the selection menu for an entry point.
	 *
	 * @param bestEntryPointName the name of the best entry point to the target
	 * @param entryPointName the name of the currently selected entry point in the menu
	 * @return a formatted string prompting the user to select an entry point option
	 */
	public static String entryPointSelection(String bestEntryPointName, String entryPointName) {
		return
			"\nYour best entry point to the target is " + bestEntryPointName +
				"\n[1] Select this entry point: " + entryPointName +
				"\n[2] Next Entry Point" +
				"\n\nOption: ";

	}

	/**
	 * Generates a message indicating the section or header for possible moves in a game or application.
	 *
	 * @return A string containing the message "==== POSSIBLE MOVES ====".
	 */
	public static String possibleMovesMessage() {
		return "\n\n\t==== POSSIBLE MOVES ====";
	}

	/**
	 * Constructs a string message that indicates the next position.
	 *
	 * @param nextPositionName the name of the next position
	 * @return a formatted message indicating the next position
	 */
	public static String yourNextPositionMessage(String nextPositionName) {
		return "\nYour next position is: ["+ nextPositionName + "]";
	}

	/**
	 * Provides a message indicating that there are no MediKits in the backpack.
	 *
	 * @return A string message stating "No MediKits in BackPack!".
	 */
	public static String noMediKitsBackPackMessage() {
		return "\nNo MediKits in BackPack!";
	}

	/**
	 * Generates a message indicating that the user has chosen to stay.
	 *
	 * @return A string message stating "You choose to stay."
	 */
	public static String youChooseStayMessage() {
		return "\nYou choose to stay.";
	}

}