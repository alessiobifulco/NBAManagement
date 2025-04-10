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
        setLayout(new BorderLayout());

        JTabbedPane tabbedPane = new JTabbedPane();

        // Training History Tab
        JPanel trainingPanel = createHistoryTab("Training");
        tabbedPane.addTab("Training", trainingPanel);
        loadTrainingHistory(idTeam, (JTextArea)((JScrollPane)trainingPanel.getComponent(1)).getViewport().getView());

        // Trade History Tab
        JPanel tradePanel = createHistoryTab("Trades");
        tabbedPane.addTab("Trades", tradePanel);
        loadTradeHistory(idTeam, (JTextArea)((JScrollPane)tradePanel.getComponent(1)).getViewport().getView());

        // Game History Tab
        JPanel gamePanel = createHistoryTab("Games");
        tabbedPane.addTab("Games", gamePanel);
        loadMatchHistory(idTeam, (JTextArea)((JScrollPane)gamePanel.getComponent(1)).getViewport().getView());

        // Contract History Tab
        JPanel contractPanel = createHistoryTab("Contracts");
        tabbedPane.addTab("Contracts", contractPanel);
        loadContractHistory(idTeam, (JTextArea)((JScrollPane)contractPanel.getComponent(1)).getViewport().getView());

        // Back Button
        JButton backButton = new JButton("Back to Team");
        backButton.addActionListener(e -> {
            new TeamPage(parentFrame, model, idTeam);
            dispose();
        });

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(backButton);

        add(tabbedPane, BorderLayout.CENTER);
        add(buttonPanel, BorderLayout.SOUTH);

        setVisible(true);
    }

    private JPanel createHistoryTab(String title) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBorder(new EmptyBorder(10, 10, 10, 10));

        JTextArea historyArea = new JTextArea();
        historyArea.setEditable(false);
        historyArea.setLineWrap(true);
        historyArea.setWrapStyleWord(true);

        JScrollPane scrollPane = new JScrollPane(historyArea);
        panel.add(new JLabel(title + " History:"), BorderLayout.NORTH);
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