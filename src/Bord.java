@SuppressWarnings("SameParameterValue")
public class Bord {

    private int[][] tiles = new int[GameConstants.COLUMNS][GameConstants.ROWS];;
    boolean isMaxTurn;
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
            tiles[move][row] = getPlayer(isMaxTurn);
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
        }

        tiles[move][row] = getPlayer(isMaxTurn);
        last2Moves[(getPlayer(isMaxTurn)+1)/2][0] =move;
        last2Moves[(getPlayer(isMaxTurn)+1)/2][1] =row;
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

    public static boolean isWinningMove(int x, int y, int[][] tiles) {
        if ((x == -1 || y == -1) || (tiles[x][y] == 0)) return false;
        
        for (int dirIndex = 0; dirIndex < GameConstants.INDICES.length/2; dirIndex++) {
            int count = 1;
            
            // Vorwärts prüfen
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
            
            // Rückwärts prüfen
            for (int step = 1; step < GameConstants.WINNING_LENGTH; step++) {
                int newX = x - GameConstants.INDICES[dirIndex][0] * step;
                int newY = y - GameConstants.INDICES[dirIndex][1] * step;
                
                if (newX < 0 || newX >= GameConstants.COLUMNS || 
                    newY < 0 || newY >= GameConstants.ROWS ||
                    tiles[x][y] != tiles[newX][newY]) {
                    break;
                }
                count++;
            }

            if (count >= GameConstants.WINNING_LENGTH) {
                return true;
            }
        }
        return false;
    }

    public static long hash(int[][] tiles) {
        long hash=0;
        for(int i = 0; i < GameConstants.COLUMNS; i++){
            for(int j = GameConstants.ROWS-1; j >=0 ; j--){
                hash+=(long)tiles[i][j]*GameConstants.HASH_VALUES[i][j];
            }

        }
        return hash;
    }

    public int[][] getLast2Moves() {
        return last2Moves;
    }

}

class Entry {
    int score;
    int depth;

    Entry(int score, int depth) {
        this.score = score;
        this.depth = depth;
    }
}