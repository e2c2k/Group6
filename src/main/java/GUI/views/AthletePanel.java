package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.AthletesDatabase;

public class AthletePanel extends JPanel {
    private JPanel contentPanel;
    private int meetId;
    private AthletesDatabase athletesDB;

    public AthletePanel(int meetId) {
        this.meetId = meetId;
        athletesDB = new AthletesDatabase();
        setLayout(new BorderLayout());
        
        // Title
        JLabel titleLabel = new JLabel("Athletes", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);
        
        // Content Panel
        contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        JScrollPane scrollPane = new JScrollPane(contentPanel);
        add(scrollPane, BorderLayout.CENTER);
        
        // Load athletes
        loadAthletesForMeet();
    }

    private void loadAthletesForMeet() {
        contentPanel.removeAll();
        try {
            athletesDB.connect();
            ResultSet rs = athletesDB.getAthletesByMeetId(meetId);
            while (rs.next()) {
                String name = rs.getString("surname") + ", " + rs.getString("givenName");
                String id = rs.getString("athleteId");
                String team = rs.getString("team");
                
                JPanel athleteRow = new JPanel(new FlowLayout(FlowLayout.LEFT));
                athleteRow.add(new JLabel(name + " (" + id + ") - " + team));
                contentPanel.add(athleteRow);
            }
            athletesDB.disconnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading athletes: " + e.getMessage());
        }
        contentPanel.revalidate();
        contentPanel.repaint();
    }
} 