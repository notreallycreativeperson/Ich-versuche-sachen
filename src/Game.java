/**
 * In der Game-Klasse wird das Spiel gestartet und findet dort auch statt.
 * Diese Klasse verwaltet die Spieler, das Spielbrett und die Spiellogik.
 */
public class Game {
    /**
     * Das Spielbrett
     */
    public Bord bord;
    /**
     * Der erste Spieler (meist maximierender Spieler)
     */
    protected Player player1;
    /**
     * Der zweite Spieler (meist minimierender Spieler)
     */
    protected Player player2;

    /**
     * Standardkonstruktor, der ein neues Spiel mit einem leeren Spielbrett erstellt.
     */
    Game() {
        bord = new Bord();
    }

    /**
     * Konstruktor mit vorgegebenem Spielbrett.
     *
     * @param bord Das zu verwendende Spielbrett
     */
    Game(Bord bord) {
        this.bord = bord;
    }

    /**
     * Konstruktor mit zwei Spielern und Startbedingung.
     *
     * @param player1   Der erste Spieler
     * @param player2   Der zweite Spieler
     * @param isMaxTurn Gibt an, ob der maximierende Spieler beginnt
     */
    Game(Player player1, Player player2, boolean isMaxTurn) {
        this.player1 = player1;
        this.player2 = player2;
        bord = new Bord(isMaxTurn);
    }

    /**
     * Startet das Spiel und zeigt am Ende das Ergebnis sowie Statistiken an.
     * Diese Methode führt das Spiel aus und gibt den Gewinner oder ein Unentschieden bekannt.
     */
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

    /**
     * Führt die Hauptspielschleife aus.
     * Wechselt zwischen den Spielern ab, bis das Spiel beendet ist.
     *
     * @return Die Nummer des Gewinners (1 oder 2), oder -1 bei Unentschieden
     */
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

            if (move == -1) {
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

    /**
     * Setzt den zweiten Spieler.
     *
     * @param player2 Der zweite Spieler
     */
    public void setPlayer2(Player player2) {
        this.player2 = player2;
    }

    /**
     * Setzt den ersten Spieler.
     *
     * @param player1 Der erste Spieler
     */
    public void setPlayer1(Player player1) {
        this.player1 = player1;
    }

    /**
     * Innere Klasse für Spiele zwischen zwei KI-Spielern (Engine vs Engine).
     */
    static class EvE extends Game {

        /**
         * Erstellt ein EvE-Spiel mit zwei Standard-Bot-Spielern.
         */
        EvE() {
            player1 = PlayerInhumane.getBot();
            player2 = PlayerInhumane.getBot();
        }

        /**
         * Erstellt ein EvE-Spiel mit spezifischen Spielern und Startbedingung.
         *
         * @param player1   Der erste Bot-Spieler
         * @param player2   Der zweite Bot-Spieler
         * @param isMaxTurn Gibt an, ob der maximierende Spieler beginnt
         */
        EvE(Player player1, Player player2, boolean isMaxTurn) {
            this.player1 = player1;
            this.player2 = player2;
            bord = new Bord(isMaxTurn);
        }
    }

    /**
     * Innere Klasse für Spiele zwischen einem menschlichen Spieler und einer KI (Player vs Engine).
     */
    static class PvE extends Game {
        /**
         * Erstellt ein PvE-Spiel mit einem menschlichen Spieler und einem Standard-Bot.
         */
        public PvE() {
            setPlayer1(new PlayerHuman());
            setPlayer2(PlayerInhumane.getBot());
        }

        /**
         * Erstellt ein PvE-Spiel mit spezifischen Spielern und Spielbrett.
         *
         * @param player1 Der menschliche Spieler
         * @param player2 Der Bot-Spieler
         * @param bord    Das zu verwendende Spielbrett
         */
        public PvE(PlayerHuman player1, PlayerInhumane player2, Bord bord) {
            super(bord);
            setPlayer1(player1);
            setPlayer2(player2);
        }
    }

    /**
     * Innere Klasse für schnelle Spiele mit voreingestellter Suchtiefe.
     * Verwendet einen kompetenten Bot mit definierter Tiefe.
     */
    static class Fast extends PvE {
        /**
         * Erstellt ein schnelles Spiel mit Standard-Suchtiefe 12.
         */
        public Fast() {
            super(new PlayerHuman("Spieler 1"), new PlayerCompetent(12), new Bord(true));
        }

        /**
         * Erstellt ein schnelles Spiel mit angegebener Suchtiefe.
         *
         * @param depth Die Suchtiefe für den Bot
         */
        public Fast(int depth) {
            super(new PlayerHuman("Spieler 1"), new PlayerCompetent(depth), new Bord(true));
        }
    }

    /**
     * Innere Klasse für Spiele zwischen zwei menschlichen Spielern (Player vs Player).
     */
    static class PvP extends Game {
        /**
         * Erstellt ein PvP-Spiel mit zwei menschlichen Spielern.
         */
        PvP() {
            setPlayer1(new PlayerHuman());
            setPlayer2(new PlayerHuman());
        }
    }
}