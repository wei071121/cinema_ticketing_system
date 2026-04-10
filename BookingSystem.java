import java.util.*;
import java.io.*;

public class BookingSystem {

    static Scanner input = new Scanner(System.in);

    // 影厅类
    static class Hall {
        String name;
        int rows, cols;
        char[][] seats;

        public Hall(String name, int rows, int cols) {
            this.name = name;
            this.rows = rows;
            this.cols = cols;
            seats = new char[rows][cols];
            for (int i = 0; i < rows; i++)
                Arrays.fill(seats[i], 'O'); // 初始化全部座位为普通座位
        }

        // 打印座位图，前两行为VIP标记
        public void printSeats() {
            System.out.println("Seat Map (V = VIP, O = Standard, X = booked):");
            System.out.print("   ");
            for (int c = 0; c < cols; c++) System.out.print((c + 1) + " ");
            System.out.println();
            for (int r = 0; r < rows; r++) {
                System.out.print((char)('A'+r) + "  ");
                for (int c = 0; c < cols; c++) {
                    if (seats[r][c] == 'O' && r < 2) { // 前两行 VIP
                        System.out.print("V ");
                    } else {
                        System.out.print(seats[r][c] + " ");
                    }
                }
                System.out.println();
            }
        }

        // 预订座位
        public boolean bookSeat(String seat) {
            if (seat.length() < 2) return false;
            int row = seat.toUpperCase().charAt(0) - 'A';
            int col;
            try {
                col = Integer.parseInt(seat.substring(1)) - 1;
            } catch (NumberFormatException e) {
                return false;
            }
            if (row >= 0 && row < rows && col >= 0 && col < cols && seats[row][col] == 'O') {
                seats[row][col] = 'X';
                return true;
            } else if (row >=0 && row < rows && col >=0 && col < cols && seats[row][col] == 'O' && row < 2) { 
                // VIP
                seats[row][col] = 'X';
                return true;
            }
            return false;
        }

        // 保存座位状态
        public void saveSeats(String filename) {
            try (PrintWriter pw = new PrintWriter(new FileWriter(filename))) {
                for (int i = 0; i < rows; i++) {
                    for (int j = 0; j < cols; j++)
                        pw.print(seats[i][j]);
                    pw.println();
                }
            } catch (IOException e) {
                System.out.println("Error saving seats: " + e.getMessage());
            }
        }

        // 读取座位状态
        public void loadSeats(String filename) {
            File f = new File(filename);
            if (!f.exists()) return;
            try (Scanner sc = new Scanner(f)) {
                int r = 0;
                while (sc.hasNextLine() && r < rows) {
                    String line = sc.nextLine();
                    for (int c = 0; c < Math.min(line.length(), cols); c++)
                        seats[r][c] = line.charAt(c);
                    r++;
                }
            } catch (IOException e) {
                System.out.println("Error reading seats: " + e.getMessage());
            }
        }
    }

    // 时间段类
    static class Showtime {
        String time;
        Hall hall;

        public Showtime(String time, Hall hall) {
            this.time = time;
            this.hall = hall;
        }
    }

    // 电影类
    static class Movie {
        String name;
        List<Showtime> showtimes = new ArrayList<>();

        public Movie(String name) {
            this.name = name;
        }

        public void addShowtime(String time, Hall hall) {
            showtimes.add(new Showtime(time, hall));
        }
    }

    // 电影列表
    static List<Movie> movies = new ArrayList<>();

    static {
        // 三种影厅大小
        Hall small = new Hall("Small Hall", 5, 6);
        Hall medium = new Hall("Medium Hall", 6, 8);
        Hall large = new Hall("Large Hall", 8, 10);

        // 五个电影，每个电影五个时间，每个时间随机选择影厅大小
        String[] movieNames = {"Avengers","Avatar","Titanic","Spiderman","Jurassic"};
        String[] times = {"10:00","13:00","16:00","19:00","22:00"};
        Random rand = new Random();

        for (String mName : movieNames) {
            Movie m = new Movie(mName);
            for (String t : times) {
                Hall h;
                int r = rand.nextInt(3);
                if (r == 0) h = new Hall(small.name, small.rows, small.cols);
                else if (r == 1) h = new Hall(medium.name, medium.rows, medium.cols);
                else h = new Hall(large.name, large.rows, large.cols);
                m.addShowtime(t, h);
            }
            movies.add(m);
        }
    }

    public static void startBooking(String username) {
        System.out.println("===== MOVIE BOOKING =====");
        for (int i = 0; i < movies.size(); i++)
            System.out.println((i + 1) + ". " + movies.get(i).name);
        int movieChoice = input.nextInt() - 1;
        input.nextLine();
        if (movieChoice < 0 || movieChoice >= movies.size()) return;
        Movie movie = movies.get(movieChoice);

        System.out.println("\nSelect Showtime:");
        for (int i = 0; i < movie.showtimes.size(); i++) {
            System.out.println((i+1) + ". " + movie.showtimes.get(i).time +
                    " (" + movie.showtimes.get(i).hall.name + ")");
        }
        int timeChoice = input.nextInt() - 1;
        input.nextLine();
        if (timeChoice < 0 || timeChoice >= movie.showtimes.size()) return;
        Showtime show = movie.showtimes.get(timeChoice);

        // 文件名保存座位状态
        String seatFile = movie.name + "_" + show.time.replace(":","") + "_seats.txt";
        show.hall.loadSeats(seatFile);

        function.clearScreen();
        show.hall.printSeats();

        System.out.println("Select your seat (e.g., A1): ");
        String seat = input.next().toUpperCase();
        while (!show.hall.bookSeat(seat)) {
            System.out.println("Seat unavailable or invalid. Choose again:");
            seat = input.next().toUpperCase();
        }

        // 判断VIP或普通座位
        double seatPrice = (seat.charAt(0) < 'C') ? 36 : 18; // 前两行VIP
        double total = seatPrice;

        int bookingId = new Random().nextInt(900)+100;
        System.out.println("\nBooking ID: " + bookingId);
        System.out.printf("TOTAL: RM %.2f\n", total);
        function.pressEnterToContinue(input);

        // 支付
        function.clearScreen();
        System.out.println("1. TNG");
        System.out.println("2. Bank");
        int method = input.nextInt();
        input.nextLine();
        function.clearScreen();

        boolean success = false;
        if (method == 1) success = payment.TNG(input, total);
        else if (method == 2) success = payment.bank(input, total);

        if (success) {
            String qr = payment.generateQR();
            System.out.println("Payment Successful! QR: " + qr);
            // 保存booking到文件
            payment.saveBooking(bookingId, username, movie.name, show.time, seat, total, qr);
            // 保存座位状态
            show.hall.saveSeats(seatFile);
            function.pressEnterToContinue(input);
        } else {
            System.out.println("Payment Failed / Cancelled.");
            function.pressEnterToContinue(input);
        }
    }
}