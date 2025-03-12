package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class HeatsDataBase extends DataBase{
    public HeatsDataBase(){}

    public void addHeat(int eventID, int heatNum)throws SQLException{
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO = Heats (eventId,heatNumber) "+
                            "VALUES ("+eventID+","+heatNum+");";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();

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
                csvWriter.append(HeatNumber).append(",");
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
