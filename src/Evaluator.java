public interface Evaluator {
    int evaluate(int[][] tiles);
}

class EvalHandler {

    final Evaluator[] evaluators;
    final int[] weights;

    EvalHandler(Evaluator[] evaluators, int[] weights){
        this.evaluators=evaluators;
        this.weights=weights;
    }

    public int evaluate(int[][] tiles){
        int eval = 0;
        for (int i = 0; i < evaluators.length; i++) {
            eval+=weights[i]*(evaluators[i].evaluate(tiles));
        }
        return eval;
    }



}

class EvaluatorLinesSimple implements Evaluator {

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

class EvaluatorLinesStrong implements Evaluator {

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
                                } else if (evalTemp * tiles[i + l * Bord.INDICES[k][0]][j + l * Bord.INDICES[k][1]] < 0) {
                                    evalTemp = 0;
                                    break;
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

class EvaluatorTiles implements Evaluator {
    private int[][] values = {
            {3, 13, 25, 28, 21, 17},
            {8, 25, 35, 40, 31, 26},
            {15, 34, 40, 45, 37, 30},
            {27, 39, 46, 50, 39, 36},
            {15, 34, 40, 45, 37, 30},
            {8, 25, 35, 40, 31, 26},
            {3, 13, 25, 28, 21, 17}

    };

    public EvaluatorTiles(int[][] values) {
        this.values = values;
    }

    public EvaluatorTiles() {
    }

    public int evaluate(int[][] tiles){
        int eval = 0;
        for (int i = 0; i < tiles.length; i++) {
            for (int j = 0; j < tiles[0].length; j++) {
                if (tiles[i][j] != 0) {
                    eval += tiles[i][j] * values[i][j];
                }
            }
        }
        return eval;
    }


}