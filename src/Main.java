public class Main {

    public static void main(String[] args) {
        Player[] players = new Player[55];
        int counter=0;
        for(int i=0;i<15;i++){
            players[i]=new PlayerCompetent(i+1);
            counter++;
        }
        for(int i=0;i<10;i++){
            players[counter]=new PlayerMinimax(i,true,false,false);
            counter++;
            players[counter]=new PlayerMinimax(i,true, false, true);
            counter++;
            players[counter]=new PlayerMinimax(i,false,true,false);
            counter++;
            players[counter]=new PlayerMinimax(i,true,true,false);
            counter++;
        }
        Tournament t = new Tournament(players);
        t.execute();
        t.printResults();
    }

}