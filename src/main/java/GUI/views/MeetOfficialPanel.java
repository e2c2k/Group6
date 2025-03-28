package GUI.views;

import javax.swing.*;
import java.awt.*;
import GUI.utils.ButtonStyler;
import GUI.Launcher;
import GUI.LoginFrame;



public class MeetOfficialPanel extends JPanel {
    private JPanel sidebarPanel;
    private CardLayout cardLayout;
    private JPanel contentPanel;
    private int currentMeetId;

    public MeetOfficialPanel(int meetId) {
        this.currentMeetId = meetId;
        setLayout(new BorderLayout());

        // Add logout button to top
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        JButton logoutButton = new JButton("Logout");
        ButtonStyler.styleButton(logoutButton);
        //after logout, everything is removed and then needs to be re-added
        logoutButton.addActionListener(e -> {
            // refresh data
            Launcher.refreshAfterLogout();
            
            Launcher.frame.getContentPane().removeAll();
            
            // Re-add login button
            JPanel loginPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
            ImageIcon icon = new ImageIcon(Launcher.class.getResource("/icons/user-icon.png"));
            Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
            JButton loginButton = new JButton(new ImageIcon(scaled));
            loginButton.setBorderPainted(false);     
            loginButton.setContentAreaFilled(false); 
            loginButton.setFocusPainted(false);      
            loginButton.addActionListener(e2 -> new LoginFrame().setVisible(true));
            loginPanel.add(loginButton);
            Launcher.frame.add(loginPanel, BorderLayout.NORTH);
            
            JPanel centerPanel = new JPanel(new BorderLayout());
            centerPanel.setBackground(Color.WHITE);
            centerPanel.add(Launcher.contentPanel, BorderLayout.CENTER);
            
            // Re-create and add sidebar
            JPanel sidebarPanel = new JPanel();
            sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
            sidebarPanel.setBackground(new Color(45, 45, 45));
            sidebarPanel.setPreferredSize(new Dimension(200, Launcher.frame.getHeight()));

            JButton homeButton = new JButton("Home");
            JButton eventsButton = new JButton("Events");
            JButton reportsButton = new JButton("Athletes");

            for (JButton button : new JButton[]{homeButton, eventsButton, reportsButton}) {
                ButtonStyler.styleSidebarButton(button);
                sidebarPanel.add(button);
                sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
            }
            //need to use CardLayout from Launcher.contentPanel
            homeButton.addActionListener(e3 -> ((CardLayout)Launcher.contentPanel.getLayout()).show(Launcher.contentPanel, "Home"));
            eventsButton.addActionListener(e3 -> ((CardLayout)Launcher.contentPanel.getLayout()).show(Launcher.contentPanel, "Events"));
            reportsButton.addActionListener(e3 -> ((CardLayout)Launcher.contentPanel.getLayout()).show(Launcher.contentPanel, "Athletes"));

            centerPanel.add(sidebarPanel, BorderLayout.WEST);
            
            Launcher.frame.add(centerPanel);
        
            JPanel announcementsContainer = new JPanel(new BorderLayout());
            JLabel announcementsLabel = new JLabel("Announcements", SwingConstants.CENTER);
            announcementsLabel.setFont(new Font("Arial", Font.BOLD, 18));
            announcementsContainer.add(announcementsLabel, BorderLayout.NORTH);
            announcementsContainer.add(Launcher.announcementsPanel, BorderLayout.CENTER);
            Launcher.frame.add(announcementsContainer, BorderLayout.SOUTH);
            
            Launcher.frame.revalidate();
            Launcher.frame.repaint();
        });
        topPanel.add(logoutButton);
        add(topPanel, BorderLayout.NORTH);

        sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(45, 45, 45));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));

        JButton createMeetButton = new JButton("Create Meet");
        JButton addEventsButton = new JButton("Add Events");
        JButton addHeatsButton = new JButton("Add Heats");
        JButton manageAthletesButton = new JButton("Manage Athletes");
        JButton addEntryButton = new JButton("Add Entry");
        JButton addResultsButton = new JButton("Add Results");
        JButton addAnnouncementButton = new JButton("Add Announcement");

        for (JButton button : new JButton[]{createMeetButton, addEventsButton, addHeatsButton, manageAthletesButton, addEntryButton, addResultsButton, addAnnouncementButton}) {
            ButtonStyler.styleSidebarButton(button);
            sidebarPanel.add(button);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        cardLayout = new CardLayout();
        contentPanel = new JPanel(cardLayout);
        contentPanel.setBackground(Color.WHITE);

        add(sidebarPanel, BorderLayout.WEST);
        add(contentPanel, BorderLayout.CENTER);

        createMeetButton.addActionListener(e -> {
            contentPanel.remove(0);  
            contentPanel.add(new CreateMeetPanel(), "CreateMeet");
            cardLayout.show(contentPanel, "CreateMeet");
        });
        addEventsButton.addActionListener(e -> {
            contentPanel.remove(1); 
            contentPanel.add(new AddEventsPanel(), "AddEvents");
            cardLayout.show(contentPanel, "AddEvents");
        });
        addHeatsButton.addActionListener(e -> {
            contentPanel.remove(2);  
            contentPanel.add(new AddHeatsPanel(), "AddHeats");
            cardLayout.show(contentPanel, "AddHeats");
        });
        manageAthletesButton.addActionListener(e -> {
            contentPanel.remove(3);  
            contentPanel.add(new ManageAthletesPanel(), "ManageAthletes");
            cardLayout.show(contentPanel, "ManageAthletes");
        });
        addEntryButton.addActionListener(e -> {
            contentPanel.remove(4);  
            contentPanel.add(new AddEntryPanel(), "AddEntry");
            cardLayout.show(contentPanel, "AddEntry");
        });
        addResultsButton.addActionListener(e -> {
            contentPanel.remove(5);  
            contentPanel.add(new AddResultsPanel(), "AddResults");
            cardLayout.show(contentPanel, "AddResults");
        });
        addAnnouncementButton.addActionListener(e -> {
            contentPanel.remove(6);  // New index for announcements
            contentPanel.add(new AddAnnouncementPanel(currentMeetId), "AddAnnouncement");
            cardLayout.show(contentPanel, "AddAnnouncement");
        });

        contentPanel.add(new CreateMeetPanel(), "CreateMeet");
        contentPanel.add(new AddEventsPanel(), "AddEvents");
        contentPanel.add(new AddHeatsPanel(), "AddHeats");
        contentPanel.add(new ManageAthletesPanel(), "ManageAthletes");
        contentPanel.add(new AddEntryPanel(), "AddEntry");
        contentPanel.add(new AddResultsPanel(), "AddResults");
        contentPanel.add(new AddAnnouncementPanel(currentMeetId), "AddAnnouncement");
    }
}