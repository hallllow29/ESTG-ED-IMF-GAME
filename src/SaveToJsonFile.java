import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class SaveToJsonFile {

    public static void saveJsonFile(Report report) {

        JSONObject jsonReport = new JSONObject();
        jsonReport.put("simulationId" , report.getSimulationId());
        jsonReport.put("timestamp", report.getTimestamp());
        jsonReport.put("type", report.getType());

        JSONObject player = new JSONObject();
        player.put("name", report.getPlayer().getName());
        player.put("finalHealth", report.getPlayer().getCurrentHealth());
        player.put("firePower", report.getPlayer().getFirePower());
        jsonReport.put("player", player);

        JSONObject mission = new JSONObject();
        mission.put("code", report.getMission().getCode());
        mission.put("target", report.getMission().getTarget().getRoom().getName());
        mission.put("entryPoint", report.getEntryPoint());
        mission.put("missionStatus", report.getMissionStatus());
        jsonReport.put("mission", mission);

        JSONArray pathToTargetArray = new JSONArray();
        for (String room : report.getTrajectoryToTarget()) {
            pathToTargetArray.add(room);
        }
        jsonReport.put("trajetoryToTarget", pathToTargetArray);

        JSONArray pathToExtraction = new JSONArray();
        for (String room : report.getTrajectoryToExtraction()) {
            pathToExtraction.add(room);
        }

        jsonReport.put("trajectoryToExtraction", pathToExtraction);

        JSONArray enemiesArray = new JSONArray();
        for (String enemy : report.getEnemiesSurvived()) {
            enemiesArray.add(enemy);
        }
        jsonReport.put("enemiesSurvived", enemiesArray);

        try (FileWriter file = new FileWriter(report.getPlayer().getName() + ".json")) {
            file.write(jsonReport.toJSONString());
            System.out.println("Report saved in: " + report.getPlayer().getName() + ".json");
        } catch (IOException e) {
            System.out.println("Error saving report");
        }

    }
}
