import java.io.*;
import java.util.*;

public class fnbHistory {

    public static void printFnbHistory(Scanner input, String username) {
        String fileName = "order.txt"; // F&B 订单文件
        boolean hasRecord = false;
        StringBuilder allRecords = new StringBuilder();

        try (BufferedReader br = new BufferedReader(new FileReader(fileName))) {
            String line;
            boolean isUserRecord = false;
            StringBuilder record = new StringBuilder();

            while ((line = br.readLine()) != null) {

                // 检查订单开始
                if (line.startsWith("===== F&B ORDER RECEIPT =====")) {
                    // 下一行应该是 User
                    String userLine = br.readLine();
                    if (userLine != null && userLine.toLowerCase().contains(username.toLowerCase())) {
                        isUserRecord = true;
                        record.setLength(0); // 清空
                        record.append(line).append("\n"); // 添加 F&B ORDER RECEIPT 行
                        record.append(userLine).append("\n"); // 添加 User 行
                    } else {
                        isUserRecord = false;
                    }
                } else if (isUserRecord) {
                    record.append(line).append("\n");
                }

                // 订单结束
                if (line.startsWith("============================") && isUserRecord) {
                    allRecords.append(record.toString()).append("\n");
                    hasRecord = true;
                    isUserRecord = false;
                }
            }

            // 清屏显示全部记录
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