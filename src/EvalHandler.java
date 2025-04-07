public class EvalHandler {

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

