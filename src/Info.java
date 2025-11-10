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
        turns = 0;
        time = 0;
        gamesPlayed = 0;
        gamesWon = 0;
        gamesLost = 0;
        gamesDraw = 0;
        prunesTT = 0;
        prunesAB = 0;
    }
    public void printPlayerData(){
        System.out.println("turns played: " + turns);
        System.out.println("time played: " + time);
        System.out.println("time per turn: " + (time/turns) + " ms");
        System.out.println("gamesPlayed: " + gamesPlayed);
        System.out.println("gamesWon: " + gamesWon);
        System.out.println("gamesLost: " + gamesLost);
        System.out.println("gamesDraw: " + gamesDraw);
        System.out.println("prunesTT per turn: " + prunesTT/turns);
        System.out.println("prunesAB per turn: " + prunesAB/turns);
        System.out.println("nodesVisited per turn: " + nodesVisited/turns);
    }
}

class GameData {
    private TimeWatcher watcher;
    Player playerMax;
    private long timeMsMax;
    private long nodesVisitedMax;
    private long prunesTTMax;
    private long prunesABMax;
    Player playerMin;
    private long timeMsMin;
    private long nodesVisitedMin;
    private long prunesTTMin;
    private long prunesABMin;
    boolean isPlayerMaxWinner;
    boolean isDraw;
    public void setWinner(boolean isPlayerMaxWinner) {
        this.isPlayerMaxWinner = isPlayerMaxWinner;
        if (isPlayerMaxWinner) {
            playerMax.getPlayerData().gamesWon++;
            playerMin.getPlayerData().gamesLost++;
        } else {
            playerMin.getPlayerData().gamesWon++;
            playerMax.getPlayerData().gamesLost++;
        }
        saveGameDataToPlayer();
    }

    public void setDraw() {
        isDraw = true;
        playerMax.getPlayerData().gamesDraw++;
        playerMin.getPlayerData().gamesDraw++;
        saveGameDataToPlayer();
    }

    public void saveGameDataToPlayer() {
        playerMax.getPlayerData().gamesPlayed++;
        playerMin.getPlayerData().gamesPlayed++;
        playerMax.getPlayerData().time +=timeMsMax;
        playerMin.getPlayerData().time +=timeMsMin;
        playerMax.getPlayerData().nodesVisited += nodesVisitedMax;
        playerMin.getPlayerData().nodesVisited += nodesVisitedMin;
        playerMax.getPlayerData().prunesTT += prunesTTMax;
        playerMax.getPlayerData().prunesAB += prunesABMax;
        playerMin.getPlayerData().prunesTT += prunesTTMin;
        playerMin.getPlayerData().prunesAB += prunesABMin;
    }

    public void logTimeStart() {
        watcher.start();
    }

    public void logTimeEnd(boolean isMaxPlayer) {
        if (isMaxPlayer) {
            timeMsMax += watcher.stop();
            playerMax.getPlayerData().turns ++;
        }else {
            timeMsMin += watcher.stop();
            playerMin.getPlayerData().turns ++;
        }
    }

    public void logPrune(boolean isTT, boolean isMaxPlayer) {
        if(isMaxPlayer) {
            if(isTT) {
                prunesTTMax++;
            }else {
                prunesABMax++;
            }
        }else{
            if(isTT) {
                prunesTTMin++;
            }else {
                prunesABMin++;
            }
        }
    }

    public void logNodesVisit(boolean isMaxTurn) {
        if(isMaxTurn) {
            nodesVisitedMax++;
        }else  {
            nodesVisitedMin++;
        }
    }
    GameData(Player playerMax, Player playerMin) {
        this.playerMax = playerMax;
        this.playerMin = playerMin;
        watcher = new TimeWatcher();
    }
    GameData(GameData gameData1, GameData gameData2) {
        this.playerMax = gameData1.playerMax;
        this.playerMin = gameData1.playerMin;
        this.timeMsMax = gameData1.timeMsMax+gameData2.timeMsMin;
        this.nodesVisitedMax = gameData1.nodesVisitedMax+gameData2.nodesVisitedMin;
        this.prunesTTMax=gameData1.prunesTTMax+gameData2.prunesTTMin;
        this.prunesABMax=gameData1.prunesABMax+gameData2.prunesABMin;
        this.timeMsMin = gameData1.timeMsMin+gameData2.timeMsMax;
        this.nodesVisitedMin=gameData1.nodesVisitedMin+gameData2.nodesVisitedMax;
        this.prunesTTMin=gameData1.prunesTTMin+gameData2.prunesTTMax;
        this.prunesABMin=gameData1.prunesABMin+gameData2.prunesABMax;
        if((gameData1.isPlayerMaxWinner&&!gameData2.isPlayerMaxWinner)||(gameData1.isDraw&&!(gameData2.isDraw|| gameData2.isPlayerMaxWinner))) {
            isPlayerMaxWinner=true;
        } else if (!((gameData2.isPlayerMaxWinner&&!gameData1.isPlayerMaxWinner)|| (gameData2.isDraw&&!gameData1.isDraw))) {
            isDraw=true;
        }
    }
}