import entities.Player;

public class ManualSimulation implements GameMode {
    private final Mission mission;
    private final Player player;

    public ManualSimulation(Mission mission, Player player) {
        this.mission = mission;
        this.player = player;
    }


    @Override
    public void play() {

    }
}
