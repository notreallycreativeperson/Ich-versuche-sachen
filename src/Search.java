public class Search {

    final EvalHandler eval;
    final MiniMax minimax;
    final int depth;

    Search(EvalHandler eval, int depth) {
        this.depth = depth;
        this.eval=eval;
        minimax = new MiniMax(eval);
    }

    public int getBestMove(Bord bord){
        return minimax.miniMax(bord.getTiles(),
                depth,
                Integer.MIN_VALUE,
                Integer.MAX_VALUE,
                bord.isMaxTurn,
                false);
    }




}
