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
        JPanel mainPanel = createBackgroundPanel(); // Usa il pannello con lo sfondo
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Title panel (North)
        JLabel titleLabel = new JLabel("Team Management Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Left panel with buttons in a column
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50)); // Optional padding

        // Create smaller styled buttons
        JButton historyButton = createMenuButton("History", "View team history and operations");
        JButton staffButton = createMenuButton("Staff", "Manage coaching staff and observers");
        JButton tradeButton = createMenuButton("Trade", "Manage player trades and contracts");
        JButton backButton = createMenuButton("Back to Login", "Return to login screen");

        titleLabel.setOpaque(false);
        buttonPanel.setOpaque(false);

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

        // Add buttons to panel (stacked vertically)
        buttonPanel.add(historyButton);
        buttonPanel.add(Box.createVerticalStrut(10)); // Spazio tra i bottoni
        buttonPanel.add(staffButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(tradeButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(backButton);

        // Add button panel to the left side
        mainPanel.add(buttonPanel, BorderLayout.WEST);

        // Status bar (South)
        JLabel statusLabel = new JLabel("Team ID: " + idTeam + " | NBA Management System", SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        add(mainPanel);
        setVisible(true);
    }

    private JPanel createBackgroundPanel() {
        return new JPanel(new BorderLayout()) {
            private final Image backgroundImage = new ImageIcon("NBA_Management/IMAGES/lakersBackground.jpg")
                    .getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

    private JButton createMenuButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setPreferredSize(new Dimension(200, 60)); // Bottone più piccolo
        button.setFocusPainted(false);
        return button;
    }
}
