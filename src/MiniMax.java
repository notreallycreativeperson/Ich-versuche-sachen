public class MiniMax {

    final EvalHandler eval;

    MiniMax(EvalHandler eval){
        this.eval=eval;
    }

    public int miniMax(int[][] tiles, int depth, int alpha, int beta,boolean isMaxTurn,int[][] lastMoves, boolean isScore) {
        if (depth == 0) {
            return eval.evaluate(tiles);
        }
        if (Bord.isWinningMove(lastMoves[depth+1][0],lastMoves[depth+1][1],tiles)){
            if (isScore){
                return (isMaxTurn?Integer.MIN_VALUE:Integer.MAX_VALUE);
            }
        }


        int bestScore;
        int bestMove = -1;

        bestScore = (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
            for (int i :GameConstants.EXPLORE_ORDER) {
                if (tiles[i][5] == 0) {
                    lastMoves[depth][0] = i;
                    lastMoves[depth][1] = Bord.getRow(tiles, i);
                    tiles[i][lastMoves[depth][1]] = (isMaxTurn ? GameConstants.PLAYER_MAX : GameConstants.PLAYER_MIN);
                    int score = miniMax(tiles, depth - 1, alpha, beta, !isMaxTurn, lastMoves, true);
                    tiles[i][lastMoves[depth][1]] = 0;
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
