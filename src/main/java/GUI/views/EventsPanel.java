package GUI.views;

import java.awt.BorderLayout;
import java.awt.Font;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class EventsPanel extends JPanel {
    public EventsPanel() {
      setLayout(new BorderLayout());
      JLabel eventsLabel = new JLabel("Events");
      eventsLabel.setFont(new Font("Arial", Font.BOLD, 24));
      eventsLabel.setHorizontalAlignment(JLabel.CENTER);
      add(eventsLabel, BorderLayout.NORTH);

      JPanel eventsList = new JPanel();
      add(eventsList, BorderLayout.CENTER);
    }
}
