public interface Player {
    int getMove(Bord bord);
}

class PlayerHuman implements Player {

    public final String name;

    public PlayerHuman() {
        name = Visual.getName();
    }

    public PlayerHuman(String name) {
        this.name=name;
    }




    @Override
    public int getMove(Bord bord) {
        int move=-1;
        while (move<0){
            move=Visual.getMove();
        }
        return move;
    }
}

abstract class PlayerMinimax implements Player {

    protected SearchMoves search;

    public static PlayerMinimax getBot() {
        return switch (Visual.getBot()) {
            case 1 -> new PlayerStrange();
            case 2 -> new PlayerStupid();
            default -> new PlayerCompetent();
        };
    }

    public int getMove(Bord bord){
        return search.getBestMove(bord);
    }

}

class PlayerCompetent extends PlayerMinimax{

    final Evaluator[] evaluators = {new EvaluatorLinesStrong(), new EvaluatorTiles()};
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

class PlayerStrange extends PlayerMinimax {

    final Evaluator[] evaluators = {new EvaluatorTiles()};
    final int[] weights = {1};

    public PlayerStrange() {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new SearchMoves(eval, 3);
    }
}

class PlayerStupid extends PlayerMinimax {
    int counter = 0;


    @Override
    public int getMove(Bord bord) {
        for (int i = 0; i < 7; i++) {
            addCounter();
            if (bord.getHumanRow(counter) >= 0) {
                return counter;
            }
        }
        return -1;
    }

    private void addCounter() {
        counter++;
        if (counter >= 7) {
            counter = 0;
        }
    }
}



