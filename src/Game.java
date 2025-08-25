public class Game implements Startable {
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
            return;
        }

        Visual.winner(winner);

    }

    protected int game() {

        while (!bord.check()) {
            int move = -1;
            int rev = 0;
            while (bord.getHumanRow(move) < 0 && rev <= 4) {
                if (bord.isMaxTurn) {
                    System.out.println("Spieler 1:");
                    move = player1.getMove(bord);
                } else {
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

        if (Bord.isWon(bord.getTiles())) {
            return (bord.isMaxTurn ? 2 : 1);
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

    static class EvE extends Game {

        EvE() {
            player1 = PlayerMinimax.getBot();
            player2 = PlayerMinimax.getBot();
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
            setPlayer2(PlayerMinimax.getBot());
        }

        public PvE(PlayerHuman player1, PlayerMinimax player2, Bord bord) {
            super(bord);
            setPlayer1(player1);
            setPlayer2(player2);
        }
    }

    static class Fast extends PvE {
        public Fast() {
            super(new PlayerHuman("Spieler 1"), new PlayerCompetent(13), new Bord(true));
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

    static class Turnament extends Game {
        @Override
        public void start() {

        }

        @Override
        public int game() {
            return 0;
        }
    }
}