import java.util.Scanner;
public class menu {
    public static void mainmenu(Scanner input,String username){
        int choice = 0;
        do{
        System.out.println("=================================");
        System.out.println("         WTF CINEMA MENU         ");
        System.out.println("=================================");
        System.out.println("1. Booking Ticket");
        System.out.println("2. F&B Order");
        System.out.println("3. Settings");
        System.out.println("0. sign out");
        System.out.print("Enter your choice: ");

  choice = input.nextInt();
        input.nextLine();
        function.clearScreen();
        switch(choice){

            case 1:
                BookingSystem.startBooking(username);
                break;

            case 2:
                fnb.orderPage(input,username);
                break;

            case 3:
                System.out.println("\n--- Settings ---");
                break;

            case 0:
                System.out.println("Thank you for using WTF Cinema!");
                break;

            default:
                System.out.println("Invalid choice! Try again.");
        }

        if(choice != 0){
            function.clearScreen();
        }

    }while(choice != 0);
}
}
