import entities.Room;
import entities.Target;
import lib.DoubleLinkedOrderedList;

public class Mission {

    private String code;
    private int version;
    private final DoubleLinkedOrderedList<Room> entry_exit_points;
    private Target target;

    public Mission(String code, int versison) {
        this.code = code;
        this.version = versison;
        this.entry_exit_points = new DoubleLinkedOrderedList<Room>();
        this.target = null;
    }

    public String getCode () {
        return this.code;
    }

    public int getVersion() {
        return this.version;
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
        this.entry_exit_points.add(entry_exit_point);
    }

    public DoubleLinkedOrderedList<Room> getEntryPoints() {
        return this.entry_exit_points;
    }
}
