package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import Database.EntriesDatabase;
import GUI.utils.ButtonStyler;

public class AddResultsPanel extends JPanel {
    private JTextField athleteIdField;
    private JTextField eventIdField;
    private JTextField resultField;
    private JTextField placeField;
    private EntriesDatabase entriesDB;

    public AddResultsPanel() {
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

        // Event ID
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Event ID:"), gbc);
        gbc.gridx = 1;
        eventIdField = new JTextField(20);
        add(eventIdField, gbc);

        // Result (Time)
        gbc.gridx = 0;
        gbc.gridy = 2;
        add(new JLabel("Result (in seconds):"), gbc);
        gbc.gridx = 1;
        resultField = new JTextField(20);
        add(resultField, gbc);

        // Place
        gbc.gridx = 0;
        gbc.gridy = 3;
        add(new JLabel("Place:"), gbc);
        gbc.gridx = 1;
        placeField = new JTextField(20);
        add(placeField, gbc);

        // Add Button
        gbc.gridx = 1;
        gbc.gridy = 4;
        JButton addButton = new JButton("Add Result");
        ButtonStyler.styleButton(addButton);
        addButton.addActionListener(e -> addResult());
        add(addButton, gbc);
    }

    private void addResult() {
        try {
            String athleteId = athleteIdField.getText();
            int eventId = Integer.parseInt(eventIdField.getText());
            double result = Double.parseDouble(resultField.getText());
            int place = Integer.parseInt(placeField.getText());

            entriesDB.connect();
            entriesDB.updateResult(athleteId, eventId, result, place);
            entriesDB.disconnect();

            JOptionPane.showMessageDialog(this, "Result added successfully!");
            clearFields();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding result: " + e.getMessage());
        }
    }

    private void clearFields() {
        athleteIdField.setText("");
        eventIdField.setText("");
        resultField.setText("");
        placeField.setText("");
    }
} 