package GUI;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import java.awt.Font;
import java.awt.Color;
import java.awt.Component;
import javax.swing.Box;
import java.awt.Dimension;
import javax.swing.BoxLayout;
import java.awt.CardLayout;
import GUI.views.*;

public class Launcher {
    private static JPanel contentPanel;
    public static void main(String[] args){
        JFrame frame = new JFrame("Track Meet Manager");
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
        JPanel titlePanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel titleLabel = new JLabel("Track & Field Meets");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titlePanel.add(titleLabel);
        centerPanel.add(titlePanel, BorderLayout.NORTH);

        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new HomePanel(), "Home");
        contentPanel.add(new EventsPanel(), "Events");
        contentPanel.add(new TeamsPanel(), "Teams");
        contentPanel.add(new ScoresPanel(), "Scores");
        contentPanel.add(new AthletesPanel(), "Athletes");
        centerPanel.add(contentPanel, BorderLayout.CENTER);
        
        

        frame.add(centerPanel);
        
        // Announcements panel
        JPanel announcementsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel announcementsLabel = new JLabel("Announcements");
        announcementsLabel.setFont(new Font("Arial", Font.BOLD, 18));
        announcementsPanel.add(announcementsLabel);
        frame.add(announcementsPanel, BorderLayout.SOUTH);

        // Create sidebar panel
        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(45, 45, 45));

        //button sidebar
        JButton homeButton = new JButton("Home");
        JButton eventsButton = new JButton("Events");
        JButton teamsButton = new JButton("Teams");
        JButton scoresButton = new JButton("Scores");
        JButton reportsButton = new JButton("Athletes");

        for (JButton button : new JButton[]{homeButton, eventsButton, teamsButton, scoresButton, reportsButton}) {
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setMaximumSize(new Dimension(200, 40));
            button.setBackground(new Color(45, 45, 45));
            button.setForeground(Color.WHITE);
            button.setBorderPainted(false);
            button.setFocusPainted(false);
            sidebarPanel.add(button);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10))); 
        }

        homeButton.addActionListener(e -> switchView("Home"));
        eventsButton.addActionListener(e -> switchView("Events"));
        teamsButton.addActionListener(e -> switchView("Teams"));
        scoresButton.addActionListener(e -> switchView("Scores"));
        reportsButton.addActionListener(e -> switchView("Athletes"));

        frame.add(sidebarPanel, BorderLayout.WEST);

        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }

    private static void switchView(String viewName){
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, viewName);
    }
}
