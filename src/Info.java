public class Info {
    static int betaPrune=0;
    static int [] ttPrune = new int[13];
    static int movesP1=0;
    static int movesP2=0;
    static int turns=0;
    public static void logPrune(int depth){
        if(depth<ttPrune.length){
            ttPrune[depth]++;
        }else{
            betaPrune++;
        }
    }

    public static void printPrunes(){
        System.out.println("BetaPrunes: "+betaPrune);
        betaPrune=0;
        for(int i=0;i< ttPrune.length;i++){
            System.out.println("Prunes at depth "+(i)+" : "+ttPrune[i]);
            ttPrune[i]=0;
        }
    }

    static void logTurns(){
        turns++;
    }

    static void logMoves(int player){
        if(player==1){
            movesP1++;
        }else{
            movesP2++;
        }
    }

    public static void printTime(){
        System.out.println(TimeWatcher.getElapsedTime(1)/(turns/2)+"ms pro Zug bei P1");
        System.out.println(TimeWatcher.getElapsedTime(2)/(turns/2)+"ms pro Zug bei P2");
    }
}

class TimeWatcher{
    static long startTime;
    static long startTimeP1;
    static long elapsedTimeP1;
    static long startTimeP2;
    static long elapsedTimeP2;
    static void start(){
        startTime = System.currentTimeMillis();
    }
    static long getElapsedTime(){
        return System.currentTimeMillis()-startTime;
    }
    static void start(int player){
        if(player==1){
            startTimeP1 = System.currentTimeMillis();
        }else{
            startTimeP2 = System.currentTimeMillis();
        }
    }

    static void stop(int player){
        if(player==1){
            elapsedTimeP1 += System.currentTimeMillis()-startTimeP1;
        }else{
            elapsedTimeP2 += System.currentTimeMillis()-startTimeP2;
        }
    }

    static long getElapsedTime(int player){
        if(player==1){
            return elapsedTimeP1;
        }else{
            return elapsedTimeP2;
        }
    }
}