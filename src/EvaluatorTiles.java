public class EvaluatorTiles implements Evaluator {
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
