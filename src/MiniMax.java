import java.util.HashMap;

/**
 * Implementierung des MiniMax-Algorithmus mit verschiedenen Optimierungen.
 * Unterstützt Alpha-Beta-Pruning, Transpositionstabellen und optimierte Zugreihenfolge.
 * Wird von {@link Search} verwendet, um den besten Zug zu finden.
 */
public class MiniMax {

    /**
     * Der {@link EvalHandler} für die Stellungsbewertung
     */
    final EvalHandler eval;
    /**
     * Flag für Alpha-Beta-Pruning
     */
    private final boolean abp;
    /**
     * Flag für Transpositionstabellen-Verwendung
     */
    private final boolean tpt;
    /**
     * Flag für optimierte Zugreihenfolge
     */
    private final boolean oso;

    /**
     * Erstellt eine MiniMax-Instanz mit konfigurierbaren Optimierungen.
     *
     * @param eval Der {@link EvalHandler} für die Bewertung
     * @param abp  Alpha-Beta-Pruning aktivieren
     * @param tpt  Transpositionstabelle aktivieren
     * @param oso  Optimierte Zugreihenfolge aktivieren (verwendet {@link GameConstants#EXPLORE_ORDER_OPTIMIZED})
     */
    MiniMax(EvalHandler eval, boolean abp, boolean tpt, boolean oso) {
        this.eval = eval;
        this.abp = abp;
        this.tpt = tpt;
        this.oso = oso;
    }

    /**
     * Rekursive MiniMax-Funktion zur Berechnung des besten Zuges oder der besten Bewertung.
     * <p>
     * Der Algorithmus funktioniert wie folgt:
     * <ul>
     *   <li>Prüft auf Gewinnzüge mit {@link Bord#isWinningMove(int, int, int[][])}</li>
     *   <li>Bei Tiefe 0: Bewertung über {@link EvalHandler#evaluate(int[][], int, int)}</li>
     *   <li>Nutzt Transpositionstabelle zur Vermeidung von Neuberechnungen</li>
     *   <li>Wendet Alpha-Beta-Pruning an zur Effizienzsteigerung</li>
     *   <li>Protokolliert Pruning-Ereignisse über {@link Info#logPrune(int)}</li>
     * </ul>
     *
     * @param tiles              Das Spielbrett als 2D-Array
     * @param depth              Die verbleibende Suchtiefe
     * @param alpha              Der Alpha-Wert für Pruning
     * @param beta               Der Beta-Wert für Pruning
     * @param isMaxTurn          True, wenn der maximierende Spieler am Zug ist
     * @param isScore            True, um die Bewertung zurückzugeben; False für den Zug
     * @param lastmoveX          X-Koordinate des letzten Zuges
     * @param lastmoveY          Y-Koordinate des letzten Zuges
     * @param transPositionTable Die Transpositionstabelle mit {@link Entry}-Objekten
     * @param hashlast           Hash-Wert der Position, berechnet mit {@link Bord#hash(int[][])}
     * @return Die beste Bewertung oder der beste Zug (abhängig von isScore)
     */
    public int miniMax(int[][] tiles, int depth, int alpha, int beta, boolean isMaxTurn, boolean isScore, int lastmoveX, int lastmoveY, HashMap<Long, Entry> transPositionTable, long hashlast) {
        if (Bord.isWinningMove(lastmoveX, lastmoveY, tiles) && isScore) {
            return (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
        }
        if (depth == 0) {
            return eval.evaluate(tiles, lastmoveX, lastmoveY);
        }
        if (tpt) {
            if (transPositionTable.containsKey(hashlast)) {
                Entry entry = transPositionTable.get(hashlast);
                if (entry.depth() == depth + 1) {
                    Info.logPrune(depth + 1);
                    return entry.score();
                }
            }
        }

        int bestScore;
        int bestMove = -1;

        bestScore = (isMaxTurn ? Integer.MIN_VALUE : Integer.MAX_VALUE);
        for (int i : (oso ? GameConstants.EXPLORE_ORDER_OPTIMIZED : GameConstants.EXPLORE_ORDER_BASIC)) {
            if (tiles[i][5] == 0) {
                int row = Bord.getRow(tiles, i);
                tiles[i][row] = (isMaxTurn ? GameConstants.PLAYER_MAX : GameConstants.PLAYER_MIN);
                long hash = Bord.hash(tiles);
                int score = miniMax(tiles, depth - 1, alpha, beta, !isMaxTurn, true, i, row, transPositionTable, hash);
                if (tpt) {
                    transPositionTable.put(hash, new Entry(score, depth));
                }
                tiles[i][row] = 0;
                if (abp) {
                    if (isMaxTurn) {
                        if (score > bestScore) {
                            bestScore = score;
                            bestMove = i;
                        }
                        alpha = Math.max(alpha, score);
                    } else {
                        if (score < bestScore) {
                            bestScore = score;
                            bestMove = i;
                        }
                        beta = Math.min(beta, score);
                    }
                    if (beta <= alpha) {
                        Info.logPrune(20);
                        break;
                    }
                }
            }
        }
        return (isScore ? bestScore : bestMove);
    }

}
