import java.util.Scanner;
import java.util.Random;

public class BookingSystem {

    static Scanner input = new Scanner(System.in);

    public static void startBooking(){

        System.out.println("===== MOVIE BOOKING =====");

        // select movie
        System.out.println("Select Movie:");
        System.out.println("1. Avengers");
        System.out.println("2. Avatar");

        int movieChoice = input.nextInt();

        String movieName;

        if(movieChoice == 1){

            movieName = "Avengers";

        }else{

            movieName = "Avatar";

        }

        // select time
        System.out.println("Select Show Time:");
        System.out.println("1. 18:00");
        System.out.println("2. 21:00");

        int timeChoice = input.nextInt();

        String showTime;

        if(timeChoice == 1){

            showTime = "18:00";

        }else{

            showTime = "21:00";

        }

        // select seat
        System.out.println("Select Seat Type:");
        System.out.println("1. VIP (RM36)");
        System.out.println("2. Standard (RM18)");

        int seatChoice = input.nextInt();

        double seatPrice;
        String seatType;

        if(seatChoice == 1){

            seatPrice = 36;
            seatType = "VIP";

        }else{

            seatPrice = 18;
            seatType = "Standard";

        }

        // snack
        System.out.println("Add Snack?");
        System.out.println("1. Yes (RM12)");
        System.out.println("2. No");

        int snackChoice = input.nextInt();

        double snackPrice;

        if(snackChoice == 1){

            snackPrice = 12;

        }else{

            snackPrice = 0;

        }

        // create booking id
        Random rand = new Random();

        int bookingId = rand.nextInt(900) + 100;

        System.out.println("Booking ID: " + bookingId);

        // go to payment
        PaymentSystem.processPayment(
                bookingId,
                movieName,
                showTime,
                seatType,
                seatPrice,
                snackPrice
        );

    }

}