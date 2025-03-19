package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import Database.HeatsDataBase;
import GUI.utils.ButtonStyler;

public class AddHeatsPanel extends JPanel {
    private JTextField eventIdField;
    private JTextField heatNumberField;
    private HeatsDataBase heatsDB;

    public AddHeatsPanel() {
        heatsDB = new HeatsDataBase();
        setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);

        // Event ID
        gbc.gridx = 0;
        gbc.gridy = 0;
        add(new JLabel("Event ID:"), gbc);
        gbc.gridx = 1;
        eventIdField = new JTextField(20);
        add(eventIdField, gbc);

        // Heat Number
        gbc.gridx = 0;
        gbc.gridy = 1;
        add(new JLabel("Heat Number:"), gbc);
        gbc.gridx = 1;
        heatNumberField = new JTextField(20);
        add(heatNumberField, gbc);

        // Add Button
        gbc.gridx = 1;
        gbc.gridy = 2;
        JButton addButton = new JButton("Add Heat");
        ButtonStyler.styleButton(addButton);
        addButton.addActionListener(e -> addHeat());
        add(addButton, gbc);
    }

    private void addHeat() {
        try {
            int eventId = Integer.parseInt(eventIdField.getText());
            int heatNum = Integer.parseInt(heatNumberField.getText());
            
            heatsDB.connect();
            heatsDB.addHeat(eventId, heatNum);
            heatsDB.disconnect();
            
            JOptionPane.showMessageDialog(this, "Heat added successfully!");
            eventIdField.setText("");
            heatNumberField.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding heat: " + e.getMessage());
        }
    }
} 