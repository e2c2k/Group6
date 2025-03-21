package UnitTesting;



import Database.HeatsDataBase;
import java.sql.SQLException;
import java.sql.ResultSet;

public class HeatsUnitTesting {
    public static void main(String[] args) {
        HeatsDataBase db = new HeatsDataBase();

        System.out.println("===== Manual Testing for HeatsDataBase =====");

        try {
            // Establish database connection
            System.out.println("Connecting to database...");
            db.connect();
            System.out.println("SUCCESS: Database connection established.");

            // Test adding a heat
            System.out.println("Test: Adding Heat...");
            try {
                db.addHeat(101, 1);  // Example: Event ID 101, Heat Number 1
                db.addHeat(102, 2);  // Example: Event ID 102, Heat Number 2
                System.out.println("SUCCESS: Heat added.");
            } catch (SQLException e) {
                System.err.println("ERROR (addHeat): " + e.getMessage());
            }

            // Test removing a heat by ID
            System.out.println("Test: Removing Heat by ID...");
            try {
                db.removeHeat(1);  // Change to a valid heat ID in your database
                System.out.println("SUCCESS: Heat removed.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeHeat): " + e.getMessage());
            }

            // Test removing heats by Event ID
            System.out.println("Test: Removing Heat by Event ID...");
            try {
                db.removeByEvent(102);  // Change to a valid event ID in your database
                System.out.println("SUCCESS: Heats removed by event ID.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeByEvent): " + e.getMessage());
            }

            // Test exporting heats to CSV
            System.out.println("Test: Exporting Heats to CSV...");
            try {
                db.heatsCSV();
                System.out.println("SUCCESS: Heats exported to CSV.");
            } catch (Exception e) {
                System.err.println("ERROR (heatsCSV): " + e.getMessage());
            }

            // Test displaying the table
            System.out.println("Test: Displaying Heats Table...");
            try {
                String table = db.displayTable();
                System.out.println("SUCCESS: Displayed Heats Table");
                System.out.println(table);
            } catch (SQLException e) {
                System.err.println("ERROR (displayTable): " + e.getMessage());
            }

            // Test getting all heats
            System.out.println("Test: Retrieving All Heats...");
            try {
                ResultSet rs = db.getAllHeats();
                while (rs.next()) {
                    System.out.println("Heat ID: " + rs.getInt("heatId") +
                                       ", Event ID: " + rs.getInt("eventId") +
                                       ", Heat Number: " + rs.getInt("heatNumber"));
                }
                rs.close();
            } catch (SQLException e) {
                System.err.println("ERROR (getAllHeats): " + e.getMessage());
            }

        } catch (Exception e) {
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