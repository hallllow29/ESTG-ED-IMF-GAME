import entities.*;
import lib.ArrayList;
import lib.ArrayUnorderedList;
import lib.Network;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;

import java.util.Iterator;
import java.util.Random;

public abstract class SimulationBase {
    private final Mission mission;
    private final Player player;
    private boolean gameOver;
    private boolean returningToExit;

    public SimulationBase(Mission mission, Player player) {
        this.mission = mission;
        this.player = player;
        this.gameOver = false;
    }

    public Player getPlayer() {
        return this.player;
    }

    public boolean isGameOver() {
        return gameOver;
    }

    public Mission getMission() {
        return this.mission;
    }

    protected void handleEnemiesInRoom() throws EmptyCollectionException, ElementNotFoundException {
        Room currentRoom = player.getPosition();
        getEnemiesInRoom(currentRoom);
        Iterator<Enemy> enemiesInRoom;

        if (!currentRoom.hasEnemies()) {
            System.out.println("The room is clear");
            return;
        }

        System.out.println("Player is confronting enemies");
        attackEnemies(currentRoom);

        enemiesInRoom = getEnemiesInRoom(currentRoom);
        if (!enemiesInRoom.hasNext()) {
            System.out.println("All enemies in current room are dead");
            currentRoom.setEnemies(false);
            return;
        }

        System.out.println("Enemies are attacking the player!");
        playerUnderAttackBy(enemiesInRoom);

        if (player.isAlive()) {
            System.out.println("Player survived! Other enemies are moving....");
            moveEnemiesNotInSameRoom();
        } else {
            System.out.println("The player is dead! ");
            this.gameOver = true;
        }

    }

    protected void handleItemsInRoom() {
        System.out.println("Checking if the room has items.....");
        Room currentRoom = player.getPosition();

        if (currentRoom.hasItems()) {
            Iterator<Item> itemIterator = mission.getItems().iterator();
            while (itemIterator.hasNext()) {
                Item item = itemIterator.next();
                if (item == null) {
                    continue;
                }

                if (item.getPosition() != null && item.getPosition().equals(currentRoom)) {
                    if (item instanceof MediKit) {
                        this.player.addKitToBackPack((MediKit) item);
                        System.out.println("Medikit added to the backPack");
                    } else if (item instanceof Kevlar) {
                        this.player.equipKevlar((Kevlar) item);
                        System.out.println("Kevlar equipped, current health" + this.player.getCurrentHealth());
                    }

                    item.setPosition(null);
                    currentRoom.setItemInRoom(false);
                    //itemIterator.remove();
                }
            }
        }
    }

    protected void updateBestPath() throws ElementNotFoundException {
        updateWeightsForEnemies();
        Room targetRoom;

        if (returningToExit) {
            targetRoom = bestExtractionPoint();
            System.out.println("Calculating best extraction point");
        } else {
            targetRoom = mission.getTarget().getRoom();
            System.out.println("Calculating best path to target...");
        }
        System.out.println("Path to destiny: ");
        displayPathToExit(player.getPosition(), targetRoom);
    }

    protected void moveEnemies() {
        ArrayList<Room> possible_moves;

        Random random = new Random();

        for (Enemy enemyObj : this.mission.getEnemies()) {

            possible_moves = getPossibleMoves(enemyObj.getPosition());

            if (!possible_moves.isEmpty()) {

                int random_index = random.nextInt(possible_moves.size());

                Room next_room = possible_moves.getElement(random_index);

                enemyObj.setPosition(next_room);
            }
        }
    }

    protected void enemyTurn() {
        Room playerRoom = this.player.getPosition();
        boolean enemiesAttack = false;

        System.out.println("\nEnemies turn");

        for (Enemy enemy : mission.getEnemies()) {
            Room enemyRoom = enemy.getPosition();

            if (enemyRoom.equals(playerRoom)) {
                System.out.println(enemy.getName() + " attacks " + player.getName());
                player.takesDamageFrom(enemy.getFirePower());
                enemiesAttack = true;

                if (!player.isAlive()) {
                    System.out.println(player.getName() + " has been defeated! Mission failed");
                    gameOver = true;
                    return;
                }
            }
        }

        if (!enemiesAttack) {
            System.out.println("Enemies are moving...");
            moveEnemies();
        }
    }

    protected void checkVictoryConditions() throws EmptyCollectionException, ElementNotFoundException {
        Room currentRoom = player.getPosition();
        Room targetRoom = mission.getTarget().getRoom();

        if (currentRoom.equals(targetRoom) && !currentRoom.hasEnemies()) {
            System.out.println("Player reached the target room and enemies are not there, target secured");
            mission.setTargetSecured(true);
        }

        if (mission.isTargetSecured() && mission.getEntryExitPoints().contains(currentRoom)) {
            System.out.println("Mission accomplished!");
            this.gameOver = true;
            return;
        }


        if (!player.isAlive()) {
            System.out.println("Player is dead! Mission failed");
            this.gameOver = true;
        }

    }

    protected void playerTurn() throws EmptyCollectionException, ElementNotFoundException {
        Room currentRoom = player.getPosition();
        Room targetRoom = mission.getTarget().getRoom();

        System.out.println("--------PLAYER TURN----------");
        System.out.println("Current Position -> " + currentRoom.getName());

        this.handleEnemiesInRoom();
        if (isGameOver()) {
            return;
        }

        handleItemsInRoom();

        if (playerNeedsRecoveryItem()) {
            player.useMediKit();
        }

        if (!returningToExit && currentRoom.equals(mission.getTarget().getRoom()) && !currentRoom.hasEnemies()) {
            System.out.println("Player reached the target room and there are no enemies.");
            mission.setTargetSecured(true);
            returningToExit = true;
        }

        checkVictoryConditions();
        if (isGameOver()) return;

        updateBestPath();

        Room nextDestination = determineNextDestination();
        movePlayer(nextDestination);

    }

    protected abstract void movePlayer(Room room) throws ElementNotFoundException;

    protected Room bestExtractionPoint() throws ElementNotFoundException {
        Network<Room> battlefield = mission.getBattlefield();
        Room bestExtractionPoint = null;
        Iterator<Room> extractionPoints = mission.getEntryExitPoints().iterator();
        double minimalDamage = Double.MAX_VALUE;

        while (extractionPoints.hasNext()) {

            Room extractionPoint = extractionPoints.next();
            Iterator<Room> extractionPointsPaths = battlefield.iteratorShortestPath(mission.getTarget().getRoom(), extractionPoint);
            double calculatedDamage = calculatePathDamage(extractionPointsPaths);
            if (calculatedDamage < minimalDamage) {
                minimalDamage = calculatedDamage;
                bestExtractionPoint = extractionPoint;
            }
        }
        return bestExtractionPoint;
    }

    private double calculatePathDamage(Iterator<Room> path) {
        double totalDamage = 0;
        int playerHealth = this.player.getCurrentHealth();
        int playerMaxHealth = 100;

        while (path.hasNext()) {

            Room room = path.next();

            if (room.hasEnemies()) {

                for (Enemy enemy : mission.getEnemies()) {
                    if (enemy.getPosition().equals(room)) {
                        totalDamage += enemy.getFirePower();
                    }
                }
            }

            if (room.hasItems()) {
                for (Item item : mission.getItems()) {
                    if (item.getPosition() != null && item.getPosition().equals(room)) {
                        if (item instanceof MediKit) {
                            playerHealth = Math.min(playerMaxHealth, playerHealth + ((MediKit) item).getHealPower());
                        } else if (item instanceof Kevlar) {
                            playerHealth += ((Kevlar) item).getExtraHp();
                        }
                    }
                }
            }
        }

        return totalDamage;
    }

    private void updateWeightsForEnemies() {
        for (Room room : mission.getBattlefield().getVertices()) {
            for (Room connectedRoom : mission.getBattlefield().getConnectedVertices(room)) {
                double newWeight = calculateWeight(connectedRoom);
                mission.getBattlefield().addEdge(room, connectedRoom, newWeight);
            }
        }
    }

    private void moveEnemiesNotInSameRoom() {
        Room playerRoom = player.getPosition();

        for (Enemy enemy : mission.getEnemies()) {
            if (!enemy.getPosition().equals(playerRoom)) {
                moveEnemy(enemy);
            }
        }
    }

    private void moveEnemy(Enemy enemy) {
        Room currentRoom = enemy.getPosition();
        ArrayUnorderedList<Room> possibleMoves = getPossibleMoves(currentRoom);

        if (!possibleMoves.isEmpty()) {
            Random random = new Random();
            int randomIndex = random.nextInt(possibleMoves.size());
            Room nextRoom = possibleMoves.getElement(randomIndex);

            System.out.println(enemy.getName() + " moved to " + nextRoom.getName());
            enemy.setPosition(nextRoom);
        }
    }

    private void attackEnemies(Room currentRoom) throws EmptyCollectionException, ElementNotFoundException {
        int playerFirePower = player.getFirePower();
        ArrayUnorderedList<Enemy> enemiesDefeated = new ArrayUnorderedList<>();

        for (Enemy enemy : mission.getEnemies()) {
            if (enemy.getPosition().equals(currentRoom)) {
                System.out.println(player.getName() + " attack " + enemy.getName() + " dealing " + playerFirePower + " damage");
                enemy.takesDamageFrom(playerFirePower);
            }

            if (!enemy.isAlive()) {
                System.out.println(enemy.getName() + " defeated");
                enemiesDefeated.addToRear(enemy);
            }
        }

        for (Enemy defeated : enemiesDefeated) {
            mission.getEnemies().remove(defeated);
            defeated.setPosition(null);
        }
    }

    private double calculateWeight(Room room) {
        double weight = 0.0;

        if (room.hasEnemies()) {
            int totalDamage = 0;
            for (Enemy enemy : mission.getEnemies()) {
                if (enemy.getPosition().equals(room)) {
                    totalDamage += enemy.getFirePower();
                }
            }
            weight += totalDamage;
        } else {
            return 0.0;
        }
        return weight;
    }

    private Iterator<Enemy> getEnemiesInRoom(Room room) {
        ArrayUnorderedList<Enemy> enemiesInRoom = new ArrayUnorderedList<>();

        for (Enemy enemy : mission.getEnemies()) {
            if (enemy.getPosition().equals(room)) {
                enemiesInRoom.addToRear(enemy);
            }
        }

        return enemiesInRoom.iterator();
    }

    private void playerUnderAttackBy(Iterator<Enemy> enemies) {
        final int ZERO = 0;

        while (enemies.hasNext()) {
            Enemy enemy = enemies.next();
            System.out.println(enemy.getName() + " deal " + enemy.getFirePower() + " damage to player");
            player.takesDamageFrom(enemy.getFirePower());

            if (!player.isAlive()) {
                System.out.println(player.getName() + " has been defeated");
                break;
            }
        }

    }

    private ArrayUnorderedList<Room> getPossibleMoves(Room from_room) {
        ArrayUnorderedList<Room> possible_moves = new ArrayUnorderedList<>();

        try {
            Iterator<Room> bfs_iterator = mission.getBattlefield().iteratorBFS(from_room);
            int bfs_level = 0;

            while (bfs_iterator.hasNext() && bfs_level <= 2) {

                Room to_room = bfs_iterator.next();

                if (bfs_level == 1 || bfs_level == 2) {

                    if (to_room != null) {
                        possible_moves.addToRear(to_room);
                    }
                }

                bfs_level++;
            }

        } catch (EmptyCollectionException e) {
            System.err.println(e.getMessage());
        }

        return possible_moves;
    }

    protected Room findBestEntryPoint() throws ElementNotFoundException {
        Network<Room> building = mission.getBattlefield();
        Room targetPosition = mission.getTarget().getRoom();
        Room bestEntryPoint = null;

        Iterator<Room> entryPoints = mission.getEntryExitPoints().iterator();
        double minimalDamage = Double.MAX_VALUE;

        while (entryPoints.hasNext()) {
            Room entryPoint = entryPoints.next();
            Iterator<Room> entryPointsPath = building.iteratorShortestPath(entryPoint, targetPosition);
            double calculateDamage = calculatePathDamage(entryPointsPath);
            if (calculateDamage < minimalDamage) {
                minimalDamage = calculateDamage;
                bestEntryPoint = entryPoint;
            }
        }

        return bestEntryPoint;
    }

    private boolean playerNeedsRecoveryItem() {
        // O Tó Cruz pode apanhar kits de recuperação de vida que permitem
        // recuperar um determinado número de pontos até ao limite máximo permitido.

        int playerCriticalHealth = 70;

        return this.player.getCurrentHealth() <= playerCriticalHealth && this.player.hasRecoveryItem();
    }

    protected Room bestExitPoint(Room currentRoom) throws ElementNotFoundException {
        Network<Room> building = mission.getBattlefield();
        Room bestExit = null;
        double minimalDamage = Double.MAX_VALUE;

        for (Room exitPoint : mission.getEntryExitPoints()) {
            Iterator<Room> pathToExit = building.iteratorShortestPath(currentRoom, exitPoint);
            double calculateDamage = calculatePathDamage(pathToExit);

            if (calculateDamage < minimalDamage) {
                minimalDamage = calculateDamage;
                bestExit = exitPoint;
            }
        }

        System.out.println("Best exit point -> " + bestExit.getName());

        return bestExit;
    }

    private Room determineNextDestination() throws ElementNotFoundException {
        Room currentRoom = player.getPosition();

        if (returningToExit) {
            Room bestExit = bestExitPoint(currentRoom);
            System.out.println("Best extraction point -> " + bestExit.getName());
            return getNextRoomInPath(currentRoom, bestExit);
        } else {
            return getNextRoomInPath(currentRoom, mission.getTarget().getRoom());
        }
    }

    public void displayPathToExit(Room fromRoom, Room toRoom) throws ElementNotFoundException {
        Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(fromRoom, toRoom);

        while (path.hasNext()) {
            Room nextRoom = path.next();
            System.out.println("-> " + nextRoom.getName());
        }
    }

    private Room getNextRoomInPath(Room fromRoom, Room toRoom) throws ElementNotFoundException {
        Iterator<Room> path = mission.getBattlefield().iteratorShortestPath(fromRoom, toRoom);

        if (path.hasNext()) {
            path.next();
            if (path.hasNext()) {
                return path.next();
            } else {
                System.out.println("Player already in the destiny room: " + toRoom.getName());
                return fromRoom;
            }
        }
        throw new ElementNotFoundException("Not path found to dstiny.");
    }

}
