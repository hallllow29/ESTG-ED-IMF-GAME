import entities.Room;
import entities.Target;
import lib.CircularDoubleLinkedList;
import lib.DoubleLinkedUnorderedList;

public class Mission {

    private String code;
    private int version;
    private final DoubleLinkedUnorderedList<Room> entry_exit_points;
    private Target target;
    // SE CALHAR ENEIES AQUI DASSE POR HOJE FECHEOU....

    public Mission(String code, int versison) {
        this.code = code;
        this.version = version;
        this.entry_exit_points = new DoubleLinkedUnorderedList<>();
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
        return "Mission:" + this.code + "\nVersion: " + this.version;
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
