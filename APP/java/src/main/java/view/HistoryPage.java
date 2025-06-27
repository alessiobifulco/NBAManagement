package view;

import core.Model;
import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class HistoryPage extends JFrame {
    private final Model model;
    private final int idTeam;

    public HistoryPage(JFrame parentFrame, Model model, int idTeam) {
        this.model = model;
        this.idTeam = idTeam;
        initializeUI(parentFrame);
    }

    private void initializeUI(JFrame parentFrame) {
        setTitle("Team History - " + idTeam);
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Main panel with background
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            private final Image backgroundImage = new ImageIcon("NBA_Management/IMAGES/lakersBackground.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Title panel
        JLabel titleLabel = new JLabel("Team History - Team " + idTeam, SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 28));
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setOpaque(false);
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Create tabbed pane with semi-transparent background
        JTabbedPane tabbedPane = new JTabbedPane();
        tabbedPane.setOpaque(false);

        // Make tabs transparent and customize their appearance
        tabbedPane.setUI(new javax.swing.plaf.basic.BasicTabbedPaneUI() {
            @Override
            protected void paintTabBackground(Graphics g, int tabPlacement, int tabIndex, 
                                           int x, int y, int w, int h, boolean isSelected) {
                if (isSelected) {
                    g.setColor(new Color(200, 200, 200, 150)); // Semi-transparent white for selected tab
                } else {
                    g.setColor(new Color(150, 150, 150, 100)); // More transparent for unselected tabs
                }
                g.fillRect(x, y, w, h);
            }

            @Override
            protected void paintContentBorder(Graphics g, int tabPlacement, int selectedIndex) {
                // Remove content border
            }
        });

        // Create tabs with semi-transparent panels
        JPanel trainingPanel = createHistoryTab("Training");
        tabbedPane.addTab("Training", trainingPanel);
        loadTrainingHistory(idTeam, (JTextArea) ((JScrollPane) trainingPanel.getComponent(1)).getViewport().getView());

        JPanel tradePanel = createHistoryTab("Trades");
        tabbedPane.addTab("Trades", tradePanel);
        loadTradeHistory(idTeam, (JTextArea) ((JScrollPane) tradePanel.getComponent(1)).getViewport().getView());

        JPanel gamePanel = createHistoryTab("Games");
        tabbedPane.addTab("Games", gamePanel);
        loadMatchHistory(idTeam, (JTextArea) ((JScrollPane) gamePanel.getComponent(1)).getViewport().getView());

        JPanel contractPanel = createHistoryTab("Contracts");
        tabbedPane.addTab("Contracts", contractPanel);
        loadContractHistory(idTeam, (JTextArea) ((JScrollPane) contractPanel.getComponent(1)).getViewport().getView());

        // Customize tab appearance
        tabbedPane.setForeground(Color.WHITE);
        tabbedPane.setFont(new Font("Arial", Font.BOLD, 16));

        // Add tabbed pane to main panel
        mainPanel.add(tabbedPane, BorderLayout.CENTER);

        // Back button with styling
        JButton backButton = new JButton("Back to Team");
       
        backButton.addActionListener(e -> {
            new TeamPage(parentFrame, model, idTeam);
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel createHistoryTab(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(20, 20, 20, 20));
        panel.setOpaque(false);

        JLabel titleLabel = new JLabel(title + " History:");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 20));
        titleLabel.setForeground(Color.WHITE);
        panel.add(titleLabel, BorderLayout.NORTH);

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);
        historyArea.setFont(new Font("Arial", Font.PLAIN, 16));
        historyArea.setForeground(Color.WHITE);
        historyArea.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(historyArea);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(255, 255, 255, 100), 2));

        panel.add(scrollPane, BorderLayout.CENTER);

        return panel;
    }

    private void loadTrainingHistory(int idTeam, JTextArea area) {
        try {
            List<String> history = model.getTrainingHistory(idTeam);
            if (history.isEmpty()) {
                area.setText("No training history available");
            } else {
                history.forEach(record -> area.append("• " + record + "\n\n"));
            }
        } catch (SQLException e) {
            area.setText("Error loading training history:\n" + e.getMessage());
        }
    }

    private void loadTradeHistory(int idTeam, JTextArea area) {
        try {
            List<String> history = model.getTradeHistory(idTeam);
            if (history.isEmpty()) {
                area.setText("No trade history available");
            } else {
                history.forEach(record -> area.append("- " + record + "\n\n"));
            }
        } catch (SQLException e) {
            area.setText("Error loading trade history:\n" + e.getMessage());
        }
    }

    private void loadMatchHistory(int idTeam, JTextArea area) {
        try {
            List<String> history = model.getMatchHistory(idTeam);
            if (history.isEmpty()) {
                area.setText("No match history available");
            } else {
                history.forEach(record -> area.append("• " + record + "\n\n"));
            }
        } catch (SQLException e) {
            area.setText("Error loading match history:\n" + e.getMessage());
        }
    }

    private void loadContractHistory(int idTeam, JTextArea area) {
        try {
            List<String> history = model.getContractHistory(idTeam);
            if (history.isEmpty()) {
                area.setText("No contract history available");
            } else {
                history.forEach(record -> area.append("- " + record + "\n\n"));
            }
        } catch (SQLException e) {
            area.setText("Error loading contract history:\n" + e.getMessage());
        }
    }
}