public class GamePvE extends Game{

    public GamePvE() {
        setPlayer1(new PlayerHuman());
        setPlayer2(PlayerMinimax.getBot());
    }


    public GamePvE(PlayerHuman player1, PlayerMinimax player2, Bord bord) {
        super(bord);
        setPlayer1(player1);
        setPlayer2(player2);
    }
}
