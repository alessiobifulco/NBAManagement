package view;

import core.Model;
import javax.swing.*;
import java.awt.*;

public class StaffPage extends JFrame {

    public StaffPage(JFrame jFrame, Model model) {    
        setTitle("Staff");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton backButton = new JButton("Back to Team");
        backButton.addActionListener(e -> {
            dispose();
        });

        add(backButton);
        setVisible(true);
    }
}
