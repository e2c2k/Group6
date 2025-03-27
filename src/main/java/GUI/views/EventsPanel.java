package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import Database.EventsDatabase;
import GUI.utils.ButtonStyler;
import javax.swing.table.DefaultTableModel;
import Database.EntriesDatabase;

public class EventsPanel extends JPanel {
    private JPanel contentPanel;
    private int meetId;
    private EventsDatabase eventsDB;
    private JTable entriesTable;
    private JTable resultsTable;
    private DefaultTableModel entriesModel;
    private DefaultTableModel resultsModel;
    private EntriesDatabase entriesDB;

    public EventsPanel(int meetId) {
        this.meetId = meetId;
        setLayout(new BorderLayout());
        eventsDB = new EventsDatabase();
        entriesDB = new EntriesDatabase();
        
        
        JLabel eventsLabel = new JLabel("Events");
        eventsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        eventsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(eventsLabel, BorderLayout.NORTH);

        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);

        loadEventsForMeet();
    }

    private void loadEventsForMeet() {
        try {
            eventsDB.connect();
            ResultSet events = eventsDB.getEventsByMeetId(meetId);
            
            while(events.next()) {
                String eventName = events.getString("eventName");
                String eventType = events.getString("eventType");
                int eventId = events.getInt("eventId");
                
                JButton eventButton = new JButton(eventName + " (" + eventType + ")");
                ButtonStyler.styleButton(eventButton);
                eventButton.addActionListener(e -> {
                    showEventDetails(eventName, eventId);
                });
                contentPanel.add(eventButton);
            }
            eventsDB.disconnect();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading events: " + e.getMessage());
        }
    }

    public void showEventDetails(String eventName, int eventId) {
        contentPanel.removeAll();
        contentPanel.setLayout(new BorderLayout());

        
        JLabel eventTitle = new JLabel(eventName);
        eventTitle.setFont(new Font("Arial", Font.BOLD, 18));
        contentPanel.add(eventTitle, BorderLayout.NORTH);

        JPanel tablesPanel = new JPanel(new GridLayout(2, 1, 0, 10));
        
        // Entries table
        JPanel entriesPanel = new JPanel(new BorderLayout());
        entriesPanel.add(new JLabel("Entries"), BorderLayout.NORTH);
        String[] entriesColumns = {"Athlete", "Team", "Heat", "Seed Time"};
        entriesModel = new DefaultTableModel(entriesColumns, 0);
        entriesTable = new JTable(entriesModel);
        entriesPanel.add(new JScrollPane(entriesTable), BorderLayout.CENTER);
        
        // Results table
        JPanel resultsPanel = new JPanel(new BorderLayout());
        resultsPanel.add(new JLabel("Results"), BorderLayout.NORTH);
        String[] resultsColumns = {"Place", "Athlete", "Team", "Result"};
        resultsModel = new DefaultTableModel(resultsColumns, 0);
        resultsTable = new JTable(resultsModel);
        resultsPanel.add(new JScrollPane(resultsTable), BorderLayout.CENTER);
        
        tablesPanel.add(entriesPanel);
        tablesPanel.add(resultsPanel);
        
        contentPanel.add(tablesPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();

        // Clear and update entries table
        entriesModel.setRowCount(0);
        try {
            entriesDB.connect();
            ResultSet entries = entriesDB.getEntriesByEventId(eventId);
            while(entries.next()) {
                String athlete = entries.getString("surname") + ", " + entries.getString("givenName");
                String team = entries.getString("team");
                int heat = entries.getInt("heatId");
                double seedTime = entries.getDouble("seedTime");
                String formattedTime = formatTime(seedTime);
                entriesModel.addRow(new Object[]{athlete, team, heat, formattedTime});
            }
            
            // Clear results table
            resultsModel.setRowCount(0);
            
            
            ResultSet results = entriesDB.getResultsByEventId(eventId);
            while(results.next()) {
                int place = results.getInt("place");
                String athlete = results.getString("surname") + ", " + results.getString("givenName");
                String team = results.getString("team");
                double result = results.getDouble("result");
                String formattedResult = formatTime(result);
                resultsModel.addRow(new Object[]{place, athlete, team, formattedResult});
            }
            
            entriesDB.disconnect(); 
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error loading data: " + e.getMessage());
        }
    }

    private String formatTime(double seconds) {
        int minutes = (int) (seconds / 60);
        double remainingSeconds = seconds % 60;
        return String.format("%d:%05.2f", minutes, remainingSeconds);
    }
}
