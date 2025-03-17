package view;

import core.Model;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")

public class HistoryPage extends JFrame {

    private Model model;
    private int idTeam;


    public HistoryPage(JFrame jFrame, Model model, int idTeam) {
        this.model = model;
        this.idTeam = idTeam;

        setTitle("History");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Creiamo un JTabbedPane per i vari storici
        JTabbedPane tabbedPane = new JTabbedPane();

        // Tab per lo storico degli allenamenti
        JPanel trainingPanel = new JPanel();
        JTextArea trainingHistoryArea = new JTextArea(20, 50);
        trainingHistoryArea.setEditable(false);
        JScrollPane trainingScrollPane = new JScrollPane(trainingHistoryArea);
        trainingPanel.add(trainingScrollPane);
        tabbedPane.addTab("Training", trainingPanel);
        loadTrainingHistory(idTeam, trainingHistoryArea);

        // Tab per lo storico degli scambi
        JPanel tradePanel = new JPanel();
        JTextArea tradeHistoryArea = new JTextArea(20, 50);
        tradeHistoryArea.setEditable(false);
        JScrollPane tradeScrollPane = new JScrollPane(tradeHistoryArea);
        tradePanel.add(tradeScrollPane);
        tabbedPane.addTab("Trades", tradePanel);
        loadTradeHistory(idTeam, tradeHistoryArea);

        // Tab per lo storico delle partite
        JPanel gamePanel = new JPanel();
        JTextArea gameHistoryArea = new JTextArea(20, 50);
        gameHistoryArea.setEditable(false);
        JScrollPane gameScrollPane = new JScrollPane(gameHistoryArea);
        gamePanel.add(gameScrollPane);
        tabbedPane.addTab("Games", gamePanel);
        loadMatchHistory(idTeam, gameHistoryArea);

        // Tab per lo storico dei contratti
        JPanel contractPanel = new JPanel();
        JTextArea contractHistoryArea = new JTextArea(20, 50);
        contractHistoryArea.setEditable(false);
        JScrollPane contractScrollPane = new JScrollPane(contractHistoryArea);
        contractPanel.add(contractScrollPane);
        tabbedPane.addTab("Contracts", contractPanel);
        loadContractHistory(idTeam, contractHistoryArea);

        // Bottone di ritorno
        JButton backButton = new JButton("Back to Team");
        backButton.addActionListener(e -> {
            new TeamPage(jFrame, model, idTeam);
            dispose(); // Chiude la finestra della HistoryPage
        });

        // Aggiungiamo il TabbedPane e il bottone
        add(tabbedPane, BorderLayout.CENTER);
        add(backButton, BorderLayout.SOUTH);

        setVisible(true);
    }

    // Funzione per caricare lo storico degli allenamenti
    private void loadTrainingHistory(int idTeam, JTextArea trainingHistoryArea) {
        try {
            List<String> trainingHistory = model.getTrainingHistory(idTeam);
            for (String record : trainingHistory) {
                trainingHistoryArea.append(record + "\n");
            }
        } catch (SQLException e) {
            trainingHistoryArea.setText("Error loading training history: " + e.getMessage());
        }
    }

    // Funzione per caricare lo storico degli scambi
    private void loadTradeHistory(int idTeam, JTextArea tradeHistoryArea) {
        try {
            List<String> tradeHistory = model.getTradeHistory(idTeam);
            for (String record : tradeHistory) {
                tradeHistoryArea.append(record + "\n");
            }
        } catch (SQLException e) {
            tradeHistoryArea.setText("Error loading trade history: " + e.getMessage());
        }
    }

    // Funzione per caricare lo storico delle partite
    private void loadMatchHistory(int idTeam, JTextArea gameHistoryArea) {
        try {
            List<String> matchHistory = model.getMatchHistory(idTeam);
            for (String record : matchHistory) {
                gameHistoryArea.append(record + "\n");
            }
        } catch (SQLException e) {
            gameHistoryArea.setText("Error loading match history: " + e.getMessage());
        }
    }

    // Funzione per caricare lo storico dei contratti
    private void loadContractHistory(int idTeam, JTextArea contractHistoryArea) {
        try {
            List<String> contractHistory = model.getContractHistory(idTeam);
            for (String record : contractHistory) {
                contractHistoryArea.append(record + "\n");
            }
        } catch (SQLException e) {
            contractHistoryArea.setText("Error loading contract history: " + e.getMessage());
        }
    }
}
