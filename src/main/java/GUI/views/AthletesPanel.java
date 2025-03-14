package GUI.views;

import Database.AthletesDatabase;
import GUI.utils.ButtonStyler;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.sql.SQLException;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

public class AthletesPanel extends JPanel {
    private JPanel contentPanel;
    private AthletesDatabase athletesDatabase;
    
    public AthletesPanel() {
        setLayout(new BorderLayout());
        athletesDatabase = new AthletesDatabase();
        
        // Top title
        JLabel athletesLabel = new JLabel("Athletes");
        athletesLabel.setFont(new Font("Arial", Font.BOLD, 24));
        athletesLabel.setHorizontalAlignment(JLabel.CENTER);
        add(athletesLabel, BorderLayout.NORTH);
        
        // Left panel with athlete management buttons
        JPanel athleteControls = new JPanel();
        athleteControls.setLayout(new BoxLayout(athleteControls, BoxLayout.Y_AXIS));
        
        JButton addAthlete = new JButton("Add Athlete");
        JButton removeAthlete = new JButton("Remove Athlete");
        
        for (JButton button : new JButton[]{addAthlete, removeAthlete}) {
            ButtonStyler.styleButton(button);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setMaximumSize(new Dimension(250, 40));
            athleteControls.add(button);
            athleteControls.add(Box.createRigidArea(new Dimension(0, 5))); 
        }
        athleteControls.setBackground(new Color(240, 240, 240)); 
        add(athleteControls, BorderLayout.WEST);
        
        // Center panel to show athlete details
        contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBackground(Color.WHITE);
        add(contentPanel, BorderLayout.CENTER);
        
        // Button actions
        addAthlete.addActionListener(e -> showAddAthleteForm());
        removeAthlete.addActionListener(e -> showRemoveAthleteForm());
    }
    
    private void showAddAthleteForm() {
        contentPanel.removeAll();
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        JTextField surnameField = new JTextField(10);
        JTextField givenNameField = new JTextField(10);
        JTextField teamField = new JTextField(10);
        JTextField dobField = new JTextField(10);
        JButton submitButton = new JButton("Add");
        
        formPanel.add(new JLabel("Surname:"));
        formPanel.add(surnameField);
        formPanel.add(new JLabel("Given Name:"));
        formPanel.add(givenNameField);
        formPanel.add(new JLabel("Team:"));
        formPanel.add(teamField);
        formPanel.add(new JLabel("Date of Birth:"));
        formPanel.add(dobField);
        formPanel.add(submitButton);
        
        submitButton.addActionListener(e -> {
            try {
                athletesDatabase.addAthlete(surnameField.getText(), givenNameField.getText(), teamField.getText(), dobField.getText());
                JOptionPane.showMessageDialog(this, "Athlete Added Successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error adding athlete: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
    
    private void showRemoveAthleteForm() {
        contentPanel.removeAll();
        JPanel formPanel = new JPanel();
        formPanel.setLayout(new BoxLayout(formPanel, BoxLayout.Y_AXIS));
        
        JTextField surnameField = new JTextField(10);
        JTextField givenNameField = new JTextField(10);
        JButton submitButton = new JButton("Remove");
        
        formPanel.add(new JLabel("Surname:"));
        formPanel.add(surnameField);
        formPanel.add(new JLabel("Given Name:"));
        formPanel.add(givenNameField);
        formPanel.add(submitButton);
        
        submitButton.addActionListener(e -> {
            try {
                athletesDatabase.removeAthlete(surnameField.getText(), givenNameField.getText());
                JOptionPane.showMessageDialog(this, "Athlete Removed Successfully!");
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error removing athlete: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });
        
        contentPanel.add(formPanel, BorderLayout.CENTER);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}