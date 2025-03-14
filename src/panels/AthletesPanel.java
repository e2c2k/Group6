package panels;

import java.awt.*;
import javax.swing.*;
import utils.ButtonStyler;

public class AthletesPanel extends JPanel {
    public AthletesPanel() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Athletes Management", JLabel.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.setBackground(Color.BLACK);
        topPanel.add(titleLabel, BorderLayout.CENTER);
        add(topPanel, BorderLayout.NORTH);
        
        // Main content panel for displaying athlete information
        JPanel contentPanel = new JPanel();
        contentPanel.setBackground(Color.DARK_GRAY);
        contentPanel.setLayout(new GridLayout(5, 2, 10, 10));

        // Labels and Fields for athlete entry
        JLabel nameLabel = new JLabel("Name:");
        JTextField nameField = new JTextField();
        JLabel ageLabel = new JLabel("Age:");
        JTextField ageField = new JTextField();
        JLabel eventLabel = new JLabel("Event:");
        JTextField eventField = new JTextField();
        
        JButton addAthleteButton = new JButton("Add Athlete");
        ButtonStyler.styleButton(addAthleteButton);

        contentPanel.add(nameLabel);
        contentPanel.add(nameField);
        contentPanel.add(ageLabel);
        contentPanel.add(ageField);
        contentPanel.add(eventLabel);
        contentPanel.add(eventField);
        contentPanel.add(new JLabel());
        contentPanel.add(addAthleteButton);

        add(contentPanel, BorderLayout.CENTER);
    }
}