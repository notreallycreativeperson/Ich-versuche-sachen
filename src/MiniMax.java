import java.util.HashMap;

public class MiniMax {

    final EvalHandler eval;

    MiniMax(EvalHandler eval){
        this.eval=eval;
    }

    public int miniMax(int[][] tiles, int depth, int alpha, int beta, boolean isMaxTurn, boolean isScore, int lastmoveX, int lastmoveY, HashMap<Long,Entry> transPositionTable,long hashLast) {
        Entry entry=transPositionTable.get(hashLast);
        if (entry != null && entry.depth == depth) {
            return entry.score;
        }
        if (Bord.isWinningMove(lastmoveX, lastmoveY, tiles) && isScore) {
            return (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
        }
        if (depth == 0) {
            return eval.evaluate(tiles,lastmoveX,lastmoveY);
        }


        int bestScore;
        int bestMove = -1;

        bestScore = (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            for (int i :Search.exploreOrder) {
                if (tiles[i][5] == 0) {
                    int row = Bord.getRow(tiles, i);
                    tiles[i][row] = (isMaxTurn ? GameConstants.PLAYER_MAX : GameConstants.PLAYER_MIN);
                    long hash=Bord.hash(tiles);
                    int score = miniMax(tiles, depth - 1, alpha, beta, !isMaxTurn, true,i,row,transPositionTable,hash);
                    transPositionTable.put(hash,new Entry(score,depth));
                    if(!isScore){
                        Search.logScore(i,score);
                    }
                    tiles[i][row] = 0;
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
                        break;
                    }
                }
            }
        return (isScore ? bestScore : bestMove);
    }

}
