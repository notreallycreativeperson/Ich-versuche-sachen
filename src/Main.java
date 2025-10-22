public class Main {

    public static void main(String[] args) {
        GameConstants.init(false);
        Game game = setMode();
        game.start();
    }

    private static Game setMode() {
        return switch (Visual.getMode()) {
            case 1 -> new Game.Fast();
            case 2 -> new Game.PvP();
            case 3 -> new Game.EvE();
            case 4 -> new Game.EvE(new PlayerCompetent(11), new PlayerCompetent(11), true);
            default -> new Game.PvE();
        };
    }
}