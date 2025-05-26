import java.util.Arrays;

@SuppressWarnings("SameParameterValue")
public class Bord {

    private int[][] tiles;
    boolean isMaxTurn;
    final int[] moves;
    int moveCount;
    int[][] last20Moves = new int[20][2];
    private final int lastLength = last20Moves.length;

    public int[][] getTiles() {
        return tiles;
    }

    Bord() {
        initializeBord();
        isMaxTurn = Visual.whoStarts();
        moves = new int[GameConstants.MAX_MOVES];
        moveCount = 0;
        initializeMoves();
    }

    Bord(int[] moves, int tiles[][], int moveCount, boolean isMaxTurn, int[][] last20Moves){
        this.tiles = tiles.clone();
        this.moves = moves.clone();
        this.moveCount = moveCount;
        this.isMaxTurn = isMaxTurn;
        this.last20Moves = last20Moves.clone();
    }

    @SuppressWarnings("SameParameterValue")
    Bord(boolean isMaxTurn) {
        initializeBord();
        this.isMaxTurn = isMaxTurn;
        moves = new int[GameConstants.MAX_MOVES];
        moveCount = 0;
        initializeMoves();
    }

    Bord(int[] moves) {
        initializeBord();
        this.moves = moves;
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
            tiles[move][row] = getPlayer(isMaxTurn);
            addMove(move, row);
            moveCount++;
            switchPlayer();
        }

    }



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
        if (move < 0 || move >= GameConstants.MAX_MOVES) {
            throw new IllegalArgumentException("Move out of bounds: " + move);
        }
    }

    public static boolean check(int[][] tiles) {
        return isWon(tiles) || isFinished(tiles);
    }

    @SuppressWarnings("unused")
    public boolean isFinished() {
        return isFinished(tiles);
    }

    public void move(int move) {
        if (move < 0 || move >= GameConstants.COLUMNS) {
            throw new IllegalArgumentException("Invalid move: " + move);
        }

        int row = getRow(move);
        if (row < 0){
            throw new IllegalArgumentException("Column " + move + " is already full.");
        };

        tiles[move][row] = getPlayer(isMaxTurn);
        moves[moveCount] = move;
        addMove(move, row);
        moveCount++;
        switchPlayer();
    }

    public int getHumanRow(int move) {
        if (move == -1) return move;
        else return getRow(move);
    }

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

    private void addMove(int move, int row) {
        for (int i = 0; i < lastLength-1; i++) { //todo reihenfolge umdrehen, so dass 0 der letzte move ist
            last20Moves[i+1]=last20Moves[i];
        }
        last20Moves[0][0] = move;
        last20Moves[0][1] = row;
    }

    private void removeMove() {
        for (int i = 0; i < lastLength-1; i++) {
            last20Moves[i] = last20Moves[i+1];
        }
        last20Moves[lastLength - 1][0] = -1;
        last20Moves[lastLength - 1][1] = -1;
    }

    private int getPlayer(boolean isMaxTurn) {
        return isMaxTurn ? GameConstants.PLAYER_MAX : GameConstants.PLAYER_MIN;
    }

    private void switchPlayer() {
        isMaxTurn = !isMaxTurn;
    }

    public static boolean isFinished(int[][] tiles) {
        for (int i = 0; i < GameConstants.COLUMNS; i++) {
            if (tiles[i][GameConstants.ROWS - 1] == GameConstants.EMPTY) return false;
        }
        return true;
    }

    public boolean check() {
        return check(tiles);
    }

    @SuppressWarnings("unused")
    public void unMove() {
        if (last20Moves[0][0]==-1) {
            tiles[moves[moveCount-1]][getRow(moves[moveCount-1])]=0;
        }else {
            tiles[last20Moves[0][0]][last20Moves[0][1]] = 0;
        }
        switchPlayer();
        moveCount--;
        moves[moveCount] = -1;
    }

    private void initializeLast20Moves() {
        for (int i = 0; i < last20Moves.length; i++) {
            last20Moves[i][0] = -1;
            last20Moves[i][1] = -1;
        }
    }

    private void initializeMoves() {
        Arrays.fill(moves, -1);
    }

    private void initializeBord() {
        tiles = new int[GameConstants.COLUMNS][GameConstants.ROWS];
        initializeLast20Moves();
    }

    public boolean isWinningMove() {
        if (moveCount < 3) return false;
        int move = moves[moveCount-1];
        int row = last20Moves[0][1];
        for (int dirIndex = 0; dirIndex < GameConstants.INDICES_LONG.length; dirIndex++) {
            if (GameConstants.directions[move][row][dirIndex]){
                for (int step = 1; step < GameConstants.WINNING_LENGTH; step++) {
                    if(tiles[move][row]!=tiles[move+GameConstants.INDICES_LONG[dirIndex][0]*step][row+GameConstants.INDICES_LONG[dirIndex][1]*step]){
                        break;
                    }else if(step==GameConstants.WINNING_LENGTH-1){
                        return true;
                    }
                }
            } continue;
        }
        return false;
    }

    public Bord clone() {
        return new Bord(moves, tiles, moveCount, isMaxTurn, last20Moves);
    }
}