public class Main {

    public static void main(String[] args) {
        Player[] players = new Player[24];
        int counter=0;
        for(int i=0;i<8;i++){
            players[counter]=new PlayerMinimax(i+1,false,false,false);
            counter++;
            players[counter]=new PlayerMinimax(i+1,true,false,false);
            counter++;
            players[counter]=new PlayerMinimax(i+1,true, false, true);
            counter++;
        }
        Tournament t = new Tournament(players);
        t.execute();
        t.printResults();
    }

}