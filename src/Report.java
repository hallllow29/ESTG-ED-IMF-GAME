import entities.Player;
import lib.ArrayOrderedList;
import lib.ArrayUnorderedList;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class Report {
    private String simulationId;
    private String timestamp;
    private String type;
    private Player player;
    private MissionImpl mission;
    private ArrayOrderedList<String> trajectory;
    private ArrayUnorderedList<String> enemiesSurvived;

    public Report(String type, Player player, MissionImpl mission) {
        this.simulationId = UUID.randomUUID().toString();
        this.timestamp = getCurrentTimestamp();
        this.player = player;
        this.mission = mission;
        this.type = type;
        this.trajectory = new ArrayOrderedList<>();
        this.enemiesSurvived = new ArrayUnorderedList<>();
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

    public MissionImpl getMission() {
        return mission;
    }

    public void setMission(MissionImpl mission) {
        this.mission = mission;
    }

    public ArrayOrderedList<String> getTrajectory() {
        return trajectory;
    }

    public void setTrajectory(ArrayOrderedList<String> trajectory) {
        this.trajectory = trajectory;
    }

    public String getSimulationId() {
        return simulationId;
    }

    public void setSimulationId(String simulationId) {
        this.simulationId = simulationId;
    }

    public void addRoom(String roomName) {
        this.trajectory.add(roomName);
    }

    public void addEnemy(String enemyName) {
        this.enemiesSurvived.addToRear(enemyName);
    }

    public ArrayUnorderedList<String> getEnemiesSurvived() {
        return this.enemiesSurvived;
    }

    private String getCurrentTimestamp() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss");
        return now.format(formatter);
    }

}
