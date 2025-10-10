/**
 * Interface für Evaluatoren, die Spielpositionen bewerten.
 * Implementierungen analysieren verschiedene Aspekte der Position.
 *
 * @see EvaluatorTiles
 * @see AbstractEvaluatorLines
 * @see EvaluatorLinesSimple
 * @see EvaluatorLinesStrong
 */
public interface Evaluator {
    /**
     * Bewertet eine Spielposition basierend auf dem letzten Zug.
     *
     * @param tiles Das Spielbrett als 2D-Array
     * @param x     Die X-Koordinate (Spalte) des letzten Zuges
     * @param y     Die Y-Koordinate (Zeile) des letzten Zuges
     * @return Die Bewertung der Position (positiv für Spieler 1, negativ für Spieler 2)
     */
    int evaluate(int[][] tiles, int x, int y);
}

/**
 * Handler-Klasse, die mehrere {@link Evaluator Evaluatoren} kombiniert.
 * Wendet Gewichtungen auf die einzelnen Evaluatoren an und summiert die Ergebnisse.
 * Wird von {@link Search} und {@link MiniMax} verwendet.
 *
 * @param evaluators Array der zu verwendenden {@link Evaluator Evaluatoren}
 * @param weights    Gewichtungen für jeden {@link Evaluator}
 */
record EvalHandler(Evaluator[] evaluators, int[] weights) {

    /**
     * Bewertet die Position durch gewichtete Kombination aller {@link Evaluator Evaluatoren}.
     *
     * @param tiles Das Spielbrett als 2D-Array
     * @param x     Die X-Koordinate (Spalte) des letzten Zuges
     * @param y     Die Y-Koordinate (Zeile) des letzten Zuges
     * @return Die gewichtete Gesamtbewertung
     */
    public int evaluate(int[][] tiles, int x, int y) {
        int eval = 0;
        for (int i = 0; i < evaluators.length; i++) {
            eval += weights[i] * (evaluators[i].evaluate(tiles, x, y));
        }
        return eval;
    }
}

/**
 * Abstrakte Basisklasse für Evaluatoren, die Linien analysieren.
 * Implementiert {@link Evaluator} und prüft alle 8 Richtungen auf mögliche Viererreihen.
 * Verwendet {@link GameConstants#directions} für die Richtungsprüfung.
 *
 * @see EvaluatorLinesSimple
 * @see EvaluatorLinesStrong
 */
abstract class AbstractEvaluatorLines implements Evaluator {

    /**
     * Bewertet die Position durch Analyse aller möglichen Linien.
     * Ruft {@link #evaluateLine(int[][], int, int, int)} für jede gültige Richtung auf.
     *
     * @param tiles Das Spielbrett als 2D-Array
     * @param x     Die X-Koordinate (Spalte) des letzten Zuges
     * @param y     Die Y-Koordinate (Zeile) des letzten Zuges
     * @return Die Summe aller Linienbewertungen
     */
    @Override
    public int evaluate(int[][] tiles, int x, int y) {
        int eval = 0;
        for (int k = 0; k < 8; k++) {
            if (GameConstants.directions[x][y][k]) {
                eval += evaluateLine(tiles, x, y, k);
            }
        }
        return eval;
    }

    /**
     * Abstrakte Methode zur Bewertung einer einzelnen Linie.
     * Verwendet {@link GameConstants#INDICES} für die Richtungsberechnung.
     *
     * @param tiles Das Spielbrett als 2D-Array
     * @param i     Die X-Koordinate (Spalte) des Startpunkts
     * @param j     Die Y-Koordinate (Zeile) des Startpunkts
     * @param k     Der Richtungsindex aus {@link GameConstants#INDICES}
     * @return Die Bewertung dieser Linie
     */
    protected abstract int evaluateLine(int[][] tiles, int i, int j, int k);
}

/**
 * Einfacher Linien-Evaluator, der aufeinanderfolgende gleiche Steine bewertet.
 * Erweitert {@link AbstractEvaluatorLines}.
 * Verdoppelt die Bewertung für jeden weiteren gleichen Stein in der Linie.
 */
class EvaluatorLinesSimple extends AbstractEvaluatorLines {

    /**
     * Bewertet eine Linie durch exponentielles Wachstum bei gleichen Steinen.
     * Verwendet {@link GameConstants#INDICES} für die Positionsberechnung.
     *
     * @param tiles Das Spielbrett als 2D-Array
     * @param i     Die X-Koordinate (Spalte) des Startpunkts
     * @param j     Die Y-Koordinate (Zeile) des Startpunkts
     * @param k     Der Richtungsindex aus {@link GameConstants#INDICES}
     * @return Die Bewertung dieser Linie
     */
    @Override
    protected int evaluateLine(int[][] tiles, int i, int j, int k) {
        int evalTemp = tiles[i][j];
        for (int l = 0; l < 3; l++) {
            int ni = i + l * GameConstants.INDICES[k][0];
            int nj = j + l * GameConstants.INDICES[k][1];
            if (tiles[i][j] == tiles[ni][nj]) {
                evalTemp *= 2;
            }
        }
        return evalTemp;
    }
}

/**
 * Fortgeschrittener Linien-Evaluator mit Blockierungserkennung.
 * Erweitert {@link AbstractEvaluatorLines}.
 * Setzt die Bewertung auf 0, wenn gegnerische Steine die Linie blockieren.
 * Wird von {@link PlayerCompetent} verwendet.
 */
class EvaluatorLinesStrong extends AbstractEvaluatorLines {

    /**
     * Bewertet eine Linie unter Berücksichtigung von Blockierungen.
     * Setzt Bewertung auf 0, wenn ein gegnerischer Stein gefunden wird.
     * Verwendet {@link GameConstants#INDICES} für die Positionsberechnung.
     *
     * @param tiles Das Spielbrett als 2D-Array
     * @param i     Die X-Koordinate (Spalte) des Startpunkts
     * @param j     Die Y-Koordinate (Zeile) des Startpunkts
     * @param k     Der Richtungsindex aus {@link GameConstants#INDICES}
     * @return Die Bewertung dieser Linie oder 0 bei Blockierung
     */
    @Override
    protected int evaluateLine(int[][] tiles, int i, int j, int k) {
        int evalTemp = tiles[i][j];
        for (int l = 0; l < 3; l++) {
            int ni = i + l * GameConstants.INDICES[k][0];
            int nj = j + l * GameConstants.INDICES[k][1];
            if (tiles[i][j] == tiles[ni][nj]) {
                evalTemp *= 2;
            } else if (evalTemp * tiles[ni][nj] < 0) {
                evalTemp = 0;
                break;
            }
        }
        return evalTemp;
    }
}

/**
 * Positionsbasierter Evaluator, der Feldwerte verwendet.
 * Bewertet Positionen basierend auf strategischer Wichtigkeit (Mitte besser als Rand).
 * Fast nur am Anfang und bei niedrigen Tiefen relevant.
 * Wird von {@link PlayerCompetent} und {@link PlayerStrange} verwendet.
 */
class EvaluatorTiles implements Evaluator {
    /**
     * Wertetabelle für Spielfeldpositionen.
     * Höhere Werte in der Mitte entsprechen besseren Positionen.
     * Verwendet {@link GameConstants#COLUMNS} × {@link GameConstants#ROWS}.
     */
    private int[][] values = {
            {3, 13, 25, 28, 21, 17},
            {8, 25, 35, 40, 31, 26},
            {15, 34, 40, 45, 37, 30},
            {27, 39, 46, 50, 39, 36},
            {15, 34, 40, 45, 37, 30},
            {8, 25, 35, 40, 31, 26},
            {3, 13, 25, 28, 21, 17}
    };

    /**
     * Erstellt einen Evaluator mit benutzerdefinierten Feldwerten.
     *
     * @param values Benutzerdefinierte Wertetabelle
     */
    public EvaluatorTiles(int[][] values) {
        this.values = values;
    }

    /**
     * Erstellt einen Evaluator mit Standard-Feldwerten.
     */
    public EvaluatorTiles() {
    }

    /**
     * Bewertet die Position basierend auf strategischen Feldwerten.
     * Analysiert einen 5×5 Bereich um den letzten Zug herum.
     * Verwendet {@link GameConstants#COLUMNS} und {@link GameConstants#ROWS} für Grenzen.
     *
     * @param tiles Das Spielbrett als 2D-Array
     * @param x     Die X-Koordinate (Spalte) des letzten Zuges
     * @param y     Die Y-Koordinate (Zeile) des letzten Zuges
     * @return Die gewichtete Summe der Feldwerte
     */
    public int evaluate(int[][] tiles, int x, int y) {
        int eval = 0;
        for (int i = x - 2; i < x + 3; i++) {
            if (i < 0) continue;
            if (i > GameConstants.COLUMNS - 1) break;
            for (int j = y - 2; j < y + 3; j++) {
                if (j < 0) continue;
                if (j > GameConstants.ROWS - 1) break;
                eval += (tiles[i][j] * values[i][j]);
            }
        }
        return eval;
    }
}