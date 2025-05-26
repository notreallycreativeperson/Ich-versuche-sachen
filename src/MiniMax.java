public class MiniMax {

    final EvalHandler eval;

    MiniMax(EvalHandler eval){
        this.eval=eval;
    }

    public int miniMax(Bord bord, int depth, int alpha, int beta, boolean isScore) {
        if (depth == 0) {
            return eval.evaluate(bord.getTiles());
        }
        if (bord.isWinningMove()){
            if (isScore){
                return (bord.isMaxTurn?Integer.MIN_VALUE:Integer.MAX_VALUE);
            }
        }


        int bestScore;
        int bestMove = -1;

        bestScore = (bord.isMaxTurn ? -1000000 : 100000);
            for (int i :GameConstants.EXPLORE_ORDER) {
                if (bord.getTiles()[i][5] == 0) {
                    bord.move(i);
                    int score = miniMax(bord.clone(), depth - 1, alpha, beta, true);
                    bord.unMove();
                    if (bord.isMaxTurn) {
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
