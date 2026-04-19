import java.util.Scanner;

public class function {

    // Safely wait for the user to press Enter
    public static void pressEnterToContinue(Scanner input) {
        System.out.println("Press Enter to continue...");
        input.nextLine(); // Read the whole line to avoid skipping
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }
}