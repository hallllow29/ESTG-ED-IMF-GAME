package game;

import entities.Enemy;
import entities.Item;
import entities.Room;
import entities.Target;
import lib.exceptions.ElementNotFoundException;
import lib.exceptions.EmptyCollectionException;
import lib.exceptions.NotElementComparableException;
import lib.graphs.CustomNetwork;
import lib.lists.LinkedList;

public interface MissionI {

    String getCode();
    Target getTarget();
    boolean isTargetSecured();

    CustomNetwork<Room> getBattlefield();
    LinkedList<Enemy> getEnemies();
    LinkedList<Item> getItems();
    LinkedList<Room> getEntryExitPoints();

    void setTarget(Target target);
    void setTargetSecured(boolean targetSecured);
    void setEnemy(Enemy enemy) throws NotElementComparableException;
    void setItem(Item item) throws NotElementComparableException;
    void setEntryExitPoint(Room entry_exit_point) throws NotElementComparableException;
}
