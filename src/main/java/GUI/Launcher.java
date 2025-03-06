package GUI;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;
import java.awt.BorderLayout;
import java.awt.Image;
import javax.swing.ImageIcon;
import java.awt.FlowLayout;

public class Launcher {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        frame.setLayout(new BorderLayout());
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        
        // Add login button
        ImageIcon icon = new ImageIcon(Launcher.class.getResource("/icons/user-icon.png"));
        Image scaled = icon.getImage().getScaledInstance(30, 30, Image.SCALE_SMOOTH);
        JButton loginButton = new JButton(new ImageIcon(scaled));
        loginButton.setBorderPainted(false);     
        loginButton.setContentAreaFilled(false); 
        loginButton.setFocusPainted(false);      
        loginButton.addActionListener(e -> new LoginFrame().setVisible(true));
        topPanel.add(loginButton);
        
        frame.add(topPanel, BorderLayout.NORTH);
        
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
