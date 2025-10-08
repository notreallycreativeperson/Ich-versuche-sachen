public class GameConstants {

    // Constants for board dimensions
    public static final int COLUMNS;
    public static final int ROWS;
    public static final int MAX_MOVES;
    public static final int WINNING_LENGTH;
    public static final int[] EXPLORE_ORDER_OPTIMIZED;
    public static final int[] EXPLORE_ORDER_BASIC;

    // Player constants
    public static final int EMPTY;
    public static final int PLAYER_MAX;
    public static final int PLAYER_MIN;

    // Misc constants
    public static final int[][] INDICES;
    public static long[][] HASH_VALUES;
    public static final int[] PRIMES;
    static final boolean[][][] directions;
    public static boolean DEBUG;


    static {
        PRIMES = new int[]{3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47};
        EXPLORE_ORDER_OPTIMIZED = new int[]{3, 4, 2, 5, 1, 6, 0};
        EXPLORE_ORDER_BASIC = new int[]{0, 1, 2, 3, 4, 5, 6};
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
        for (int col = 0; col < COLUMNS; col++) {
            for (int row = 0; row < ROWS; row++) {
                directions[col][row][0] = col < COLUMNS - WINNING_LENGTH;
                directions[col][row][1] = col < COLUMNS - WINNING_LENGTH && row < ROWS - WINNING_LENGTH;
                directions[col][row][2] = row < ROWS - WINNING_LENGTH;
                directions[col][row][3] = col > COLUMNS - WINNING_LENGTH && row < ROWS - WINNING_LENGTH;
                directions[col][row][4] = col > COLUMNS - WINNING_LENGTH;
                directions[col][row][5] = col > COLUMNS-WINNING_LENGTH && row > ROWS - WINNING_LENGTH;
                directions[col][row][6] = row > ROWS - WINNING_LENGTH;
                directions[col][row][7] = col < COLUMNS-WINNING_LENGTH && row > ROWS - WINNING_LENGTH;
            }
        }
    }

    private static void setHashValues() {
        HASH_VALUES = new long[COLUMNS][ROWS];
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                HASH_VALUES[i][j] = (long) PRIMES[i] * PRIMES[COLUMNS+j];
            }
        }
    }


    public static void init(boolean debug) {
        setDirections();
        setHashValues();
        DEBUG = debug;
    }

    public static class ConsoleColors {
        // Reset
        public static final String RESET = "\033[0m";  // Text Reset
        public static final String RED_BOLD = "\033[1;31m";    // RED
        public static final String YELLOW_BOLD = "\033[1;33m"; // YELLOW
        public static final String WHITE_BACKGROUND = "\033[47m";  // WHITE

    }

}
