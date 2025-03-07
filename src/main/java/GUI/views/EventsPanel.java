package GUI.views;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.BoxLayout;
import java.awt.Component;
import java.awt.FlowLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.Box;
import GUI.utils.ButtonStyler;

public class EventsPanel extends JPanel {
    private JPanel contentPanel; 
    
    public EventsPanel() {
        setLayout(new BorderLayout());
        
        // Top title
        JLabel eventsLabel = new JLabel("Events");
        eventsLabel.setFont(new Font("Arial", Font.BOLD, 24));
        eventsLabel.setHorizontalAlignment(JLabel.CENTER);
        add(eventsLabel, BorderLayout.NORTH);

        // Left side - Schedule buttons
        JPanel schedulePanel = new JPanel();
        schedulePanel.setLayout(new BoxLayout(schedulePanel, BoxLayout.Y_AXIS));
        JButton fridayField = new JButton("Friday Field Events");
        JButton fridayTrack = new JButton("Friday Track Events");
        JButton satMornField = new JButton("Saturday Morning Field Events");
        JButton satMornTrack = new JButton("Saturday Morning Track Events");
        JButton satAftTrack = new JButton("Saturday Afternoon Track Events");
        JButton satAftField = new JButton("Saturday Afternoon Field Events");
        JButton notRun = new JButton("NotRun");
        
        for (JButton button : new JButton[]{fridayField, fridayTrack, satMornField, 
                                          satMornTrack, satAftTrack, satAftField, notRun}) {
            ButtonStyler.styleButton(button);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setMaximumSize(new Dimension(250, 40));
            schedulePanel.add(button);
            schedulePanel.add(Box.createRigidArea(new Dimension(0, 5))); 
        }
        schedulePanel.setBackground(new Color(240, 240, 240)); 
        add(schedulePanel, BorderLayout.WEST);  

        
        fridayField.addActionListener(e -> showEventDetails("Friday Field Events"));
        fridayTrack.addActionListener(e -> showEventDetails("Friday Track Events"));
        satMornField.addActionListener(e -> showEventDetails("Saturday Morning Field Events"));
        satMornTrack.addActionListener(e -> showEventDetails("Saturday Morning Track Events"));
        satAftTrack.addActionListener(e -> showEventDetails("Saturday Afternoon Track Events"));
        satAftField.addActionListener(e -> showEventDetails("Saturday Afternoon Field Events"));
        notRun.addActionListener(e -> showEventDetails("NotRun"));

        // Center panel with Results/Entries tabs
        JPanel centerPanel = new JPanel(new BorderLayout());
        JPanel tabsPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        JButton resultsTab = new JButton("Results");
        JButton entriesTab = new JButton("Entries");
        tabsPanel.add(resultsTab);
        tabsPanel.add(entriesTab);
        
        // Content area for results/entries
        contentPanel = new JPanel();
        contentPanel.setBackground(Color.WHITE);
        
        centerPanel.add(tabsPanel, BorderLayout.NORTH);
        centerPanel.add(contentPanel, BorderLayout.CENTER);
        
        add(centerPanel, BorderLayout.CENTER);

        // For tabs
        ButtonStyler.styleButton(resultsTab);
        ButtonStyler.styleButton(entriesTab);
    }

    private void showEventDetails(String eventName) {
        contentPanel.removeAll();
        JLabel eventTitle = new JLabel(eventName);
        eventTitle.setFont(new Font("Arial", Font.BOLD, 18));
        contentPanel.add(eventTitle, BorderLayout.NORTH);
        contentPanel.revalidate();
        contentPanel.repaint();
    }
}
