import java.io.*;
import java.util.*;

class BookingRecord {
    int bookingId;
    String user;
    String movie;
    String showTime;
    String seat;
    double total;
    String qr;

    public BookingRecord(int bookingId, String user, String movie, String showTime, String seat, double total, String qr) {
        this.bookingId = bookingId;
        this.user = user;
        this.movie = movie;
        this.showTime = showTime;
        this.seat = seat;
        this.total = total;
        this.qr = qr;
    }

    @Override
    public String toString() {
        return "Booking ID: " + bookingId + "\n" +
               "User: " + user + "\n" +
               "Movie: " + movie + "\n" +
               "Show Time: " + showTime + "\n" +
               "Seat: " + seat + "\n" +
               String.format("Total: RM %.2f%n", total) +
               "QR Code: " + qr + "\n" +
               "===========================\n";
    }
}

public class BookingHistory {

    public static List<BookingRecord> readBookingHistory(String filename) {
        List<BookingRecord> history = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new FileReader(filename))) {
            String line;
            int bookingId = 0;
            String user = "", movie = "", showTime = "", seat = "", qr = "";
            double total = 0;

            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (line.startsWith("Booking ID:")) {
                    bookingId = Integer.parseInt(line.split(":")[1].trim());
                } else if (line.startsWith("User:")) {
                    user = line.split(":")[1].trim();
                } else if (line.startsWith("Movie:")) {
                    movie = line.split(":")[1].trim();
                } else if (line.startsWith("Show Time:")) {
                    showTime = line.split(":")[1].trim();
                } else if (line.startsWith("Seat:")) {
                    seat = line.split(":")[1].trim();
                } else if (line.startsWith("Total:")) {
                    total = Double.parseDouble(line.split("RM")[1].trim());
                } else if (line.startsWith("QR Code:")) {
                    qr = line.split(":")[1].trim();
                } else if (line.startsWith("================")) {
                    // 到达一条记录结束
                    history.add(new BookingRecord(bookingId, user, movie, showTime, seat, total, qr));
                    // 重置变量
                    bookingId = 0; user = ""; movie = ""; showTime = ""; seat = ""; qr = ""; total = 0;
                }
            }

        } catch (IOException e) {
            System.out.println("Error reading booking file: " + e.getMessage());
        }

        return history;
    }

    // 测试打印历史记录
    public static void printHistory(String filename) {
        List<BookingRecord> history = readBookingHistory(filename);
        System.out.println("===== BOOKING HISTORY =====\n");
        for (BookingRecord record : history) {
            System.out.println(record);
        }
    }


}