package GUI.utils;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

public class ButtonStyler {
    public static void styleButton(JButton button) {
        button.setBackground(new Color(169, 169, 169));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(192, 192, 192));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(169, 169, 169));
            }
        });
    }
} 