import java.util.List;
import java.util.Scanner;

public class menu {
    public static void mainmenu(Scanner input, String username){
        int choice = 0;

        do {
            System.out.println("=================================");
            System.out.println("         WTF CINEMA MENU         ");
            System.out.println("=================================");
            System.out.println("1. Booking Ticket");
            System.out.println("2. F&B Order");
            System.out.println("3. Booking history");
            System.out.println("4. F&B history");
            System.out.println("0. Sign out");
            System.out.print("Enter your choice: ");

            choice = input.nextInt();
            input.nextLine();
            function.clearScreen();

            switch(choice) {

                case 1:
                    BookingSystem.loadMoviesFromFile("movies.txt");
                    BookingSystem.startBooking(username);
                    break;

                case 2:
                    fnb.orderPage(input, username);
                    break;

                case 3:
                    List<BookingRecord> history = BookingHistory.readBookingHistory("booking.txt");

                    System.out.println("\n===== YOUR BOOKING HISTORY =====\n");

                    boolean hasRecord = false;
                    for (BookingRecord record : history) {
                        if (record.user.equals(username)) {  // 只显示特定用户
                            System.out.println(record);
                            hasRecord = true;
                        }
                    }

                    if (!hasRecord) {
                        System.out.println("No booking history found for user: " + username);
                    }

                    // 等待用户按回车再返回菜单
                    function.pressEnterToContinue(input);

                    break;

                case 4:
                    fnbHistory.printFnbHistory(input, username);
                    break;

                case 0:
                    System.out.println("Thank you for using WTF Cinema!");
                    break;

                default:
                    System.out.println("Invalid choice! Try again.");
                    function.pressEnterToContinue(input);
            }

            if(choice != 0){
                function.clearScreen(); // 循环末尾再清屏
            }

        } while(choice != 0); // 正确闭合 while

    }
}