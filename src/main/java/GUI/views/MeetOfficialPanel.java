package GUI.views;

import javax.swing.*;
import java.awt.*;
import GUI.utils.ButtonStyler;

public class MeetOfficialPanel extends JPanel {
    private JPanel sidebarPanel;
    private JPanel contentPanel;

    public MeetOfficialPanel() {
        setLayout(new BorderLayout());

        // Initialize toolbar panel
        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(45, 45, 45));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));

        // Create buttons
        JButton createMeetButton = new JButton("Create Meet");
        JButton addEventsButton = new JButton("Add Events");
        JButton addHeatsButton = new JButton("Add Heats");
        JButton manageAthletesButton = new JButton("Manage Athletes");
        JButton addEntryToHeatButton = new JButton("Add Entry to Heat");
        JButton addResultsButton = new JButton("Add Results");

        // Style and add buttons
        for (JButton button : new JButton[]{createMeetButton, addEventsButton, addHeatsButton, manageAthletesButton, addEntryToHeatButton, addResultsButton}) {
            ButtonStyler.styleSidebarButton(button);
            sidebarPanel.add(button);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        // Initialize content panel
        contentPanel = new JPanel(new CardLayout());
        contentPanel.setBackground(Color.WHITE);

        // Add panels to main panel
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);
    }
}