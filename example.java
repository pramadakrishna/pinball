import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
class example{
    public static void main(String[] args) {
        JFrame f = new JFrame("Example");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.setSize(400,200);
        f.setLayout(new FlowLayout());
        String[] ex = {"java","C","Python","R"};
        JComboBox<String> comboBox = new JComboBox<>(ex);
        JLabel label = new JLabel("select one : ");
        label.setPreferredSize(new Dimension(250,50));
        
        comboBox.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                String selected =(String)comboBox.getSelectedItem();
                label.setText("You selected : "+selected);
            }
        });
        
        f.add(comboBox);
        f.add(label);
        f.setVisible(true);
    }
}