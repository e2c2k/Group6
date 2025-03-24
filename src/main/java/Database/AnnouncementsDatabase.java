package Database;

import java.sql.SQLException;
import java.sql.ResultSet;
import java.sql.PreparedStatement;
import java.sql.Statement;

public class AnnouncementsDatabase extends DataBase {
    public AnnouncementsDatabase() {
        try {
            connect();
            createAnnouncementsTable();
            disconnect();
        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
        }
    }
    //creates the announcements table if it doesn't exist
    public void createAnnouncementsTable() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        
        String sql = "CREATE TABLE IF NOT EXISTS Announcements (" +
                    "announcementId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "meetId INTEGER NOT NULL," +
                    "message TEXT NOT NULL," +
                    "timestamp DATETIME DEFAULT CURRENT_TIMESTAMP," +
                    "FOREIGN KEY(meetId) REFERENCES Meets(meetId))";
                    
        Statement stmt = conn.createStatement();
        stmt.execute(sql);
        stmt.close();
    }
    //adds an announcement to the database
    public void addAnnouncement(int meetId, String message) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "INSERT INTO Announcements (meetId, message) VALUES (?, ?)";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, meetId);
        pstmt.setString(2, message);
        pstmt.executeUpdate();
        pstmt.close();
    }
    //gets the announcements by meet id
    public ResultSet getAnnouncementsByMeetId(int meetId) throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }

        String sql = "SELECT * FROM Announcements WHERE meetId = ? ORDER BY timestamp DESC";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setInt(1, meetId);
        return pstmt.executeQuery();
    }
    //displays the announcements table
    public String displayTable() throws SQLException {
        if (conn == null || conn.isClosed()) {
            throw new SQLException("Database connection is not established. Call connect() first.");
        }
        return super.displayTable("Announcements");
    }
} 