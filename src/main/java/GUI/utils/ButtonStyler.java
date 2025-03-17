package GUI.utils;

import javax.swing.JButton;
import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.Component;
import java.awt.Dimension;

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

    public static void styleSidebarButton(JButton button) {
        button.setBackground(new Color(45, 45, 45));
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setOpaque(true);
        
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        button.setPreferredSize(new Dimension(200, 60));
        button.setMaximumSize(new Dimension(200, 60));
        
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent evt) {
                button.setBackground(new Color(60, 60, 60));
            }
            public void mouseExited(MouseEvent evt) {
                button.setBackground(new Color(45, 45, 45));
            }
        });
    }
} 