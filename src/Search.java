import java.util.HashMap;

public class Search {

    static int[] scores = new int[7];
    final EvalHandler eval;
    final MiniMax minimax;
    final int depth;
    HashMap<Long, Entry> transPositionTable = new HashMap<>();

    Search(EvalHandler eval, int depth) {
        this(eval, depth, true, true, true);
    }

    Search(EvalHandler eval, int depth, boolean abp, boolean tpt, boolean oso) {
        this.depth = depth;
        this.eval = eval;

        minimax = new MiniMax(eval, abp, tpt, oso);
    }

    public int getBestMove(Board board, PlayerData playerData) {
        int i = minimax.miniMax(board.getTiles(), depth, Integer.MIN_VALUE, Integer.MAX_VALUE, board.isMaxTurn, false, 0, 0, transPositionTable, 2, playerData);
        transPositionTable.clear();
        return i;
    }
}
