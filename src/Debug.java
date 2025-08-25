public class Debug {

    public static void main(String[] args) {
        GameConstants.init(true);
        for (int i = 2; i < 30; i++) {
            System.out.println(i);
            if(new Game.EvE(new PlayerCompetent(i),new PlayerCompetent(i),true).game()==-1){
                break;
            }
        }
    }



}
