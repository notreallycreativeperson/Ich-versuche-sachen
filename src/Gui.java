import java.util.Random;
import java.util.Scanner;

import static java.lang.System.out;

class Visual {

    static final Scanner scanner = new Scanner(System.in);

    public static int getMode(){
        int mode=-1;
        do{
            out.println("In welchem Modus möchtest du spielen?");
            out.println("1 für schnelles spiel");
            try {
                mode = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                out.println("Bitte gib eine zulässige Zahl ein.");
            }
        }while (mode==-1);
        return mode;
    }

    public static int getBot(){
        int bot=-1;
        do{
            out.println("Welcher Bot soll spielen");
            out.println("1->Strange | 2->Stupid | 3->Competent");
            try {
                bot = Integer.parseInt(scanner.next());
            } catch (Exception e) {
                out.println("Bitte gib eine zulässige Zahl ein.");
            }
        }while (bot==-1);
        return bot;
    }

    public static String getName() {
        out.println("Spieler, wie ist dein Name?");
        String name = scanner.next();
        out.println("Hallo " + name);
        out.println();
        return name;
    }

    public static boolean whoStarts() {
        boolean run = true;
        int input = 0;
        while (run) {
            out.println("Soll x oder o beginnen?");
            out.println("x->1 | o->2 | zufall->3");
            try {
                input = Integer.parseInt(scanner.next());
                if (input < 4 && input > 0) run = false;
            } catch (Exception e){
                out.println("Bitte gib eine zulässige Zahl ein.");
            }
        }
        return switch (input) {
            case 2 -> false;
            case 3 -> new Random().nextBoolean();
            default -> true;
        };
    }

    public static void winner(int winner){
        out.println("Spieler "+ winner+ " hat gewonnen.");
    }

    public static void tie(){
        out.println("Das spiel endet unendschieden.");
    }

    public static void displayBord(Bord bord) {
        displayBord(bord.getTiles());
    }

    public static void displayBord(int[][] bord) {
        for (int i = 1; i < 8; i++) {
            out.print(" " + i + "  ");
        }
        out.println();
        for (int i = 5; i >= 0; i--) {
            for (int j = 0; j < 7; j++) {
                out.print("[");
                switch (bord[j][i]) {
                    case 1: {
                        out.print("x");
                        break;
                    }
                    case -1: {
                        out.print("o");
                        break;
                    }
                    default:
                        out.print(" ");
                }
                out.print("] ");
            }
            out.println();
        }
        out.println();
        out.println();
    }

    public static int getMove() {
        int move = -1;
        out.println("Welchen Zug möchtest du ziehen?");

        Scanner scanner = new Scanner(System.in);
        try {
            move = Integer.parseInt(scanner.next()) - 1;


        } catch (Exception e) {
            out.println("Bitte gib eine zulässige Zahl von 1 bis 7 ein.");
        }
        if (move > 6||move<0) {
            out.println("Diese Zahl ist zu groß");
            move = -1;
        }
        return move;
    }



}
