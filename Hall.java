import java.io.*;
import java.util.*;

public class Hall {

    private String name;
    private int rows;
    private int cols;
    private char[][] seats;

    public Hall(String name, int rows, int cols) {
    this.name = name;
    this.rows = rows;
    this.cols = cols;

    seats = new char[rows][cols];

    for (int i = 0; i < rows; i++) {
        Arrays.fill(seats[i], 'O');
    }


    }


    public void printSeats() {
    System.out.println("Seat Map:");

    System.out.print("   ");
    for (int i = 1; i <= cols; i++) {
        System.out.print(i + " ");
    }
    System.out.println();

    for (int r = 0; r < rows; r++) {

        System.out.print((char)('A' + r) + "  ");

        for (int c = 0; c < cols; c++) {

            if (seats[r][c] == 'X') {
                System.out.print("X ");
            } else if (isVIP(r)) {
                System.out.print("V ");
            } else {
                System.out.print("O ");
            }
        }
        System.out.println();
    }
}

    public boolean bookSeat(String seat) {
        int r = seat.charAt(0) - 'A';
        int c = Integer.parseInt(seat.substring(1)) - 1;

        if (r < 0 || r >= rows || c < 0 || c >= cols) return false;

        if (seats[r][c] == 'X') return false;

        seats[r][c] = 'X';
        return true;
    }

    public void loadSeats(String file) {
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
                    seats[r][c] = line.charAt(c);
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
                    fw.write(seats[i][j]);
                }
                fw.write("\n");
            }

            fw.close();

        } catch (Exception e) {
            System.out.println("Save error: " + e.getMessage());
        }
    }

    public boolean isVIP(int r) {
    return r < 2; // 前两排 VIP
}
}