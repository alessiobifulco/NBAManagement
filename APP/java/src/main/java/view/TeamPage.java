package view;

import core.Model;
import javax.swing.*;
import java.awt.*;

@SuppressWarnings("unused")
public class TeamPage extends JFrame {
    private final Model model;
    private final JFrame frame;
    private final int idTeam;

    public TeamPage(JFrame jFrame, Model model, int idTeam) {
        this.model = model;
        this.frame = jFrame;
        this.idTeam = idTeam;
        
        setTitle("Team Management");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        // Main panel with BorderLayout
        JPanel mainPanel = new JPanel(new BorderLayout(10, 10));
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        
        // Title panel (North)
        JLabel titleLabel = new JLabel("Team Management Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);
        
        // Center panel with grid for buttons
        JPanel buttonPanel = new JPanel(new GridLayout(2, 2, 30, 30));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 150, 50, 150));
        
        // Create styled buttons
        JButton historyButton = createMenuButton("History", "View team history and operations");
        JButton staffButton = createMenuButton("Staff", "Manage coaching staff and observers");
        JButton tradeButton = createMenuButton("Trade", "Manage player trades and contracts");
        JButton backButton = createMenuButton("Back to Login", "Return to login screen");
        
        // Add action listeners
        historyButton.addActionListener(e -> {
            new HistoryPage(jFrame, model, idTeam);
            dispose();
        });
        
        staffButton.addActionListener(e -> {
            new StaffPage(jFrame, model, idTeam);
            dispose();
        });
        
        tradeButton.addActionListener(e -> {
            new TradePage(jFrame, model, idTeam);
            dispose();
        });
        
        backButton.addActionListener(e -> {
            new LoginPage(jFrame, model);
            dispose();
        });
        
        // Add buttons to panel
        buttonPanel.add(historyButton);
        buttonPanel.add(staffButton);
        buttonPanel.add(tradeButton);
        buttonPanel.add(backButton);
        
        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        
        // Status bar (South)
        JLabel statusLabel = new JLabel("Team ID: " + idTeam + " | NBA Management System", SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(statusLabel, BorderLayout.SOUTH);
        
        add(mainPanel);
        setVisible(true);
    }
    
    private JButton createMenuButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(200, 100));
        button.setFocusPainted(false);
        return button;
    }
}