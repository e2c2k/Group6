package GUI.views;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TeamsPanel extends JPanel {
  public TeamsPanel() {
    setLayout(new BorderLayout());
    JLabel teamsLabel = new JLabel("Teams");
    teamsLabel.setFont(new Font("Arial", Font.BOLD, 24));
    teamsLabel.setHorizontalAlignment(JLabel.CENTER);
    add(teamsLabel, BorderLayout.NORTH);

    JPanel teamsList = new JPanel();
    add(teamsList, BorderLayout.CENTER);
  }
}
