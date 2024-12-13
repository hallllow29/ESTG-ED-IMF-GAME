package game.briefings;

import com.sun.security.jgss.GSSUtil;
import game.util.SaveToJsonFile;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.interfaces.OrderedListADT;
import lib.lists.LinkedOrderedList;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

/**
 * The MissionReportManager class provides functionality to manage mission reports,
 * including listing available reports, visualizing individual reports, and
 * retrieving information about missions and simulations.
 */
public class MissionReportManager {

    private LinkedOrderedList<Report> reports;

    public MissionReportManager() {
        this.reports = new LinkedOrderedList<>();
    }

    public void addReport(Report report) throws NotElementComparableException {
        this.reports.add(report);
    }

    public void viewReports() {
        if (this.reports == null) {
            System.out.println("No reports found");
        }

        for (Report report : this.reports) {
            System.out.println(report.toString());
        }
    }

    public void saveALlReports() {
        if (this.reports.isEmpty()) {
            System.out.println("No reports found");
        }

        for (Report report : this.reports) {
            SaveToJsonFile.saveJsonFile(report);
        }

        System.out.println("All reports saved!");
    }

    /**
     * Displays the details of a specific mission report stored in a JSON file.
     *
     * The method parses the given file, extracts information such as simulation details,
     * player statistics, mission details, trajectories to target and extraction points,
     * and lists of enemies survived or killed. The extracted data is printed in a structured format.
     *
     * @param file the JSON file containing the mission report to be displayed
     */
    private void showReport(File file) {
        JSONParser parser = new JSONParser();

        try (FileReader reader = new FileReader(file)) {
            JSONObject report = (JSONObject) parser.parse(reader);

            System.out.println("\n==== REPORT DETAILS ====");
            System.out.println("Simulation ID -> " + report.get("simulationId"));
            System.out.println("Time -> " + report.get("timestamp"));
            System.out.println("Type -> " + report.get("type"));

            JSONObject player = (JSONObject) report.get("player");
            System.out.println("\n==== PLAYER DETAILS ====");
            System.out.println("Name ->" + player.get("name"));
            System.out.println("Final HP -> " + player.get("finalHealth"));
            System.out.println("Fire Power -> " + player.get("firePower"));
            System.out.println("Backpack -> " + player.get("backPack"));

            JSONObject mission = (JSONObject) report.get("mission");
            System.out.println("\n==== MISSION DETAILS ====");
            System.out.println("Code -> " + mission.get("code"));
            System.out.println("Target -> " + mission.get("target"));
            System.out.println("Entry point -> " + mission.get("entryPoint"));
            System.out.println("Status -> " + mission.get("missionStatus"));

            System.out.println("\n==== TRAJECTORY TO TARGET ====");
            JSONArray trajectoryToTarget = (JSONArray) report.get("trajetoryToTarget");

            for (int i = 0; i < trajectoryToTarget.size(); i++) {
                System.out.print("->" + trajectoryToTarget.get(i));
            }

            System.out.println("\n==== TRAJECTORY TO EXTRACTION POINT ====");
            JSONArray trajectoryToExtraction = (JSONArray) report.get("trajectoryToExtraction");

            if (trajectoryToExtraction.isEmpty()) {
                System.out.println("Player are bad that he didnt even make to the extraction point!");
            } else {
                for (int i = 0; i < trajectoryToExtraction.size(); i++) {
                    System.out.print("-> " + trajectoryToExtraction.get(i));
                }
            }

            System.out.println("\n==== ENEMIES SURVIVED ====");
            JSONArray enemiesSurvived = (JSONArray) report.get("enemiesSurvived");

            if (enemiesSurvived.isEmpty()) {
                System.out.println("Player is so good that he rescue the target without killing an enemy! Ninja!");
            } else {
                for (int i = 0; i < enemiesSurvived.size(); i++) {
                    System.out.println("-> " + enemiesSurvived.get(i));
                }
            }



            System.out.println("\n==== ENEMIES KILLED ====");
            JSONArray enemiesKilled = (JSONArray) report.get("enemiesKilled");

            for (int i = 0; i < enemiesKilled.size(); i++) {
                System.out.println("-> " + enemiesKilled.get(i));
            }
        } catch (IOException | ParseException e) {
            System.out.println("Error reading report" + e.getMessage());
        }
    }

}
