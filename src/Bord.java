@SuppressWarnings("SameParameterValue")
public class Bord {
    // Constants for board dimensions
    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    public static final int MAX_MOVES = COLUMNS * ROWS;
    public static final int WINNING_LENGTH = 4;

    // Player constants
    public static final int EMPTY = 0;
    public static final int PLAYER_MAX = 1;
    public static final int PLAYER_MIN = -1;

    public static final int[][] INDICES ={
            {1,0},
            {1,1},
            {0,1},
            {-1,1}
    };

    int[][] tiles;
    boolean isMaxTurn;
    final int[] moves;
    int moveCount;
    final int[][] last8Moves = new int[8][2];
    static final boolean[][][] directions = new boolean[COLUMNS][ROWS][4];

    Bord() {
        newBord();
        isMaxTurn = Visual.whoStarts();
        moves = new int[MAX_MOVES];
        moveCount = 0;
        initializeLast8Moves();
    }

    @SuppressWarnings("SameParameterValue")
    Bord(boolean isMaxTurn) {
        newBord();
        this.isMaxTurn = isMaxTurn;
        moves = new int[MAX_MOVES];
        moveCount = 0;

    }


    Bord(int[] moves) {
        newBord();
        this.moves = moves;
        this.isMaxTurn = true; // Initialize isMaxTurn
        for (int move : moves) {
            tiles[move][getRow(move)] = getPlayer(isMaxTurn);
            switchPlayer();
        }

        moveCount = moves.length;
        for (int i = 0; i < Math.min(8, moves.length); i++) { // Prevent ArrayIndexOutOfBoundsException
            last8Moves[i][0] = moves[moves.length - 1 - i];
            last8Moves[i][1] = getRow(moves[moves.length - 1 - i]);
        }
    }

    protected static void setDirections() {
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                directions[i][j][0] = i < COLUMNS - WINNING_LENGTH + 1;
                directions[i][j][1] = (i < COLUMNS - WINNING_LENGTH + 1 && j < ROWS - WINNING_LENGTH + 1);
                directions[i][j][2] = j < ROWS - WINNING_LENGTH + 1;
                directions[i][j][3] = (i > WINNING_LENGTH - 2 && j < ROWS - WINNING_LENGTH + 1);
            }
        }
    }

    public static boolean isWon(int[][] tiles) {
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                if (tiles[col][row] == EMPTY) continue;

                for (int dirIndex = 0; dirIndex < INDICES.length; dirIndex++) {
                    if (!directions[col][row][dirIndex]) continue;

                    boolean isWinningLine = true;
                    for (int step = 1; step < WINNING_LENGTH; step++) {
                        int nextCol = col + INDICES[dirIndex][0] * step;
                        int nextRow = row + INDICES[dirIndex][1] * step;

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


    private void newBord() {
        tiles = new int[COLUMNS][ROWS];
        setDirections();
    }

    public static int getRow(int[][] tiles ,int move) {
        if (move < 0 || move >= COLUMNS) {
            throw new IllegalArgumentException("Invalid move at get row: " + move);
        }

        for (int row = 0; row < ROWS; row++) {
            if (tiles[move][row] == EMPTY) {
                return row;
            }
        }
        return -1; // Column is full
    }

    public static boolean check(int[][] tiles){

        return isWon(tiles) || isFinished(tiles);

    }

    @SuppressWarnings("unused")
    public boolean isFinished(){
        return isFinished(tiles);
    }

    public void move(int move) {
        if (move < 0 || move >= COLUMNS) {
            throw new IllegalArgumentException("Invalid move: " + move);
        }

        int row = getRow(move);
        if (row < 0) return; // Column is full

        tiles[move][row] = getPlayer(isMaxTurn);
        moves[moveCount] = move;
        addMove(move, row);
        moveCount++;
        switchPlayer();
    }

    public int getHumanRow(int move){
        if (move==-1) return move;
        else return getRow(move);
    }

    public int getRow(int move) {
        if (move < 0 || move >= COLUMNS) {
            return -1; // Invalid column
        }

        for (int row = 0; row < ROWS; row++) {
            if (tiles[move][row] == EMPTY) {
                return row;
            }
        }
        return -1; // Column is full
    }

    private void addMove(int move, int row) {
        for (int i = last8Moves.length - 1; i > 0; i--) {
            last8Moves[i][0] = last8Moves[i - 1][0];
            last8Moves[i][1] = last8Moves[i - 1][1];
        }
        last8Moves[0][0] = move;
        last8Moves[0][1] = row;
    }

    private boolean removeMove() {
        if (last8Moves[0][0] == -1) return true;

        for (int i = 0; i < 7; i++) {
            last8Moves[i][0] = last8Moves[i + 1][0];
            last8Moves[i][1] = last8Moves[i + 1][1];
        }
        last8Moves[7][0] = -1;
        last8Moves[7][1] = -1;
        return false;
    }

    private int getPlayer(boolean isMaxTurn) {
        return isMaxTurn ? PLAYER_MAX : PLAYER_MIN;
    }

    private void switchPlayer() {
        isMaxTurn = !isMaxTurn;
    }

    public static boolean isFinished(int[][] tiles) {
        for (int i = 0; i < 7; i++) {
            if (tiles[i][5] == 0) return false;
        }

        return true;
    }

    public boolean check(){
        return check(tiles);
    }

    @SuppressWarnings("unused")
    public void unMove() {
        if (removeMove()) {
            return;
        }
        isMaxTurn = !isMaxTurn;
        moveCount--;
        moves[moveCount] = 0;
        tiles[last8Moves[0][0]][last8Moves[0][1]] = 0;
    }

    private void initializeLast8Moves() {
        for (int i = 0; i < 8; i++) {
            last8Moves[i][0] = -1;
            last8Moves[i][1] = -1;
        }
    }
}