package Models;


import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class AthleteFileWriter {
    private static final String FILE_PATH = "athletes.txt";

    public static void writeAthletesToFile(List<Athlete> athletes) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE_PATH))) {
            writer.write("===== Athlete Records =====\n");
            for (Athlete athlete : athletes) {
                writer.write(athlete.formatForFile() + "\n");
            }
            System.out.println("Athlete data successfully written to " + FILE_PATH);
        } catch (IOException e) {
            System.err.println("Error writing to file: " + e.getMessage());
        }
    }
}
