import entities.Enemy;
import entities.Room;
import entities.Target;
import lib.CircularDoubleLinkedList;
import lib.DoubleLinkedOrderedList;
import lib.DoubleLinkedUnorderedList;

import java.util.LinkedList;

public class Mission {

    private String code;
    private int version;
    private final DoubleLinkedUnorderedList<Room> entry_exit_points;
    private LinkedList<Enemy> enemies;
    private Target target;

    public Mission(String code, int versison) {
        this.code = code;
        this.version = versison;
        this.entry_exit_points = new DoubleLinkedUnorderedList<>();
        this.target = null;
    }

    public String getCode () {
        return this.code;
    }

    public int getVersion() {
        return this.version;
    }

    public LinkedList<Enemy> getEnemies() {
        return this.enemies;
    }

    public Target getTarget() {
        return this.target;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public void setTarget (Target target) {
        this.target = target;
    }

    public String toString() {
        return "Mission " + this.code + " version " + version;
    }

    public void addEntryExitPoint(Room entry_exit_point) {
        this.entry_exit_points.addToRear(entry_exit_point);
    }

    public DoubleLinkedUnorderedList<Room> getEntryPoints() {
        return this.entry_exit_points;
    }

    public CircularDoubleLinkedList<Room> getEntryPointsSelection() {
        CircularDoubleLinkedList<Room> entry_points_selection = new CircularDoubleLinkedList<>();
        for (Room entry_point : getEntryPoints() ) {
            entry_points_selection.add(entry_point);
        }

        return entry_points_selection;
    }
}
