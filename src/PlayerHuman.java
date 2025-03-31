public class PlayerHuman implements Player {

    public final String name;

    public PlayerHuman() {
        name = Visual.getName();
    }

    public PlayerHuman(String name) {
        this.name=name;
    }




    @Override
    public int getMove() {
        int move=-1;
        while (move<0){
            move=Visual.getMove();
        }
        return move;
    }
}
