import java.util.Random;
import java.util.Scanner;

public abstract class Payment {

    protected double amount;

    public Payment(double amount) {
        this.amount = amount;
    }

    public abstract boolean pay(Scanner input);

    // ===== QR GENERATION (shared by all subclasses) =====
    public String generateQR() {
        Random rand = new Random();
        int num = rand.nextInt(900000) + 100000;
        return "QR" + num;
    }
}