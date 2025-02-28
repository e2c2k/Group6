package Database;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class AthletesDatabase {
    private Connection conn;
    private String url = "jdbc:sqlite:Track.db";

    public AthletesDatabase() {
        
    }

    // Connect to the database
    public void connect() throws SQLException {
        try{
            conn = DriverManager.getConnection(url);
        }catch(Exception e){
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Disconnect from the database
    public void disconnect() throws SQLException {
        if (conn != null && !conn.isClosed()) {
            conn.close();
        }
    }

    /**
     * Add an athlete to the database
     * @param surname
     * @param givenName
     * @param team
     * @param dob
     * @throws SQLException
     */
    public void addAthlete(String surname,String givenName ,String team,String dob) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sqlCommand = "INSERT INTO Athletes (surname,givenName,team,dob) "+
                            "VALUES ("+surname+","+givenName+","+team+","+dob+");";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
        stmt.close();
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

        String sqlCommand = "DELETE FROM Athletes WHERE surname = " + surname + " AND givenName = " + givenName + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
        stmt.close();
    }
    /**
     * remove by AthleteID
     * @param surname
     * @param givenName
     * @throws SQLException
     */
    public void removeAthlete(int athleteID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sqlCommand = "DELETE FROM Athletes WHERE athleteID = " + athleteID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
        stmt.close();
    }

    public void removeAthlete(String team) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sqlCommand = "DELETE FROM Athletes WHERE team = " + team + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
        stmt.close();
    }

    public void AthleteCSV() throws SQLException, IOException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "SELECT * FROM Athletes";
        try (Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery(sql);
            FileWriter csvWriter = new FileWriter("OutputFiles/athletes.csv")) {

            csvWriter.append("athleteId,surname,givenName,team,dob\n");

            while (rs.next()) {
                String athleteId = String.valueOf(rs.getInt("athleteId"));
                String surname = rs.getString("surname");
                String givenName = rs.getString("givenName");
                String team = rs.getString("team") != null ? rs.getString("team") : "";
                String dob = rs.getString("dob") != null ? rs.getString("dob") : "";

                csvWriter.append(String.format("%s,\"%s\",\"%s\",\"%s\",\"%s\"\n",
                athleteId, escapeCSV(surname), escapeCSV(givenName), escapeCSV(team), escapeCSV(dob)));
            }
            System.out.println("Athletes exported to OutputFiles/athletes.csv");
        }
    }

    private String escapeCSV(String value) {
        if (value == null) return "";
        return value.replace("\"", "\"\""); // Double quotes for CSV compliance
    }
}
