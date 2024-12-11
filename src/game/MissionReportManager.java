package game;

import entities.Room;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Scanner;

public class MissionReportManager {


    public void listReports() {
        File dir = new File("reports/");

        if (this.checkIfThereAreReports(dir)) {
            File[] files = dir.listFiles();

            System.out.println("==== REPORTS AVAILABLE ====");
            int index = 1;
            for (File file : files) {
                if (file.isFile() && file.getName().endsWith(".json")) {
                    System.out.println("[" + index + "]-> " + file.getName());
                    index++;
                }
            }
        } else {
            System.out.println("No reports available! Do missions little warrior");
        }

    }

    private boolean checkIfThereAreReports(File dir) {
        if (!dir.exists() || !dir.isDirectory()) {
            return false;
        }

        File[] files = dir.listFiles();
		return files != null && files.length != 0;

	}

    public void visualizeReport() {
        Scanner scanner = new Scanner(System.in);
        File dir = new File("reports/");

        File[] files = dir.listFiles();
        if (files == null || files.length == 0) {
            System.out.println("Reports not found");
            return;
        }

        File[] jsonFiles = filterJsonFiles(files);
        if (jsonFiles.length == 0) {
            System.out.println("No json file found");
            return;
        }

        System.out.println("==== Select a report to visualize ====");
        for (int i = 0; i < jsonFiles.length; i++) {
            System.out.println("[" + (i + 1) + "] -> " + jsonFiles[i].getName());

        }

        try {
            int choice = scanner.nextInt() - 1;

            if (choice < 0 || choice >= jsonFiles.length) {
                System.out.println("==== INVALID OPTION ==== ");
                return;
            }

            File selectedFile = jsonFiles[choice];
            showReport(selectedFile);

        } catch (NumberFormatException e) {
            System.out.println("Please select a number!");
        }
    }

    private File[] filterJsonFiles(File[] files) {
        int counter = 0;
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                counter++;
            }
        }

        File[] jsonFiles = new File[counter];
        int index = 0;
        for (File file : files) {
            if (file.isFile() && file.getName().endsWith(".json")) {
                jsonFiles[index++] = file;
            }
        }

        return jsonFiles;
    }

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
