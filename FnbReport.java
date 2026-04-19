import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FnbReport {

    private static final String ORDER_FILE = "order.txt";
    private final Map<String, Integer> itemQuantity = new HashMap<>();
    private final Map<String, Double> itemRevenue = new HashMap<>();
    private int totalOrders = 0;
    private double totalRevenue = 0.0;

    public void showReport(Scanner input) {
        function.clearScreen();
        loadReportData();
        displayReport();
        function.pressEnterToContinue(input);
    }

    private void loadReportData() {
        itemQuantity.clear();
        itemRevenue.clear();
        totalOrders = 0;
        totalRevenue = 0.0;

        try (BufferedReader reader = new BufferedReader(new FileReader(ORDER_FILE))) {
            String line;
            boolean insideReceipt = false;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) {
                    continue;
                }

                if (line.contains("F&B ORDER RECEIPT")) {
                    totalOrders++;
                    insideReceipt = true;
                    continue;
                }

                if (!insideReceipt) {
                    continue;
                }

                if (line.startsWith("TOTAL:")) {
                    totalRevenue += parseTotalLine(line);
                    insideReceipt = false;
                    continue;
                }

                if (line.startsWith("User:") || line.startsWith("---") || line.startsWith("===") || line.startsWith("====")) {
                    continue;
                }

                processItemLine(line);
            }
        } catch (FileNotFoundException e) {
            System.out.println("Order file not found: " + ORDER_FILE);
        } catch (IOException e) {
            System.out.println("Error reading F&B order file: " + e.getMessage());
        }
    }

    private void processItemLine(String line) {
        Pattern pattern = Pattern.compile("^(.+?)\\s*\\(RM([0-9]+(?:\\.[0-9]{1,2})?)\\)\\s*x\\s*([0-9]+)");
        Matcher matcher = pattern.matcher(line);
        if (!matcher.find()) {
            return;
        }

        String itemName = matcher.group(1).trim();
        double unitPrice = Double.parseDouble(matcher.group(2));
        int quantity = Integer.parseInt(matcher.group(3));
        double revenue = unitPrice * quantity;

        itemQuantity.put(itemName, itemQuantity.getOrDefault(itemName, 0) + quantity);
        itemRevenue.put(itemName, itemRevenue.getOrDefault(itemName, 0.0) + revenue);
    }

    private double parseTotalLine(String line) {
        Pattern pattern = Pattern.compile("RM([0-9]+(?:\\.[0-9]{1,2})?)");
        Matcher matcher = pattern.matcher(line);
        if (matcher.find()) {
            return Double.parseDouble(matcher.group(1));
        }
        return 0.0;
    }

    private void displayReport() {
        System.out.println("===== F&B SALES REPORT =====");
        System.out.println();

        if (totalOrders == 0) {
            System.out.println("No F&B orders have been recorded yet.");
            System.out.println();
            return;
        }

        System.out.println("Total F&B Orders: " + totalOrders);
        System.out.println("Total F&B Revenue: RM" + String.format("%.2f", totalRevenue));
        System.out.println("Average Order Value: RM" + String.format("%.2f", totalRevenue / totalOrders));
        System.out.println();

        List<Map.Entry<String, Integer>> quantityList = sortByValue(itemQuantity);
        System.out.println("Top F&B Items by Quantity Sold:");
        for (Map.Entry<String, Integer> entry : quantityList) {
            System.out.println("- " + entry.getKey() + ": " + entry.getValue() + " pcs");
        }
        System.out.println();

        List<Map.Entry<String, Double>> revenueList = sortByValue(itemRevenue);
        System.out.println("Top F&B Items by Revenue:");
        for (Map.Entry<String, Double> entry : revenueList) {
            System.out.println("- " + entry.getKey() + ": RM" + String.format("%.2f", entry.getValue()));
        }
        System.out.println();
    }

    private <T extends Comparable<T>> List<Map.Entry<String, T>> sortByValue(Map<String, T> data) {
        List<Map.Entry<String, T>> list = new ArrayList<>(data.entrySet());
        list.sort((a, b) -> b.getValue().compareTo(a.getValue()));
        return list;
    }
}
