package GUI.views;

import javax.swing.*;
import java.awt.*;
import GUI.utils.ButtonStyler;

public class MeetOfficialPanel extends JPanel {
    private JPanel sidebarPanel;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private int meetId;

    public MeetOfficialPanel(int meetId) {
        this.meetId = meetId;
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
        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        // Add panels to main panel
        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        // Add button actions
        createMeetButton.addActionListener(e -> cardLayout.show(contentPanel, "CreateMeet"));
        addEventsButton.addActionListener(e -> cardLayout.show(contentPanel, "AddEvents"));
        addHeatsButton.addActionListener(e -> cardLayout.show(contentPanel, "AddHeats"));
        manageAthletesButton.addActionListener(e -> cardLayout.show(contentPanel, "ManageAthletes"));
        addEntryToHeatButton.addActionListener(e -> cardLayout.show(contentPanel, "AddEntryToHeat"));
        addResultsButton.addActionListener(e -> cardLayout.show(contentPanel, "AddResults"));

        // Add panels to content panel
        contentPanel.add(new CreateMeetPanel(), "CreateMeet");
        contentPanel.add(new AddEventsPanel(), "AddEvents");
        contentPanel.add(new AddHeatsPanel(), "AddHeats");
        contentPanel.add(new ManageAthletesPanel(), "ManageAthletes");
        //TODO: Implement these panels
        // contentPanel.add(new AddEntryToHeatPanel(), "ADD_ENTRY_TO_HEAT");
        // contentPanel.add(new AddResultsPanel(), "ADD_RESULTS");
    }
}