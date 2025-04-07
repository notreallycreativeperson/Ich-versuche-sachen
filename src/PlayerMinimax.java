abstract class PlayerMinimax extends Player {

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
