public interface Player {
    int getMove(Bord bord);
}

/**
 * Record-Klasse für menschliche Spieler.
 * Implementiert {@link Player} und verwendet {@link Visual} für die Eingabe.
 *
 * @param name Der Name des Spielers
 */
record PlayerHuman(String name) implements Player {

    /**
     * Standardkonstruktor, der den Namen über {@link Visual#getName()} abfragt.
     */
    public PlayerHuman() {
        this(Visual.getName());
    }


    /**
     * Holt den nächsten Zug von einem menschlichen Spieler über {@link Visual#getMove()}.
     * Wiederholt die Abfrage, bis ein gültiger Zug eingegeben wurde.
     *
     * @param bord Das aktuelle {@link Bord Spielbrett}
     * @return Die Spaltennummer des gewählten Zuges
     */
    @Override
    public int getMove(Bord bord) {
        int move = -1;
        while (move < 0) {
            move = Visual.getMove();
        }
        return move;
    }
}

/**
 * Abstrakte Basisklasse für KI-gesteuerte Spieler (nicht-menschliche Spieler).
 * Verwendet eine {@link Search}-Instanz für die Zugfindung mittels {@link MiniMax}-Algorithmus.
 *
 * @see PlayerCompetent
 * @see PlayerStrange
 * @see PlayerBasic
 */
abstract class PlayerInhumane implements Player {

    /**
     * Die Suchinstanz für die KI-Zugberechnung
     */
    public Search search;

    /**
     * Factory-Methode, die einen Bot basierend auf Benutzereingabe erstellt.
     * Verwendet {@link Visual#getBot()} für die Auswahl.
     *
     * @return Eine Instanz von {@link PlayerStrange} oder {@link PlayerCompetent}
     */
    public static PlayerInhumane getBot() {
        return switch (Visual.getBot()) {
            case 1 -> new PlayerStrange();
            default -> new PlayerCompetent(20);
        };
    }

    /**
     * Ermittelt den besten Zug mittels {@link Search#getBestMove(Bord)}.
     *
     * @param bord Das aktuelle {@link Bord Spielbrett}
     * @return Die Spaltennummer des besten Zuges
     */
    public int getMove(Bord bord) {
        return search.getBestMove(bord);
    }
}

/**
 * Kompetenter KI-Spieler mit fortgeschrittener Evaluation.
 * Verwendet {@link EvaluatorTiles} und {@link EvaluatorLinesStrong} für die Stellungsbewertung.
 * Erweitert {@link PlayerInhumane}.
 */
class PlayerCompetent extends PlayerInhumane {

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
    PlayerCompetent() {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new Search(eval, 20);
    }

    /**
     * Erstellt einen kompetenten Spieler mit angegebener Suchtiefe.
     *
     * @param depth Die Suchtiefe für den {@link MiniMax}-Algorithmus
     */
    @SuppressWarnings("SameParameterValue")
    PlayerCompetent(int depth) {
        EvalHandler eval = new EvalHandler(evaluators, weights);
        search = new Search(eval, depth);
    }


}

/**
 * Ein einfacher KI-Spieler mit unkonventioneller Strategie.
 * Verwendet nur {@link EvaluatorTiles} mit geringer Suchtiefe.
 * Erweitert {@link PlayerInhumane}.
 */
class PlayerStrange extends PlayerInhumane {
    final Evaluator[] evaluators = {new EvaluatorTiles()};

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
}


/**
 * Basis-Implementierung eines KI-Spielers.
 * Erweitert {@link PlayerInhumane}, derzeit ohne zusätzliche Implementierung.
 */
class PlayerBasic extends PlayerInhumane {

}