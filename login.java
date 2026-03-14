import java.util.*;
import java.io.*;


public class login {
    public static void loginpage(){
        Scanner input = new Scanner(System.in);
        while(true){
        System.out.println("====CINIMA SYSTEM====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.print("pls enter your choose: ");
        int userchoose = input.nextInt();

        switch(userchoose){
            case 1:
                System.out.print("goto login function");
            case 2:
                System.out.print("goto Register function");
            case 3:
                System.out.print("end the code");
        }
        input.close();
    }
    
    }

    public static void login(Scanner input){
        System.out.print("USER NAME: ");
        String username = input.nextLine();
        System.out.print("PASSWORD: ");
        String password = input.nextLine();
        

    }
    
}
