import java.io.*;
import java.util.*;

public class fnbHistory {

    public static void printFnbHistory(Scanner input, String username) {

        String fileName = "order.txt";
        boolean hasRecord = false;
        StringBuilder allRecords = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {

            String line;
            boolean isUserRecord = false;
            StringBuilder record = new StringBuilder();

            while ((line = br.readLine()) != null) {

                // ===== 订单开始 =====
                if (line.contains("F&B ORDER RECEIPT")) {

                    String userLine = br.readLine(); // 读取 User 行

                    if (userLine != null && userLine.toLowerCase().contains(username.toLowerCase())) {

                        isUserRecord = true;
                        record.setLength(0); // 清空旧记录

                        record.append(line).append("\n");
                        record.append(userLine).append("\n");

                    } else {
                        isUserRecord = false;
                    }

                    continue; // 很重要，避免重复处理
                }

                // ===== 如果是当前用户订单，继续记录 =====
                if (isUserRecord) {
                    record.append(line).append("\n");
                }

                // ===== 订单结束 =====
                if (line.contains("====") && isUserRecord) {

                    allRecords.append(record.toString()).append("\n");

                    hasRecord = true;
                    isUserRecord = false;
                }
            }

            // ===== 输出 =====
            function.clearScreen();

            if (hasRecord) {
                System.out.println("===== ALL F&B ORDER HISTORY FOR USER: " + username + " =====\n");
                System.out.println(allRecords.toString());
            } else {
                System.out.println("No F&B order history found for user: " + username);
            }

        } catch (IOException e) {
            System.out.println("Error reading order file: " + e.getMessage());
        }

        System.out.println("\nPress Enter to return to menu...");
        input.nextLine();
    }
}