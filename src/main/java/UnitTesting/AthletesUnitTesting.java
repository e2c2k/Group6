package UnitTesting;

import java.sql.ResultSet;
import java.sql.SQLException;

import Database.AthletesDatabase;

public class AthletesUnitTesting {
    public static void main(String[] args) {
        AthletesDatabase db = new AthletesDatabase();

        System.out.println("===== Manual Testing for AthletesDatabase =====");

        try {
            // Establish database connection
            System.out.println("Connecting to database...");
            db.connect();
            System.out.println("SUCCESS: Database connection established.");

            // Test adding an athlete
            System.out.println("Test: Adding Athlete...");
            try {
                db.addAthlete("Smith", "John", "A001", "1990-05-15", "TeamA", 1);
                db.addAthlete("Doe", "Jane", "A002", "1995-08-20", "TeamB", 1);
                System.out.println("SUCCESS: Athletes added.");
            } catch (SQLException e) {
                System.err.println("ERROR (addAthlete): " + e.getMessage());
            }

            // Test removing an athlete by ID
            System.out.println("Test: Removing Athlete by ID...");
            try {
                db.removeAthlete("A001");
                System.out.println("SUCCESS: Athlete removed by ID.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeAthlete by ID): " + e.getMessage());
            }

            // Test removing an athlete by full name
            System.out.println("Test: Removing Athlete by Full Name...");
            try {
                db.removeAthlete("Smith", "John");
                System.out.println("SUCCESS: Athlete removed by full name.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeAthlete by full name): " + e.getMessage());
            }

            // Test removing athletes by team
            System.out.println("Test: Removing Athletes by Team...");
            try {
                db.removeAthlete("TeamA");
                System.out.println("SUCCESS: Athletes removed by team.");
            } catch (SQLException e) {
                System.err.println("ERROR (removeAthlete by team): " + e.getMessage());
            }

            // Test exporting athletes to CSV
            System.out.println("Test: Exporting Athletes to CSV...");
            try {
                db.athleteCSV();
                System.out.println("SUCCESS: Athletes exported to CSV.");
            } catch (Exception e) {
                System.err.println("ERROR (athleteCSV): " + e.getMessage());
            }

            // Test displaying the table
            System.out.println("Test: Displaying Athletes Table...");
            try {
                String table = db.displayTable();
                System.out.println("SUCCESS: Displayed Athletes Table");
                System.out.println(table);
            } catch (SQLException e) {
                System.err.println("ERROR (displayTable): " + e.getMessage());
            }

            // Test retrieving athletes by meet ID
            System.out.println("Test: Retrieving Athletes by Meet ID...");
            try {
                ResultSet rs = db.getAthletesByMeetId(1);  // Change the meet ID as needed
                while (rs.next()) {
                    System.out.println("Athlete ID: " + rs.getString("athleteId") +
                                       ", Surname: " + rs.getString("surname") +
                                       ", Given Name: " + rs.getString("givenName") +
                                       ", DOB: " + rs.getString("dob") +
                                       ", Team: " + rs.getString("team"));
                }
                rs.close();
            } catch (SQLException e) {
                System.err.println("ERROR (getAthletesByMeetId): " + e.getMessage());
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