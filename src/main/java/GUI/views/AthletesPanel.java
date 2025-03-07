package GUI.views;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class AthletesPanel extends JPanel {
  public AthletesPanel() {
    setLayout(new BorderLayout());
    JLabel athletesLabel = new JLabel("Athletes");
    athletesLabel.setFont(new Font("Arial", Font.BOLD, 24));
    athletesLabel.setHorizontalAlignment(JLabel.CENTER);
    add(athletesLabel, BorderLayout.NORTH);

    JPanel athletesList = new JPanel(); 
    add(athletesList, BorderLayout.CENTER);
  }
}
