package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.MeetsDatabase;
import GUI.utils.ButtonStyler;
import GUI.Launcher;
import Database.AnnouncementsDatabase;


public class HomePanel extends JPanel {
    private JPanel meetsPanel;
    private MeetsDatabase meetsDB;

    public HomePanel() {
        setLayout(new BorderLayout());
        
        // Title at top
        JLabel titleLabel = new JLabel("Track & Field Meets", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        add(titleLabel, BorderLayout.NORTH);

        // Panel for meet buttons
        meetsPanel = new JPanel();
        meetsPanel.setLayout(new BoxLayout(meetsPanel, BoxLayout.Y_AXIS));
        meetsPanel.setBackground(Color.WHITE);
        
        loadMeets();
        add(meetsPanel, BorderLayout.CENTER);
    }

    private void loadMeets() {
        try {
            meetsDB = new MeetsDatabase();
            meetsDB.connect();
            ResultSet meets = meetsDB.getAllMeets();
            
            while(meets.next()) {
                String meetName = meets.getString("meetName");
                String meetDate = meets.getString("meetDate");
                int meetId = meets.getInt("meetId");
                
                // Create button with meet name and date
                JButton meetButton = new JButton(meetName + " - " + meetDate);
                meetButton.addActionListener(e -> {
                    showMeetDetails(meetId);
                });
                ButtonStyler.styleButton(meetButton);
                meetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
                meetButton.setMaximumSize(new Dimension(300, 40));
                
                meetsPanel.add(Box.createRigidArea(new Dimension(0, 10)));
                meetsPanel.add(meetButton);
            }
            meetsDB.disconnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading meets: " + e.getMessage());
        }
    }

    private void showMeetDetails(int meetId) {
        CardLayout cardLayout = (CardLayout) Launcher.contentPanel.getLayout();
        Launcher.contentPanel.add(new EventsPanel(meetId), "Events");
        Launcher.contentPanel.add(new AthletePanel(meetId), "Athletes");
        
        // Load announcements for this meet
        Launcher.announcementsPanel.removeAll();
        try {
            AnnouncementsDatabase db = new AnnouncementsDatabase();
            db.connect();
            ResultSet rs = db.getAnnouncementsByMeetId(meetId);
            while (rs.next()) {
                String message = rs.getString("message");
                String timestamp = rs.getString("timestamp");
                JLabel label = new JLabel(timestamp + ": " + message);
                Launcher.announcementsPanel.add(label);
            }
            db.disconnect();
            Launcher.announcementsPanel.revalidate();
            Launcher.announcementsPanel.repaint();
        } catch (SQLException e) {
            System.err.println("Error loading announcements: " + e.getMessage());
        }
        
        cardLayout.show(Launcher.contentPanel, "Events");
    }
}
