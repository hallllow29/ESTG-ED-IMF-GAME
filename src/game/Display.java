package game;

public class Display {

	//------------------------------SCENARIOS---------------------------------------------

	public static String scenarioUMstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 1 START >>] ==========" +
			"\n|\t" + playerName + " makes contact with ENEMIES..." +
			"\n|\tAND has priority of attack over ENEMIES...";
	}

	public static String scenarioUMendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 1  END  >>] ==========";
	}

	// Scenario 2
	public static String scenarioDOISstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 2 START >>] ==========" +
			"\n|\tAND the room " + playerName + " entered is clear...";
	}

	public static String scenarioDOISendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 2  END  >>] ==========";
	}

	// Scenario 3
	public static String scenarioTRESstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 3 START >>] ==========" +
			"\n|\tENEMIES make contact with " + playerName + "..." +
			"\n|\tAND have priority of attack over " + playerName + "...";
	}

	public static String scenarioTRESendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 3  END  >>] ==========";
	}

	// Scenario 4
	public static String scenarioQUATROstartMessage(String playerName) {
		return "\n|" + "\n|========== [<< SCENARIO 4 START >>] ==========" +
			"\n|\t" + playerName + " seems to be injured...";
	}

	public static String scenarioQUATROendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 4  END  >>] ==========";
	}

	// Scenario 5
	public static String scenarioCINCOstartMessage(String playerName) {
		return "\n|========== [<< SCENARIO 5 START >>] ==========" +
			"\n|\t" + playerName + " makes contact with ENEMIES..." +
			"\n|\tAND has priority of attack over ENEMIES..." +
			"\n|\tAND in that room there is the TARGET...";
	}

	public static String scenarioCINCOendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 5  END  >>] ==========";
	}

	// Scenario 6
	public static String scenarioSEISstartMessage() {
		return "\n|========== [<< SCENARIO 6 START >>] ==========" +
			"\n|\tAND in that room there is the TARGET..." +
			"\n|\tLOOK AT THAT, it is clear...";
	}

	public static String scenarioSEISendMessage() {
		return "\n|" + "\n|========== [<< SCENARIO 6  END  >>] ==========";
	}

	public static String scenarioNRendMessage(int scenarioNumber) {
		return "\n|" + "\n|========== [<< SCENARIO " + scenarioNumber + "  END  >>] ==========";
	}

	public static String scenarioNRstartMessage(int scenarioNumber) {
		return "\n|========== [<< SCENARIO " + scenarioNumber + " START >>] ==========";
	}

	//-----------------------------PLAYER-------------------------------------------------

	public static String playerStartsInEntryPoint(String playerName, String missionName, String entryPointName) {
		return "\n" + playerName + " starts mission " + missionName + " in" +
		"\n\t [" + entryPointName +"]";
	}

	public static String playerTurnMessage() {
		return "\n|" + "\n|\t-------------- PLAYER  TURN --------------";
	}

	public static String playerIsAttackingMessage(String playerName, String enemyName, int playerAttack) {
		return "\n|\t" + playerName + " is attacking " + enemyName + "..." +
			"\n|\twith " + playerAttack + " damage...";
	}

	public static String playerDiedMessage(String playerName) {
		return "\n|\t" + playerName + " DIED DAMN IT!!!!!";
	}

	public static String playerChecksBackPackMessage(String playerName) {
		return "\n|\t" + playerName + " halts and is checking BackPack...";
	}

	public static String playerAddsItemMessage(String playerName, String itemName) {
		return "\n|" +"\n|\t" + playerName + " adds " + itemName + " to his BackPack...";
	}

	public static String playerEquipsItemMessage(String playerName, String itemName, int givenItemValue) {
		return "\n|" + "\n|\t" + playerName + " equips " + itemName + ", current health " + givenItemValue + "...";
	}

	public static String playerSearchsMessage(String playerName, String playerPosition) {
		return "\n|" +"\n|\t" + playerName + " searches the " + playerPosition + "...";
	}

	public static String playerEliminatedAllEnemiesInPositionMessage(String playerName, String playerPositionName) {
		return "\n|" +
			"\n|\t" + playerName + " eliminated all ENEMIES..." +
			"\n|\tin " + playerPositionName + "...";
	}

	public static String playerEntersInMessage(String playerName, String playerPositionName) {
		return "\n" + playerName + " enters in " + playerPositionName + "...";
	}

	public static String playerIsInMessage(String playerName, String playerPositionName) {
		return "\n" + playerName + " is in " + playerPositionName + "...";
	}

	// ------------------------------ENEMY------------------------------------------------
	public static String enemyEnduredAttackMessage(String enemyName, int playerAttack) {
		return "\n|\tENEMY " + enemyName + " endured " + playerAttack + " of attack..." + "\n|";
	}

	public static String enemySufferedAttackMessage(String enemyName, int playerAttack) {
		return "\n|" +"\n|\tENEMY " + enemyName + " suffered " + playerAttack + " of attack...";
	}

	public static String enemyIsDeadMessage() {
		return "\n|\tAND is now DEAD!!!";
	}

	public static String enemyIsAttackingMessage(String enemyName, String playerName, int enemyAttack) {
		return "\n|" + "\n|\t" + enemyName + " is attacking " + playerName + "..." +
			"\n|\twith " + enemyAttack + " damage...";
	}

	public static String enemiesSurvivedAttackMessage(String playerPositionName) {
		return "\n|\tENEMIES in " + playerPositionName + " survived the attack..." +
			"\n|\tENEMIES not in " + playerPositionName + " are moving...";
	}

	public static String enemiesNotIsTheSamePositionMessage(String playerPositionName) {
		return
			"\n|\tENEMIES in " + playerPositionName + " engaging in confront..." +
			"\n|\tENEMIES not in " + playerPositionName + " are moving...";
	}

	public static String enemiesAreMovingMessage() {
		return "\n|\tBUT the enemies are somewhere..." +
			"\n|\tEnemies are moving...";
	}

	public static String noEnemiesLeftMessage() {
		return "\n|\tBUT there are no enemies left...";
	}

	public static String enemyTurnMessage() {
		return "\n|" + "\n|\t-------------- ENEMY  TURN  --------------";
	}

	public static String enemiesEngageConfront(String playerPositionName) {
		return "\n|\tENEMIES engage confront in " + playerPositionName + "...";
	}

	//------------------------------TARGET------------------------------------------------
	public static String targetInExtractionPointMessage(String playerName) {
		return
			"\n|" +
				"\n|\tTARGET is in EXTRACTION POINT..." +
				"\n|\tWELL DONE " + playerName + " is returning to base...";
	}

	public static String targetIsSecuredMessage(String playerName, String nextObjectiveName) {
		return "\n|" + "\n|\tTARGET is now secured..." +
			"\n|\tWELL DONE " + playerName + " return to " + nextObjectiveName + "...";
	}

	//------------------------------PATH--------------------------------------------------
	protected static String bestPathObjectiveMessage() {
		return "\nCalculating best path to OBJECTIVE..." +
			"\nPath to OBJECTIVE";
	}

	protected static String bestPathExtractionMessage() {
		return "\nCalculating best path to EXTRACTION POINT..." +
			"\nPath to EXTRACTION POINT";
	}

	//-----------------------------ITEM---------------------------------------------------
	public static String itemsSpottedMessage() {
		return "\n|\tAND spots some items in the room...";
	}

	//----------------------------BANNERS-------------------------------------------------
	public static String initSimulation() {
		return
			"\n==== SSS Sophisticated Spy System ====" +
			"\n|Initializing Simulation..." +
			"\n|Gathering Intel for Simulation...";
	}

	public static String collectingData() {
		return "\n==== SSS Sophisticated Spy System ====" +
			"\n|Collecting Simulation Environment Data...";
	}

	public static String enemiesBanner() {
		return "\n\n\t========= INIMIGOS =========";
	}

	public static String enemiesIntelMessage(String enemyName, int enemyAttack, String enemyPositionName) {
		return "\n|Name: " + enemyName + "\tFire Power: " + enemyAttack + "\tPosition: " + enemyPositionName;

	}

	public static String mediKitsKevlarsBanner() {
		return "\n\n\t=========  ITEMS  =========";
	}

	public static String itemsIntelMessage(String itemName, int itemValue, String itemPositionName) {
		return "\n|Item: " + itemName + "\tValue: " + itemValue + "\tPosition: " + itemPositionName;
	}

	public static String closestMediKitBanner() {
		return "\n\n==== BEST PATH TO CLOSEST MEDIC KIT ====";
	}

	public static String noMediKitsLeftMessage() {
		return "\n" +
			"There are no more medic kits available on the building!";
	}

	public static String playerBanner(){
		return "\n\n\t========  PLAYER  =========";
	}

	public static String playerHealthStatusMessage(int playerHealth) {
		return "\n|HP: " + playerHealth + "/100";
	}

	public static String backPackBanner() {
		return "\n\n\t======== BACKPACK =========";
	}

	public static String backPackContentMessage(String backPackContent) {
		return "\n" + backPackContent;
	}

	public static String backPackNoItemsMessage() {
		return "\n|BackPack has no items";
	}

	public static String renderingNextSituationMessage() {
		return "\n\t======== ENVIRONMENT =========" +
			"\n|Rendering next situation...";
	}

	public static String allPossibleEntriesBanner(){
		return "\n\n\t==== IMF - ENTRY POINT SELECTION ====";
	}
}