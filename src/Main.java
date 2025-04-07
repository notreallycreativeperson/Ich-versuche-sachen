public class Main {

    public static void main(String[] args) {
        Startableable startableable =setMode();
        startableable.start();
    }

    private static Startableable setMode(){
        return switch (Visual.getMode()) {
            case 1 -> new GameFast();
            case 2 -> new GamePvP();
            case 3 -> new GameEvE();
            case 4 -> new TournamentHandler();
            case 0 -> new _1GameDebug();
            default -> new GamePvE();
        };
    }
}