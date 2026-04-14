import java.util.Scanner;

public class BankPayment extends Payment {

    public BankPayment(double amount) {
        super(amount);
    }

    @Override
    public boolean pay(Scanner input) {

        System.out.println("\n===== BANK PAYMENT =====");
        System.out.printf("Amount: RM %.2f%n", amount);

        System.out.println("1. Public Bank");
        System.out.println("2. MAE");
        System.out.print("Choose bank: ");
        int bank = input.nextInt();
        input.nextLine(); // 🔥 important fix

        if (bank < 1 || bank > 2) {
            System.out.println("Invalid bank");
            return false;
        }

        System.out.print("Enter account number: ");
        String acc = input.nextLine();

        System.out.print("Enter 6-digit PIN: ");
        String pin = input.nextLine();

        if (pin.matches("\\d{6}") && acc.length() >= 8) {
            System.out.println("Bank Payment Successful");
            return true;
        }

        System.out.println("Bank Payment Failed");
        return false;
    }
}