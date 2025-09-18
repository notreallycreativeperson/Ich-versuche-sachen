import java.util.HashMap;

public class MiniMax {

    private boolean abp; //uses Alpha beta Pruning
    private boolean tpt; //uses transposition table
    final EvalHandler eval;

    MiniMax(EvalHandler eval, boolean abp, boolean tpt) {
        this.eval=eval;
        this.abp=abp;
        this.tpt=tpt;
    }

    public int miniMax(int[][] tiles, int depth, int alpha, int beta, boolean isMaxTurn, boolean isScore, int lastmoveX, int lastmoveY, HashMap<Long,Entry> transPositionTable,long hashLast) {
        if (Bord.isWinningMove(lastmoveX, lastmoveY, tiles) && isScore) {
            return (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
        }
        if (tpt){
            if(transPositionTable.containsKey(hashLast)) {
                Entry entry = transPositionTable.get(hashLast);
                if(entry.depth() ==depth+1) {
                    return entry.score();
                }
            }
        }
        if (depth == 0) {
            return eval.evaluate(tiles,lastmoveX,lastmoveY);
        }


        int bestScore;
        int bestMove = -1;

        bestScore = (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            for (int i :GameConstants.EXPLORE_ORDER) {
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
