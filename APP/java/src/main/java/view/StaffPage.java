package view;

import core.Model;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class StaffPage extends JFrame {
    private final Model model;
    private final JFrame parentFrame;
    private final int idTeam;
    private JComboBox<String> playerComboBox;
    private JRadioButton singleTraining;
    private JRadioButton groupTraining;
    private JTextField dateField, durationField, focusField;
    private JButton saveButton, cancelButton, coachButton, observerButton;
    private JPanel exercisesPanel;
    private ArrayList<JCheckBox> exerciseCheckboxes;

    public StaffPage(JFrame parentFrame, Model model, int idTeam) {
        this.model = model;
        this.parentFrame = parentFrame;
        this.idTeam = idTeam;
        exerciseCheckboxes = new ArrayList<>();

        setTitle("Staff - Organize Training");
        setSize(600, 500);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new GridLayout(3, 2));  // Layout per i bottoni "Coach" e "Observer" iniziali

        // Bottoni per coach e observer
        coachButton = new JButton("Coach");
        observerButton = new JButton("Observer");

        add(coachButton);
        add(observerButton);

        // Azioni per i bottoni
        coachButton.addActionListener(e -> showTrainingSetup());
        observerButton.addActionListener(e -> showObserverView());  // Puoi implementare la vista Observer se serve

        setVisible(true);
    }

    private void showTrainingSetup() {
        // Rimuovi i bottoni e visualizza gli altri componenti
        getContentPane().removeAll();
        setLayout(new GridLayout(8, 2));  // Layout per i componenti di allenamento

        add(new JLabel("Training Date (YYYY-MM-DD):"));
        dateField = new JTextField();
        add(dateField);

        add(new JLabel("Duration (minutes):"));
        durationField = new JTextField();
        add(durationField);

        add(new JLabel("Focus:"));
        focusField = new JTextField();
        add(focusField);

        singleTraining = new JRadioButton("Single Training");
        groupTraining = new JRadioButton("Group Training");
        ButtonGroup group = new ButtonGroup();
        group.add(singleTraining);
        group.add(groupTraining);
        add(singleTraining);
        add(groupTraining);

        add(new JLabel("Select Player:"));
        playerComboBox = new JComboBox<>();
        add(playerComboBox);

        add(new JLabel("Select Exercises:"));
        exercisesPanel = new JPanel();
        exercisesPanel.setLayout(new GridLayout(0, 1));
        JScrollPane scrollPane = new JScrollPane(exercisesPanel);
        add(scrollPane);

        saveButton = new JButton("Save Training");
        cancelButton = new JButton("Cancel");
        add(saveButton);
        add(cancelButton);

        loadPlayers();
        loadExercises();

        singleTraining.addActionListener(e -> playerComboBox.setEnabled(true));
        groupTraining.addActionListener(e -> playerComboBox.setEnabled(false));
        saveButton.addActionListener(e -> saveTraining());
        cancelButton.addActionListener(e -> backToBaseView());

        // Rende visibile la nuova interfaccia
        revalidate();
        repaint();
    }

    private void showObserverView() {
        // Implementa la vista per l'observer, se necessaria
        JOptionPane.showMessageDialog(this, "Observer view not implemented yet.");
    }

    private void backToBaseView() {
        // Rimuovi gli attuali componenti (allenamento) e torna alla schermata base con i bottoni
        getContentPane().removeAll();
        setLayout(new GridLayout(3, 2));  // Layout per i bottoni di base

        add(coachButton);
        add(observerButton);

        // Rende visibile la schermata base
        revalidate();
        repaint();
    }

    private void loadPlayers() {
        try {
            String query = "SELECT idGiocatore, nome, cognome, categoria FROM GIOCATORE WHERE idGiocatore IN (SELECT idGiocatore FROM CONTRATTO WHERE idSquadra = " + idTeam + ")";
            ResultSet rs = model.executeQuery(query);
            while (rs.next()) {
                String player = rs.getInt("idGiocatore") + " - " + rs.getString("nome") + " " + rs.getString("cognome") + " (" + rs.getString("categoria") + ")";
                playerComboBox.addItem(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void loadExercises() {
        try {
            String query = "SELECT idEsercizio, nome FROM ESERCIZIO";
            ResultSet rs = model.executeQuery(query);
            while (rs.next()) {
                JCheckBox checkBox = new JCheckBox(rs.getInt("idEsercizio") + " - " + rs.getString("nome"));
                exerciseCheckboxes.add(checkBox);
                exercisesPanel.add(checkBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void saveTraining() {
        String date = dateField.getText();
        String durationText = durationField.getText();
        String focus = focusField.getText();
        boolean isSingle = singleTraining.isSelected();
        String selectedPlayer = (String) playerComboBox.getSelectedItem();

        if (date.isEmpty() || durationText.isEmpty() || focus.isEmpty() || (!isSingle && !groupTraining.isSelected())) {
            JOptionPane.showMessageDialog(this, "All fields must be filled", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int duration;
        try {
            duration = Integer.parseInt(durationText);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Duration must be a valid number", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        int playerId = isSingle ? Integer.parseInt(selectedPlayer.split(" - ")[0]) : 0;
        String category = isSingle ? "Singolo" : "Gruppo";

        try {
            String insertTraining = "INSERT INTO ALLENAMENTO (idAllenatore, categoria, idGiocatore, durata, data, focus) " +
                    "VALUES ((SELECT idAllenatore FROM SQUADRA WHERE idSquadra = " + idTeam + "), '" + category + "', " + (isSingle ? playerId : "NULL") + ", " + duration + ", '" + date + "', '" + focus + "')";
            model.executeUpdate(insertTraining);

            ResultSet rs = model.executeQuery("SELECT LAST_INSERT_ID() as id");
            rs.next();
            int trainingId = rs.getInt("id");

            for (JCheckBox checkBox : exerciseCheckboxes) {
                if (checkBox.isSelected()) {
                    int exerciseId = Integer.parseInt(checkBox.getText().split(" - ")[0]);
                    String insertExercise = "INSERT INTO ESERCIZIO_IN_ALLENAMENTO (idAllenamento, idEsercizio, serie) " +
                            "VALUES (" + trainingId + ", " + exerciseId + ", 3)";
                    model.executeUpdate(insertExercise);
                }
            }

            JOptionPane.showMessageDialog(this, "Training saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving training", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
