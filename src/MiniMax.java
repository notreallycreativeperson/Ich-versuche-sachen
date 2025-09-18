import java.util.HashMap;

public class MiniMax {

    private final boolean abp; //uses Alpha beta Pruning
    private final boolean tpt;//uses transposition table
    private final boolean oso; //uses optimized Search Order
    final EvalHandler eval;

    MiniMax(EvalHandler eval, boolean abp, boolean tpt, boolean oso) {
        this.eval=eval;
        this.abp=abp;
        this.tpt=tpt;
        this.oso=oso;
    }

    public int miniMax(int[][] tiles, int depth, int alpha, int beta, boolean isMaxTurn, boolean isScore, int lastmoveX, int lastmoveY, HashMap<Long,Entry> transPositionTable,long hashlast) {
        if (Bord.isWinningMove(lastmoveX, lastmoveY, tiles) && isScore) {
            return (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
        }
        if (depth == 0) {
            return eval.evaluate(tiles,lastmoveX,lastmoveY);
        }
        if (tpt){
            if(transPositionTable.containsKey(hashlast)) {
                Entry entry = transPositionTable.get(hashlast);
                if(entry.depth() ==depth+1) {
                    Info.logPrune(depth+1);
                    return entry.score();
                }
            }
        }

        int bestScore;
        int bestMove = -1;

        bestScore = (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            for (int i :(oso?GameConstants.EXPLORE_ORDER_OPTIMIZED:GameConstants.EXPLORE_ORDER_BASIC)) {
                if (tiles[i][5] == 0) {
                    int row = Bord.getRow(tiles, i);
                    tiles[i][row] = (isMaxTurn ? GameConstants.PLAYER_MAX : GameConstants.PLAYER_MIN);
                    long hash=Bord.hash(tiles);
                    int score = miniMax(tiles, depth - 1, alpha, beta, !isMaxTurn, true,i,row,transPositionTable,hash);
                    if(tpt){
                        transPositionTable.put(hash,new Entry(score,depth));
                    }
                    tiles[i][row] = 0;
                    if(abp){
                        if (isMaxTurn) {
                            if (score > bestScore) {
                                bestScore = score;
                                bestMove = i;
                            }
                            alpha = Math.max(alpha, score);
                        }else {
                            if (score < bestScore) {
                                bestScore = score;
                                bestMove = i;
                            }
                            beta = Math.min(beta, score);
                        }
                        if (beta <= alpha) {
                            Info.logPrune(20);
                            break;
                        }
                    }
                }
            }
        return (isScore ? bestScore : bestMove);
    }

}
