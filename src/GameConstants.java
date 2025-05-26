public class GameConstants {
    // Constants for board dimensions
    public static final int COLUMNS = 7;
    public static final int ROWS = 6;
    public static final int MAX_MOVES = COLUMNS * ROWS;
    public static final int WINNING_LENGTH = 4;
    public static final int[] EXPLORE_ORDER = {3,4,2,5,1,6,0};

    // Player constants
    public static final int EMPTY = 0;
    public static final int PLAYER_MAX = 1;
    public static final int PLAYER_MIN = -1;
    public static final int[][] INDICES = {
            {1, 0},
            {1, 1},
            {0, 1},
            {-1, 1}
    };

    static final boolean[][][] directions = new boolean[COLUMNS][ROWS][INDICES.length];

    protected static void setDirections() {
        // Only initialize if not already set
        if (directions[0][0][0]) return;
        for (int i = 0; i < COLUMNS; i++) {
            for (int j = 0; j < ROWS; j++) {
                directions[i][j][0] = i < COLUMNS - WINNING_LENGTH + 1;
                directions[i][j][1] = (i < COLUMNS - WINNING_LENGTH + 1 && j < ROWS - WINNING_LENGTH + 1);
                directions[i][j][2] = j < ROWS - WINNING_LENGTH + 1;
                directions[i][j][3] = (i > WINNING_LENGTH - 2 && j < ROWS - WINNING_LENGTH + 1);
            }
        }
    }


    public static void init() {
        setDirections();
    }

}
