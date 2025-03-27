package GUI;

import javax.swing.*;
import java.awt.*;
import GUI.views.*;
import GUI.utils.ButtonStyler;

public class Launcher {
    public static JFrame frame; 
    public static JPanel contentPanel;
    public static JPanel announcementsPanel; 
    public static EventsPanel eventsPanel; 
    
    public static void main(String[] args){
        frame = new JFrame("Track Meet Manager");
        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // Add login button
        ImageIcon icon = new ImageIcon(Launcher.class.getResource("/icons/user-icon.png"));
        Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton loginButton = new JButton(new ImageIcon(scaled));
        loginButton.setBorderPainted(false);     
        loginButton.setContentAreaFilled(false); 
        loginButton.setFocusPainted(false);      
        loginButton.addActionListener(e -> new LoginFrame().setVisible(true));
        topPanel.add(loginButton);
        
        frame.add(topPanel, BorderLayout.NORTH);
        
        // main content center panel
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE); 

        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new HomePanel(), "Home");
        contentPanel.add(new AthletePanel(0), "Athletes");
        eventsPanel = new EventsPanel(0); 
        contentPanel.add(eventsPanel, "Events"); 
        centerPanel.add(contentPanel, BorderLayout.CENTER);
        
        frame.add(centerPanel);

        announcementsPanel = new JPanel();
        announcementsPanel.setLayout(new BoxLayout(announcementsPanel, BoxLayout.Y_AXIS));
        announcementsPanel.setBackground(Color.WHITE);

        JPanel announcementsContainer = new JPanel(new BorderLayout());
        JLabel announcementsLabel = new JLabel("Announcements", SwingConstants.CENTER);
        announcementsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        announcementsContainer.add(announcementsLabel, BorderLayout.NORTH);
        announcementsContainer.add(announcementsPanel, BorderLayout.CENTER);

        frame.add(announcementsContainer, BorderLayout.SOUTH);

        // Create sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(45, 45, 45));
        sidebarPanel.setPreferredSize(new Dimension(200, frame.getHeight()));

        //button sidebar
        JButton homeButton = new JButton("Home");
        JButton eventsButton = new JButton("Events");
        JButton reportsButton = new JButton("Athletes");

        for (JButton button : new JButton[]{homeButton, eventsButton, reportsButton}) {
            ButtonStyler.styleSidebarButton(button);
            sidebarPanel.add(button);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        }

        homeButton.addActionListener(e -> switchView("Home"));
        eventsButton.addActionListener(e -> switchView("Events"));
        reportsButton.addActionListener(e -> switchView("Athletes"));

        frame.add(sidebarPanel, BorderLayout.WEST);

        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }

    private static void switchView(String viewName){
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, viewName);
    }

    public static void refreshAfterLogout() {
        frame.getContentPane().removeAll();
        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new HomePanel(), "Home");
        contentPanel.add(new AthletePanel(0), "Athletes");
        eventsPanel = new EventsPanel(0); 
        contentPanel.add(eventsPanel, "Events"); 
        
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(Color.WHITE);
        centerPanel.add(contentPanel, BorderLayout.CENTER);
        frame.add(centerPanel);
        
        frame.revalidate();
        frame.repaint();
    }
}
