public class PlayerCompetent extends PlayerMinimax{

    final Evaluator[] evaluators = {new EvaluatorLinesStrong(), new EvluatorTiles()};
    final int[] weights = {2, 1};

    PlayerCompetent(){
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new SearchMoves(eval, 6);
    }

    @SuppressWarnings("SameParameterValue")
    PlayerCompetent(int depth) {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new SearchMoves(eval, depth);
    }
}
