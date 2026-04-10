import java.util.Scanner;

public class TNGPayment extends Payment {

    public TNGPayment(double amount) {
        super(amount);
    }

    @Override
    public boolean pay(Scanner input) {

        System.out.println("\n===== TNG PAYMENT =====");
        System.out.printf("Amount: RM %.2f%n", amount);

        System.out.print("Enter phone number: ");
        String phone = input.nextLine();

        System.out.print("Enter 6-digit password: ");
        String password = input.nextLine();

        System.out.println("Processing...");

        if (phone.matches("\\d{10,11}") && password.matches("\\d{6}")) {
            System.out.println("TNG Payment Successful");
            return true;
        }

        System.out.println("TNG Payment Failed");
        return false;
    }
}