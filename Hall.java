import java.io.*;
import java.util.*;

public class Hall {

    private String name;
    private int rows;
    private int cols;
    private Seat[][] seats;  // 使用 Seat 对象数组
    private static final int VIP_ROWS = 2;  // 前两排为VIP座位

    public Hall(String name, int rows, int cols) {
        this.name = name;
        this.rows = rows;
        this.cols = cols;

        seats = new Seat[rows][cols];

        // 初始化座位 - 前 VIP_ROWS 行为VIP座位，其余为普通座位
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
        System.out.println("=== " + name + " 座位图 ===");
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
        System.out.println("O = 可用  V = VIP座位  X = 已预订");
    }

    public boolean bookSeat(String seat) {
        int r = seat.charAt(0) - 'A';
        int c = Integer.parseInt(seat.substring(1)) - 1;

        if (r < 0 || r >= rows || c < 0 || c >= cols) {
            return false;
        }

        // 使用 Seat 对象的 book() 方法
        return seats[r][c].book();
    }

    public void loadSeats(String file) {
        // 首先初始化所有座位
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
                        seats[r][c].book();  // 标记为已预订
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
     * 获取指定座位的信息
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
     * 获取指定座位的价格
     */
    public double getSeatPrice(String seatCode) {
        Seat seat = getSeat(seatCode);
        return seat != null ? seat.getPrice() : 0;
    }

    /**
     * 检查指定座位是否是VIP座位
     */
    public boolean isVIPSeat(String seatCode) {
        Seat seat = getSeat(seatCode);
        return seat instanceof VIPSeat;
    }

    /**
     * 获取大厅名称
     */
    public String getName() {
        return name;
    }

    /**
     * 获取VIP座位数量
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
     * 获取普通座位数量
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