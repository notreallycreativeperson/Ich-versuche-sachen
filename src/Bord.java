public class Bord {

    int[][] tiles;
    boolean isMaxTurn;
    int[] moves;
    int moveCount;
    int[][] last8Moves = new int[2][8];

    public boolean isWon(){
        return isWon(this);
    }

    public boolean isFinished(){
        return isFinished(this);
    }

    public boolean winningPossible(){
        return winningPossible(this);
    }

    public static boolean isWon(Bord bord){

    }

    public static boolean isFinished(Bord bord){

    }

    public static boolean winningPossible(Bord bord){

    }

}
