/**
 * In der Game Klasse wird das Spiel gestartet und findet dort auch statt.
 */
public class Game {
    protected Player player1;
    protected Player player2;
    public Bord bord;

    Game() {
        bord = new Bord();
    }

    Game(Bord bord) {
        this.bord = bord;
    }

    Game(Player player1, Player player2, boolean isMaxTurn) {
        this.player1 = player1;
        this.player2 = player2;
        bord = new Bord(isMaxTurn);
    }

    public void start() {
        game();
        int winner = game();
        if (winner < 1) {
            Visual.tie();
            Info.printPrunes();
            Info.printTime();
            return;
        }

        Visual.winner(winner);
        Info.printPrunes();
        Info.printTime();

    }

    protected int game() {
        TimeWatcher.start();
        while (!bord.check()) {
            int move;
            if (bord.isMaxTurn) {
                TimeWatcher.start(1);
                System.out.println("Spieler 1:");
                move = player1.getMove(bord);
                TimeWatcher.stop(1);
            } else {
                TimeWatcher.start(2);
                System.out.println("Spieler 2:");
                move = player2.getMove(bord);
                TimeWatcher.stop(2);
            }

            Info.logTurns();

            if (move==-1){
                return -1;
            }

            bord.move(move);
            Visual.displayBord(bord);
        }

        if (Bord.isWon(bord.getTiles())) {
            return (bord.isMaxTurn ? 2 : 1);
        }
        return -1;
    }

    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    static class EvE extends Game {

        EvE() {
            player1 = PlayerInhumane.getBot();
            player2 = PlayerInhumane.getBot();
        }

        EvE(Player player1, Player player2, boolean isMaxTurn) {
            this.player1 = player1;
            this.player2 = player2;
            bord = new Bord(isMaxTurn);
        }
    }

    static class PvE extends Game {
        public PvE() {
            setPlayer1(new PlayerHuman());
            setPlayer2(PlayerInhumane.getBot());
        }

        public PvE(PlayerHuman player1, PlayerInhumane player2, Bord bord) {
            super(bord);
            setPlayer1(player1);
            setPlayer2(player2);
        }
    }

    static class Fast extends PvE {
        public Fast() {
             super(new PlayerHuman("Spieler 1"), new PlayerCompetent(12), new Bord(true));
        }
        public Fast(int depth) {
            super(new PlayerHuman("Spieler 1"), new PlayerCompetent(depth), new Bord(true));
        }
    }

    static class PvP extends Game {
        PvP() {
            setPlayer1(new PlayerHuman());
            setPlayer2(new PlayerHuman());
        }
    }
}