public class Main {

    public static void main(String[] args) {
        Startable startable = setMode();
        startable.start();
    }



    private static Startable setMode(){
        return switch (Visual.getMode()) {
            case 1 -> new GameFast();
            case 2 -> new GamePvP();
            case 3 -> new GameEvE();
            case 4 -> new TournamentHandler();
            default -> new GamePvE();
        };
    }
}