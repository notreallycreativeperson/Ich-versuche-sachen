/**
 * In der Game-Klasse wird das Spiel gestartet und findet dort auch statt.
 * Diese Klasse verwaltet die Spieler, das Spielbrett und die Spiellogik.
 */
public class Game {

    boolean viewBord;

    /**
     * Standardkonstruktor, der ein neues Spiel mit einem leeren Spielbrett erstellt.
     */
    Game() {
        this(true);
    }

    Game(boolean viewBord) {
        this.viewBord = viewBord;
    }



    /**
     * Führt die Hauptspielschleife aus.
     * Wechselt zwischen den Spielern ab, bis das Spiel beendet ist.
     */
    public void play(Player playerMax, Player playerMin, Board board) {
        while (true) {

            if (Board.isWon(board.getTiles())) {
                playerMax.getPlayerData().logWin(!board.isMaxTurn);
                playerMin.getPlayerData().logWin(board.isMaxTurn);
                System.out.println("Game over!");
                return;
            }

            int move;
            if (board.isMaxTurn) {
                playerMax.getPlayerData().logTimeStart();
                if(viewBord) {
                    System.out.println(playerMax.getPlayerName()+" Turn:");
                }
                move = playerMax.getMove(board,playerMax.getPlayerData());
                playerMax.getPlayerData().logTimeEnd();
                playerMax.getPlayerData().logTurn();
            } else {
                playerMin.getPlayerData().logTimeStart();
                if(viewBord) {
                    System.out.println(playerMin.getPlayerName() + " Turn:");
                }
                move = playerMin.getMove(board,playerMin.getPlayerData());
                playerMin.getPlayerData().logTimeEnd();
                playerMin.getPlayerData().logTurn();
            }

            if (move == -1) {
                playerMax.getPlayerData().logWin(!board.isMaxTurn);
                playerMin.getPlayerData().logWin(board.isMaxTurn);
                System.out.println("Game over!");

                return;
            }

            board.move(move);
            if(viewBord) {
                Visual.displayBord(board);
            }
            if(Board.isFinished(board.getTiles())){
                playerMax.getPlayerData().logDraw();
                playerMin.getPlayerData().logDraw();
                System.out.println("Game over!");
                return;
            }
        }
    }
    public void play(Player playerMax, Player playerMin) {
        play(playerMax, playerMin, new Board(true));
    }
}

