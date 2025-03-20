package Database;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntriesDatabase extends DataBase {

    public EntriesDatabase() {
        try {
            connect();
            createEntriesTable();  // Create table if it doesn't exist
            disconnect();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    public void addEntry(String athleteId, int heatID, int eventId, double seedTime) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        // Check number of athletes in heat
        String countSql = "SELECT COUNT(*) as count FROM Entries WHERE heatID = ?";
        PreparedStatement countStmt = conn.prepareStatement(countSql);
        countStmt.setInt(1, heatID);
        ResultSet rs = countStmt.executeQuery();
        
        if (rs.next() && rs.getInt("count") >= 8) {
            throw new SQLException("Heat is full (maximum 8 athletes per heat)");
        }

        // If not full, add the entry
        String sql = "INSERT INTO Entries (heatID, athleteID, eventId, seedTime) VALUES (?, ?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, heatID);
        pstmt.setString(2, athleteId);
        pstmt.setInt(3, eventId);
        pstmt.setDouble(4, seedTime);
        pstmt.executeUpdate();
        pstmt.close();
    }
    /**
     * remove by AthleteID
     * @param athleteID
     * @throws SQLException
     */
    public void removeAthleteID(int athleteID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Entries WHERE athleteID = " + athleteID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    /**
     * remove by entryID
     * @param entryID
     * @throws SQLException
     */
    public void removeEntryID(int entryID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Entries WHERE entryID = " + entryID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public void entryCSV(){
        try {
            Statement stmt = conn.createStatement();

            String sql = "select * from Entries";
            ResultSet rs = stmt.executeQuery(sql);

            FileWriter csvWriter = new FileWriter("OutputFiles/Entries.csv");
            
            csvWriter.append("EntryID,HeatID,athleteID,seedTime\n");

            while (rs.next()) {
                String entryID = rs.getString("EntryID");
                String heatID = rs.getString("HeatID");
                String athleteID = rs.getString("athleteID");
                String seedTime = rs.getString("seedTime");

                csvWriter.append(entryID).append(",");
                csvWriter.append(heatID).append(",");
                csvWriter.append(athleteID).append(",");
                csvWriter.append(seedTime).append("\n");
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
        return super.displayTable("Entries");
    }
     //creates the entries table if it doesn't exist since Track.db isnt in version control.
    public void createEntriesTable() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        
        String sql = "CREATE TABLE IF NOT EXISTS Entries (" +
                    "entryId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "athleteId TEXT," +
                    "heatId INTEGER," +
                    "eventId INTEGER," +
                    "seedTime DOUBLE," +
                    "result DOUBLE," +
                    "place INTEGER," +
                    "FOREIGN KEY(athleteId) REFERENCES Athletes(athleteId)," +
                    "FOREIGN KEY(heatId) REFERENCES Heats(heatId)," +
                    "FOREIGN KEY(eventId) REFERENCES Events(eventId))";
                    
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public ResultSet getEntriesByEventId(int eventId) throws SQLException {
        String sql = "SELECT Entries.*, Athletes.surname, Athletes.givenName, Athletes.team " +
                    "FROM Entries " +
                    "JOIN Athletes ON Athletes.athleteId = Entries.athleteId " +
                    "WHERE Entries.eventId = ?";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, eventId);
        return pstmt.executeQuery();
    }

    public ResultSet getResultsByEventId(int eventId) throws SQLException {
        String sql = "SELECT Entries.place, Athletes.surname, Athletes.givenName, Athletes.team, Entries.result " +
                    "FROM Entries " +
                    "JOIN Athletes ON Athletes.athleteId = Entries.athleteId " +
                    "WHERE Entries.eventId = ? AND Entries.place IS NOT NULL " +
                    "ORDER BY Entries.place ASC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, eventId);
        return pstmt.executeQuery();
    }

    public void updateResult(String athleteId, int eventId, double result, int place) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        // Update existing entry with new result and place
        String sql = "UPDATE Entries " +
                    "SET result = ?, place = ? " +
                    "WHERE athleteId = ? AND eventId = ?";
                    
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setDouble(1, result);
        pstmt.setInt(2, place);
        pstmt.setString(3, athleteId);
        pstmt.setInt(4, eventId);
        pstmt.executeUpdate();
        pstmt.close();
    }

}
