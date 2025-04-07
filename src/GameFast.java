public class GameFast extends GamePvE {
    public GameFast() {
        super(new PlayerHuman("Spieler 1"), new PlayerCompetent(6), new Bord(true));
    }
}
