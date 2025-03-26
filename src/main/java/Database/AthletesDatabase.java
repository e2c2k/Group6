package Database;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;



public class AthletesDatabase extends DataBase{
    public AthletesDatabase() {
        try {
            connect();
            createAthletesTable();
            disconnect();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Add an athlete to the database
     * @param surname
     * @param givenName
     * @param id
     * @param dob
     * @param team
     * @param meetId
     * @param emergencyName
     * @param emergencyPhone
     * @param emergencyRelation
     * @throws SQLException
     */
    public void addAthlete(String surname, String givenName, String id, String dob, String team, int meetId, String emergencyName, String emergencyPhone, String emergencyRelation) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Athletes (surname, givenName, athleteId, dob, team, meetId, emergencyName, emergencyPhone, emergencyRelation) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, surname);
        pstmt.setString(2, givenName);
        pstmt.setString(3, id);
        pstmt.setString(4, dob);
        pstmt.setString(5, team);
        pstmt.setInt(6, meetId);
        pstmt.setString(7, emergencyName);
        pstmt.setString(8, emergencyPhone);
        pstmt.setString(9, emergencyRelation);
        pstmt.executeUpdate();
        pstmt.close();
    }
    /**
     * Remove by full name
     * @param surname
     * @param givenName
     * @throws SQLException
     */
    public void removeAthlete(String surname, String givenName) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Athletes WHERE surname = " + surname + " AND givenName = " + givenName + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    /**
     * remove by AthleteID
     * @param athleteID
     * @throws SQLException
     */
    public void removeAthlete(int athleteID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Athletes WHERE athleteID = " + athleteID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    /**
     * 
     * @param team
     * @throws SQLException
     */
    public void removeAthlete(String team) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Athletes WHERE team = " + team + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public void athleteCSV(){
        try {
            Statement stmt = conn.createStatement();

            String sql = "select * from Athletes";
            ResultSet rs = stmt.executeQuery(sql);

            FileWriter csvWriter = new FileWriter("OutputFiles/Athletes.csv");
            
            csvWriter.append("AthleteID,Surname,GivenName,Team,DOB\n");

            while (rs.next()) {
                String athleteID = rs.getString("athleteID");
                String surname = rs.getString("surname");
                String givenName = rs.getString("givenName");
                String team = rs.getString("team");
                String dob = rs.getString("dob");
                String meetID = rs.getString("meetId");
                String emergName = rs.getString("emergencyName");
                String emergePhone = rs.getString("emergencyPhone");
                String emergeRel = rs.getString("emergencyRelation");

                // Write row to CSV
                csvWriter.append(athleteID).append(",");
                csvWriter.append(surname).append(",");
                csvWriter.append(givenName).append(",");
                csvWriter.append(team).append(",");
                csvWriter.append(dob).append(",");
                csvWriter.append(meetID).append(",");
                csvWriter.append(emergName).append(",");
                csvWriter.append(emergePhone).append(",");
                csvWriter.append(emergeRel).append("\n");



            }

            csvWriter.flush();
            csvWriter.close();
            rs.close();
            stmt.close();


        } catch (SQLException e) {
            System.err.println("database error: " + e.getMessage());
        } catch (IOException e) {
            System.err.println("Error with  csv file: " + e.getMessage());
        }
    }

    public String displayTable()throws SQLException{
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        return super.displayTable("Athletes");
    }
    //gets all athletes by meet id
    public ResultSet getAthletesByMeetId(int meetId) throws SQLException {
        String sql = "SELECT * FROM Athletes WHERE meetId = ? ORDER BY surname, givenName";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, meetId);
        return pstmt.executeQuery();
    }
     //creates the athletes table if it doesn't exist since 
    public void createAthletesTable() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        
        String sql = "CREATE TABLE IF NOT EXISTS Athletes (" +
                    "athleteId TEXT PRIMARY KEY," + 
                    "surname TEXT NOT NULL," +
                    "givenName TEXT NOT NULL," +
                    "dob TEXT NOT NULL," +
                    "team TEXT NOT NULL," +
                    "meetId INTEGER NOT NULL," +
                    "emergencyName TEXT NOT NULL," +
                    "emergencyPhone TEXT NOT NULL," +
                    "emergencyRelation TEXT NOT NULL," +
                    "FOREIGN KEY(meetId) REFERENCES Meets(meetId))";
                    
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
}
