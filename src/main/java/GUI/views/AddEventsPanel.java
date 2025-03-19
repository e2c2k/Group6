package GUI.views;

import javax.swing.*;
import java.awt.*;
import Database.EventsDatabase;
import GUI.utils.ButtonStyler;
import java.sql.SQLException;

public class AddEventsPanel extends JPanel {
    private JTextField eventNameField;
    private JTextField eventTypeField;
    private JTextField meetIDField;
    private EventsDatabase eventsDB;

    public AddEventsPanel() {
        eventsDB = new EventsDatabase();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        
        // Event Name
        gbc.gridx = 0; gbc.gridy = 0;
        add(new JLabel("Event Name:"), gbc);
        gbc.gridx = 1;
        eventNameField = new JTextField(20);
        add(eventNameField, gbc);
        
        // Event Type
        gbc.gridx = 0; gbc.gridy = 1;
        add(new JLabel("Event Type:"), gbc);
        gbc.gridx = 1;
        eventTypeField = new JTextField(20);
        add(eventTypeField, gbc);

        // Meet ID
        gbc.gridx = 0; gbc.gridy = 2;
        add(new JLabel("Meet ID:"), gbc);
        gbc.gridx = 1;
        meetIDField = new JTextField(20);
        add(meetIDField, gbc);
        
        // Add Button
        gbc.gridx = 1; gbc.gridy = 3;
        JButton addButton = new JButton("Add Event");
        ButtonStyler.styleButton(addButton);
        addButton.addActionListener(e -> addEvent());
        add(addButton, gbc);
    }

    private void addEvent() {
        try {
            eventsDB.connect();
            eventsDB.addEvent(
                eventNameField.getText(),
                eventTypeField.getText(),
                Integer.parseInt(meetIDField.getText())
            );
            JOptionPane.showMessageDialog(this, "Event added successfully!");
            eventNameField.setText("");
            eventTypeField.setText("");
            meetIDField.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, 
                "Error adding event: " + e.getMessage(),
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                "Please enter a valid Meet ID number",
                "Error",
                JOptionPane.ERROR_MESSAGE);
        } finally {
            try {
                eventsDB.disconnect();
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error disconnecting from database: " + e.getMessage());
              }
            }
        }
    }
