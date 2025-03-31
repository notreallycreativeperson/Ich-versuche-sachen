public class GamePvP extends Game{

    GamePvP(){
        setPlayer1(new PlayerHuman());
        setPlayer2(new PlayerHuman());
    }

    @Override
    public void start() {

        int winner=game();
        if (winner<1){
            Visual.tie();
            return;
        }

        Visual.winner(winner);

    }
}
