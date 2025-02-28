package Authentication;

import java.security.MessageDigest;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

public class AuthUtils {
    //Hashing password
    public static String hashPassword(String password) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256"); 
            byte[] passwordBytes = password.getBytes(StandardCharsets.UTF_8);
            byte[] hash = digest.digest(passwordBytes);
            return Base64.getEncoder().encodeToString(hash);
        } 
        catch(Exception e) {
            throw new RuntimeException("Error hashing password", e);
        }
    }

    //Verifying password
    public static boolean verifyPassword(String inputPassword, String storedHash) {
        String hashedInput = hashPassword(inputPassword);
        return hashedInput.equals(storedHash);
    }
}
