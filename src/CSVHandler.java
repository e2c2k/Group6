import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class CSVHandler {
    private static final String FILE_PATH = "src/data/users.csv"; // Updated file path

    public static List<String[]> readCSV() {
        List<String[]> data = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new FileReader(FILE_PATH))) {
            String line;
            while ((line = br.readLine()) != null) {
                data.add(line.split(","));
            }
        } catch (IOException e) {
            System.out.println("Error reading CSV: " + e.getMessage());
        }
        return data;
    }

    public static void writeCSV(List<String[]> data) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH))) {
            for (String[] row : data) {
                bw.write(String.join(",", row));
                bw.newLine();
            }
        } catch (IOException e) {
            System.out.println("Error writing CSV: " + e.getMessage());
        }
    }

    public static void addUserToCSV(String email, String password, String role) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(FILE_PATH, true))) {
            bw.write(email + "," + password + "," + role);
            bw.newLine();
        } catch (IOException e) {
            System.out.println("Error adding user to CSV: " + e.getMessage());
        }
    }
}