public class GameConstants {

    // Constants for board dimensions
    public static final int COLUMNS;
    public static final int ROWS;
    public static final int MAX_MOVES;
    public static final int WINNING_LENGTH;
    public static final int[] EXPLORE_ORDER;

    // Player constants
    public static final int EMPTY;
    public static final int PLAYER_MAX;
    public static final int PLAYER_MIN;

    // Misc constants
    public static final int[][] INDICES;
    public static int[][] HASH_VALUES;
    public static int[] PRIMES;
    static final boolean[][][] directions;
    public static boolean DEBUG;


    static {
        PRIMES = new int[]{3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
        EXPLORE_ORDER = new int[]{3, 4, 2, 5, 1, 6, 0};
        COLUMNS = 7;
        ROWS = 6;
        MAX_MOVES = COLUMNS * ROWS;
        WINNING_LENGTH = 4;
        EMPTY = 0;
        PLAYER_MAX = 1;
        PLAYER_MIN = -1;
        INDICES = new int[][]{
                {1, 0},
                {1, 1},
                {0, 1},
                {-1, 1},
                {-1, 0},
                {-1, -1},
                {0, -1},
                {1, -1},

        };
        directions = new boolean[COLUMNS][ROWS][INDICES.length];
    }

    private static void setDirections() {
        // Only initialize if not already set
        if (directions[0][0][0]) return;
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                directions[i][j][0] = i < COLUMNS - WINNING_LENGTH + 1;
                directions[i][j][1] = (i < COLUMNS - WINNING_LENGTH + 1 && j < ROWS - WINNING_LENGTH + 1);
                directions[i][j][2] = j < ROWS - WINNING_LENGTH + 1;
                directions[i][j][3] = (i > WINNING_LENGTH - 2 && j < ROWS - WINNING_LENGTH + 1);
                directions[i][j][4] = j > COLUMNS - WINNING_LENGTH + 1;
                directions[i][j][5] = i > COLUMNS-WINNING_LENGTH+1 && j > ROWS - WINNING_LENGTH+1;
                directions[i][j][6] = j > ROWS - WINNING_LENGTH+1;
                directions[i][j][7] = i < COLUMNS-WINNING_LENGTH+1&&j > ROWS - WINNING_LENGTH+1;
            }
        }
    }

    private static void setHashValues() {
        HASH_VALUES = new int[COLUMNS][ROWS];
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                HASH_VALUES[i][j] = PRIMES[i] * PRIMES[COLUMNS+j-1];
            }
        }
    }


    public static void init(boolean debug) {
        setDirections();
        setHashValues();
        DEBUG = debug;
    }

}
