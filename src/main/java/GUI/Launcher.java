package GUI;
import javax.swing.JFrame;
import javax.swing.JPanel;
import Authentication.AuthMethods;

public class Launcher {
    public static void main(String[] args) {
        // Test adding user
        AuthMethods.addUser("sam", "password123");
        
        // Test correct login
        boolean validLogin = AuthMethods.verifyUser("sam", "password123");
        System.out.println("Valid login: " + validLogin);  // Should print true
        
        // Test wrong password
        boolean wrongPass = AuthMethods.verifyUser("sam", "wrong");
        System.out.println("Wrong password: " + wrongPass);  // Should print false
        
        // Test non-existent user
        boolean noUser = AuthMethods.verifyUser("nobody", "password123");
        System.out.println("Non-existent user: " + noUser);  // Should print false
    }
    
}
