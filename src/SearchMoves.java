public class SearchMoves {

    EvalHandler eval;
    MiniMax minimax;

    SearchMoves(EvalHandler eval){
        this.eval=eval;
        minimax = new MiniMax(eval);
    }

    public int getBestMove(Bord bord){
        return minimax.minimax();
    }


}
