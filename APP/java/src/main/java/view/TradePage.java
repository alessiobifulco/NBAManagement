package view;

import core.Model;
import core.View;
import javax.swing.*;
import java.awt.*;

public class TradePage extends JFrame {

    public TradePage(JFrame jFrame, Model model) {
        setTitle("Trade");
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
