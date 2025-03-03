package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventsDatabase extends DataBase{
    public EventsDatabase(){}
    
    public void addEvent(String eventName ,String eventType,int meetID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sqlCommand = "INSERT INTO Events (eventName,eventType,meetID) "+
                            "VALUES ("+eventName+","+eventType+","+meetID+");";
        Statement stmt = conn.createStatement();
        stmt.execute(sqlCommand);
        stmt.close();
    }
    /**
     * remove by event Name
     * @param eventName
     * @throws SQLException
     */
    public void removeEventName(String eventName) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Events WHERE eventName = " + eventName + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    public void removeEventType(String eventType) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Events WHERE eventType = " + eventType + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    /**
     * remove by eventID
     * @param eventID
     * @throws SQLException
     */
    public void removeEventID(int eventID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Events WHERE eventID = " + eventID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    public void removeMeetID(int meetID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "DELETE FROM Events WHERE meetID = " + meetID + ";";
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }

    public void eventsCSV(){
        try {
            Statement stmt = conn.createStatement();

            String sql = "select * from Events";
            ResultSet rs = stmt.executeQuery(sql);

            FileWriter csvWriter = new FileWriter("OutputFiles/Events.csv");
            
            csvWriter.append("EventID,EventName,EventType,MeetID\n");

            while (rs.next()) {
                String eventID = rs.getString("eventID");
                String eventName = rs.getString("eventName");
                String eventType = rs.getString("eventType");
                String meetID = rs.getString("meetID");

                csvWriter.append(eventID).append(",");
                csvWriter.append(eventName).append(",");
                csvWriter.append(eventType).append(",");
                csvWriter.append(meetID).append("\n");
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
