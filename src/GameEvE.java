public class GameEvE extends Game{

    GameEvE(){
        player1 = PlayerMinimax.getBot();
        player2 = PlayerMinimax.getBot();
    }
}
