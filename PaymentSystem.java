import java.io.FileWriter;
import java.io.IOException;
import java.util.Random;

public class PaymentSystem {

    public static void processPayment(
            int bookingId,
            String movieName,
            String showTime,
            String seatType,
            double seatPrice){

        double total;

        total = seatPrice;

        System.out.println("Total Payment: RM " + total);

        System.out.println("Processing Payment...");

        // generate QR
        String qr = generateTicketCode();

        // save booking
        saveBooking(
                bookingId,
                movieName,
                showTime,
                seatType,
                total,
                qr
        );

        System.out.println("Payment Successful!");
        System.out.println("Your QR Code: " + qr);

    }

    public static void saveBooking(
            int bookingId,
            String movieName,
            String showTime,
            String seatType,
            double amount,
            String qr){

        try{

            FileWriter fw = new FileWriter("booking.txt",true);

            fw.write(
                    bookingId + "," +
                    movieName + "," +
                    showTime + "," +
                    seatType + "," +
                    amount + "," +
                    qr + "\n"
            );

            fw.close();

        }catch(IOException e){

            System.out.println(e);

        }

    }

    public static String generateTicketCode(){

        Random rand = new Random();

        int num = rand.nextInt(90000) + 10000;

        return "QR" + num;

    }

}