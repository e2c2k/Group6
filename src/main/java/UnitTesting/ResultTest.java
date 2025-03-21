package UnitTesting;

import java.sql.SQLException;

import Database.ResultsDataBase;

public class ResultTest {
    public static void main(String[] args) {
        ResultsDataBase db = new ResultsDataBase();

        System.out.println("===== Manual Testing for ResultsDataBase =====");

        try {
            // Establish database connection
            System.out.println("Connecting to database...");
            db.connect();
            System.out.println("SUCCESS: Database connection established.");

            // Test adding a result
            System.out.println("Test: Adding Result...");
            try {
                db.addResult(1, 12.45, 1);  // Example entry ID and result
                System.out.println("SUCCESS: Result added.");
            } catch (SQLException e) {
                System.err.println("ERROR (addResult): " + e.getMessage());
            }

            // Test removing a result
            System.out.println("Test: Removing Result...");
            try {
                db.removeResult(1);  // Change to a valid result ID in your database
                System.out.println("SUCCESS: Result removed.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeResult): " + e.getMessage());
            }

            // Test removing results from an event
            System.out.println("Test: Removing Results from Event...");
            try {
                db.removeFromEvent(1);  // Change to a valid meet ID in your database
                System.out.println("SUCCESS: Results removed from event.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeFromEvent): " + e.getMessage());
            }

            // Test exporting to CSV
            System.out.println("Test: Exporting Results to CSV...");
            try {
                db.resultCSV();
                System.out.println("SUCCESS: Results exported to CSV.");
            } catch (Exception e) {
                System.err.println("ERROR (resultCSV): " + e.getMessage());
            }

            // Test displaying the table
            System.out.println("Test: Displaying Results Table...");
            try {
                String table = db.displayTable();
                System.out.println("SUCCESS: Displayed Results Table");
                System.out.println(table);
            } catch (SQLException e) {
                System.err.println("ERROR (displayTable): " + e.getMessage());
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