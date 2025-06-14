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

    public Search search;

    public static PlayerMinimax getBot() {
        return switch (Visual.getBot()) {
            case 1 -> new PlayerStrange();
            case 2 -> new PlayerStupid();
            case 4 -> new PlayerItr();
            default -> new PlayerCompetent(12);
        };
    }
    public int getMove(Bord bord) {
        return search.getBestMove(bord);
    }
}

class PlayerCompetent extends PlayerMinimax{

    final Evaluator[] evaluators = {};
    final int[] weights = {};

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

class PlayerItr extends PlayerCompetent {

    PlayerItr(){
        super(12);
    }

    @Override
    public int getMove(Bord bord) {
        return super.search.getBestMoveItr(bord);
    }
}

class PlayerStrange extends PlayerMinimax {

    final Evaluator[] evaluators = {new EvaluatorTiles()};
    final int[] weights = {1};

    public PlayerStrange() {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new Search(eval, 3);
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