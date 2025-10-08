public interface Player {
    int getMove(Bord bord);
}

record PlayerHuman(String name) implements Player {

    public PlayerHuman() {
        this(Visual.getName());
    }


    @Override
    public int getMove(Bord bord) {
        int move = -1;
        while (move < 0) {
            move = Visual.getMove();
        }
        return move;
    }
}

abstract class PlayerInhumane implements Player {

    public Search search;

    public static PlayerInhumane getBot() {
        return switch (Visual.getBot()) {
            case 1 -> new PlayerStrange();
            default -> new PlayerCompetent(20);
        };
    }
    public int getMove(Bord bord) {
        return search.getBestMove(bord);
    }
}

class PlayerCompetent extends PlayerInhumane {

    final Evaluator[] evaluators = {new EvaluatorTiles(),new EvaluatorLinesSimple()};
    final int[] weights = {2,1};

    PlayerCompetent() {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new Search(eval, 20);
    }

    @SuppressWarnings("SameParameterValue")
    PlayerCompetent(int depth) {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new Search(eval, depth);
    }


}

class PlayerStrange extends PlayerInhumane {

    final Evaluator[] evaluators = {new EvaluatorTiles()};
    final int[] weights = {1};

    public PlayerStrange() {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new Search(eval, 3);
    }
}



class PlayerBasic extends PlayerInhumane {

}