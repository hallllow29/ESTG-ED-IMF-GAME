import entities.Player;
import entities.Room;
import lib.Graph;

public class Simulation {

    private Mission mission;
    private Graph<Room> graph;
    private Player player;
    private boolean gameOver;

    public Simulation(Mission mission, Graph<Room> graph, Player player) {
        this.mission = mission;
        this.graph = graph;
        this.player = player;
        this.gameOver = false;
    }

    private void playerTurn() {
        Room currentRoom = player.getCurrentPosition();

        System.out.println("Tó Cruz current position is " + currentRoom);

        if (currentRoom.hasEnemies()) {
            //cenario1;
        } else if (currentRoom.equals(mission.getTarget().getRoom())) {
            if (!currentRoom.hasEnemies()) {
                //cenario6
            } else {
                //cenario5
            }
        } else {
            //cenario2
        }
    }

    private void enemyTurn() {
        System.out.println("Enemies turn!");

        for (String roomName: this.graph.getVertices()) {
            Room room = graph.getRoom(roomName);

            if (room != null && room.hasEnemies()) {
                //moverInimigos;
            }

        }
    }

    private void scenario1(Room room) {
        //TODO: Sala com inimigos TOM CRUISE CONFRONTA
    }

    private void scnario2(Room room ) {
        //TODO: TO CRUZ ENTRE NA SALA MAS NAO TEM INIMIGOS, INIMIGOS MOVEM-SE ALEATORIAMENTE
    }

    private void scnario3(Room room) {
        //TODO: CASO OS INIMGOS ENTREM NUMA SALA QUE TO CRUZ ESTA, CENARIO 1 É DESPOLETADO MAS OS INIMIGOS FICAM COM PRIORIDADE
    }

    private void scnario4(Room room ) {
        //TODO: TO CRUZ USA UM KIT DEVIDA PARA SE CURAR NAO PODE EFETUAR UMA MOVIMANTAÇÃO PERDE O TURNO
    }

    private void scnario5(Room room) {
        //TODO: TO CRUZ ENCONTRA A SALA COM O ALVO MAS A SALA TEM INIMIGOS, O TO DEVE MATAR OS INIMIGOS TODOS PRIMEIRO E SO DEPOIS E QUE PODE RESGATAR O ALVO
        //TODO: NA FASE DOS INIMIGOS OS INIMIGOS MOVEM-SE PARA A SALA QUE O TO ESTA
    }

    private void scnario6(Room room) {
        //TODO: O TO ENCONTRA O ALVO MAS NAO TEM INIMIGOS NA SALA, O TO RESGATA O ALVO E DEPOIS TEM DE SAIR DO EDIFICIO.
    }

    private void moveEnemies(Room room) {

    }
}
