import entities.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import lib.Graph;

import javax.xml.stream.events.StartDocument;

public class JsonSimpleRead {

    public static Mission loadMissionFromJson(String filePath, Graph<String> graph) throws IOException, ParseException {

        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));

        String code = (String) jsonObject.get("cod-missao");
        int version = ((Long) jsonObject.get("versao")).intValue();

        Mission mission = new Mission(code, version);

        JSONArray building = (JSONArray) jsonObject.get("edificio");
        for (Object name : building) {
            if (name != null) {
                graph.addVertex((String) name);
            } else {
                System.out.println("Vertice null");
            }

        }

        JSONArray conections = (JSONArray) jsonObject.get("ligacoes");
        for (Object connectionObj : conections) {
            JSONArray connection = (JSONArray) connectionObj;
            String from = (String) connection.get(0);
            String to = (String) connection.get(1);
            graph.addEdge(from, to);
        }

        JSONArray enemies = (JSONArray) jsonObject.get("inimigos");
        for (Object enemyObj : enemies) {
            JSONObject enemyJson = (JSONObject) enemyObj;
            String name = (String) enemyJson.get("nome");
            String roomName = (String) enemyJson.get("divisao");
            Object powerObj = ((JSONObject) enemyObj).get("poder");

            if (powerObj == null || !(powerObj instanceof Number)) {
                System.out.println("Campo poder");
                continue;
            }

            int power =  ((Number) powerObj).intValue();

            Room room = graph.getRoom(roomName);
            if (room != null) {
                Enemy enemy = new Enemy(name, power, room);
                room.addEnemy(enemy);
            } else {
                System.out.println("Room " + roomName + " not found for the enemy");
            }
        }

        JSONArray itens = (JSONArray) jsonObject.get("itens");
        for (Object itemObj : itens) {
            JSONObject itemJson = (JSONObject) itemObj;

            String type = (String) itemJson.get("tipo");
            String roomName = (String) itemJson.get("divisao");
            int points = itemJson.containsKey("pontos-recuperados")
                    ? ((Long) itemJson.get("pontos-recuperados")).intValue()
                    : itemJson.containsKey("pontos-extra") ? ((Long) itemJson.get("pontos-extra")).intValue() : 0;

            Room room = graph.getRoom(roomName);
            if (room == null) {
                System.err.println("Room " + roomName + " not found " + type);
                continue;
            }

            Item item;
            if ("kit de vida".equalsIgnoreCase(type)) {
                item = new MediKit("MediKit", room, points);
            } else if ("colete".equalsIgnoreCase(type)) {
                item = new Kevlar("Kevlar", room, points);
            } else {
                System.err.println("Tipo de item desconhecido: " + type);
                continue;
            }

            room.addItem(item);
        }

        JSONArray entriesAndOuts = (JSONArray) jsonObject.get("entradas-saidas");
        for (Object entry : entriesAndOuts) {
            String roomName = (String) entry;
            Room room = graph.getRoom(roomName);
            if (room != null) {
                mission.addEntryPoint(room);
            } else {
                System.out.println("Room not found");
            }
        }

        JSONObject target = (JSONObject) jsonObject.get("alvo");
        String targetRoom = (String) target.get("divisao");
        String targetType = (String) target.get("tipo");

        Room targetRoomObj = graph.getRoom(targetRoom);
        if (targetRoomObj != null) {
            Target missionTarget = new Target(targetRoomObj, targetType);
            mission.setTarget(missionTarget);
        } else {
            System.out.println("Target room " + targetRoom + " not found!");
        }

        return mission;
    }

}
