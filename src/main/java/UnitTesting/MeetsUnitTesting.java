package UnitTesting;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.MeetsDatabase;

public class MeetsUnitTesting {
    public static void main(String[] args) {
        MeetsDatabase db = new MeetsDatabase();

        System.out.println("===== Manual Testing for MeetsDatabase =====");

        try {
            // Establish database connection
            System.out.println("Connecting to database...");
            db.connect();
            System.out.println("SUCCESS: Database connection established.");

            // Test adding a meet
            System.out.println("Test: Adding Meet...");
            try {
                db.addMeet("Spring Meet", "2025-04-15");
                System.out.println("SUCCESS: Meet added.");
            } catch (SQLException e) {
                System.err.println("ERROR (addMeet): " + e.getMessage());
            }

            // Test removing a meet by name
            System.out.println("Test: Removing Meet by Name...");
            try {
                db.removeMeetName("Spring Meet");
                System.out.println("SUCCESS: Meet removed by name.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeMeetName): " + e.getMessage());
            }

            // Test removing a meet by date
            System.out.println("Test: Removing Meet by Date...");
            try {
                db.addMeet("Winter Meet", "2025-12-01");
                db.removeMeetDate("2025-12-01");
                System.out.println("SUCCESS: Meet removed by date.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeMeetDate): " + e.getMessage());
            }

            // Test removing a meet by ID
            System.out.println("Test: Removing Meet by ID...");
            try {
                db.addMeet("Summer Meet", "2025-06-20");
                db.removeMeetID(1); // Adjust the ID according to your data
                System.out.println("SUCCESS: Meet removed by ID.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeMeetID): " + e.getMessage());
            }

            // Test exporting meets to CSV
            System.out.println("Test: Exporting Meets to CSV...");
            try {
                db.meetCSV();
                System.out.println("SUCCESS: Meets exported to CSV.");
            } catch (Exception e) {
                System.err.println("ERROR (meetCSV): " + e.getMessage());
            }

            // Test displaying the table
            System.out.println("Test: Displaying Meets Table...");
            try {
                String table = db.displayTable();
                System.out.println("SUCCESS: Displayed Meets Table");
                System.out.println(table);
            } catch (SQLException e) {
                System.err.println("ERROR (displayTable): " + e.getMessage());
            }

            // Test getting all meets
            System.out.println("Test: Retrieving All Meets...");
            try {
                ResultSet rs = db.getAllMeets();
                while (rs.next()) {
                    System.out.println("Meet ID: " + rs.getInt("meetId") +
                                       ", Name: " + rs.getString("meetName") +
                                       ", Date: " + rs.getString("meetDate"));
                }
                rs.close();
            } catch (SQLException e) {
                System.err.println("ERROR (getAllMeets): " + e.getMessage());
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