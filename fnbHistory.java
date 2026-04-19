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

                // ===== Order begins =====
                if (line.contains("F&B ORDER RECEIPT")) {

                    String userLine = br.readLine(); // Read User line

                    if (userLine != null && userLine.toLowerCase().contains(username.toLowerCase())) {

                        isUserRecord = true;
                        record.setLength(0); // Clear previous record

                        record.append(line).append("\n");
                        record.append(userLine).append("\n");

                    } else {
                        isUserRecord = false;
                    }

                    continue; // Important to avoid duplicate processing
                }

                // ===== Continue recording if this is the current user's order =====
                if (isUserRecord) {
                    record.append(line).append("\n");
                }

                // ===== Order ends =====
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