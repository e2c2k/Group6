import java.awt.*;
import javax.swing.*;

public class LoginFrame extends JFrame {
    private JTextField emailField;
    private JPasswordField passwordField;
    private JButton loginButton, cancelButton;

    public LoginFrame() {
        setTitle("Meet Official Login");
        setSize(400, 250);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        JPanel panel = new JPanel(new GridLayout(3, 2, 10, 10));
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        panel.add(new JLabel("Email:"));
        emailField = new JTextField();
        panel.add(emailField);

        panel.add(new JLabel("Password:"));
        passwordField = new JPasswordField();
        panel.add(passwordField);

        loginButton = new JButton("Login");
        cancelButton = new JButton("Cancel");
        panel.add(loginButton);
        panel.add(cancelButton);

        add(panel);
        setUpActions();
    }

    private void setUpActions() {
        loginButton.addActionListener(e -> handleLogin());
        cancelButton.addActionListener(e -> dispose());
    }

    private void handleLogin() {
        String email = emailField.getText();
        String password = new String(passwordField.getPassword());

        if (UserDatabase.verifyUser(email, password, "meet_official")) {
            JOptionPane.showMessageDialog(this, "Login Successful!", "Success", JOptionPane.INFORMATION_MESSAGE);
            dispose();
            Launcher.main(null); // Open main application
        } else {
            JOptionPane.showMessageDialog(this, "Access Denied! Only Meet Officials can login.", "Login Failed", JOptionPane.ERROR_MESSAGE);
        }
    }
}