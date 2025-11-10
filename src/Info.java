class TimeWatcher {
    long startTime;

    void start() {
        startTime = System.currentTimeMillis();
    }

    long stop() {
        return System.currentTimeMillis() - startTime;
    }
}

class PlayerData {
    private TimeWatcher watcher;
    int turns;
    int time;
    int gamesPlayed;
    int gamesWon;
    int gamesLost;
    int gamesDraw;
    long prunesTT;
    long prunesAB;
    long nodesVisited;

    PlayerData() {
        watcher = new TimeWatcher();
        turns = 0;
        time = 0;
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
        gamesDraw = 0;
        prunesTT = 0;
        prunesAB = 0;
    }

    public void printPlayerData() {
        System.out.println("nodesVisited per turn: " + nodesVisited / turns);
    }

    public void logTimeStart() {
        watcher.start();
    }

    public void logTimeEnd() {
        time += watcher.stop();
    }

    public void logPrune(boolean isTT) {
        if (isTT) {
            prunesTT++;
        } else {
            prunesAB++;
        }
    }

    public void logNodesVisit() {
            nodesVisited++;
    }

    public void logGame() {
        gamesPlayed++;
    }
    public void logTurn() {
        turns++;
    }
    public void logWin(boolean isWinner) {
        if (!isWinner) {
        gamesWon++;} else {
        gamesLost++;}
    }

    public void logDraw() {
        gamesDraw++;
    }
}

