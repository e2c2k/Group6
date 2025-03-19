package Database;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntriesDatabase extends DataBase {

    public EntriesDatabase(){}

    public void addEntry(int athleteID, int heatID, double seedTime) throws SQLException {
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
        String sql = "INSERT INTO Entries (heatID, athleteID, seedTime) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, heatID);
        pstmt.setInt(2, athleteID);
        pstmt.setDouble(3, seedTime);
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
                    "seedTime DOUBLE," +
                    "FOREIGN KEY(athleteId) REFERENCES Athletes(athleteId)," +
                    "FOREIGN KEY(heatId) REFERENCES Heats(heatId))";
                    
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

}
