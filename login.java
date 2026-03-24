import java.util.*;
import java.io.*;


public class login {

    static final String FILE = "user.txt";

    public static void loginpage(){
        Scanner input = new Scanner(System.in);
        while(true){
        System.out.println("====CINIMA SYSTEM====");
        System.out.println("1. Login");
        System.out.println("2. Register");
        System.out.println("0. Exit");
        System.out.print("pls enter your choose: ");
        int userchoose = input.nextInt();
        input.nextLine();

        switch(userchoose){
            case 1:
                loginfunction(input);
                break;
            case 2:
                System.out.print("goto Register function");
                break;
            case 0:
                System.out.print("end the system");
                input.close();
                return;
            default:
                System.out.print("invalid input!");
                break;
                
        }

    }

    
    }

    public static void loginfunction(Scanner input){
        System.out.print("USER NAME: ");
        String username = input.nextLine();
        System.out.print("PASSWORD: ");
        String password = input.nextLine();
        
        List<String> user = readUsers();
                for (String line : user) {
            String[] parts = line.split(":");
            if (parts.length >= 3 && parts[0].equals(username) && parts[1].equals(password)) {
                String role = parts[2];
                function.clearScreen();
                if (role.equalsIgnoreCase("admin")) {
                    System.out.println("admin login, welcome" + username + "!");
                } else {
                    System.out.println("user login, welcome " + username + "!");
                    menu.mainmenu(input,username);
                }
                
                return;
            }
        }
        System.out.println("user or password invalid pls try again.");
    }

    
    
static List<String> readUsers() {
        List<String> users = new ArrayList<>();
        File file = new File(FILE);

        try {
            if (!file.exists()) {
                file.createNewFile();
            }

            BufferedReader br = new BufferedReader(new FileReader(file));
            String line;
            while ((line = br.readLine()) != null) {
                line = line.trim();
                if (!line.isEmpty()) {
                    users.add(line);
                }
            }
            br.close();
        } catch (IOException e) {
            System.out.println("Error reading user file: " + e.getMessage());
        }

        return users;
    }

}

