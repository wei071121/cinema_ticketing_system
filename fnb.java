import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class fnb {

    public static final String fileName = "order.txt"; // 所有用户共用文件

    public static void orderPage(Scanner input, String username) {
        String[] items = {"Popcorn", "Soda", "Nachos", "Hotdog"};
        double[] prices = {10.0, 5.0, 12.0, 8.0};
        int[] quantities = new int[items.length];

        boolean running = true;

        while (running) {
            System.out.println("\n===== F&B MENU =====");
            for (int i = 0; i < items.length; i++) {
                System.out.println((i + 1) + ". " + items[i] + " - RM" + prices[i] + " (Qty: " + quantities[i] + ")");
            }
            System.out.println("------------------------");
            System.out.println("R. Remove Food");
            System.out.println("C. Checkout & Save Order");
            System.out.println("0. Exit to Main Menu");
            System.out.print("Choose item number to add, or option: ");

            String choice = input.nextLine().trim().toUpperCase();

            if (choice.matches("[1-4]")) {
                int index = Integer.parseInt(choice) - 1;
                System.out.print("Enter quantity to add for " + items[index] + ": ");
                int qty = getQuantity(input);
                quantities[index] += qty;
                System.out.println(qty + " x " + items[index] + " added.");
            } 
            else if (choice.equals("R")) {
                int index = selectItem(input, items, "remove");
                if (index != -1) {
                    System.out.print("Enter quantity to remove: ");
                    int qty = getQuantity(input);
                    if (qty > quantities[index]) qty = quantities[index];
                    quantities[index] -= qty;
                    System.out.println(qty + " x " + items[index] + " removed.");
                }
            } 
            else if (choice.equals("C")) {
                printTotal(input,items, prices, quantities,username);
                saveOrderToFile(username, items, prices, quantities);
                System.out.println("Order saved for user: " + username);
                System.out.println("Press Enter to continue...");
                input.nextLine();
            } 
            else if (choice.equals("0")) {
                running = false;
            } 
            else {
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

    // 打印购物车总价
    public static void printTotal(Scanner input,String[] items, double[] prices, int[] quantities,String username) {
        double total = 0;
        System.out.println("\n===== ORDER SUMMARY =====");
        for (int i = 0; i < items.length; i++) {
            if (quantities[i] > 0) {
                double itemTotal = quantities[i] * prices[i];
                total += itemTotal;
                System.out.println(quantities[i] + " x " + items[i] + " = RM" + itemTotal);
            }
        }
        int choice = 0;
        System.out.println("TOTAL: RM" + total);
        System.out.println("=========================");
        System.out.println("1.TNG");
        System.out.println("2.BANK");
        System.out.println("0. Cancle order");
        System.out.println("Your choice: ");
        choice = input.nextInt();
        input.nextLine();
        if(choice >= 0 || choice <= 2){
            switch(choice){
                case 1:
                    payment.TNG(input,total);
                    break;
                case 2:
                    payment.bank(input,total);
                    break;
                case 0:
                    menu.mainmenu(input,username);
                    break;
            }
        }
    }

    // 保存订单到 order.txt（追加方式）
    public static void saveOrderToFile(String username, String[] items, double[] prices, int[] quantities) {
        try (FileWriter writer = new FileWriter(fileName, true)) { // true = append
            double total = 0;
            writer.write("===== ORDER FOR USER: " + username + " =====\n");
            for (int i = 0; i < items.length; i++) {
                if (quantities[i] > 0) {
                    double itemTotal = quantities[i] * prices[i];
                    total += itemTotal;
                    writer.write(quantities[i] + " x " + items[i] + " = RM" + itemTotal + "\n");
                }
            }
            writer.write("TOTAL: RM" + total + "\n");
            writer.write("=========================\n\n");
        } catch (IOException e) {
            System.out.println("Error saving order: " + e.getMessage());
        }
    }
}