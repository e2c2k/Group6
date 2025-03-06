package GUI;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JButton;

public class Launcher {
    public static void main(String[] args){
        JFrame frame = new JFrame();
        JPanel panel = new JPanel();
        frame.setSize(800,600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);

        JButton loginButton = new JButton("Meet Official Login");
        loginButton.addActionListener(e -> new LoginFrame().setVisible(true));
        panel.add(loginButton);
    }
    
}
