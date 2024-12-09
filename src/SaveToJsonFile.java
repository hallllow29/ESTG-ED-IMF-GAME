import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.io.FileWriter;
import java.io.IOException;

public class SaveToJsonFile {

    public static void saveJsonFile(Report report, String filePath) {

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
        mission.put("target", report.getMission().getTarget());
        jsonReport.put("mission", mission);

        JSONArray pathArray = new JSONArray();
        for (String room : report.getTrajectory()) {
            pathArray.add(room);
        }
        jsonReport.put("trajetory", pathArray);

        JSONArray enemiesArray = new JSONArray();
        for (String enemy : report.getEnemiesSurvived()) {
            enemiesArray.add(enemy);
        }
        jsonReport.put("enemiesSurvived", enemiesArray);

        try (FileWriter file = new FileWriter(filePath)) {
            file.write(jsonReport.toJSONString());
            System.out.println("Report saved in: " + filePath);
        } catch (IOException e) {
            System.out.println("Error saving report");
        }

    }
}
