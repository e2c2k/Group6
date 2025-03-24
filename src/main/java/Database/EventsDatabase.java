package Database;

import java.io.FileWriter;
import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class EventsDatabase extends DataBase{
    public EventsDatabase() {
        try {
            connect();
            createEventsTable();
            disconnect();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    
    public void addEvent(String eventName, String eventType, int meetID) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Events (eventName, eventType, meetID) VALUES (?, ?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, eventName);
        pstmt.setString(2, eventType);
        pstmt.setInt(3, meetID);
        pstmt.executeUpdate();
        pstmt.close();
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
    public String displayTable()throws SQLException{
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        return super.displayTable("Events");
    }

    public ResultSet getEventsByMeetId(int meetId) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        
        String sql = "SELECT * FROM Events WHERE meetId = ? ORDER BY eventName";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, meetId);
        return pstmt.executeQuery();
    }
    //creates the events table if it doesn't exist
    public void createEventsTable() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        
        String sql = "CREATE TABLE IF NOT EXISTS Events (" +
                    "eventId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "eventName TEXT NOT NULL," +
                    "eventType TEXT NOT NULL," +
                    "meetId INTEGER NOT NULL," +
                    "FOREIGN KEY(meetId) REFERENCES Meets(meetId))";
                    
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
}
