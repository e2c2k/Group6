package panels;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class ScoresPanel extends JPanel {
  public ScoresPanel() {
    setLayout(new BorderLayout());
    JLabel scoresLabel = new JLabel("Scores");
    scoresLabel.setFont(new Font("Arial", Font.BOLD, 24));
    scoresLabel.setHorizontalAlignment(JLabel.CENTER);
    add(scoresLabel, BorderLayout.NORTH);

    JPanel scoresList = new JPanel();
    add(scoresList, BorderLayout.CENTER);
  }
}
