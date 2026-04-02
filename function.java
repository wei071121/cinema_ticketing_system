import java.util.Scanner;

public class function {

    // 安全等待用户按回车
    public static void pressEnterToContinue(Scanner input) {
        System.out.println("Press Enter to continue...");
        input.nextLine(); // 读取整行，避免跳过
    }

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (Exception e) {
            for (int i = 0; i < 50; i++) System.out.println();
        }
    }
}