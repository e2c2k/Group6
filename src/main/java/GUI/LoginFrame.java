package GUI;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JPasswordField;
import javax.swing.JButton;
import java.awt.GridLayout;
import Authentication.AuthMethods;
import javax.swing.JOptionPane;
import javax.swing.BorderFactory;

public class LoginFrame extends JFrame {
  private JTextField emailField;
  private JPasswordField passwordField;
  private JButton loginButton;
  private JButton cancelButton;

  public LoginFrame(){
    setTitle("Meet Official Login");
    setSize(400,200);
    setLocationRelativeTo(null); // centers the frame
    setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);  // Just close this window

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
  
  private void setUpActions(){
    loginButton.addActionListener(e -> handleLogin());
    cancelButton.addActionListener(e-> dispose());
  }

  private void handleLogin(){
    String email = emailField.getText();
    char[] passwordChars = passwordField.getPassword();
    String password = new String(passwordChars);

    if (AuthMethods.verifyUser(email, password)) {
      dispose();
      // TODO: Open main interface with edit permissions
    }
    else{
      JOptionPane.showMessageDialog(this, "Invalid email or password", "Login Failed", JOptionPane.ERROR_MESSAGE);
    }
  }
}
