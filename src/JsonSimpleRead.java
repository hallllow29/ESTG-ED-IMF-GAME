import entities.*;
import netscape.javascript.JSObject;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

import lib.Graph;


public class JsonSimpleRead {

    public static Mission loadMissionFromJson(String file_in_path, Graph<String> graph) throws IOException, ParseException {

        /**
         * Initialize JSONParser, then instantiates through the filePath of the JSON file.
         * The JSONObject stores the content of the JSON file.
         */
        // JSONParser parser = new JSONParser();
        // JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(filePath));
        JSONObject jsonObject = parseJsonFile(file_in_path);

        // String code = (String) jsonObject.get("cod-missao");
        // int version = ((Long) jsonObject.get("versao")).intValue();
        // Mission mission = new Mission(code, version);
        Mission mission = newMission(jsonObject);


        // /**
        //  * The JSON Array building consists of the building structure of the Mission.
        // JSONArray building = (JSONArray) jsonObject.get("edificio");
        //
        // *//**
        //  * Each element (Room) of the JSON Array "building" counts as a vertex.
        //  * Each vertex (Room) is added to a graph. The graph represents the
        //  * blueprint of the Building where the Mission occurs.
        //  *//*
        // for (Object name : building) {
        //     if (name != null) {
        //         graph.addVertex((String) name);
        //     } else {
        //         System.out.println("Vertice null");
        //     }
        // }*/
        JSONArray building = (JSONArray) jsonObject.get("edificio");
        addRoomsToGraph(building, graph);

        // /**
        //  * The JSON Array connections consists the rooms which are connected to each other.
        //  */
        // JSONArray conections = (JSONArray) jsonObject.get("ligacoes");
        //
        // /**
        //  * Each element (a pair of Rooms that are connected) of the JSON Array "connections"
        //  * counts as a edge between two vertices.
        //  * Each edge (Room connected with another Room) is added to a graph as well.
        //  */
        // for (Object connectionObj : conections) {
        //     JSONArray connection = (JSONArray) connectionObj;
        //     String from = (String) connection.get(0);
        //     String to = (String) connection.get(1);
        //     graph.addEdge(from, to);
        // }
        JSONArray connections = (JSONArray) jsonObject.get("ligacoes");
        addConnectionsToGraph(connections, graph);

        /**
         * Each element (Enemy) of the JSON Array "enemies" has a name, the location of
         * the enemy (divisao), and the damage that can inflict on the player (poder).
         */
        // for (Object enemyObj : enemies) {
        //     JSONObject enemyJson = (JSONObject) enemyObj;
        //     String name = (String) enemyJson.get("nome");
        //     String roomName = (String) enemyJson.get("divisao");
        //     Object powerObj = ((JSONObject) enemyObj).get("poder");
        //
        //
        //     int power =  ((Number) powerObj).intValue();
        //
        //     /**
        //      * Each room can contain one or more enemies.
        //      */
        //     Room room = graph.getRoom(roomName);
        //
        //     /**
        //      * Fail proofing in the case there is a typo in a String value inside the
        //      * JSON Array with the JSON key "edificio"
        //      */
        //     if (room != null) {
        //         Enemy enemy = new Enemy(name, power, room);
        //         room.addEnemy(enemy);
        //     } else {
        //         System.out.println("Room " + roomName + " not found for the enemy");
        //     }
        // }
        JSONArray enemies = (JSONArray) jsonObject.get("inimigos");
        addEnemiesToRooms(enemies, graph);

        // /**
        //  * This JSON Array contains JSON Objects. Each JSON Object represents an Item that
        //  * can possible be found throughout the Mission.
        //  */
        // JSONArray itens = (JSONArray) jsonObject.get("itens");


        // /**
        //  * Each Item has three JSON key values
        //  * <ul>
        //  *  <li>"divisao": The location (Room) of a item. (String value)</li>
        //  *  <li>"pontos-recuperados" or "pontos-extra": The amount of points a item can provide to the player. (Number value)</li>
        //  *  <li>"tipo": The type of the item. (String value)</li>
        //  * </ul>
        //  */
        // /*for (Object itemObj : itens) {
        //     JSONObject itemJson = (JSONObject) itemObj;
        //
        //     String type = (String) itemJson.get("tipo");
        //     String roomName = (String) itemJson.get("divisao");
        //
        //     *//**
        //      * Each type of Item has purpose. Healing meaning ? : (?)
        //      * Nested ternary operators?
        //      *//*
        //     *//*int points = itemJson.containsKey("pontos-recuperados")
        //         ? ((Long) itemJson.get("pontos-recuperados")).intValue()
        //         : itemJson.containsKey("pontos-extra") ? ((Long) itemJson.get("pontos-extra")).intValue() : 0;*//*
        //
        //     *//**
        //      * To determine the purpose of the Item and the amount of points.
        //      * <ul>
        //      *     <li>"pontos-recuperados": The Item has a purpose to heal/recover the health of the player.</li>
        //      *     <li>"pontos-extra": The Item has a purpose to protect the health of the player with extra points.</li>
        //      * </ul>
        //      *//*
        //     int points;
        //     if (itemJson.containsKey("pontos-recuperados")) {
        //         points = ((Long) itemJson.get("pontos-recuperados")).intValue();
        //     } else if (itemJson.containsKey("pontos-extra")) {
        //         points = ((Long) itemJson.get("pontos-extra")).intValue();
        //     } else {
        //         points = 0;
        //     }
        //
        //
        //     Room room = graph.getRoom(roomName);
        //
        //     *//**
        //      * Fail proofing in the case there is a typo in a String value inside the
        //      * JSON Array with the JSON key "edificio"
        //      *//*
        //     if (room == null) {
        //         System.err.println("Room " + roomName + " not found " + type);
        //         continue;
        //     }
        //
        //     *//**
        //      * To determine the type of Item.
        //      * <ul>
        //      *     <li>"kit de vida": MediKit, subclass of Item</li>
        //      *     <li>"colete": Kevlar, subclass of Item</li>
        //      * </ul>
        //      *
        //      * However if the type of the Item is unknown, there is no instantiation.
        //      *//*
        //     Item item;
        //     if ("kit de vida".equalsIgnoreCase(type)) {
        //         item = new MediKit("MediKit", room, points);
        //     } else if ("colete".equalsIgnoreCase(type)) {
        //         item = new Kevlar("Kevlar", room, points);
        //     } else {
        //         System.err.println("Tipo de item desconhecido: " + type);
        //         continue;
        //     }
        //
        //     room.addItem(item);
        // }*/
        JSONArray items = (JSONArray) jsonObject.get("itens");
        addItemsToRooms(items, graph);





        // /**
        //  * The JSON Array entrances_and_exits represents each division (Room) that serve and entry and exit points for the Player in the Mission.
        //  */
        // JSONArray entrances_and_exits = (JSONArray) jsonObject.get("entradas-saidas");
        //
        //
        // for (Object entry_and_exit : entrances_and_exits) {
        //     String roomName = (String) entry_and_exit;
        //
        //     Room room = graph.getRoom(roomName);
        //
        //     /**
        //      * Fail proofing in the case there is a typo in a String value inside the
        //      * JSON Array with the JSON key "entradas-saidas"
        //      */
        //     if (room != null) {
        //         mission.addEntryExitPoint(room);
        //     } else {
        //         System.out.println("Room not found");
        //     }
        // }
        JSONArray entries_exits = (JSONArray) jsonObject.get("entradas-saidas");
        addEntryAndExitsPoints(entries_exits, graph, mission);

        // JSONObject target = (JSONObject) jsonObject.get("alvo");
        // String targetRoom = (String) target.get("divisao");
        // String targetType = (String) target.get("tipo");
        //
        // Room targetRoomObj = graph.getRoom(targetRoom);
        // if (targetRoomObj != null) {
        //     Target missionTarget = new Target(targetRoomObj, targetType);
        //     mission.setTarget(missionTarget);
        // } else {
        //     System.out.println("Target room " + targetRoom + " not found!");
        // }

        JSONObject targetJson = (JSONObject) jsonObject.get("alvo");
        setMissionTarget(targetJson, graph, mission);

        return mission;
    }

    /**
     * Parses a JSON file from the specified file path and returns its content as a JSONObject.
     *
     * @param file_in_path the path to the JSON file to be parsed
     * @return a JSONObject containing the parsed content of the file
     * @throws IOException if an I/O error occurs while reading the file
     * @throws ParseException if the file content cannot be parsed as a valid JSON
     */
    private static JSONObject parseJsonFile(String file_in_path) throws IOException, ParseException {
        JSONParser parser = new JSONParser();
        JSONObject jsonObject = (JSONObject) parser.parse(new FileReader(file_in_path));
        return jsonObject;
    }

    /**
     * Creates a new Mission object based on the information provided in the JSON object.
     *
     * @param jsonObject the JSON object containing the mission data, expected to have keys "cod-missao" and "versao"
     * @return a new Mission instance initialized with the specified code and version
     */
    private static Mission newMission(JSONObject jsonObject) {
        String mission_code = (String) jsonObject.get("cod-missao");
        int mission_version = ((Long) jsonObject.get("versao")).intValue();
        return new Mission(mission_code, mission_version);
    }

    /**
     * Adds room names from a JSONArray to a graph as vertices.
     * If a room name is null, it prints a message indicating this.
     *
     * @param building a JSONArray containing room names to be added as vertices in the graph
     * @param graph a Graph where each valid room name from the JSONArray is added as a vertex
     */
    private static void addRoomsToGraph(JSONArray building, Graph<String> graph) {
        for (Object roomObj : building) {

            String room_name = (String) roomObj;

            if (roomObj != null) {
                graph.addVertex(room_name);
            } else {
                System.err.println("Vertice null");
            }
        }
    }

    /**
     * Adds connections between rooms to a graph based on the specified JSON array.
     * Each element in the JSONArray should be a JSONArray itself, containing exactly two elements:
     * the name of the starting room and the name of the destination room.
     * These room names are used to create directed edges in the graph.
     *
     * @param connections a JSONArray where each element is a JSONArray containing two strings: the starting room and the destination room
     * @param graph a Graph to which connections from the JSONArray are added as directed edges
     */
    private static void addConnectionsToGraph(JSONArray connections, Graph<String> graph) {
        for (Object connectionObj : connections) {
            JSONArray connectionArray = (JSONArray) connectionObj;
            String from_room = (String) connectionArray.get(0);
            String to_room = (String) connectionArray.get(1);
            graph.addEdge(from_room, to_room);
        }
    }

    /**
     * Adds enemies from the JSONArray to their respective rooms in the graph.
     * For each enemy, attempts to locate the specified room in the graph and
     * add an enemy object with the given name and power to that room.
     * If a room corresponding to the enemy's location cannot be found,
     * it prints a message indicating the missing room.
     *
     * @param enemies a JSONArray containing enemy data; each element is a JSONObject
     *                with information about the enemy's name, power, and location
     * @param graph a Graph<String> used to find and add enemies to the appropriate rooms
     */
    private static void addEnemiesToRooms(JSONArray enemies, Graph<String> graph) {
        for (Object enemyObj : enemies) {
            JSONObject enemyJson = (JSONObject) enemyObj;

            String enemy_name = (String) enemyJson.get("nome");

            // int enemy_power = ((Number) enemyJson.get("poder")).intValue();
            Object power_obj = ((JSONObject) enemyObj).get("poder");
            int enemy_power =  ((Number) power_obj).intValue();

            String enemy_location = (String) enemyJson.get("divisao");

            /*
            Room enemy_location = graph.getRoom(enemy_location_name)
            if (enemy_location != null) {
                Enemy enemy_in_mission = new Enemy(enemy_name, enemy_power, enemy_location);
                enemy_location.addEnemy(enemy_in_mission);
            */

            /*
            Room room = graph.getRoom(enemy_location)
            if (enemy_location != null) {
                Enemy enemy_in_room = new Enemy(enemy_name, enemy_power, enemy_location);
                room.addEnemy(enemy_in_room);
            */

            Room room =  graph.getRoom(enemy_location);
            if (room != null) {
                Enemy enemy_in_mission = new Enemy(enemy_name, enemy_power, room);
                room.addEnemy(enemy_in_mission);
            } else {
                System.err.println("Room " + enemy_location + " not found for the enemy");
            }
        }
    }

    /**
     * Adds items from the JSONArray to their respective rooms in the graph.
     * For each item, this method identifies the location in the graph and
     * attempts to create and add an item object with the specified type and points.
     * If a room corresponding to the item's location cannot be found, it prints a message
     * indicating the missing room.
     *
     * @param items a JSONArray containing item data; each element is a JSONObject
     *              with information about the item's location, type, and points
     * @param graph a Graph<String> used to find and add items to the appropriate rooms
     */
    private static void addItemsToRooms(JSONArray items, Graph<String> graph) {
        for (Object itemObj : items) {
            JSONObject itemJson = (JSONObject) itemObj;

            String item_location = (String) itemJson.get("divisao");


            // Maybe refactoring...? For modularity...
            // int item_points = getItemPoints(JSONObject itemJson);
            int item_points = 0;
            Object points_recovered_obj = itemJson.get("pontos-recuperados");
            Object points_extra_obj = itemJson.get("pontos-extra");
            if (points_recovered_obj != null) {
                item_points = ((Number) points_recovered_obj).intValue();
            } else if (points_extra_obj != null) {
                item_points = ((Number) points_extra_obj).intValue();
            }

            String item_type = (String) itemJson.get("tipo");

            Room room = graph.getRoom(item_location);

            if (room != null) {
               Item item = defineItem(room, item_points, item_type);
               room.addItem(item);
            } else {
                System.err.println("Room " + item_location + " not found for the item " + item_type);
            }
            /*String item_type = (String) itemJson.get("tipo");
            if ("kit de vida".equalsIgnoreCase(item_type)) {
                Item mediKit_in_room = new MediKit("MediKit", room, item_points);
            } else if ("colete".equalsIgnoreCase(item_type)) {
                Item kevlar_in_room = new Kevlar("Kevlar", room, item_points);
            }*/
        }
    }

    /**
     * Defines and initializes an item within a specified room based on the item's type and points.
     *
     * @param room the Room object in which the item is to be placed.
     * @param item_points an integer representing the points or value associated with the item.
     * @param item_type a String representing the type of item to create, e.g., "kit de vida" for a MediKit or "colete" for a Kevlar.
     * @return an instance of Item based on the specified type and points if recognized; otherwise, null if the item type is unknown.
     */
    private static Item defineItem(Room room, int item_points, String item_type) {
        Item item = null;
        if ("kit de vida".equalsIgnoreCase(item_type)) {
            item = new MediKit("MediKit", room, item_points);
        } else if ("colete".equalsIgnoreCase(item_type)) {
            item = new Kevlar("Kevlar", room, item_points);
        } else {
            System.err.println("Item type is unknown.");
        }
        return item;
    }

    /**
     * Adds entry and exit points to the mission by processing the provided JSONArray.
     * Each element in the JSONArray represents a room name, and the method attempts
     * to retrieve the corresponding Room from the graph. If the room is found, it is
     * added as an entry or exit point in the mission.
     *
     * @param entries_exits a JSONArray containing room names designated as entry and exit points
     * @param graph a Graph<String> used to retrieve Room objects for the given room names
     * @param mission a Mission object to which the entry and exit points are added
     */
    private static void addEntryAndExitsPoints(JSONArray entries_exits, Graph<String> graph, Mission mission) {
        for (Object entry_exit_obj : entries_exits) {
            String room_name = (String) entry_exit_obj;
            Room room = graph.getRoom(room_name);

            if (room != null) {
                mission.addEntryExitPoint(room);
            } else {
                System.err.println("Room " + room_name + " not found as entry or exit point");
            }
        }
    }

    /**
     * Sets the target for a mission based on the provided JSON object.
     * Attempts to locate the specified target room in the graph and creates
     * a target object with the given room and type. If the room cannot be found,
     * logs an error message.
     *
     * @param targetJson a JSONObject containing the target data, expected to have keys "divisao" and "tipo"
     * @param graph a Graph<String> used to locate the target room based on the specified room name
     * @param mission the Mission object in which the target is to be set
     */
    private static void setMissionTarget(JSONObject targetJson, Graph<String> graph, Mission mission) {
        String target_room = (String) targetJson.get("divisao");
        String target_type = (String) targetJson.get("tipo");

        Room room = graph.getRoom(target_room);

        if (room != null) {
            Target target = new Target(room, target_type);
            mission.setTarget(target);
        } else {
            System.err.println("Room " + target_room + "not found as target room");
        }
    }
}
