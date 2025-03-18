public class GamePvE extends Game{

    public GamePvE() {
        player1 = new PlayerHuman();
        player2 = Main.getBot();
    }

    @Override
    public void start() {

    }
}
