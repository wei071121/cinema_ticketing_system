import java.io.*;
import java.util.*;

public class ForgotPassword {

    public static void forgotPassword(String fileName, Scanner input) {

        try {
            BufferedReader br = new BufferedReader(new FileReader(fileName));

            System.out.print("Enter username: ");
            String username = input.nextLine();

            String line;
            boolean found = false;
            String password = null;

            // 找用户
            while ((line = br.readLine()) != null) {

                String[] parts = line.split(":");

                if (parts.length == 3 && parts[0].equals(username)) {
                    found = true;
                    password = parts[1];
                    break;
                }
            }

            br.close();

            if (!found) {
                System.out.println("User not found!");
                return;
            }

            // 输入 6 位数（不验证内容，只检查长度）
            System.out.print("Enter 6-digit code: ");
            String code = input.nextLine();

            // check length + is numeric
            if (code.matches("\\d{6}")) {
                System.out.println("Verification success!");
                System.out.println("Your password is: " + password);
            } else {
                System.out.println("Invalid code! Must be 6 digits.");
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }
}