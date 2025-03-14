package utils;

import java.awt.*;
import javax.swing.*;

public class ButtonStyler {
    public static void styleButton(JButton button) {
        button.setBackground(new Color(45, 45, 45));  // Dark grey
        button.setForeground(Color.WHITE);  // White text
        button.setFont(new Font("Arial", Font.BOLD, 14));
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);

        // Hover effect
        button.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(60, 60, 60));  
            }

            public void mouseExited(java.awt.event.MouseEvent evt) {
                button.setBackground(new Color(45, 45, 45)); 
            }
        });
    }
}