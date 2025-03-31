public class GameEvE extends Game{

    GameEvE(){
        setPlayer1(Main.getBot());
        setPlayer2(Main.getBot());
    }

    @Override
    public void start() {

    }

    @Override
    public int game() {
        return 0;
    }
}
