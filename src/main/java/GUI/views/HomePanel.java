package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.MeetsDatabase;
import GUI.utils.ButtonStyler;
import GUI.Launcher;

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
        
        // Load meets from database
        loadMeets();
        
        // Add scrolling if many meets
        JScrollPane scrollPane = new JScrollPane(meetsPanel);
        scrollPane.setBorder(null);
        add(scrollPane, BorderLayout.CENTER);
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
                meetButton.addActionListener(e -> showMeetDetails(meetId));
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
        cardLayout.show(Launcher.contentPanel, "Events");
    }
}
