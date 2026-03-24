public class function {
     public static void pressEnterToContinue(){
        System.out.println("Press Enter to continue...");
        try{
            System.in.read();
        }catch(Exception e){}
    }


public static void clearScreen() {
    try {
        new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
    } catch (Exception e) {
        for (int i = 0; i < 50; i++) {
            System.out.println();
        }
    }
}
}
