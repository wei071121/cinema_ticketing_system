import java.util.Scanner;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class Payment {

    // TNG 支付方法
    public static boolean TNG(Scanner input, double amount) {
    System.out.println("\n===== TNG E-WALLET PAYMENT =====");
    System.out.printf("Amount to Pay: RM %.2f%n", amount);



    System.out.print("Enter Phone Number: ");
    String phone = input.nextLine();

    System.out.print("Enter Password (6 digits): ");
    String password = input.nextLine();

    System.out.println("Processing Payment...\n");
        try {
            Thread.sleep(2000); // 暂停 2000 毫秒，也就是 2 秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Payment Completed!");

    // ✅ 不要在这里按回车，直接返回
    if (phone.matches("\\d{10,11}") && password.matches("\\d{6}")) {
        System.out.println("Login Successful!");
        System.out.println("Payment Approved via TNG!");
        try {
            Thread.sleep(2000); // 暂停 2000 毫秒，也就是 2 秒
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return true;
    } else {
        System.out.println("Payment Failed! Invalid phone or password.");
        return false;
    }
}

    // BANK 支付方法
    public static boolean bank(Scanner input, double amount) {

        System.out.println("\n===== BANK PAYMENT =====");
        System.out.printf("Amount to Pay: RM %.2f%n", amount);

        System.out.println("Enter Bank Name: ");
        System.out.println("1. PUBLIC BANK");
        System.out.println("2. MAE");
        System.out.print("Your choice: ");
        String bankname = input.nextLine();

        System.out.print("Enter Account Number: ");
        String accNumber = input.nextLine();

        System.out.print("Enter PIN (6 digits): ");
        String pin = input.nextLine();

        System.out.println("\nProcessing Bank Payment...");

        if (pin.length() == 6 && pin.matches("\\d+")) {
            System.out.println("Payment Approved by Bank!");
            function.pressEnterToContinue(input);
            return true;
        } else {
            System.out.println("Payment Failed! Invalid PIN.");
            function.pressEnterToContinue(input);
            return false;
        }
    }

    // 生成随机 QR
    public static String generateQR() {
        Random rand = new Random();
        int num = rand.nextInt(900000) + 100000;
        return "QR" + num;
    }

    // 保存到文件 + 打印收据
    public static void saveBooking(
            int bookingId,
            String username,
            String movieName,
            String showTime,
            String seatType,
            double total,
            String qr) {

        StringBuilder receipt = new StringBuilder();
        receipt.append("===== BOOKING RECORD =====\n");
        receipt.append("Booking ID: ").append(bookingId).append("\n");
        receipt.append("User: ").append(username).append("\n");
        receipt.append("Movie: ").append(movieName).append("\n");
        receipt.append("Show Time: ").append(showTime).append("\n");
        receipt.append("Seat: ").append(seatType).append("\n");
        receipt.append(String.format("Total: RM %.2f%n", total));
        receipt.append("QR Code: ").append(qr).append("\n");
        receipt.append("===========================\n\n");

        // 打印收据
        System.out.println(receipt.toString());

        // 写到文件
        try (FileWriter fw = new FileWriter("booking.txt", true)) {
            fw.write(receipt.toString());
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }
    }
}