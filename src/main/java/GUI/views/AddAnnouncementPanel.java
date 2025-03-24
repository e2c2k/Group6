package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.sql.ResultSet;
import Database.AnnouncementsDatabase;
import GUI.Launcher;

public class AddAnnouncementPanel extends JPanel {
    private JTextArea messageArea;
    private JTextField meetIdField;
    private AnnouncementsDatabase announcementsDB;

    public AddAnnouncementPanel(int meetId) {
        this.announcementsDB = new AnnouncementsDatabase();
        setLayout(new BorderLayout());

        JPanel topPanel = new JPanel(new BorderLayout());
        JLabel titleLabel = new JLabel("Add Announcement", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        
        JPanel meetIdPanel = new JPanel(new FlowLayout());
        JLabel meetIdLabel = new JLabel("Meet ID: ");
        meetIdField = new JTextField(10);
        meetIdPanel.add(meetIdLabel);
        meetIdPanel.add(meetIdField);
        topPanel.add(meetIdPanel, BorderLayout.CENTER);
        
        add(topPanel, BorderLayout.NORTH);

        //panel for message
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        messageArea = new JTextArea(5, 40);
        messageArea.setLineWrap(true);
        messageArea.setWrapStyleWord(true);
        centerPanel.add(messageArea, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JButton submitButton = new JButton("Post Announcement");
        submitButton.addActionListener(e -> postAnnouncement());
        buttonPanel.add(submitButton);
        centerPanel.add(buttonPanel, BorderLayout.SOUTH);

        add(centerPanel, BorderLayout.CENTER);
    }

    private void postAnnouncement() {
        String message = messageArea.getText().trim();
        int meetId;
        
        try {
            meetId = Integer.parseInt(meetIdField.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Please enter a valid Meet ID");
            return;
        }

        if (message.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter an announcement message");
            return;
        }

        try {
            announcementsDB.connect();
            announcementsDB.addAnnouncement(meetId, message);
            announcementsDB.disconnect();
            messageArea.setText("");
            JOptionPane.showMessageDialog(this, "Announcement posted successfully!");
            
            // Update announcements display
            Launcher.announcementsPanel.removeAll();
            AnnouncementsDatabase db = new AnnouncementsDatabase();
            db.connect();
            ResultSet rs = db.getAnnouncementsByMeetId(meetId);
            while (rs.next()) {
                String msg = rs.getString("message");
                String timestamp = rs.getString("timestamp");
                JLabel label = new JLabel(timestamp + ": " + msg);
                Launcher.announcementsPanel.add(label);
            }
            db.disconnect();
            Launcher.announcementsPanel.revalidate();
            Launcher.announcementsPanel.repaint();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error posting announcement: " + e.getMessage());
        }
    }
} 