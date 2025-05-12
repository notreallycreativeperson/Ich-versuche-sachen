public class Main {

    public static void main(String[] args) {
        Startable startable = setMode();
        startable.start();
    }

    private static Startable setMode(){
        return switch (Visual.getMode()) {
            case 1 -> new Game.Fast();
            case 2 -> new Game.PvP();
            case 3 -> new Game.EvE();
            case 4 -> new TournamentHandler();
            default -> new Game.PvE();
        };
    }
}