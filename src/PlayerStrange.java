public class PlayerStrange extends PlayerMinimax {

    final Evaluator[] evaluators = {new EvaluatorTiles()};
    final int[] weights = {1};

    public PlayerStrange() {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new SearchMoves(eval, 3);
    }
}
