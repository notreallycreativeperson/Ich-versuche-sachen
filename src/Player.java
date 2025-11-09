public interface Player {
    int getMove(Board board,GameData gameData);
    /** Human-readable name for logging and test display. */
    PlayerData getPlayerData();

    String getPlayerName();

    void printPlayerData();
}

/**
 * Record-Klasse für menschliche Spieler.
 * Implementiert {@link Player} und verwendet {@link Visual} für die Eingabe.
 *
 */
class PlayerHuman implements Player {

    private PlayerData playerData;
    private String playerName;
    /**
     * Standardkonstruktor, der den Namen über {@link Visual#getName()} abfragt.
     */
    public PlayerHuman() {
        Visual.getName();
    }

    /**
     * Holt den nächsten Zug von einem menschlichen Spieler über {@link Visual#getMove()}.
     * Wiederholt die Abfrage, bis ein gültiger Zug eingegeben wurde.
     *
     * @param board Das aktuelle {@link Board Spielbrett}
     * @return Die Spaltennummer des gewählten Zuges
     */
    @Override
    public int getMove(Board board,GameData gameData) {
        int move = -1;
        while (move < 0) {
            move = Visual.getMove();
        }
        return move;
    }


    public PlayerData getPlayerData() {
        return playerData;
    }

    public String getPlayerName(){
        return playerName;
    }

    public void printPlayerData() {
        Visual.printPlayerData(playerName,playerData);
    }
}

/**
 * Abstrakte Basisklasse für KI-gesteuerte Spieler (nicht-menschliche Spieler).
 * Verwendet eine {@link Search}-Instanz für die Zugfindung mittels {@link MiniMax}-Algorithmus.
 *
 * @see PlayerMinimax
 * @see PlayerStrange
 */
abstract class PlayerInhumane implements Player {

    private PlayerData playerData;
    /**
     * Die Suchinstanz für die KI-Zugberechnung
     */
    public Search search;


    public PlayerInhumane() {
        playerData=new PlayerData();
    }
    /**
     * Factory-Methode, die einen Bot basierend auf Benutzereingabe erstellt.
     * Verwendet {@link Visual#getBot()} für die Auswahl.
     *
     * @return Eine Instanz von {@link PlayerStrange} oder {@link PlayerMinimax}
     */
    public static PlayerInhumane getBot() {
        return switch (Visual.getBot()) {
            case 1 -> new PlayerStrange();
            default -> new PlayerMinimax(20);
        };
    }


    /**
     * Ermittelt den besten Zug mittels {@link Search#getBestMove(Board, GameData)} )}.
     *
     * @param board Das aktuelle {@link Board Spielbrett}
     * @return Die Spaltennummer des besten Zuges
     */
    public int getMove(Board board,GameData gameData) {
        return search.getBestMove(board, gameData);
    }

    public PlayerData getPlayerData() {
        return playerData;
    }
}

/**
 * Kompetenter KI-Spieler mit fortgeschrittener Evaluation.
 * Verwendet {@link EvaluatorTiles} und {@link EvaluatorLinesStrong} für die Stellungsbewertung.
 * Erweitert {@link PlayerInhumane}.
 */
class PlayerMinimax extends PlayerInhumane {

    String name;

    /**
     * Array der verwendeten {@link Evaluator Evaluatoren}
     */
    final Evaluator[] evaluators = {new EvaluatorTiles(), new EvaluatorLinesStrong()};

    /**
     * Gewichtungen für die einzelnen {@link Evaluator Evaluatoren}
     */
    final int[] weights = {2, 1};

    /**
     * Erstellt einen kompetenten Spieler mit Standard-Suchtiefe 20.
     * Initialisiert {@link Search} mit {@link EvalHandler}.
     */


    PlayerMinimax(EvalHandler eval, int depth, boolean abp, boolean tpt, boolean oso){
        search = new Search(eval, depth, abp, tpt, oso);
        name="Minimax "+"depth="+depth;
        if (abp){
            name+=", abp";
        }
        if (tpt){
            name+=", tpt";
        }
        if (oso){
            name+=", oso";
        }
    }

    PlayerMinimax(int depth, boolean abp, boolean tpt, boolean oso){
        final Evaluator[] evaluators = {new EvaluatorTiles(), new EvaluatorLinesStrong()};
        final int[] weights = {2, 1};
        final EvalHandler eval=new EvalHandler(evaluators,weights);
        search = new Search(eval, depth, abp, tpt, oso);
        name="Minimax "+"depth="+depth;
        if (abp){
            name+=", abp";
        }
        if (tpt){
            name+=", tpt";
        }
        if (oso){
            name+=", oso";
        }
    }



    /**
     * Erstellt einen kompetenten Spieler mit angegebener Suchtiefe.
     *
     * @param depth Die Suchtiefe für den {@link MiniMax}-Algorithmus
     */
    @SuppressWarnings("SameParameterValue")
    PlayerMinimax(int depth) {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new Search(eval, depth);
        name="Minimax "+"depth="+depth+", abp"+", tpt"+", oso";
    }

    public String getPlayerName() {
        return name;
    }

    public void printPlayerData() {
        Visual.printPlayerData(name,this.getPlayerData());
    }

    @Override
    public PlayerData getPlayerData() {
        return super.getPlayerData();
    }
}

class PlayerCompetent extends PlayerMinimax {
    PlayerCompetent(int depth) {
        super(depth);
    }

    @Override
    public String getPlayerName() {
        return super.getPlayerName();
    }
}

/**
 * Ein einfacher KI-Spieler mit unkonventioneller Strategie.
 * Verwendet nur {@link EvaluatorTiles} mit geringer Suchtiefe.
 * Erweitert {@link PlayerInhumane}.
 */
class PlayerStrange extends PlayerInhumane {
    final Evaluator[] evaluators = {new EvaluatorTiles()};
    final String name="PlayerStrage";
    /**
     * Gewichtung für den {@link Evaluator}
     */
    final int[] weights = {1};

    /**
     * Erstellt einen "Strange"-Spieler mit Suchtiefe 3.
     * Initialisiert {@link Search} mit {@link EvalHandler}.
     */
    public PlayerStrange() {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new Search(eval, 3);
    }

    public String getPlayerName() {
        return name;
    }

    public void printPlayerData() {
        Visual.printPlayerData(name,this.getPlayerData());
    }

    @Override
    public PlayerData getPlayerData() {
        return super.getPlayerData();
    }
}