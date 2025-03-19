package view;

import core.Model;
import javax.swing.*;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

@SuppressWarnings("unused")
public class StaffPage extends JFrame {
    private final Model model;
    private final JFrame parentFrame;
    private final int idTeam;
    private JComboBox<String> playerComboBox;
    private JRadioButton singleTraining;
    private JRadioButton groupTraining;
    private JSpinner dateSpinner;
    private JTextField durationField, focusField;
    private JButton saveButton, cancelButton, coachButton, observerButton, goBackButton;
    private JPanel exercisesPanel;
    private ArrayList<JCheckBox> exerciseCheckboxes;

    public StaffPage(JFrame parentFrame, Model model, int idTeam) {
        this.model = model;
        this.parentFrame = parentFrame;
        this.idTeam = idTeam;
        exerciseCheckboxes = new ArrayList<>();

        setTitle("Staff - Organization");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new FlowLayout(FlowLayout.CENTER, 25, 25));

        // setLayout(new GridLayout(3, 2));

        coachButton = new JButton("Coach");
        observerButton = new JButton("Observer");
        goBackButton = new JButton("Go Back");

        coachButton.setPreferredSize(new Dimension(100, 60));
        observerButton.setPreferredSize(new Dimension(100, 60));
        goBackButton.setPreferredSize(new Dimension(100, 60));

        add(coachButton);
        add(observerButton);
        add(goBackButton);

        coachButton.addActionListener(e -> showTrainingSetup());
        observerButton.addActionListener(e -> showObserverView());
        goBackButton.addActionListener(e -> goBackToTeamPage());

        setVisible(true);
    }

    private void goBackToTeamPage() {
        new TeamPage(parentFrame, model, idTeam);
        this.dispose();
    }

    private void showTrainingSetup() {
        getContentPane().removeAll();
        setLayout(new GridLayout(8, 2));

        add(new JLabel("Training Date:"));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        dateSpinner.setEditor(new JSpinner.DateEditor(dateSpinner, "yyyy-MM-dd"));
        add(dateSpinner);

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
        exercisesPanel = new JPanel(new GridLayout(0, 1));
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

        revalidate();
        repaint();
    }

    private void loadPlayers() {
        try {
            String query = "SELECT idGiocatore, nome, cognome, categoria FROM GIOCATORE WHERE idGiocatore IN (SELECT idGiocatore FROM CONTRATTO WHERE idSquadra = "
                    + idTeam + ")";
            ResultSet rs = model.executeQuery(query);
            while (rs.next()) {
                String player = rs.getInt("idGiocatore") + " - " + rs.getString("nome") + " " + rs.getString("cognome")
                        + " (" + rs.getString("categoria") + ")";
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
        // Ottieni la data selezionata dal JSpinner
        Date selectedDate = (Date) dateSpinner.getValue();

        // Se vuoi formattarla in un formato specifico (es. YYYY-MM-DD)
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String formattedDate = dateFormat.format(selectedDate);

        String durationText = durationField.getText();
        String focus = focusField.getText();
        boolean isSingle = singleTraining.isSelected();
        String selectedPlayer = (String) playerComboBox.getSelectedItem();

        if (formattedDate.isEmpty() || durationText.isEmpty() || focus.isEmpty()
                || (!isSingle && !groupTraining.isSelected())) {
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
            String insertTraining = "INSERT INTO ALLENAMENTO (idAllenatore, categoria, idGiocatore, durata, data, focus) "
                    +
                    "VALUES ((SELECT idAllenatore FROM SQUADRA WHERE idSquadra = " + idTeam + "), '" + category + "', "
                    +
                    (isSingle ? playerId : "NULL") + ", " + duration + ", '" + formattedDate + "', '" + focus + "')";
            model.executeUpdate(insertTraining);

            ResultSet rs = model.executeQuery("SELECT LAST_INSERT_ID() as id");
            rs.next();
            int trainingId = rs.getInt("id");

            for (JCheckBox checkBox : exerciseCheckboxes) {
                if (checkBox.isSelected()) {
                    int exerciseId = Integer.parseInt(checkBox.getText().split(" - ")[0]);
                    String insertExercise = "INSERT INTO ESERCIZIO_IN_ALLENAMENTO (idAllenamento, idEsercizio, serie) "
                            +
                            "VALUES (" + trainingId + ", " + exerciseId + ", 3)";
                    model.executeUpdate(insertExercise);
                }
            }

            JOptionPane.showMessageDialog(this, "Training saved successfully!", "Success",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving training", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToBaseView() {
        new StaffPage(parentFrame, model, idTeam);
        this.dispose();
    }

    private void showObserverView() {
        getContentPane().removeAll();
        setLayout(new GridLayout(6, 2));

        add(new JLabel("Select Attribute:"));
        String[] attributes = { "Position", "Years of Experience", "Category" };
        JComboBox<String> attributeComboBox = new JComboBox<>(attributes);
        add(attributeComboBox);

        add(new JLabel("Select Value:"));
        JComboBox<String> valueComboBox = new JComboBox<>();
        add(valueComboBox);

        attributeComboBox.addActionListener(e -> {
            valueComboBox.removeAllItems();
            String selectedAttribute = (String) attributeComboBox.getSelectedItem();

            if ("Position".equals(selectedAttribute)) {
                for (String pos : new String[] { "PG", "SG", "SF", "PF", "C" }) {
                    valueComboBox.addItem(pos);
                }
            } else if ("Category".equals(selectedAttribute)) {
                for (String cat : new String[] { "Superstar", "All-Star", "Role Player", "Bench Player" }) {
                    valueComboBox.addItem(cat);
                }
            } else if ("Years of Experience".equals(selectedAttribute)) {
                try {
                    ResultSet rs = model.executeQuery(
                            "SELECT DISTINCT anni_esperienza FROM GIOCATORE ORDER BY anni_esperienza ASC");
                    while (rs.next()) {
                        valueComboBox.addItem(String.valueOf(rs.getInt("anni_esperienza")));
                    }
                } catch (SQLException ex) {
                    ex.printStackTrace();
                }
            }
        });

        add(new JLabel("Number of Players:"));
        JSlider numberSlider = new JSlider(1, 10, 1);
        numberSlider.setMajorTickSpacing(10);
        numberSlider.setMinorTickSpacing(1);
        numberSlider.setPaintTicks(true);
        numberSlider.setPaintLabels(true);
        add(numberSlider);

        JButton searchButton = new JButton("Search");
        add(searchButton);

        JTable resultsTable = new JTable();
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        add(scrollPane);

        searchButton.addActionListener(e -> {
            String selectedAttribute = (String) attributeComboBox.getSelectedItem();
            String selectedValue = (String) valueComboBox.getSelectedItem();
            String query = "SELECT * FROM GIOCATORE WHERE " + selectedAttribute + " = '" + selectedValue + "'";
            try {
                ResultSet rs = model.executeQuery(query);
                // Update table with the result
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(e -> backToBaseView());
        add(cancelButton);

        revalidate();
        repaint();
    }
}
