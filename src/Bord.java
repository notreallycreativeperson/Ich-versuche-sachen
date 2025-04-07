@SuppressWarnings("SameParameterValue")
public class Bord {

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
    final int[][] last8Moves = new int[2][8];
    static final boolean[][][] directions = new boolean[7][6][4];

    Bord() {
        newBord();
        isMaxTurn = Visual.whoStarts();
        moves = new int[42];
        moveCount = 0;

    }

    @SuppressWarnings("SameParameterValue")
    Bord(boolean isMaxTurn) {
        newBord();
        this.isMaxTurn = isMaxTurn;
        moves = new int[42];
        moveCount = 0;

    }


    Bord(int[] moves) {
        newBord();
        this.moves = moves;
        boolean isMaxTurn = true;
        for (int move : moves) {
            tiles[move][getRow(move)] = getPlayer(isMaxTurn);
            switchPlayer();
        }

        moveCount = moves.length;
        for (int i = 0; i < 8; i++) {
            last8Moves[i][0] = moves[moves.length - 1 - i];
            last8Moves[i][1] = getRow(moves[moves.length - 1 - i]);
        }

    }

    protected static void setDirections() {
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                directions[i][j][0] = i < 4;
                directions[i][j][1] = (i < 4 && j < 3);
                directions[i][j][2] = j < 3;
                directions[i][j][3] = (i > 2 && j < 3);
            }
        }
    }

    public static boolean isWon(int[][] tiles) {
        // Methode zur Überprüfung, ob ein Spieler gewonnen hat
        for (int i = 0; i < 7; i++) {
            for (int j = 0; j < 6; j++) {
                if (tiles[i][j] != 0) {
                    for (int k = 0; k < 4; k++) {
                        if (directions[i][j][k]){
                            if ((tiles[i][j] == tiles[i + INDICES[k][0]][j + INDICES[k][1]]) && (tiles[i][j] == tiles[i + INDICES[k][0] * 2][j + INDICES[k][1] * 2]) && (tiles[i][j] == tiles[i + INDICES[k][0] * 3][j + INDICES[k][1] * 3]))
                            {return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }


    private void newBord() {
        tiles = new int[7][6];
        setDirections();
    }

    public static int getRow(int[][] tiles ,int move) {
        int row = -1;
        for (int i = 0; i < 6; i++) {
            if (tiles[move][i] == 0) {
                row = i;
                break;
            }
        }
        return row;
    }

    public static boolean check(int[][] tiles){

        return isWon(tiles) || isFinished(tiles);

    }

    @SuppressWarnings("unused")
    public boolean isFinished(){
        return isFinished(tiles);
    }

    public void move(int move) {
        int row = getRow(move);
        if (row < 0) return;
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
        int row = -1;
        for (int i = 0; i < 6; i++) {
            if (tiles[move][i] == 0) {
                row = i;
                break;
            }
        }
        return row;
    }

    private void addMove(int move, int row) {
        for (int i = last8Moves.length - 1; i > 0; i--) {
            last8Moves[i][0] = last8Moves[i - 1][0];
            last8Moves[i][1] = last8Moves[i - 1][1];
        }
        last8Moves[0][0] = move;
        last8Moves[0][1] = row;

    }

    private void addMove(int move) {
        addMove(move, getRow(move));
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
        if (isMaxTurn) {
            return 1;
        } else {
            return -1;
        }
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


}
