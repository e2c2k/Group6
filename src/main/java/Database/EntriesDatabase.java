package Database;
import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EntriesDatabase extends DataBase {

    public EntriesDatabase(){}

    public void addEntry(int athleteID ,int heatID,double seedTime) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Entries (heatID,athleteID,seedTime) "+
                            "VALUES ("+heatID+","+athleteID+","+seedTime+");";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
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



}
