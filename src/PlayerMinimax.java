abstract class PlayerMinimax implements Player {

    private SearchMoves search;

    public int getMove(Bord bord){
        return search.getBestMove(bord);
    };

}
