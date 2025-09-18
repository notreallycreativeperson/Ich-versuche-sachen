import java.util.HashMap;

public class Search {

    final EvalHandler eval;
    final MiniMaxBest minimax;
    static int[] scores = new int[7];
    HashMap<Long,Entry> transPositionTable = new HashMap<>();
    final int depth;

    Search(EvalHandler eval, int depth) {
        this.depth = depth;
        this.eval=eval;

        minimax = new MiniMaxBest(eval);
    }

    public int getBestMove(Bord bord){
        int i = minimax.miniMax(bord.getTiles(), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, bord.isMaxTurn, false, 0, 0, transPositionTable,0);
        transPositionTable.clear();
        return i;
    }

    public static void logScore(int move, int score){
        scores[move]=score;
    }

    private static void sortOrder(){
        for (int i = 1; i < 6; i++) {
            for (int j = i; j < 7; j++) {
                if (scores[GameConstants.EXPLORE_ORDER[j-1]] < scores[GameConstants.EXPLORE_ORDER[j]]) {
                    int temp =GameConstants.EXPLORE_ORDER[j-1];
                    GameConstants.EXPLORE_ORDER[j-1]=GameConstants.EXPLORE_ORDER[j];
                    GameConstants.EXPLORE_ORDER[j]=temp;
                }

            }

        }
    }
}
