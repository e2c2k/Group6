import java.awt.*;
import javax.swing.*;
import panels.AthletesPanel;
import panels.HomePanel;
import panels.ScoresPanel;
import panels.TeamsPanel;

public class MainFrame extends JFrame {
    private JPanel contentPanel;

    public MainFrame(boolean isMeetOfficial) {
        setTitle("Meet Official Dashboard");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JPanel mainPanel = new JPanel(new BorderLayout());

        JPanel sidebarPanel = new JPanel();
        sidebarPanel.setLayout(new BoxLayout(sidebarPanel, BoxLayout.Y_AXIS));
        sidebarPanel.setBackground(new Color(45, 45, 45));
        sidebarPanel.setPreferredSize(new Dimension(200, getHeight()));

        JButton homeButton = new JButton("Home");
        JButton athletesButton = new JButton("Athletes");
        JButton teamsButton = new JButton("Teams");
        JButton scoresButton = new JButton("Scores");
        JButton logoutButton = new JButton("Logout");

        // Apply button styling
        for (JButton button : new JButton[]{homeButton, athletesButton, teamsButton, scoresButton, logoutButton}) {
            button.setBackground(new Color(45, 45, 45));
            button.setForeground(Color.WHITE);
            button.setFocusPainted(false);
            button.setBorderPainted(false);
            button.setOpaque(true);
            button.setAlignmentX(Component.LEFT_ALIGNMENT);
            button.setPreferredSize(new Dimension(200, 50));
            button.setMaximumSize(new Dimension(200, 50));
            sidebarPanel.add(button);
            sidebarPanel.add(Box.createRigidArea(new Dimension(0, 10)));
        }

        contentPanel = new JPanel(new CardLayout());
        contentPanel.add(new HomePanel(), "Home");
        contentPanel.add(new AthletesPanel(), "Athletes");
        contentPanel.add(new TeamsPanel(), "Teams");
        contentPanel.add(new ScoresPanel(), "Scores");

        mainPanel.add(sidebarPanel, BorderLayout.WEST);
        mainPanel.add(contentPanel, BorderLayout.CENTER);

        homeButton.addActionListener(e -> switchView("Home"));
        athletesButton.addActionListener(e -> switchView("Athletes"));
        teamsButton.addActionListener(e -> switchView("Teams"));
        scoresButton.addActionListener(e -> switchView("Scores"));
        logoutButton.addActionListener(e -> logout());

        add(mainPanel);
    }

    private void switchView(String viewName) {
        CardLayout cardLayout = (CardLayout) contentPanel.getLayout();
        cardLayout.show(contentPanel, viewName);
    }

    private void logout() {
        dispose();
        Launcher.main(null);
    }
}
