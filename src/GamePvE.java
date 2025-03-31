public class GamePvE extends Game{

    public GamePvE() {
        setPlayer1(new PlayerHuman());
        setPlayer2(Main.getBot());
    }

    public GamePvE(PlayerHuman player1, PlayerMinimax player2) {
        setPlayer1(player1);
        setPlayer2(player2);
    }



        @Override
    public void start() {

    }

    @Override
    public int game() {
        return 0;
    }
}
