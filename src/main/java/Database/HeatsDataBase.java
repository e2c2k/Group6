package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HeatsDataBase extends DataBase{
    public HeatsDataBase() {
        try {
            connect();
            createHeatsTable();  // Create table if it doesn't exist
            disconnect();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    public void addHeat(int eventID, int heatNum) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Heats (eventId, heatNumber) " +
                    "VALUES (?, ?)";  // Use prepared statement
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, eventID);
        pstmt.setInt(2, heatNum);
        pstmt.executeUpdate();
        pstmt.close();
    }

    public void removeHeat(int heatID)throws SQLException{
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELTE from Heats where heatId = "+heatID+";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public void removeByEvent(int eventID)throws SQLException{
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELTE from Heats where eventId = "+eventID+";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public void heatsCSV(){
        try {
            Statement stmt = conn.createStatement();

            String sql = "select * from heats";
            ResultSet rs = stmt.executeQuery(sql);

            FileWriter csvWriter = new FileWriter("OutputFiles/Heats.csv");
            
            csvWriter.append("HeatID,EventID,HeatNumber\n");

            while (rs.next()) {
                String heatID = rs.getString("HeatId");
                String eventID = rs.getString("eventId");
                String HeatNumber = rs.getString("HeatNumber");

                // Write row to CSV
                csvWriter.append(heatID).append(",");
                csvWriter.append(eventID).append(",");
                csvWriter.append(HeatNumber).append("\n");
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
        return super.displayTable("Heats");
    }
     //creates the heats table if it doesn't exist
     public void createHeatsTable() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        
        String sql = "CREATE TABLE IF NOT EXISTS Heats (" +
                    "heatId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "eventId INTEGER," +
                    "heatNumber INTEGER," +
                    "FOREIGN KEY(eventId) REFERENCES Events(eventId))";
                    
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
}
