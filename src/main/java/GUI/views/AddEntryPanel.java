package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import Database.EntriesDatabase;
import GUI.utils.ButtonStyler;

public class AddEntryPanel extends JPanel {
    private JTextField athleteIdField;
    private JTextField heatIdField;
    private JTextField eventIdField;
    private JTextField seedTimeField;
    private EntriesDatabase entriesDB;

    public AddEntryPanel() {
        entriesDB = new EntriesDatabase();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Athlete ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Athlete ID:"), gbc);
        gbc.gridx = 1;
        athleteIdField = new JTextField(20);
        add(athleteIdField, gbc);

        // Heat ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Heat ID:"), gbc);
        gbc.gridx = 1;
        heatIdField = new JTextField(20);
        add(heatIdField, gbc);

        // Event ID
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Event ID:"), gbc);
        gbc.gridx = 1;
        eventIdField = new JTextField(20);
        add(eventIdField, gbc);

        // Seed Time
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Seed Time (In Seconds):"), gbc);
        gbc.gridx = 1;
        seedTimeField = new JTextField(20);
        add(seedTimeField, gbc);

        // Add Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton addButton = new JButton("Add Entry");
        ButtonStyler.styleButton(addButton);
        addButton.addActionListener(e -> addEntry());
        add(addButton, gbc);
    }

    private void addEntry() {
        try {
            int athleteId = Integer.parseInt(athleteIdField.getText());
            int heatId = Integer.parseInt(heatIdField.getText());
            int eventId = Integer.parseInt(eventIdField.getText());
            double seedTime = Double.parseDouble(seedTimeField.getText());

            entriesDB.connect();
            entriesDB.addEntry(athleteId, heatId, eventId, seedTime);
            entriesDB.disconnect();

            JOptionPane.showMessageDialog(this, "Entry added successfully!");
            athleteIdField.setText("");
            heatIdField.setText("");
            eventIdField.setText("");
            seedTimeField.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding entry: " + e.getMessage());
        }
    }

} 