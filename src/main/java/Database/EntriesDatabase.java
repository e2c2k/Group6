package Database;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class EntriesDatabase {
    private Connection conn;
    private String url = "jdbc:sqlite:Track.db";
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

    public void addEntry(int entryID,int athleteID ,int heatID,String seedTime) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sqlCommand = "INSERT INTO Entries (entryID,heatID,athleteID,seedTime) "+
                            "VALUES ("+entryID+","+heatID+","+athleteID+","+seedTime+");";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
        stmt.close();
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

        String sqlCommand = "DELETE FROM Entries WHERE athleteID = " + athleteID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
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

        String sqlCommand = "DELETE FROM Entries WHERE entryID = " + entryID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
        stmt.close();
    }



}
