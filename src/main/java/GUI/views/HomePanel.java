package GUI.views;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class HomePanel extends JPanel {

  public HomePanel(){
    setLayout(new BorderLayout());
    JLabel welcomeLabel = new JLabel("Welcome");
    welcomeLabel.setFont(new Font("Arial", Font.BOLD, 24));
    welcomeLabel.setHorizontalAlignment(JLabel.CENTER);
    add(welcomeLabel, BorderLayout.NORTH);

    JPanel upcomingMeets = new JPanel();
    add(upcomingMeets, BorderLayout.CENTER);
  }
  
}
