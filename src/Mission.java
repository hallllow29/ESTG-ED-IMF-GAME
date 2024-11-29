import entities.Room;
import entities.Target;
import lib.DoubleLinkedOrderedList;

public class Mission {

    private String code;
    private int version;
    private DoubleLinkedOrderedList<Room> entryPoints;
    private Target target;

    public Mission(String code, int versison) {
        this.code = code;
        this.version = versison;
        this.entryPoints = new DoubleLinkedOrderedList<Room>();
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

    public void addEntryPoint(Room entryPoint) {
        this.entryPoints.add(entryPoint);
    }

    public DoubleLinkedOrderedList<Room> getEntryPoints() {
        return this.entryPoints;
    }
}
