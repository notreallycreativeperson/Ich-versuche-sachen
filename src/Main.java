public class Main {

    public static void main(String[] args) {
        Player[] players = new Player[10];
        players[0]=new PlayerCompetent(9);
        players[1]= new PlayerMinimax(9,true,false,true);
        players[2]= new PlayerMinimax(9,false,true,false);
        players[3]= new PlayerMinimax(9,true,true,false);
        players[4]= new PlayerMinimax(9,true,false,false);
        players[5]= new PlayerMinimax(9,false,false,false);
        players[8]= new PlayerMinimax(9,true,true,true);
        players[7]= new PlayerCompetent(10);
        players[6]= new PlayerMinimax(9,true,true,true);
        players[9]= new PlayerCompetent(10);
        Tournament t = new Tournament(players);
        t.execute();
        t.printResults();
    }
}