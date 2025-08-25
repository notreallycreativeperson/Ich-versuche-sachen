public class Info {
    static int betaPrune=0;
    static int [] ttPrune = new int[13];
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
}
