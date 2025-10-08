import java.util.HashMap;

/**
Die Klasse Bord enthält alle nötigen Informationen, um das Spiel zu beschreiben.
Des Weiteren ist sie auch für Veränderungen an den in der Klasse enthaltenen Daten verantwortlich.
 */
public class Bord {

    /**
     * Das Spielbrett an sich
     */
    private int[][] tiles = new int[GameConstants.COLUMNS][GameConstants.ROWS];

    /**
     * Ist es der Zug des maximierenden Spielers
     */
    boolean isMaxTurn;

    /**
     * Letzten 2 Züge; Relevant für das Highlighten in der Gui-Klasse ({@link Visual#displayBord(int[][], int[][])})
     */
    int[][] last2Moves = new int[2][2];

    public int[][] getTiles() {
        return tiles;
    }

    Bord() {
        isMaxTurn = Visual.whoStarts();
    }

    Bord(int[] moves, int[][] tiles, int moveCount, boolean isMaxTurn, int[][] last20Moves){
        this.tiles = tiles.clone();
        this.isMaxTurn = isMaxTurn;
    }

    @SuppressWarnings("SameParameterValue")
    Bord(boolean isMaxTurn) {
        this.isMaxTurn = isMaxTurn;
    }

    Bord(int[] moves) {
        this.isMaxTurn = true; // Initialize isMaxTurn
        for (int move : moves) {
            if (move == -1) break;
            if (move < 0 || move >= GameConstants.COLUMNS) {
                throw new IllegalArgumentException("Move out of bounds: " + move);
            }
            int row = getRow(move);
            if (row < 0) {
                throw new IllegalArgumentException("Column " + move + " is already full.");
            }
            tiles[move][row] = getStartingPlayer(isMaxTurn);
            switchPlayer();
        }
    }


    /**
     * Langsame, aber gründliche Version der {@link Bord#isWinningMove(int, int, int[][])} Methode.
     * Wurde in früheren Iterationen häufiger verwendet, wird nun aber nur noch ausßerhalb der Rekursion in {@link MiniMax#miniMax(int[][], int, int, int, boolean, boolean, int, int, HashMap, long)} verwendet.
     * @param tiles
     * @return true, wenn das Spiel gewonnen ist
     */
    public static boolean isWon(int[][] tiles) {
        for (int col = 0; col < GameConstants.COLUMNS; col++) {
            for (int row = 0; row < GameConstants.ROWS; row++) {
                if (tiles[col][row] == GameConstants.EMPTY) continue;

                for (int dirIndex = 0; dirIndex < GameConstants.INDICES.length; dirIndex++) {
                    if (!GameConstants.directions[col][row][dirIndex]) continue;

                    boolean isWinningLine = true;
                    for (int step = 1; step < GameConstants.WINNING_LENGTH; step++) {
                        int nextCol = col + GameConstants.INDICES[dirIndex][0] * step;
                        int nextRow = row + GameConstants.INDICES[dirIndex][1] * step;

                        if (tiles[col][row] != tiles[nextCol][nextRow]) {
                            isWinningLine = false;
                            break;
                        }
                    }

                    if (isWinningLine) return true;
                }
            }
        }
        return false;
    }

    /**
     * Eine suboptimale Lösung für die Simulation der Schwerkraft, wenn man den Spielstein einwirft.
     * @param tiles
     * @param move
     * @return Die Zeile, auf die der Stein fallen würde
     */
    public static int getRow(int[][] tiles, int move) {
        Bord.checkMoveBounds(move);
        for (int row = 0; row < GameConstants.ROWS; row++) {
            if (tiles[move][row] == GameConstants.EMPTY) {
                return row;
            }
        }
        return -1; // Column is full
    }

    static private void checkMoveBounds(int move) {
        if (move < 0 || move >= GameConstants.COLUMNS) {
            throw new IllegalArgumentException("Move out of bounds: " + move);
        }
    }

    /**
     * Überprüft, ob das Spiel, entweder durch Sieg oder die Ausfüllung der Spalten, beendet ist.
     * @param tiles
     * @return true, wenn das Spiel beendet ist
     */
    public static boolean check(int[][] tiles) {
        return isWon(tiles) || isFinished(tiles);
    }

    /**
     * updated die Variablen die beim Zug verändert werden müssen
     * @param move
     */
    public void move(int move) {
        if (move < 0 || move >= GameConstants.COLUMNS) {
            throw new IllegalArgumentException("Invalid move: " + move);
        }

        int row = getRow(move);
        if (row < 0){
            throw new IllegalArgumentException("Column " + move + " is already full.");
        }

        tiles[move][row] = getStartingPlayer(isMaxTurn);
        last2Moves[(getStartingPlayer(isMaxTurn)+1)/2][0] =move;
        last2Moves[(getStartingPlayer(isMaxTurn)+1)/2][1] =row;
        switchPlayer();
    }

    /**
     * siehe {@link Bord#getRow(int[][], int)}
     * @param move
     * @return
     */
    public int getRow(int move) {
        if (move < 0 || move >= GameConstants.COLUMNS) {
            return -1; // Invalid column
        }

        for (int row = 0; row < GameConstants.ROWS; row++) {
            if (tiles[move][row] == GameConstants.EMPTY) {
                return row;
            }
        }
        return -1; // Column is full
    }

    /**
     * Wandelt den {@param isMaxTurn}-Wert in einen Spielstein-Wert um.
     * @param isMaxTurn
     * @return true
     */
    private int getStartingPlayer(boolean isMaxTurn) {
        return isMaxTurn ? GameConstants.PLAYER_MAX : GameConstants.PLAYER_MIN;
    }

    private void switchPlayer() {
        isMaxTurn = !isMaxTurn;
    }

    /**
     *
     * @param tiles
     * @return true, wenn das Spielfeld voll ist
     */
    public static boolean isFinished(int[][] tiles) {
        for (int i = 0; i < GameConstants.COLUMNS; i++) {
            if (tiles[i][GameConstants.ROWS - 1] == GameConstants.EMPTY) return false;
        }
        return true;
    }

    public boolean check() {
        return check(tiles);
    }

    /**
     * Kürzere Version der {@link Bord#isWon(int[][])} Methode, die nur das zuletzt gezogene Feld überprüft
     * @param x
     * @param y
     * @param tiles
     * @return
     */
    public static boolean isWinningMove(int x, int y, int[][] tiles) {
        if ((x == -1 || y == -1) || (tiles[x][y] == 0)) return false;
        
        for (int dirIndex = 0; dirIndex < GameConstants.INDICES.length; dirIndex++) {
            int count = 1;

            for (int step = 1; step < GameConstants.WINNING_LENGTH; step++) {
                int newX = x + GameConstants.INDICES[dirIndex][0] * step;
                int newY = y + GameConstants.INDICES[dirIndex][1] * step;
                
                if (newX < 0 || newX >= GameConstants.COLUMNS || 
                    newY < 0 || newY >= GameConstants.ROWS ||
                    tiles[x][y] != tiles[newX][newY]) {
                    break;
                }
                count++;
            }

            if (count == GameConstants.WINNING_LENGTH) {
                return true;
            }
        }
        return false;
    }

    public static long hash(int[][] tiles) {
        return java.util.Arrays.deepHashCode(tiles);
    }

    public int[][] getLast2Moves() {
        return last2Moves;
    }

}