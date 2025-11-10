public class Tournament {

    int numberOfGames=0;
    Player[] players;

    public void versus(Player player1,Player player2) {
        Game game = new Game(false);
        game.play(player1, player2);
        game.play(player2, player1);
        numberOfGames++;
        System.out.println("Game " + numberOfGames + " finished");
    }

    public void execute() {
        for (int i = 0; i < players.length; i++) {
            for (int j = i+1; j < players.length ; j++) {
                versus(players[i],players[j]);
            }
        }
    }

    public void printResults() {
        System.out.println();
        System.out.println("------------------------------------");
        System.out.println("PlayerData");

        for (Player player : players) {
            player.printPlayerData();
        }
    }

    Tournament(Player[] players) {
        this.players = players;
    }
}
