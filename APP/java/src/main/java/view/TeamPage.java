package view;

import core.View;
import javax.swing.*;
import java.awt.*;

public class TeamPage extends JFrame {

    public TeamPage(View view) {
        setTitle("Team");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        JButton historyButton = new JButton("History");
        JButton staffButton = new JButton("Staff");
        JButton tradeButton = new JButton("Trade");
        JButton backButton = new JButton("Back to Login");

        // Navigazione alle pagine corrispondenti
        historyButton.addActionListener(e -> {
            new HistoryPage(view);
            dispose();
        });

        staffButton.addActionListener(e -> {
            new StaffPage(view);
            dispose();
        });

        tradeButton.addActionListener(e -> {
            new TradePage(view);
            dispose();
        });

        backButton.addActionListener(e -> {
            new LoginPage(view);
            dispose();
        });

        add(historyButton);
        add(staffButton);
        add(tradeButton);
        add(backButton);
        setVisible(true);
    }
}
