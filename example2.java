import java.awt.*;
import javax.swing.*;

public class example2{
    public static void main(String[] args) {
        JFrame f = new JFrame("GridBagLayout ");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400, 400);

        JPanel p = new JPanel(new GridBagLayout());
        GridBagConstraints g = new GridBagConstraints();
        g.insets = new Insets(5,5,5,5);

    }
}