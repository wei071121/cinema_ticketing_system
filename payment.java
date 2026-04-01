import java.util.Random;
import java.util.Scanner;

public class Payment {

    public static boolean TNG(Scanner input, double amount) {

    System.out.println("\n===== TNG E-WALLET PAYMENT =====");
    System.out.printf("Amount to Pay: RM %.2f%n", amount);

    // 输入手机号
    System.out.print("Enter Phone Number: ");
    String phone = input.nextLine();

    // 输入密码
    System.out.print("Enter Password (6 digits): ");
    String password = input.nextLine();

    System.out.println("\nProcessing TNG Payment...");

    // 简单验证
    if (phone.matches("\\d{10,11}") && password.matches("\\d{6}")) {
        System.out.println("Login Successful!");
        System.out.println("Payment Approved via TNG!");
        return true;
    } else {
        System.out.println("Payment Failed! Invalid phone or password.");
        return false;
    }
}

    // ===== BANK PAYMENT =====
    public static boolean bank(Scanner input, double amount) {

        System.out.println("\n===== BANK PAYMENT =====");
        System.out.printf("Amount to Pay: RM %.2f%n", amount);

        System.out.print("Enter Bank Name: ");
        String bankName = input.nextLine();

        System.out.print("Enter Account Number: ");
        String accNumber = input.nextLine();

        System.out.print("Enter PIN (6 digits): ");
        String pin = input.nextLine();

        System.out.println("\nProcessing Bank Payment...");

        // 简单验证
        if (pin.length() == 6 && pin.matches("\\d+")) {
            System.out.println("Payment Approved by Bank!");
            return true;
        } else {
            System.out.println("Payment Failed! Invalid PIN.");
            return false;
        }
    }



public static String generateQR() {
    java.util.Random rand = new java.util.Random();
    int num = rand.nextInt(900000) + 100000;
    return "QR" + num;
}

public static void saveBooking(
        int bookingId,
        String username,
        String movieName,
        String showTime,
        String seatType,
        double total,
        String qr) {

    try (java.io.FileWriter fw = new java.io.FileWriter("booking.txt", true)) {

        fw.write("===== BOOKING =====\n");
        fw.write("Booking ID: " + bookingId + "\n");
        fw.write("User: " + username + "\n");
        fw.write("Movie: " + movieName + "\n");
        fw.write("Show Time: " + showTime + "\n");
        fw.write("Seat: " + seatType + "\n");
        fw.write(String.format("Total: RM %.2f\n", total));
        fw.write("QR Code: " + qr + "\n");
        fw.write("====================\n\n");

    } catch (java.io.IOException e) {
        System.out.println("Error saving booking: " + e.getMessage());
    }
}
}