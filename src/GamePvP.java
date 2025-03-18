public class GamePvP extends Game{
    @Override
    public void start() {
        player1 = new PlayerHuman();
        player2 = new PlayerHuman();
    }
}
