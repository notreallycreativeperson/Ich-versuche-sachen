public abstract class Game implements Startableable {
    public GameMode gameMode;
    protected Player player1;
    protected Player player2;
    public Bord bord;
    private int rev = 0;

    Game(){
        bord=new Bord();
    }

    Game(Bord bord) {
        this.bord = bord;
    }

    public void start() {
        game();
        int winner = game();
        if (winner < 1) {
            Visual.tie();
            return;
        }

        Visual.winner(winner);

    }

    protected int game() {

        while (!bord.check() && rev <= 4) {
            int move=-1;
            rev = 0;
            while (bord.getHumanRow(move) < 0 && rev <= 4) {
                if (bord.isMaxTurn) {
                    System.out.println("Spieler 1:");
                    move = player1.getMove(bord);
                }else {
                    System.out.println("Spieler 2:");
                    move = player2.getMove(bord);
                }
                rev++;
            }

            if (rev >= 4) {
                return (bord.isMaxTurn ? 1 : 2);
            }
            bord.move(move);
            Visual.displayBord(bord);

        }

        if(Bord.isWon(bord.tiles)){
            return (bord.isMaxTurn?2:1);
        }
        return -1;
    }

    public void setBord(Bord bord) {
        this.bord = bord;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    public GameMode getGameMode() {
        return gameMode;
    }

    public void setGameMode(GameMode gameMode) {
        this.gameMode = gameMode;
    }
}
