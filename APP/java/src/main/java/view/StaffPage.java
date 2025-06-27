package view;

import core.Model;
import javax.swing.*;
import javax.swing.border.TitledBorder;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.List;

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
        this.exerciseCheckboxes = new ArrayList<>();

        setTitle("Staff - Organization");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

        // Pannello principale con sfondo
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            private final Image backgroundImage = new ImageIcon("NBA_Management/IMAGES/lakersBackground.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        // Pannello a sinistra con i bottoni
        JPanel leftPanel = new JPanel();
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));
        leftPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        leftPanel.setOpaque(false);

        // Creazione bottoni
        coachButton = createMenuButton("Coach", "Organize team training sessions");
        observerButton = createMenuButton("Observer", "Scout free agents and analyze players");
        goBackButton = createMenuButton("Go Back", "Return to team page");

        // Action listeners
        coachButton.addActionListener(e -> showTrainingSetup());
        observerButton.addActionListener(e -> showObserverView());
        goBackButton.addActionListener(e -> goBackToTeamPage());

        // Aggiunta bottoni
        leftPanel.add(coachButton);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(observerButton);
        leftPanel.add(Box.createVerticalStrut(20));
        leftPanel.add(goBackButton);

        mainPanel.add(leftPanel, BorderLayout.WEST);
        setContentPane(mainPanel);
        setVisible(true);
    }

    private JButton createMenuButton(String text, String tooltip) {
        JButton button = new JButton(text);
        button.setToolTipText(tooltip);
        button.setAlignmentX(Component.LEFT_ALIGNMENT);
        return button;
    }

    private void goBackToTeamPage() {
        new TeamPage(parentFrame, model, idTeam);
        this.dispose();
    }

    private void showTrainingSetup() {
        getContentPane().removeAll();

        JPanel mainPanel = new JPanel(new BorderLayout()) {
            private final Image backgroundImage = new ImageIcon("NBA_Management/IMAGES/lakersBackground.jpg").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        contentPanel.setOpaque(false);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Organizza Allenamento");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        contentPanel.add(titleLabel, gbc);

        JPanel trainingTypePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        trainingTypePanel.setOpaque(false);
        singleTraining = new JRadioButton("Allenamento Singolo (Solo Superstar)");
        singleTraining.setForeground(Color.WHITE);
        singleTraining.setOpaque(false);
        groupTraining = new JRadioButton("Allenamento di Gruppo");
        groupTraining.setForeground(Color.WHITE);
        groupTraining.setOpaque(false);
        ButtonGroup group = new ButtonGroup();
        group.add(singleTraining);
        group.add(groupTraining);
        trainingTypePanel.add(singleTraining);
        trainingTypePanel.add(groupTraining);

        gbc.gridy++;
        contentPanel.add(trainingTypePanel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        JLabel dateLabel = new JLabel("Data Allenamento:");
        dateLabel.setForeground(Color.WHITE);
        contentPanel.add(dateLabel, gbc);

        JPanel datePanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        datePanel.setOpaque(false);
        dateSpinner = new JSpinner(new SpinnerDateModel());
        JSpinner.DateEditor dateEditor = new JSpinner.DateEditor(dateSpinner, "dd/MM/yyyy");
        dateSpinner.setEditor(dateEditor);
        dateSpinner.setPreferredSize(new Dimension(120, 25));
        datePanel.add(dateSpinner);
        gbc.gridx = 1;
        contentPanel.add(datePanel, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        JLabel durationLabel = new JLabel("Durata (minuti):");
        durationLabel.setForeground(Color.WHITE);
        contentPanel.add(durationLabel, gbc);
        durationField = new JTextField(10);
        gbc.gridx = 1;
        contentPanel.add(durationField, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        JLabel focusLabel = new JLabel("Focus Allenamento:");
        focusLabel.setForeground(Color.WHITE);
        contentPanel.add(focusLabel, gbc);
        focusField = new JTextField(10);
        gbc.gridx = 1;
        contentPanel.add(focusField, gbc);
        gbc.gridx = 0;

        gbc.gridy++;
        JLabel playerLabel = new JLabel("Giocatore:");
        playerLabel.setForeground(Color.WHITE);
        contentPanel.add(playerLabel, gbc);
        playerComboBox = new JComboBox<>();
        playerComboBox.setEnabled(false);
        gbc.gridx = 1;
        contentPanel.add(playerComboBox, gbc);
        gbc.gridx = 0;
        
        gbc.gridy++;
        gbc.gridwidth = 2;
        exercisesPanel = new JPanel(new GridLayout(0, 2, 10, 5));
        exercisesPanel.setOpaque(false);
        JScrollPane scrollPane = new JScrollPane(exercisesPanel);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.setBorder(BorderFactory.createTitledBorder(
            BorderFactory.createEtchedBorder(), "Esercizi", 
            TitledBorder.DEFAULT_JUSTIFICATION, TitledBorder.DEFAULT_POSITION, 
            new Font("Arial", Font.BOLD, 12), Color.WHITE));
        
        contentPanel.add(scrollPane, gbc);

        gbc.gridy++;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.setOpaque(false);
        saveButton = new JButton("Salva Allenamento");
        saveButton.setPreferredSize(new Dimension(180, 30));
        saveButton.setBackground(new Color(253, 185, 39));
        saveButton.setForeground(Color.BLACK);
        cancelButton = new JButton("Annulla");
        cancelButton.setPreferredSize(new Dimension(180, 30));
        cancelButton.setBackground(new Color(85, 37, 130));
        cancelButton.setForeground(Color.WHITE);
        buttonPanel.add(saveButton);
        buttonPanel.add(cancelButton);
        contentPanel.add(buttonPanel, gbc);

        JButton backButton = new JButton("Indietro");
        backButton.addActionListener(e -> {
            new StaffPage(parentFrame, model, idTeam);
            dispose();
        });
        backButton.setBackground(new Color(85, 37, 130));
        backButton.setForeground(Color.WHITE);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        loadExercises();

        singleTraining.addActionListener(e -> {
            playerComboBox.setEnabled(true);
            loadPlayers("Superstar");
        });
        groupTraining.addActionListener(e -> {
            playerComboBox.setEnabled(false);
            playerComboBox.removeAllItems();
        });
        saveButton.addActionListener(e -> saveTraining());
        cancelButton.addActionListener(e -> backToBaseView());

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);
        revalidate();
        repaint();
    }

    private void showObserverView() {
        getContentPane().removeAll();
        
        JPanel mainPanel = new JPanel(new BorderLayout()) {
            private final Image backgroundImage = new ImageIcon("NBA_Management/IMAGES/lakersBackground.jpg").getImage();
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        JPanel contentPanel = new JPanel(new BorderLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        contentPanel.setOpaque(false);

        JPanel controlPanel = new JPanel(new GridBagLayout());
        controlPanel.setOpaque(false);
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Free Agent Scouting");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setForeground(Color.WHITE);
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 4;
        controlPanel.add(titleLabel, gbc);

        gbc.gridwidth = 1; gbc.gridy++;
        
        gbc.gridx = 0;
        JLabel filterLabel = new JLabel("Filter by:");
        filterLabel.setForeground(Color.WHITE);
        controlPanel.add(filterLabel, gbc);
        
        gbc.gridx = 1;
        String[] attributes = { "All", "Position", "Experience", "Category" };
        JComboBox<String> attributeCombo = new JComboBox<>(attributes);
        controlPanel.add(attributeCombo, gbc);
        
        gbc.gridx = 2;
        JComboBox<String> valueCombo = new JComboBox<>();
        valueCombo.setEnabled(false);
        controlPanel.add(valueCombo, gbc);
        
        gbc.gridy++;
        gbc.gridx = 0;
        JLabel maxLabel = new JLabel("Max results:");
        maxLabel.setForeground(Color.WHITE);
        controlPanel.add(maxLabel, gbc);
        
        gbc.gridx = 1;
        JSpinner numberSpinner = new JSpinner(new SpinnerNumberModel(5, 1, 20, 1));
        controlPanel.add(numberSpinner, gbc);

        gbc.gridx = 2;
        JButton searchButton = new JButton("Find Best Free Agents");
        searchButton.setBackground(new Color(253, 185, 39));
        searchButton.setForeground(Color.BLACK);
        searchButton.setFont(new Font("Arial", Font.BOLD, 14));
        controlPanel.add(searchButton, gbc);

        attributeCombo.addActionListener(e -> {
            valueCombo.removeAllItems();
            String selectedAttribute = (String) attributeCombo.getSelectedItem();
            if (selectedAttribute == null || selectedAttribute.equals("All")) {
                valueCombo.setEnabled(false);
                return;
            }
            valueCombo.setEnabled(true);
            try {
                String query = "";
                if (selectedAttribute.equals("Position")) {
                    query = "SELECT DISTINCT position FROM GIOCATORE WHERE freeAgent = TRUE ORDER BY position";
                } else if (selectedAttribute.equals("Category")) {
                    query = "SELECT DISTINCT categoria FROM GIOCATORE WHERE freeAgent = TRUE ORDER BY categoria";
                } else if (selectedAttribute.equals("Experience")) {
                    query = "SELECT DISTINCT anni_esperienza FROM GIOCATORE WHERE freeAgent = TRUE ORDER BY anni_esperienza";
                }
                ResultSet rs = model.executeQuery(query);
                while (rs.next()) {
                    valueCombo.addItem(rs.getString(1));
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
            }
        });

        DefaultTableModel tableModel = new DefaultTableModel();
        String[] columnNames = {"ID", "Name", "Surname", "Position", "Experience", "Category", "Rank"};
        for(String col : columnNames) tableModel.addColumn(col);
        
        JTable resultsTable = new JTable(tableModel);
        resultsTable.setAutoCreateRowSorter(true);
        resultsTable.setFillsViewportHeight(true);
        resultsTable.setOpaque(false);
        ((JComponent)resultsTable.getDefaultRenderer(Object.class)).setOpaque(false);
        resultsTable.setForeground(Color.WHITE);
        resultsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        resultsTable.getTableHeader().setBackground(new Color(85, 37, 130));
        resultsTable.getTableHeader().setForeground(Color.WHITE);
        
        JScrollPane scrollPane = new JScrollPane(resultsTable);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);

        JButton cancelButton = new JButton("Back");
        cancelButton.setBackground(new Color(85, 37, 130));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.addActionListener(e -> backToBaseView());

        contentPanel.add(controlPanel, BorderLayout.NORTH);
        contentPanel.add(scrollPane, BorderLayout.CENTER);
        contentPanel.add(cancelButton, BorderLayout.SOUTH);

        mainPanel.add(contentPanel, BorderLayout.CENTER);
        setContentPane(mainPanel);

        searchButton.addActionListener(e -> {
            searchAndDisplayFreeAgents(
                (String) attributeCombo.getSelectedItem(),
                (String) valueCombo.getSelectedItem(),
                (Integer) numberSpinner.getValue(),
                tableModel
            );
        });
        
        searchAndDisplayFreeAgents("All", null, (Integer) numberSpinner.getValue(), tableModel);

        revalidate();
        repaint();
    }
    
    private void searchAndDisplayFreeAgents(String attribute, String value, int limit, DefaultTableModel tableModel) {
        try {
            StringBuilder queryBuilder = new StringBuilder(
                "SELECT idGiocatore, nome, cognome, position, anni_esperienza, categoria, valutazione " +
                "FROM GIOCATORE WHERE freeAgent = TRUE ");
            
            List<Object> params = new ArrayList<>();

            if (attribute != null && !attribute.equals("All") && value != null) {
                switch (attribute) {
                    case "Position":
                        queryBuilder.append("AND position = ? ");
                        params.add(value);
                        break;
                    case "Category":
                        queryBuilder.append("AND categoria = ? ");
                        params.add(value);
                        break;
                    case "Experience":
                        queryBuilder.append("AND anni_esperienza >= ? ");
                        params.add(Integer.parseInt(value));
                        break;
                }
            }

            queryBuilder.append("ORDER BY valutazione DESC LIMIT ?");
            params.add(limit);
            
            tableModel.setRowCount(0);

            ResultSet rs = model.executeQuery(queryBuilder.toString(), params.toArray());
            while (rs.next()) {
                tableModel.addRow(new Object[] {
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
        } catch (NumberFormatException ex) {
             JOptionPane.showMessageDialog(this, "Invalid number for experience filter.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }


    private void loadPlayers(String categoryFilter) {
        try {
            String query = "SELECT idGiocatore, nome, cognome FROM GIOCATORE " +
                           "WHERE idGiocatore IN (SELECT idGiocatore FROM CONTRATTO WHERE idSquadra = ? AND stato = TRUE) " +
                           "AND categoria = ?";
            
            ResultSet rs = model.executeQuery(query, idTeam, categoryFilter);
            playerComboBox.removeAllItems();
            while (rs.next()) {
                String player = rs.getInt("idGiocatore") + " - " + rs.getString("nome") + " " + rs.getString("cognome");
                playerComboBox.addItem(player);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading players: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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
                checkBox.setForeground(Color.WHITE);
                checkBox.setOpaque(false);
                exerciseCheckboxes.add(checkBox);
                exercisesPanel.add(checkBox);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error loading exercises: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
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

        if (durationText.isEmpty() || focus.isEmpty() || (!isSingle && !groupTraining.isSelected()) || (isSingle && selectedPlayer == null)) {
            JOptionPane.showMessageDialog(this, "All fields must be filled correctly.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            int duration = Integer.parseInt(durationText);
            Integer playerId = isSingle ? Integer.parseInt(selectedPlayer.split(" - ")[0]) : null;
            String category = isSingle ? "Singolo" : "Gruppo";

            String insertTrainingQuery = "INSERT INTO ALLENAMENTO (idAllenatore, categoria, idGiocatore, durata, data, focus) " +
                                         "VALUES ((SELECT idAllenatore FROM SQUADRA WHERE idSquadra = ?), ?, ?, ?, ?, ?)";
            model.executeUpdate(insertTrainingQuery, idTeam, category, playerId, duration, formattedDate, focus);

            ResultSet rs = model.executeQuery("SELECT LAST_INSERT_ID() as id");
            int trainingId = -1;
            if (rs.next()) {
                trainingId = rs.getInt("id");
            }
            if (trainingId == -1) {
                throw new SQLException("Failed to retrieve generated training ID.");
            }

            for (JCheckBox checkBox : exerciseCheckboxes) {
                if (checkBox.isSelected()) {
                    int exerciseId = Integer.parseInt(checkBox.getText().split(" - ")[0]);
                    String insertExerciseQuery = "INSERT INTO ESERCIZIO_IN_ALLENAMENTO (idAllenamento, idEsercizio, serie) VALUES (?, ?, ?)";
                    model.executeUpdate(insertExerciseQuery, trainingId, exerciseId, 3);
                }
            }

            JOptionPane.showMessageDialog(this, "Training saved successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Duration must be a valid number.", "Error", JOptionPane.ERROR_MESSAGE);
        } catch (SQLException e) {
            e.printStackTrace();
            JOptionPane.showMessageDialog(this, "Error saving training: " + e.getMessage(), "Database Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void backToBaseView() {
        new StaffPage(parentFrame, model, idTeam);
        this.dispose();
    }
}
