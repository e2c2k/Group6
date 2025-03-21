package UnitTesting;

import java.sql.SQLException;

import Database.EntriesDatabase;

public class EntryDataBaseTest {
    public static void main(String[] args) {
        EntriesDatabase db = new EntriesDatabase();

        System.out.println("===== Manual Testing for EntriesDatabase =====");

        try {
            // Connect to the database
            System.out.println("Connecting to database...");
            db.connect();
            System.out.println("SUCCESS: Database connection established.");

            // Test adding an entry
            System.out.println("Test: Adding Entry...");
            db.addEntry("ATH123", 1, 101, 12.45);
            System.out.println("SUCCESS: Entry added.");

            // Test removing an athlete by ID
            System.out.println("Test: Removing Athlete by ID...");
            db.removeAthleteID(123);
            System.out.println("SUCCESS: Athlete removed.");

            // Test removing an entry by ID
            System.out.println("Test: Removing Entry by ID...");
            db.removeEntryID(1);  // Change to a valid entry ID in your database
            System.out.println("SUCCESS: Entry removed.");

            // Test CSV Export
            System.out.println("Test: Exporting to CSV...");
            db.entryCSV();
            System.out.println("SUCCESS: CSV file created.");

            // Test updating result
            System.out.println("Test: Updating Result...");
            db.updateResult("ATH123", 101, 10.5, 1);
            System.out.println("SUCCESS: Result updated.");

        } catch (SQLException e) {
            System.err.println("ERROR: " + e.getMessage());
        } finally {
            try {
                db.disconnect();
                System.out.println("SUCCESS: Database connection closed.");
            } catch (SQLException e) {
                System.err.println("ERROR closing database: " + e.getMessage());
            }
        }

        System.out.println("===== Manual Testing Complete =====");
    }
}