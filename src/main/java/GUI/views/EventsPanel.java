package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.EventsDatabase;
import GUI.utils.ButtonStyler;

public class EventsPanel extends JPanel {
    private JPanel contentPanel;
    private int meetId;
    private EventsDatabase eventsDB;

    public EventsPanel(int meetId) {
        this.meetId = meetId;
        setLayout(new BorderLayout());
        eventsDB = new EventsDatabase();
        
        // Top title
        JLabel eventsLabel = new JLabel("Events");
        eventsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        eventsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(eventsLabel, BorderLayout.NORTH);

        // Content panel for showing events
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        // Load events for this specific meet
        loadEventsForMeet();
    }

    private void loadEventsForMeet() {
        try {
            eventsDB.connect();
            ResultSet events = eventsDB.getEventsByMeetId(meetId);
            
            while(events.next()) {
                String eventName = events.getString("eventName");
                String eventType = events.getString("eventType");
                
                JButton eventButton = new JButton(eventName + " (" + eventType + ")");
                ButtonStyler.styleButton(eventButton);
                eventButton.addActionListener(e -> showEventDetails(eventName));
                contentPanel.add(eventButton);
            }
            eventsDB.disconnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading events: " + e.getMessage());
        }
    }

    private void showEventDetails(String eventName) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        // Event title at top
        JLabel eventTitle = new JLabel(eventName);
        eventTitle.setFont(new Font("Arial", Font.BOLD, 18));
        contentPanel.add(eventTitle, BorderLayout.NORTH);

        // Panel to hold both tables
        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        
        // Entries table
        JPanel entriesPanel = new JPanel(new BorderLayout());
        entriesPanel.add(new JLabel("Entries"), BorderLayout.NORTH);
        String[] entriesColumns = {"Athlete", "Team", "Heat"};
        Object[][] entriesData = {}; // TODO: Get entries data from database
        JTable entriesTable = new JTable(entriesData, entriesColumns);
        entriesPanel.add(new JScrollPane(entriesTable), BorderLayout.CENTER);
        
        // Results table
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(new JLabel("Results"), BorderLayout.NORTH);
        String[] resultsColumns = {"Place", "Athlete", "Team", "Result"};
        Object[][] resultsData = {}; // TODO: Get results data from database
        JTable resultsTable = new JTable(resultsData, resultsColumns);
        resultsPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        
        tablesPanel.add(entriesPanel);
        tablesPanel.add(resultsPanel);
        
        contentPanel.add(tablesPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
