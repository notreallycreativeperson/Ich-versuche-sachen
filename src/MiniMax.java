public class MiniMax {

    EvalHandler eval;



    MiniMax(EvalHandler eval){
        this.eval=eval;
    }

    public int miniMax(int[][] tiles, int depth, int alpha, int beta, boolean isMaxTurn, boolean isScore) {
        if (depth == 0) {
            return eval.evaluate(tiles);
        }
        if (Bord.isWon(tiles)){
            if (isScore){
                return (isMaxTurn?Integer.MIN_VALUE:Integer.MAX_VALUE);
            }
        }


        int bestScore;
        int bestMove = -1;

        bestScore = (isMaxTurn?Integer.MIN_VALUE:Integer.MAX_VALUE);
            for (int i = 0; i < 7; i++) {
                if (tiles[i][5] == 0) {
                    int row = Bord.getRow(tiles, i);
                    tiles[i][row] = 1;
                    int score = miniMax(tiles, depth - 1, alpha, beta, isMaxTurn==false, true);
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
