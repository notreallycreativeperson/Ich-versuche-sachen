public abstract class Game implements Startableable {
    public GameMode gameMode;
    private Player player1;
    private Player player2;
    public Bord bord;

    Game(){
        bord=new Bord();
    }

    public abstract void start();

    public int game(){

        int winner=-1;

        while (bord.check()!=true) {
            int move=-1;
            while (bord.getHumanRow(move)<0){
                if (bord.isMaxTurn) {
                    System.out.println("Spieler 1:");
                    move=getPlayer1().getMove();
                }else {
                    System.out.println("Spieler 2:");
                    move=getPlayer2().getMove();
                }
            }
            bord.move(move);
            Visual.displayBord(bord);

        }

        if(bord.isWon()){
            return (bord.isMaxTurn?2:1);
        }

        return -1;
    };

    public Bord getBord() {
        return bord;
    }

    public void setBord(Bord bord) {
        this.bord = bord;
    }

    public Player getPlayer2() {
        return player2;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public Player getPlayer1() {
        return player1;
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
