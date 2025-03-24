package GUI.views;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import Database.AthletesDatabase;
import GUI.utils.ButtonStyler;

public class ManageAthletesPanel extends JPanel {
    private JTextField surnameField;
    private JTextField givenNameField;
    private JTextField idField;
    private JTextField dobField;
    private JTextField teamField;
    private JTextField meetIdField;
    private JTextField emergencyNameField;
    private JTextField emergencyPhoneField;
    private JTextField emergencyRelationField;
    private AthletesDatabase athletesDB;

    public ManageAthletesPanel() {
        athletesDB = new AthletesDatabase();
        setLayout(new GridBagLayout());
        GridBagConstraints constraints = new GridBagConstraints();
        constraints.insets = new Insets(5, 5, 5, 5);

        // Meet ID 
        constraints.gridx = 0;
        constraints.gridy = 0;
        add(new JLabel("Meet ID:"), constraints);
        constraints.gridx = 1;
        meetIdField = new JTextField(20);
        add(meetIdField, constraints);

        // Surname
        constraints.gridx = 0;
        constraints.gridy = 1;
        add(new JLabel("Surname:"), constraints);
        constraints.gridx = 1;
        surnameField = new JTextField(20);
        add(surnameField, constraints);

        // Given Name
        constraints.gridx = 0;
        constraints.gridy = 2;
        add(new JLabel("Given Name:"), constraints);
        constraints.gridx = 1;
        givenNameField = new JTextField(20);
        add(givenNameField, constraints);

        // ID (6 digits)
        constraints.gridx = 0;
        constraints.gridy = 3;
        add(new JLabel("ID (6 digits):"), constraints);
        constraints.gridx = 1;
        idField = new JTextField(20);
        add(idField, constraints);

        // Date of Birth
        constraints.gridx = 0;
        constraints.gridy = 4;
        add(new JLabel("Date of Birth:"), constraints);
        constraints.gridx = 1;
        dobField = new JTextField(20);
        add(dobField, constraints);

        // Team
        constraints.gridx = 0;
        constraints.gridy = 5;
        add(new JLabel("Team:"), constraints);
        constraints.gridx = 1;
        teamField = new JTextField(20);
        add(teamField, constraints);

        // Emergency Contact Name
        constraints.gridx = 0;
        constraints.gridy = 6;
        add(new JLabel("Emergency Contact Name:"), constraints);
        constraints.gridx = 1;
        emergencyNameField = new JTextField(20);
        add(emergencyNameField, constraints);

        // Emergency Contact Phone
        constraints.gridx = 0;
        constraints.gridy = 7;
        add(new JLabel("Emergency Contact Phone:"), constraints);
        constraints.gridx = 1;
        emergencyPhoneField = new JTextField(20);
        add(emergencyPhoneField, constraints);

        // Relation to Athlete
        constraints.gridx = 0;
        constraints.gridy = 8;
        add(new JLabel("Relation to Athlete:"), constraints);
        constraints.gridx = 1;
        emergencyRelationField = new JTextField(20);
        add(emergencyRelationField, constraints);

        // Add Button
        constraints.gridx = 1;
        constraints.gridy = 9;
        JButton addButton = new JButton("Add Athlete");
        ButtonStyler.styleButton(addButton);
        addButton.addActionListener(e -> addAthlete());
        add(addButton, constraints);
    }

    private void addAthlete() {
        int meetId = Integer.parseInt(meetIdField.getText());
        String surname = surnameField.getText();
        String givenName = givenNameField.getText();
        String id = idField.getText();
        String dob = dobField.getText();
        String team = teamField.getText();
        String emergencyName = emergencyNameField.getText();
        String emergencyPhone = emergencyPhoneField.getText();
        String emergencyRelation = emergencyRelationField.getText();

        // Validate ID is 6 digits
        if (!id.matches("\\d{6}")) {
            JOptionPane.showMessageDialog(this, "ID must be exactly 6 digits");
            return;
        }

        try {
            athletesDB.connect();
            athletesDB.addAthlete(surname, givenName, id, dob, team, meetId, emergencyName, emergencyPhone, emergencyRelation);
            JOptionPane.showMessageDialog(this, "Athlete added successfully!");
            surnameField.setText("");
            givenNameField.setText("");
            idField.setText("");
            dobField.setText("");
            teamField.setText("");
            emergencyNameField.setText("");
            emergencyPhoneField.setText("");
            emergencyRelationField.setText("");
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error adding athlete: " + e.getMessage());
        } finally {
            try {
                athletesDB.disconnect();
            } catch (SQLException e) {
                System.err.println("Error disconnecting from database: " + e.getMessage());
            }
        }
    }
} 