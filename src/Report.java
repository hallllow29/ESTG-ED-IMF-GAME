import entities.Player;
import lib.ArrayUnorderedList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Report {

    private String simulationId;
    private String timestamp;
    private String type;
    private String missionStatus;
    private Player player;
    private Mission mission;
    private String entryPoint;
    private ArrayUnorderedList<String> trajectoryToTarget;
    private ArrayUnorderedList<String> trajectoryToExtraction;
    private final ArrayUnorderedList<String> enemiesSurvived;

    public Report(String type, Player player, Mission mission) {
        this.simulationId = UUID.randomUUID().toString();
        this.timestamp = getCurrentTimestamp();
        this.player = player;
        this.mission = mission;
        this.type = type;
        this.trajectoryToTarget = new ArrayUnorderedList<>();
        this.enemiesSurvived = new ArrayUnorderedList<>();
        this.trajectoryToExtraction = new ArrayUnorderedList<>();
        this.entryPoint = null;
        this.missionStatus = null;
    }

    public String getEntryPoint() {
        return this.entryPoint;
    }

    public void setEntryPoint(String roomName) {
        this.entryPoint = roomName;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(String timestamp) {
        this.timestamp = timestamp;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Player getPlayer() {
        return player;
    }

    public void setPlayer(Player player) {
        this.player = player;
    }

    public Mission getMission() {
        return mission;
    }

    public String getMissionStatus() {
        return this.missionStatus;
    }

    public void setMissionStatus(String status) {
        this.missionStatus = status;
    }

    public void setMission(Mission mission) {
        this.mission = mission;
    }

    public ArrayUnorderedList<String> getTrajectoryToTarget() {
        return trajectoryToTarget;
    }

    public String getSimulationId() {
        return simulationId;
    }

    public void addRoom(String roomName) {
        this.trajectoryToTarget.addToRear(roomName);
    }

    public void addRoomToExtraction(String roomName) {
        this.trajectoryToExtraction.addToRear(roomName);
    }

    public void addEnemy(String enemyName) {
        this.enemiesSurvived.addToRear(enemyName);
    }

    public ArrayUnorderedList<String> getEnemiesSurvived() {
        return this.enemiesSurvived;
    }

    public ArrayUnorderedList<String> getTrajectoryToExtraction() {
        return this.trajectoryToExtraction;
    }

    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return now.format(formatter);
    }

}
