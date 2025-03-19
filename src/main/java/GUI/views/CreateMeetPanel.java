package GUI.views;

import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JLabel;
import javax.swing.JButton;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import Database.MeetsDatabase;
import GUI.utils.ButtonStyler;
import java.sql.SQLException;
import javax.swing.JOptionPane;

public class CreateMeetPanel extends JPanel {
  private JTextField meetNameField;
  private JTextField dateField;
  private MeetsDatabase meetsDB;

  public CreateMeetPanel(){
    meetsDB = new MeetsDatabase();
    setLayout(new GridBagLayout());
    GridBagConstraints constraints = new GridBagConstraints();
    constraints.insets = new Insets(5, 5, 5, 5);

    // Meet Name
    constraints.gridx = 0;
    constraints.gridy = 0;
    add(new JLabel("Meet Name:"), constraints);
    constraints.gridx = 1;
    meetNameField = new JTextField(20);
    add(meetNameField, constraints);

    // Date
    constraints.gridx = 0;
    constraints.gridy = 1;
    add(new JLabel("Date:"), constraints);
    constraints.gridx = 1;
    dateField = new JTextField(20);
    add(dateField, constraints);

    // Create Button
    constraints.gridx = 1;
    constraints.gridy = 2;
    JButton createButton = new JButton("Create Meet");
    ButtonStyler.styleButton(createButton);
    createButton.addActionListener(e -> createMeet());
    add(createButton, constraints);
  } 

  private void createMeet(){
    String meetName = meetNameField.getText();
    String date = dateField.getText();

    try{
        meetsDB.connect();
        meetsDB.addMeet(meetName, date);
        JOptionPane.showMessageDialog(this, "Meet created!");
        meetNameField.setText("");
        dateField.setText("");
    }
    catch(SQLException e) {
        JOptionPane.showMessageDialog(this, "Error creating meet: " + e.getMessage());
    }
    finally {
        try {
            meetsDB.disconnect();
        }
        catch(SQLException e) {
            JOptionPane.showMessageDialog(this, "Error disconnecting from database: " + e.getMessage());
        }
    }
  }
}