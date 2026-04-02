import java.util.Scanner;
import java.util.Random;

public class BookingSystem {

    static Scanner input = new Scanner(System.in);

    public static void startBooking(String username){

        System.out.println("===== MOVIE BOOKING =====");

        // select movie
        System.out.println("1. Avengers");
        System.out.println("2. Avatar");
        int movieChoice = input.nextInt();

        String movieName = (movieChoice == 1) ? "Avengers" : "Avatar";
        function.clearScreen();

        // select time
        System.out.println("\nSelect Show Time:");
        System.out.println("1. 18:00");
        System.out.println("2. 21:00");
        int timeChoice = input.nextInt();

        String showTime = (timeChoice == 1) ? "18:00" : "21:00";
        function.clearScreen();

        // select seat
        System.out.println("\nSelect Seat Type:");
        System.out.println("1. VIP (RM36)");
        System.out.println("2. Standard (RM18)");
        int seatChoice = input.nextInt();

        double seatPrice = (seatChoice == 1) ? 36 : 18;
        String seatType = (seatChoice == 1) ? "VIP" : "Standard";

        input.nextLine(); // 清buffer
        function.clearScreen();

        // ===== TOTAL（只剩座位价格）=====
        double total = seatPrice;

        // booking id
        Random rand = new Random();
        int bookingId = rand.nextInt(900) + 100;

        System.out.println("\nBooking ID: " + bookingId);
        System.out.printf("TOTAL: RM %.2f\n", total);
        function.pressEnterToContinue(input);
        function.clearScreen();

        // ===== PAYMENT =====
        System.out.println("\n===== PAYMENT METHOD =====");
        System.out.println("1. TNG");
        System.out.println("2. Bank");
        System.out.print("Your choice: ");

        int method = input.nextInt();
        function.clearScreen();

        boolean success = false;

        if (method == 1) {
            success = Payment.TNG(input, total);
        } 
        else if (method == 2) {
            success = Payment.bank(input, total);
        }

        // ===== RESULT =====
        if (success) {
            String qr = Payment.generateQR();

            System.out.println("\nPayment Successful!");
            System.out.println("Booking Confirmed!");
            System.out.println("QR Code: " + qr);

            function.clearScreen();
            Payment.saveBooking(bookingId, username, movieName, showTime, seatType, total, qr);

            // 按回车继续
            function.pressEnterToContinue(input);
        } else {
            System.out.println("\nPayment Failed / Cancelled.");
            function.pressEnterToContinue(input);
        }
}
}