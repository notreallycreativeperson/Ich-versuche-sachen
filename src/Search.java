import java.util.HashMap;

public class Search {

    final EvalHandler eval;
    final MiniMax minimax;
    static int[] scores = new int[7];
    static int[] exploreOrder = new int[]{3,4,2,5,1,6,0};
    HashMap<Long,Entry> transPositionTable = new HashMap<>();
    final int depth;

    Search(EvalHandler eval, int depth) {
        this.depth = depth;
        this.eval=eval;

        minimax = new MiniMax(eval);
    }

    public int getBestMove(Bord bord){
        int i = minimax.miniMax(bord.getTiles(), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, bord.isMaxTurn, false, 0, 0, transPositionTable,0);
        transPositionTable.clear();
        return i;
    }

    public int getBestMoveItr(Bord bord) {
        int move = 3;
        for (int i = 1; i <= depth; i++){
            move = minimax.miniMax(bord.getTiles(), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, bord.isMaxTurn, false,0,0,transPositionTable,0);
            sortOrder();
        }
        transPositionTable.clear();
        return move;
    }

    public static void logScore(int move, int score){
        scores[move]=score;
    }

    private static void sortOrder(){
        for (int i = 1; i < 6; i++) {
            for (int j = i; j < 7; j++) {
                if (scores[exploreOrder[j-1]] < scores[exploreOrder[j]]) {
                    int temp =exploreOrder[j-1];
                    exploreOrder[j-1]=exploreOrder[j];
                    exploreOrder[j]=temp;
                }

            }

        }
    }
}
