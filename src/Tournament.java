public class Tournament {

    Player[] players;

    public GameData versus(Player player1,Player player2) {
        Game game = new Game(false);
        GameData gameData = new GameData(player1,player2);
        GameData gameData1;
        GameData gameData2;
        gameData1=game.play(player1, player2);
        gameData2=game.play(player2, player1);
        return new GameData(gameData1,gameData2);
    }

    public void execute() {
        for (int i = 0; i < players.length; i++) {
            for (int j = i+1; j < players.length ; j++) {
                GameData gameData = versus(players[i],players[j]);
                Visual.printGame(gameData);
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
