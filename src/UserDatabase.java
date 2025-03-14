import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class UserDatabase {
    private static final String FILE_PATH = "data/users.csv";  // Ensure correct path
    private static Map<String, String[]> users = new HashMap<>();

    static {
        ensureFileExists();  // Ensure file exists before loading
        loadUsers();
    }

    private static void ensureFileExists() {
        File file = new File(FILE_PATH);
        if (!file.exists()) {
            try {
                File dir = new File("data");
                if (!dir.exists()) {
                    dir.mkdir(); // Create data directory if missing
                }
                file.createNewFile();
                System.out.println("Created new users.csv file at " + FILE_PATH);

                // Initialize file with headers
                try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
                    bw.write("email,password,role");
                    bw.newLine();
                }
            } catch (IOException e) {
                System.out.println("Error creating users.csv: " + e.getMessage());
            }
        }
    }

    private static void loadUsers() {
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");
                if (parts.length == 3 && !parts[0].equals("email")) { // Skip headers
                    users.put(parts[0], new String[]{parts[1], parts[2]});
                }
            }
        } catch (IOException e) {
            System.out.println("Error loading users: " + e.getMessage());
        }
    }

    public static boolean verifyUser(String email, String password, String requiredRole) {
        if (users.containsKey(email)) {
            String[] userData = users.get(email);
            System.out.println("Checking email: " + email);
            System.out.println("Stored password: " + userData[0]);
            System.out.println("Entered password: " + password);
            System.out.println("Stored role: " + userData[1]);
            System.out.println("Required role: " + requiredRole);
    
            boolean passwordMatches = userData[0].equals(password);
            boolean roleMatches = userData[1].equals(requiredRole);
    
            System.out.println("Password Match: " + passwordMatches);
            System.out.println("Role Match: " + roleMatches);
    
            return passwordMatches && roleMatches;
        }
        System.out.println("Email not found: " + email);
        return false;
    }

    public static void addUser(String email, String password, String role) {
        users.put(email, new String[]{password, role});
        saveUsers();
    }

    private static void saveUsers() {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            bw.write("email,password,role");
            bw.newLine();
            for (Map.Entry<String, String[]> entry : users.entrySet()) {
                bw.write(entry.getKey() + "," + entry.getValue()[0] + "," + entry.getValue()[1]);
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error saving users: " + e.getMessage());
        }
    }
}