package Database;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
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

    public void addEntry(int athleteID ,int heatID,double seedTime) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sqlCommand = "INSERT INTO Entries (heatID,athleteID,seedTime) "+
                            "VALUES ("+heatID+","+athleteID+","+seedTime+");";
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

    public void entryCSV(){
        try {
            // Create statement from existing connection
            Statement stmt = conn.createStatement();

            String sql = "select * from Entries";
            ResultSet rs = stmt.executeQuery(sql);

            // Create CSV file
            FileWriter csvWriter = new FileWriter("OutputFiles/Entries.csv");
            
            // Write CSV header
            csvWriter.append("EntryID,HeatID,athleteID,seedTime\n");

            // Write data rows
            while (rs.next()) {
                String entryID = rs.getString("EntryID");
                String heatID = rs.getString("HeatID");
                String athleteID = rs.getString("athleteID");
                String seedTime = rs.getString("seedTime");

                // Write row to CSV
                csvWriter.append(entryID).append(",");
                csvWriter.append(heatID).append(",");
                csvWriter.append(athleteID).append(",");
                csvWriter.append(seedTime).append("\n");
            }

            // Clean up resources
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



}
