package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class MeetsDatabase extends DataBase{
    public MeetsDatabase(){}

    /**
     * Add an meet to the database
     * @param meetName
     * @param meetDate
     * @throws SQLException
     */
    public void addMeet(String meetName,String meetDate) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Meets (meetName,meetDate) "+
                            "VALUES ("+meetName+","+meetDate+");";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
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
}
