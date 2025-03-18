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
        return 0;
    }
}
