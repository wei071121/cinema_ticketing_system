import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class fnb {

    public static final String fileName = "order.txt";

    public static void orderPage(Scanner input, String username) {
        String[] items = {"Popcorn", "Soda", "Nachos", "Hotdog"};
        double[] prices = {10.00, 5.00, 12.00, 8.00};
        int[] quantities = new int[items.length];

        boolean running = true;

        while (running) {
            System.out.println("\n===== F&B MENU =====");
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ". " + items[i] + " - RM" + prices[i] + " (Qty: " + quantities[i] + ")");
            }
            System.out.println("------------------------");
            System.out.println("R. Remove Food");
            System.out.println("C. Checkout & Pay");
            System.out.println("0. Exit to Main Menu");
            System.out.print("Your choice: ");

            String choice = input.nextLine().trim().toUpperCase();

            if (choice.matches("[1-4]")) {
                int index = Integer.parseInt(choice) - 1;
                System.out.print("Enter quantity to add for " + items[index] + ": ");
                int qty = getQuantity(input);
                quantities[index] += qty;
                function.clearScreen();
                System.out.println(qty + " x " + items[index] + " added.");
                
            } else if (choice.equals("R")) {
                int index = selectItem(input, items, "remove");
                if (index != -1) {
                    System.out.print("Enter quantity to remove: ");
                    int qty = getQuantity(input);
                    function.clearScreen();
                    if (qty > quantities[index]) qty = quantities[index];
                    quantities[index] -= qty;
                    System.out.println(qty + " x " + items[index] + " removed.");
                }
            } else if (choice.equals("C")) {
                function.clearScreen();
                // Checkout & Pay
                boolean paid = checkoutAndPay(input, username, items, prices, quantities);
                if (paid) {
                    System.out.println("Press Enter to return to main menu...");
                    input.nextLine();
                    running = false; // 支付完成退出 F&B 菜单
                }
            } else if (choice.equals("0")) {
                running = false;
            } else {
                System.out.println("Invalid choice! Try again.");
            }
        }
    }

    // 获取数量
    public static int getQuantity(Scanner input) {
        int qty;
        while (true) {
            if (input.hasNextInt()) {
                qty = input.nextInt();
                input.nextLine();
                if (qty > 0) break;
            } else {
                input.nextLine();
            }
            System.out.print("Invalid! Enter quantity again: ");
        }
        return qty;
    }

    // 选择要减少的商品
    public static int selectItem(Scanner input, String[] items, String action) {
        System.out.println("\nSelect item to " + action + ": ");
        for (int i = 0; i < items.length; i++) {
            System.out.println((i + 1) + ". " + items[i]);
        }
        System.out.println("0. Cancel");
        System.out.print("Your choice: ");

        int choice;
        while (true) {
            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine();
                if (choice >= 0 && choice <= items.length) break;
            } else {
                input.nextLine();
            }
            System.out.print("Invalid! Enter again: ");
        }
        if (choice == 0) return -1;
        return choice - 1;
    }

    // Checkout 并支付
    public static boolean checkoutAndPay(Scanner input, String username, String[] items, double[] prices, int[] quantities) {
        double total = 0;
        StringBuilder receipt = new StringBuilder();
        function.clearScreen();
        receipt.append("=========== F&B ORDER RECEIPT ==========\n");
        receipt.append("User: ").append(username).append("\n");

        for (int i = 0; i < items.length; i++) {
            if (quantities[i] > 0) {
                double itemTotal = quantities[i] * prices[i];
                total += itemTotal;
        receipt.append(items[i])
               .append("\t(RM")
               .append(String.format("%.2f", prices[i]))
               .append(")")
               .append(" x ")
               .append(quantities[i])
               .append("\t\tRM")
               .append(String.format("%.2f", itemTotal))
               .append("\n");
    }
        }
        receipt.append("-----------------------------------------\n");
        receipt.append("TOTAL: \t\t\t\tRM").append(String.format("%.2f", total)).append("\n");
        receipt.append("=========================================\n");

        // 打印收据
        System.out.println(receipt.toString());

        // 保存到文件
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write(receipt.toString());
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }

        int choice = -1;
        while (choice < 0 || choice > 2) {
            System.out.println("1. TNG");
            System.out.println("2. BANK");
            System.out.println("0. Cancel order");
            System.out.print("Your choice: ");

            if (input.hasNextInt()) {
                choice = input.nextInt();
                input.nextLine();
            } else {
                input.nextLine();
            }
        }

        Payment payment;

        if (choice == 1) {
            payment = new TNGPayment(total);
        } else if (choice == 2) {
            payment = new BankPayment(total);
        } else {
            System.out.println("Order cancelled.");
            return false;
        }

        boolean paid = payment.pay(input);

        if (paid) {
            System.out.println(receipt.toString());
        }

        return paid;
    }
}