import java.awt.Graphics;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class calculator extends JPanel {


    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawString("Hello World", 20, 20);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("Calculator");
        calculator panel = new calculator();

        frame.setSize(300, 200);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(panel);
        frame.setVisible(true);
    }
}
