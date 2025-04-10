package view;

import core.Model;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

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
        setLayout(new BorderLayout(10, 10));
    
        // Pannello principale con padding
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        add(mainPanel, BorderLayout.CENTER);
    
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Titolo
        JLabel titleLabel = new JLabel("Organizza Allenamento");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        mainPanel.add(titleLabel, gbc);
    
        // Tipo allenamento
        JPanel trainingTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        singleTraining = new JRadioButton("Allenamento Singolo (Solo Superstar)");
        groupTraining = new JRadioButton("Allenamento di Gruppo");
        ButtonGroup group = new ButtonGroup();
        group.add(singleTraining);
        group.add(groupTraining);
        trainingTypePanel.add(singleTraining);
        trainingTypePanel.add(groupTraining);
    
        gbc.gridy++;
        mainPanel.add(trainingTypePanel, gbc);
    
        // Data con calendario grafico
        gbc.gridy++;
        gbc.gridwidth = 1;
        mainPanel.add(new JLabel("Data Allenamento:"), gbc);
    
        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setPreferredSize(new Dimension(120, 25));
        datePanel.add(dateSpinner);
    
        // Icona calendario
        JButton calendarButton = new JButton(new ImageIcon("calendar_icon.png")); // Sostituisci con il tuo percorso
        calendarButton.setPreferredSize(new Dimension(25, 25));
        calendarButton.addActionListener(e -> {
            // Qui potresti implementare un JCalendar popup
            dateSpinner.setValue(new Date()); // Per semplicità, impostiamo la data corrente
        });
        datePanel.add(calendarButton);
        gbc.gridx = 1;
        mainPanel.add(datePanel, gbc);
        gbc.gridx = 0;
    
        // Durata
        gbc.gridy++;
        mainPanel.add(new JLabel("Durata (minuti):"), gbc);
        durationField = new JTextField(10);
        gbc.gridx = 1;
        mainPanel.add(durationField, gbc);
        gbc.gridx = 0;
    
        // Focus
        gbc.gridy++;
        mainPanel.add(new JLabel("Focus Allenamento:"), gbc);
        focusField = new JTextField(10);
        gbc.gridx = 1;
        mainPanel.add(focusField, gbc);
        gbc.gridx = 0;
    
        // Selezione giocatore (solo per allenamento singolo)
        gbc.gridy++;
        mainPanel.add(new JLabel("Giocatore:"), gbc);
        playerComboBox = new JComboBox<>();
        playerComboBox.setEnabled(false); // Disabilitato inizialmente
        gbc.gridx = 1;
        mainPanel.add(playerComboBox, gbc);
        gbc.gridx = 0;
    
        // Esercizi con pannello a schede
        gbc.gridy++;
        gbc.gridwidth = 2;
        JTabbedPane exercisesTabbedPane = new JTabbedPane();
    
        // Pannello per gli esercizi
        exercisesPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        JScrollPane scrollPane = new JScrollPane(exercisesPanel);
        exercisesTabbedPane.addTab("Esercizi", scrollPane);
    
        // Pannello per i predefiniti (opzionale)
        JPanel presetPanel = new JPanel();
        exercisesTabbedPane.addTab("Schede Predefinite", presetPanel);
    
        mainPanel.add(exercisesTabbedPane, gbc);
    
        // Pulsanti
        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        saveButton = new JButton("Salva Allenamento");
        saveButton.setPreferredSize(new Dimension(180, 30));
        cancelButton = new JButton("Annulla");
        cancelButton.setPreferredSize(new Dimension(180, 30));
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        mainPanel.add(buttonPanel, gbc);
    
        // Caricamento iniziale
        loadExercises();
    
        // Listener per il tipo di allenamento
        singleTraining.addActionListener(e -> {
            playerComboBox.setEnabled(true);
            loadPlayers("Superstar"); // Carica solo i Superstar
        });
    
        groupTraining.addActionListener(e -> {
            playerComboBox.setEnabled(false);
            loadPlayers(""); // Carica tutti i giocatori
        });
    
        saveButton.addActionListener(e -> saveTraining());
        cancelButton.addActionListener(e -> backToBaseView());
    
        revalidate();
        repaint();
    }

    private void loadPlayers(String categoryFilter) {
        try {
            String query;
            if (categoryFilter.isEmpty()) {
                query = "SELECT idGiocatore, nome, cognome FROM GIOCATORE " +
                        "WHERE idGiocatore IN (SELECT idGiocatore FROM CONTRATTO " +
                        "WHERE idSquadra = " + idTeam + " AND stato = TRUE)";
            } else {
                query = "SELECT idGiocatore, nome, cognome FROM GIOCATORE " +
                        "WHERE idGiocatore IN (SELECT idGiocatore FROM CONTRATTO " +
                        "WHERE idSquadra = " + idTeam + " AND stato = TRUE) " +
                        "AND categoria = '" + categoryFilter + "'";
            }
    
            ResultSet rs = model.executeQuery(query);
            playerComboBox.removeAllItems();
            while (rs.next()) {
                String player = rs.getInt("idGiocatore") + " - " + 
                               rs.getString("nome") + " " + rs.getString("cognome");
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
            exercisesPanel.removeAll();
            exerciseCheckboxes.clear();
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
        Date selectedDate = (Date) dateSpinner.getValue();
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
            String insertTraining = "INSERT INTO ALLENAMENTO (idAllenatore, categoria, idGiocatore, durata, data, focus) " +
                    "VALUES ((SELECT idAllenatore FROM SQUADRA WHERE idSquadra = " + idTeam + "), '" + category + "', " +
                    (isSingle ? playerId : "NULL") + ", " + duration + ", '" + formattedDate + "', '" + focus + "')";
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
        setLayout(new BorderLayout(10, 10));
    
        // Pannello principale
        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
    
        // Pannello di controllo
        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;
    
        // Titolo
        JLabel titleLabel = new JLabel("Free Agent Scouting");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 3;
        controlPanel.add(titleLabel, gbc);
    
        // Filtri di ricerca
        gbc.gridwidth = 1;
        gbc.gridy++;
    
        // Filtro per attributo
        controlPanel.add(new JLabel("Filter by:"), gbc);
        String[] attributes = {"Position", "Experience", "Category"};
        JComboBox<String> attributeCombo = new JComboBox<>(attributes);
        gbc.gridx++;
        controlPanel.add(attributeCombo, gbc);
    
        // Filtro per valore
        controlPanel.add(new JLabel("Value:"), gbc);
        JComboBox<String> valueCombo = new JComboBox<>();
        gbc.gridx++;
        controlPanel.add(valueCombo, gbc);
    
        // Numero di giocatori
        gbc.gridx = 0;
        gbc.gridy++;
        controlPanel.add(new JLabel("Max results:"), gbc);
        JSpinner numberSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));
        gbc.gridx++;
        controlPanel.add(numberSpinner, gbc);
    
        // Pulsante ricerca
        JButton searchButton = new JButton("Find Best Free Agents");
        searchButton.setBackground(new Color(0, 120, 215));
        searchButton.setForeground(Color.WHITE);
        gbc.gridx++;
        controlPanel.add(searchButton, gbc);
    
        // Aggiorna valori in base alla selezione
        attributeCombo.addActionListener(e -> {
            valueCombo.removeAllItems();
            String selectedAttribute = (String) attributeCombo.getSelectedItem();
            
            try {
                String query;
                if (selectedAttribute.equals("Position")) {
                    query = "SELECT DISTINCT position FROM GIOCATORE ORDER BY position";
                } else if (selectedAttribute.equals("Category")) {
                    query = "SELECT DISTINCT categoria FROM GIOCATORE ORDER BY categoria";
                } else { // Experience
                    query = "SELECT DISTINCT anni_esperienza FROM GIOCATORE ORDER BY anni_esperienza";
                }
                
                ResultSet rs = model.executeQuery(query);
                while (rs.next()) {
                    valueCombo.addItem(rs.getString(1));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });
    
        // Tabella risultati
        DefaultTableModel tableModel = new DefaultTableModel();
        tableModel.addColumn("ID");
        tableModel.addColumn("Name");
        tableModel.addColumn("Surname");
        tableModel.addColumn("Position");
        tableModel.addColumn("Experience");
        tableModel.addColumn("Category");
        tableModel.addColumn("Rank");
    
        JTable resultsTable = new JTable(tableModel);
        resultsTable.setAutoCreateRowSorter(true);
        JScrollPane scrollPane = new JScrollPane(resultsTable);
    
        // Pulsante annulla
        JButton cancelButton = new JButton("Back");
        cancelButton.addActionListener(e -> backToBaseView());
    
        // Layout
        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(scrollPane, BorderLayout.CENTER);
        mainPanel.add(cancelButton, BorderLayout.SOUTH);
    
        add(mainPanel, BorderLayout.CENTER);
    
        // Azione di ricerca
        searchButton.addActionListener(e -> {
            String attribute = (String) attributeCombo.getSelectedItem();
            String value = (String) valueCombo.getSelectedItem();
            int limit = (Integer) numberSpinner.getValue();
    
            try {
                // Query per trovare i migliori free agent
                String query = "SELECT idGiocatore, nome, cognome, position, anni_esperienza, categoria, valutazione " +
                               "FROM GIOCATORE " +
                               "WHERE freeAgent = TRUE ";
                
                if (attribute.equals("Position")) {
                    query += "AND position = '" + value + "' ";
                } else if (attribute.equals("Category")) {
                    query += "AND categoria = '" + value + "' ";
                } else { // Experience
                    query += "AND anni_esperienza >= " + value + " ";
                }
                
                query += "ORDER BY valutazione DESC LIMIT " + limit;
    
                // Pulisci la tabella
                tableModel.setRowCount(0);
    
                // Esegui query e mostra risultati
                ResultSet rs = model.executeQuery(query);
                while (rs.next()) {
                    tableModel.addRow(new Object[]{
                        rs.getInt("idGiocatore"),
                        rs.getString("nome"),
                        rs.getString("cognome"),
                        rs.getString("position"),
                        rs.getInt("anni_esperienza"),
                        rs.getString("categoria"),
                        rs.getDouble("valutazione")
                    });
                }
    
                if (tableModel.getRowCount() == 0) {
                    JOptionPane.showMessageDialog(this, "No free agents found with these criteria", 
                                                "Info", JOptionPane.INFORMATION_MESSAGE);
                }
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this, "Error searching free agents: " + ex.getMessage(), 
                                            "Error", JOptionPane.ERROR_MESSAGE);
                ex.printStackTrace();
            }
        });
    
        // Carica valori iniziali
        attributeCombo.setSelectedIndex(0);
    
        revalidate();
        repaint();
    }
}