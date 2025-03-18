import java.util.Scanner;

public class Visual {

    static Scanner scanner=new Scanner(System.in);

    public static int getMode(){
        int mode=-1;
        do{
            System.out.println("In welchem Modus möchtest du spielen?");
            System.out.println("1 für schnelles spiel");
            try {
                mode = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                System.out.println("Bitte gib eine zulässige Zahl ein.");
            }
        }while (mode==-1);
        return mode;
    }

    public static int getBot(){
        int bot=-1;
        do{
            System.out.println("Welcher Bot soll spielen");
            System.out.println("1 für schnelles spiel");
            try {
                bot = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                System.out.println("Bitte gib eine zulässige Zahl ein.");
            }
        }while (bot==-1);
        return bot;
    }

    public static String getName() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Spieler, wie ist dein Name?");
        String name = scanner.next();
        System.out.println("Hallo"+ name);
        return name;
    }

}
