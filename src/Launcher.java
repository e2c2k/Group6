import java.awt.*;
import javax.swing.*;

public class Launcher {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Track Meet Manager");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setSize(600, 400);
            frame.setLocationRelativeTo(null);

            JPanel panel = new JPanel(new GridLayout(3, 1, 10, 10));
            panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
            panel.setBackground(Color.BLACK);

            JLabel titleLabel = new JLabel("Welcome to Track Meet Organizer", JLabel.CENTER);
            titleLabel.setFont(new Font("consolas", Font.BOLD, 18));
            titleLabel.setForeground(Color.WHITE);
            panel.add(titleLabel);

            JButton loginButton = new JButton("Login as Meet Official");
            JButton guestButton = new JButton("Continue as Guest");
            
            // Set button colors
            Color darkGrey = new Color(45, 45, 45);
            loginButton.setBackground(darkGrey);
            loginButton.setForeground(Color.WHITE);
            guestButton.setBackground(darkGrey);
            guestButton.setForeground(Color.WHITE);
            
            loginButton.setOpaque(true);
            guestButton.setOpaque(true);
            
            loginButton.setBorderPainted(false);
            guestButton.setBorderPainted(false);
            
            loginButton.addActionListener(e -> new LoginFrame().setVisible(true));
            guestButton.addActionListener(e -> openMainFrame(false)); // Guest Mode
            
            panel.add(loginButton);
            panel.add(guestButton);
            
            frame.add(panel);
            frame.setVisible(true);
        });
    }

    private static void openMainFrame(boolean isMeetOfficial) {
        JFrame mainFrame = new MainFrame(isMeetOfficial);
        mainFrame.setVisible(true);
    }
}