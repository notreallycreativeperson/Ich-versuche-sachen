import java.util.Random;
import java.util.Scanner;

import static java.lang.System.out;

/**
 * Die Visual-Klasse behandelt alle Benutzerinteraktionen und die Ausgabe.
 * Verwaltet Eingaben über {@link Scanner} und zeigt das {@link Board Spielbrett} an.
 * Wird von {@link Game}, {@link PlayerHuman} und anderen Klassen verwendet.
 */
class Visual {

    /**
     * Scanner für Benutzereingaben
     */
    static final Scanner scanner = new Scanner(System.in);

    /**
     * Fragt den Benutzer nach dem gewünschten Spielmodus.
     * Gibt die Auswahl an {@link Main Main.setMode()} zurück.
     */
    public static int getMode() {
        int mode = -1;
        do {
            out.println("In welchem Modus möchtest du spielen?");
            out.println("1 für schnelles spiel");
            out.println("2 für PvP");
            out.println("3 für EvE");
            out.println("4 für Test");
            out.println("5 für PvE");
            try {
                mode = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                out.println("Bitte gib eine zulässige Zahl ein.");
            }
        } while (mode == -1);
        return mode;
    }

    /**
     * Fragt den Benutzer nach dem gewünschten Bot-Typ.
     * Wird von {@link PlayerInhumane#getBot()} aufgerufen.
     *
     * @return Die Bot-Nummer (1=Strange, 2=Stupid, 3=Competent)
     * @see PlayerStrange
     * @see PlayerMinimax
     */
    public static int getBot() {
        int bot = -1;
        do {
            out.println("Welcher Bot soll spielen");
            out.println("1->Strange | 2->Stupid | 3->Competent");
            try {
                bot = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                out.println("Bitte gib eine zulässige Zahl ein.");
            }
        } while (bot == -1);
        return bot;
    }

    /**
     * Fragt den Benutzer nach seinem Namen.
     * Wird vom {@link PlayerHuman#PlayerHuman()} Konstruktor aufgerufen.
     *
     * @return Der eingegebene Spielername
     */
    public static String getName() {
        out.println("Spieler, wie ist dein Name?");
        String name = scanner.next();
        out.println("Hallo " + name);
        out.println();
        return name;
    }

    /**
     * Fragt den Benutzer, welcher Spieler beginnen soll.
     * Wird vom {@link Board#Board()} Konstruktor aufgerufen.
     *
     * @return True, wenn Spieler X (maximierender Spieler) beginnt, sonst false
     */
    public static boolean whoStarts() {
        boolean run = true;
        int input = 0;
        while (run) {
            out.println("Soll x oder o beginnen?");
            out.println("x->1 | o->2 | zufall->3");
            try {
                input = Integer.parseInt(scanner.next());
                if (input < 4 && input > 0) run = false;
            } catch (Exception e) {
                out.println("Bitte gib eine zulässige Zahl ein.");
            }
        }
        return switch (input) {
            case 2 -> false;
            case 3 -> new Random().nextBoolean();
            default -> true;
        };
    }

    /**
     * Zeigt den Gewinner des Spiels an.
     * Wird von {@link Game#play(Player, Player)} aufgerufen.
     *
     * @param winner Die Spielernummer (1 oder 2) des Gewinners
     */
    public static void winner(int winner) {
        out.println("Spieler " + winner + " hat gewonnen.");
    }

    /**
     * Zeigt ein Unentschieden an.
     * Wird von {@link Game#play(Player, Player)} bei vollem {@link Board Spielbrett} aufgerufen.
     */
    public static void tie() {
        out.println("Das spiel endet unendschieden.");
    }

    /**
     * Zeigt das {@link Board Spielbrett} an.
     * Verwendet {@link Board#getTiles()} und {@link Board#getLast2Moves()}.
     *
     * @param board Das anzuzeigende {@link Board Spielbrett}
     */
    public static void displayBord(Board board) {
        displayBord(board.getTiles(), board.getLast2Moves());
    }

    /**
     * Zeigt das Spielbrett mit farbiger Hervorhebung der letzten Züge an.
     * Verwendet {@link GameConstants.ConsoleColors} für die Farbdarstellung.
     * Wird nach jedem Zug von {@link Game#play(Player, Player)} aufgerufen.
     *
     * @param bord       Das Spielbrett als 2D-Array
     * @param last2Moves Die letzten zwei Züge für die Hervorhebung
     */
    public static void displayBord(int[][] bord, int[][] last2Moves) {
        for (int i = 1; i < 8; i++) {
            out.print(" " + i + "  ");
        }
        out.println();
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                out.print("[");
                switch (bord[j][i]) {
                    case 1: {
                        if (last2Moves[1][0] == j && last2Moves[1][1] == i) {
                            out.print(GameConstants.ConsoleColors.RED_BOLD + GameConstants.ConsoleColors.WHITE_BACKGROUND + "X");
                        } else {
                            out.print(GameConstants.ConsoleColors.RED_BOLD + "x");
                        }
                        break;
                    }
                    case -1: {
                        if (last2Moves[0][0] == j && last2Moves[0][1] == i) {
                            out.print(GameConstants.ConsoleColors.YELLOW_BOLD + GameConstants.ConsoleColors.WHITE_BACKGROUND + "O");
                        } else {
                            out.print(GameConstants.ConsoleColors.YELLOW_BOLD + "o");
                        }
                        break;
                    }
                    default:
                        out.print(" ");
                }
                out.print(GameConstants.ConsoleColors.RESET);
                out.print("] ");
            }
            out.println();
        }
        out.println();
        out.println();
    }

    /**
     * Fragt den Benutzer nach seinem nächsten Zug.
     * Wird von {@link PlayerHuman#getMove(Board, GameData)} aufgerufen.
     * Validiert die Eingabe (muss zwischen 1 und 7 sein).
     *
     * @return Die Spaltennummer (0-6) des gewählten Zuges, oder -1 bei ungültiger Eingabe
     */
    public static int getMove() {
        int move = -1;
        out.println("Welchen Zug möchtest du ziehen?");

        Scanner scanner = new Scanner(System.in);
        try {
            move = Integer.parseInt(scanner.next()) - 1;


        } catch (Exception e) {
            out.println("Bitte gib eine zulässige Zahl von 1 bis 7 ein.");
        }
        if (move > 6 || move < 0) {
            out.println("Diese Zahl ist zu groß");
            move = -1;
        }
        return move;
    }

    public static void printPlayerData(String playerName,PlayerData playerData) {
        out.println(playerName + ": ");
        playerData.printPlayerData();
        out.println();
        out.println();
    }

    public static void printGame(GameData gameData) {
        out.print(gameData.playerMax.getPlayerName()+" vs "+ gameData.playerMin.getPlayerName()+ ": ");
        if(gameData.isDraw){
            out.println(" Draw");
        } else if (gameData.isPlayerMaxWinner) {
           out.println(gameData.playerMax.getPlayerName());
        }else {
            out.println(gameData.playerMin.getPlayerName());
        }
    }

}