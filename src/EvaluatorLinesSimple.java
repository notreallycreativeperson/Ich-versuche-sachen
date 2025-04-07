public class EvaluatorLinesSimple implements Evaluator {

    public int evaluate(int[][] tiles) {
        int eval = 0;
        int evalTemp;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != 0) {
                    for (int k = 0; k < 4; k++) {
                        evalTemp = tiles[i][j];
                        if (Bord.directions[i][j][k]) {
                            for (int l = 0; l < 3; l++) {
                                if (tiles[i][j] == tiles[i + l * Bord.INDICES[k][0]][j + l * Bord.INDICES[k][1]]) {
                                    evalTemp *= 2;
                                }
                            }
                        }
                        eval += evalTemp;
                    }
                }
            }
        }
        return eval;
    }
}
