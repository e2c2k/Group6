package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MeetsDatabase extends DataBase{
    public MeetsDatabase() {
        try {
            connect();
            createMeetsTable();
            disconnect();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }

    /**
     * Add an meet to the database
     * @param meetName
     * @param meetDate
     * @throws SQLException
     */
    public void addMeet(String meetName, String date) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Meets (meetName, meetDate) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, meetName);
        pstmt.setString(2, date);
        pstmt.executeUpdate();
        pstmt.close();
    }
    /**
     * Remove by meet name
     * @param meetName
     * @throws SQLException
     */
    public void removeMeetName(String meetName) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sqlCommand = "DELETE FROM Meets WHERE meetName = " + meetName +";";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
        stmt.close();
    }
    /**
     * remove by date 
     * @param meetDate
     * @throws SQLException
     */
    public void removeMeetDate(String meetDate) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Meets WHERE meetName = " + meetDate + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    /**
     * remove meet by the ID
     * @param meetID
     * @throws SQLException
     */
    public void removeMeetID(int meetID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Meets WHERE meetID = " + meetID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public void meetCSV(){
        try {
            Statement stmt = conn.createStatement();

            String sql = "select * from Meets";
            ResultSet rs = stmt.executeQuery(sql);

            FileWriter csvWriter = new FileWriter("OutputFiles/Meets.csv");
            
            csvWriter.append("MeetID,MeetName,MeetDate\n");

            while (rs.next()) {
                String meetID = rs.getString("meetID");
                String meetName = rs.getString("meetName");
                String meetDate = rs.getString("meetDate");

                csvWriter.append(meetID).append(",");
                csvWriter.append(meetName).append(",");
                csvWriter.append(meetDate).append("\n");
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
        return super.displayTable("Meets");
    }

    public ResultSet getAllMeets() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        
        String sql = "SELECT * FROM Meets ORDER BY meetDate";
        Statement stmt = conn.createStatement();
        return stmt.executeQuery(sql);
    }

    //creates the meets table if it doesn't exist since Track.db isnt in version control.
    public void createMeetsTable() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        
        String sql = "CREATE TABLE IF NOT EXISTS Meets (" +
                    "meetId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "meetName TEXT NOT NULL," +
                    "meetDate TEXT NOT NULL)";
                    
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
}
