import entities.Enemy;
import entities.Room;
import entities.Target;
import lib.*;

public class Mission {

    private final String code;
    private final int version;
    private final Graph<Room> battlefield;
    private final LinkedList<Enemy> enemies;



    public Mission(String code, int version, Graph<Room> battlefield) {
        this.code = code;
        this.version = version;
        this.battlefield = battlefield;
        this.enemies = new LinkedList<Enemy>();
    }

    public void addEnemy(Enemy enemy) {
        this.enemies.add(enemy);
    }

    public LinkedList<Enemy> getEnemies() {
        return this.enemies;
    }




}
