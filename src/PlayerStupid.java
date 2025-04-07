public class PlayerStupid extends PlayerMinimax {
    int counter = 0;


    @Override
    public int getMove(Bord bord) {
        for (int i = 0; i < 7; i++) {
            addCounter();
            if (bord.getHumanRow(counter) >= 0) {
                return counter;
            }
        }
        return -1;
    }

    private void addCounter() {
        counter++;
        if (counter >= 7) {
            counter = 0;
        }
    }
}
