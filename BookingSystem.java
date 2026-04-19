import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.*;

public class BookingSystem {

    static Scanner input = new Scanner(System.in);

    // ===== TICKET SUPER CLASS =====
    static class Ticket {
        protected String movie;
        protected String showTime;
        protected String seat;
        protected double price;

        public Ticket(String movie, String showTime, String seat, double price) {
            this.movie = movie;
            this.showTime = showTime;
            this.seat = seat;
            this.price = price;
        }

        public double getPrice() {
            return price;
        }
    }

    // ===== SUBCLASS =====
    static class CinemaTicket extends Ticket {

        public CinemaTicket(String movie, String showTime, String seat) {
            super(movie, showTime, seat, calculatePrice(seat));
        }

        private static double calculatePrice(String seat) {
            return (seat.charAt(0) < 'C') ? 36 : 18;
        }
    }

    // ===== SHOWTIME =====
    static class Showtime {
        String time;
        String hallSize;

        public Showtime(String time, String hallSize) {
            this.time = time;
            this.hallSize = hallSize;
        }
    }

    // ===== MOVIE =====
    static class Movie {
        String name;
        List<Showtime> showtimes = new ArrayList<>();

        public Movie(String name) {
            this.name = name;
        }
    }

    static List<Movie> movies = new ArrayList<>();

    // ===== MAIN BOOKING =====
    public static void startBooking(String username) {

        if (movies.isEmpty()) {
            loadMoviesFromFile("movies.txt");
        }

        if (movies.isEmpty()) {
            System.out.println("No movies available!");
            return;
        }

        System.out.println("===== MOVIE LIST =====");

        for (int i = 0; i < movies.size(); i++) {
            System.out.println((i + 1) + ". " + movies.get(i).name);
        }

        System.out.println("0. Exit");
        System.out.print("\nYour choice: ");

        int movieChoice = input.nextInt();

        // EXIT
        if (movieChoice == 0) {
            menu.mainmenu(input, username);
            return;
        }

        movieChoice = movieChoice - 1;

        // VALIDATION
        if (movieChoice < 0 || movieChoice >= movies.size()) {
            System.out.println("Invalid movie selection!");
            return;
        }

        Movie movie = movies.get(movieChoice);

        System.out.println("\n===== SHOWTIME =====");

        for (int i = 0; i < movie.showtimes.size(); i++) {
            Showtime s = movie.showtimes.get(i);
            System.out.println((i + 1) + ". " + s.time + " (" + s.hallSize + ")");
        }
        System.out.print("\nYour choice: ");

        int timeChoice = input.nextInt() - 1;
        input.nextLine();

        // VALIDATION
        if (timeChoice < 0 || timeChoice >= movie.showtimes.size()) {
            System.out.println("Invalid showtime!");
            return;
        }

        Showtime show = movie.showtimes.get(timeChoice);

        // =======================
        // SEAT SYSTEM
        // =======================
        Hall hall = createHall(show.hallSize);

        new File("seats").mkdirs();

        String seatFile = "seats/" + movie.name + "_" + show.time.replace(":", "-") + ".txt";

        hall.loadSeats(seatFile);

        hall.printSeats();

        System.out.print("Select seat (e.g. A1): ");
        String seat = input.nextLine().toUpperCase();

        if (!hall.bookSeat(seat)) {
            System.out.println("Seat taken!");
            return;
        }

        hall.saveSeats(seatFile);

        // ===== TICKET =====
        CinemaTicket ticket = new CinemaTicket(movie.name, show.time, seat);

        System.out.println("\nBooking Summary:");
        System.out.println("Movie: " + movie.name);
        System.out.println("Time: " + show.time);
        System.out.println("Seat: " + seat);
        System.out.printf("Price: RM %.2f%n", ticket.getPrice());

        int bookingId = new Random().nextInt(900) + 100;

        // ===== PAYMENT =====
        System.out.println("\nChoose Payment Method:");
        System.out.println("1. TNG");
        System.out.println("2. BANK");
        System.out.print("\nYour choice: ");

        int method = input.nextInt();
        input.nextLine();

        Payment payment;

        if (method == 1) {
            payment = new TNGPayment(ticket.getPrice());
        } else {
            payment = new BankPayment(ticket.getPrice());
        }

        boolean success = payment.pay(input);

        if (success) {

            String qr = payment.generateQR();

            System.out.println("\nPayment Successful!");
            System.out.println("Booking ID: " + bookingId);
            System.out.println("QR: " + qr);

            saveBooking(
                    bookingId,
                    username,
                    movie.name,
                    show.time,
                    seat,
                    ticket.getPrice(),
                    qr
            );

        } else {
            System.out.println("Payment Failed / Cancelled.");
        }

        function.pressEnterToContinue(input);
    }

    // ===== SAVE BOOKING =====
    public static void saveBooking(
            int bookingId,
            String username,
            String movieName,
            String showTime,
            String seat,
            double total,
            String qr) {

        StringBuilder receipt = new StringBuilder();

        receipt.append("===== BOOKING RECORD =====\n");
        receipt.append("Booking ID: ").append(bookingId).append("\n");
        receipt.append("User: ").append(username).append("\n");
        receipt.append("Movie: ").append(movieName).append("\n");
        receipt.append("Show Time: ").append(showTime).append("\n");
        receipt.append("Seat: ").append(seat).append("\n");
        receipt.append(String.format("Total: RM %.2f%n", total));
        receipt.append("QR Code: ").append(qr).append("\n");
        receipt.append("===========================\n\n");

        System.out.println(receipt);

        try (FileWriter fw = new FileWriter("booking.txt", true)) {
            fw.write(receipt.toString());
        } catch (IOException e) {
            System.out.println("Error saving booking: " + e.getMessage());
        }
    }

    // ===== LOAD MOVIES =====
    public static void loadMoviesFromFile(String filePath) {

        movies.clear();

        try (Scanner sc = new Scanner(new File(filePath))) {

            Movie currentMovie = null;

            while (sc.hasNextLine()) {

                String line = sc.nextLine().trim();

                if (line.isEmpty()) continue;

                if (line.equals("---")) {
                    currentMovie = null;
                    continue;
                }

                String[] parts = line.split("\\|");

                if (parts.length == 4) {
                    currentMovie = new Movie(parts[0]);
                    movies.add(currentMovie);
                } else if (parts.length == 2 && currentMovie != null) {
                    currentMovie.showtimes.add(
                            new Showtime(parts[0], parts[1])
                    );
                }
            }

        } catch (Exception e) {
            System.out.println("Error loading movies: " + e.getMessage());
        }
    }

    // ===== CREATE HALL =====
    public static Hall createHall(String size) {

        switch (size.toLowerCase()) {
            case "small":
                return new Hall("Small", 4, 6);
            case "medium":
                return new Hall("Medium", 6, 8);
            case "large":
                return new Hall("Large", 8, 10);
            default:
                return new Hall("Standard", 6, 8);
        }
    }
}