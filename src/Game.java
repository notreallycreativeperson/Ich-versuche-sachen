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
    public GameData play(Player playerMax, Player playerMin, Board board) {
        GameData gameData = new GameData((board.isMaxTurn?playerMax:playerMin),(!board.isMaxTurn?playerMax:playerMin));
        while (true) {

            if (Board.isWon(board.getTiles())) {
                gameData.setWinner(!board.isMaxTurn);
                return gameData;
            }

            int move;
            gameData.logTimeStart();
            if (board.isMaxTurn) {
                if(viewBord) {
                    System.out.println(playerMax.getPlayerName()+" Turn:");
                }
                move = playerMax.getMove(board,gameData);
                gameData.logTimeEnd(board.isMaxTurn);
            } else {
                if(viewBord) {
                    System.out.println(playerMin.getPlayerName() + " Turn:");
                }
                move = playerMin.getMove(board,gameData);
                gameData.logTimeEnd(board.isMaxTurn);
            }

            if (move == -1) {
                gameData.setWinner(!board.isMaxTurn);
                return gameData;
            }

            board.move(move);
            if(viewBord) {
                Visual.displayBord(board);
            }
            if(Board.isFinished(board.getTiles())){
                gameData.setDraw();
                return gameData;
            }
        }
    }

    public GameData play(Player player1, Player player2) {
        return play(player1, player2, new Board(true));
    }
    public GameData play(Player player1, Player player2, boolean isMaxTurn){
        return play(player1, player2, new Board(isMaxTurn));
    }
}

