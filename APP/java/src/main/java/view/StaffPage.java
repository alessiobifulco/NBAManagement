package view;

import core.View;
import javax.swing.*;
import java.awt.*;

public class StaffPage extends JFrame {

    public StaffPage(View view) {
        setTitle("Staff");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton backButton = new JButton("Back to Team");
        backButton.addActionListener(e -> {
            new TeamPage(view);
            dispose();
        });

        add(backButton);
        setVisible(true);
    }
}
