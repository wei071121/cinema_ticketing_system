import java.io.*;
import java.util.*;

public class Hall {

    private String name;
    private int rows;
    private int cols;
    private Seat[][] seats;  // Array of Seat objects
    private static final int VIP_ROWS = 2;  // First two rows are VIP seats

    public Hall(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;

        seats = new Seat[rows][cols];

        // Initialize seats - first VIP_ROWS are VIP, rest are regular
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String seatCode = (char)('A' + i) + String.valueOf(j + 1);
                if (i < VIP_ROWS) {
                    seats[i][j] = new VIPSeat(seatCode);
                } else {
                    seats[i][j] = new RegularSeat(seatCode);
                }
            }
        }
    }


    public void printSeats() {
        System.out.println("=== " + name + " Seat Map ===");
        System.out.print("   ");
        for (int i = 1; i <= cols; i++) {
            System.out.print(i % 10 + " ");
        }
        System.out.println();

        for (int r = 0; r < rows; r++) {
            System.out.print((char)('A' + r) + "  ");

            for (int c = 0; c < cols; c++) {
                Seat seat = seats[r][c];
                System.out.print(seat.getDisplayChar() + " ");
            }
            System.out.println();
        }
        System.out.println("O = Available  V = VIP  X = Booked");
    }

    public boolean bookSeat(String seat) {
        int r = seat.charAt(0) - 'A';
        int c = Integer.parseInt(seat.substring(1)) - 1;

        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return false;
        }

        // Use the Seat object's book() method
        return seats[r][c].book();
    }

    public void loadSeats(String file) {
        // First initialize all seats
        for (int i = 0; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                String seatCode = (char)('A' + i) + String.valueOf(j + 1);
                if (i < VIP_ROWS) {
                    seats[i][j] = new VIPSeat(seatCode);
                } else {
                    seats[i][j] = new RegularSeat(seatCode);
                }
            }
        }

        File f = new File(file);

        if (!f.exists()) {
            saveSeats(file);
            return;
        }

        try (Scanner sc = new Scanner(f)) {
            int r = 0;

            while (sc.hasNextLine() && r < rows) {
                String line = sc.nextLine();

                for (int c = 0; c < line.length() && c < cols; c++) {
                    char ch = line.charAt(c);
                    if (ch == 'X') {
                        seats[r][c].book();  // Mark as booked
                    }
                }
                r++;
            }

        } catch (Exception e) {
            System.out.println("Load error: " + e.getMessage());
        }
    }

    public void saveSeats(String file) {
        try {
            File folder = new File("seats");
            if (!folder.exists()) folder.mkdirs();

            FileWriter fw = new FileWriter(file);

            for (int i = 0; i < rows; i++) {
                for (int j = 0; j < cols; j++) {
                    fw.write(seats[i][j].toChar());
                }
                fw.write("\n");
            }

            fw.close();

        } catch (Exception e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    /**
     * Get seat information by seat code
     */
    public Seat getSeat(String seatCode) {
        int r = seatCode.charAt(0) - 'A';
        int c = Integer.parseInt(seatCode.substring(1)) - 1;

        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return null;
        }
        return seats[r][c];
    }

    /**
     * Get seat price by seat code
     */
    public double getSeatPrice(String seatCode) {
        Seat seat = getSeat(seatCode);
        return seat != null ? seat.getPrice() : 0;
    }

    /**
     * Check if a seat is a VIP seat
     */
    public boolean isVIPSeat(String seatCode) {
        Seat seat = getSeat(seatCode);
        return seat instanceof VIPSeat;
    }

    /**
     * Get hall name
     */
    public String getName() {
        return name;
    }

    /**
     * Get count of available VIP seats
     */
    public int getVIPSeatCount() {
        int count = 0;
        for (int i = 0; i < VIP_ROWS; i++) {
            for (int j = 0; j < cols; j++) {
                if (!seats[i][j].isBooked()) {
                    count++;
                }
            }
        }
        return count;
    }

    /**
     * Get count of available regular seats
     */
    public int getRegularSeatCount() {
        int count = 0;
        for (int i = VIP_ROWS; i < rows; i++) {
            for (int j = 0; j < cols; j++) {
                if (!seats[i][j].isBooked()) {
                    count++;
                }
            }
        }
        return count;
    }
}