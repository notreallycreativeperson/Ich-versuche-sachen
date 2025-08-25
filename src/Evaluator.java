public interface Evaluator {
    int evaluate(int[][] tiles,int x,int y);
}

record EvalHandler(Evaluator[] evaluators, int[] weights) {

    public int evaluate(int[][] tiles,int x,int y) {
        int eval = 0;
        for (int i = 0; i < evaluators.length; i++) {
            eval += weights[i] * (evaluators[i].evaluate(tiles,x,y));
        }
        return eval;
    }
}

// Abstract base class for line evaluators
abstract class AbstractEvaluatorLines implements Evaluator {

    @Override
    public int evaluate(int[][] tiles,int x,int y) {
        int eval = 0;
        for (int k = 0; k < 8; k++) {
            if (GameConstants.directions[x][y][k]) {
                eval += evaluateLine(tiles, x, y, k);
            }
        }
        return eval;
    }

    // Abstract method for the line evaluation logic that differs
    protected abstract int evaluateLine(int[][] tiles, int i, int j, int k);
}

class EvaluatorLinesSimple extends AbstractEvaluatorLines {

    @Override
    protected int evaluateLine(int[][] tiles, int i, int j, int k) {
        int evalTemp = tiles[i][j];
        for (int l = 0; l < 3; l++) {
            int ni = i + l * GameConstants.INDICES[k][0];
            int nj = j + l * GameConstants.INDICES[k][1];
            if (tiles[i][j] == tiles[ni][nj]) {
                evalTemp *= 2;
            }
        }
        return evalTemp;
    }
}

class EvaluatorLinesStrong extends AbstractEvaluatorLines {

    @Override
    protected int evaluateLine(int[][] tiles, int i, int j, int k) {
        int evalTemp = tiles[i][j];
        for (int l = 0; l < 3; l++) {
            int ni = i + l * GameConstants.INDICES[k][0];
            int nj = j + l * GameConstants.INDICES[k][1];
            if (tiles[i][j] == tiles[ni][nj]) {
                evalTemp *= 2;
            } else if (evalTemp * tiles[ni][nj] < 0) {
                evalTemp = 0;
                break;
            }
        }
        return evalTemp;
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

    public int evaluate(int[][] tiles,int x,int y) {
        int eval = 0;
        for (int i = x-2; i < x+3; i++) {
            if (i < 0) continue;
            if (i > GameConstants.COLUMNS - 1) break;
            for (int j = y - 2; j < y + 3; j++){
                if (j < 0) continue;
                if (j > GameConstants.ROWS - 1) break;
                    eval += (tiles[i][j] * values[i][j]);
            }
        }
        return eval;
    }
}
