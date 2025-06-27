package view;

import core.Model;
import data.Player;
import data.Coach;
import data.Game;
import data.Team; 

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.time.format.DateTimeParseException;
import java.util.List;
import java.util.NoSuchElementException;

public class AdministratorPage extends JFrame {
    private final Model model;

    public AdministratorPage(JFrame parentFrame, Model model) {
        this.model = model;
        initializeUI(parentFrame);
    }

    private void initializeUI(JFrame parentFrame) {
        setTitle("Administrator");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        // Pannello principale con BorderLayout e sfondo
        JPanel mainPanel = createBackgroundPanel();
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        // Pannello del titolo (Nord)
        JLabel titleLabel = new JLabel("Administrator Dashboard", SwingConstants.CENTER);
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        mainPanel.add(titleLabel, BorderLayout.NORTH);

        // Pannello a sinistra con i bottoni
        JPanel buttonPanel = new JPanel();
        buttonPanel.setLayout(new BoxLayout(buttonPanel, BoxLayout.Y_AXIS));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));
        buttonPanel.setOpaque(false);

        // Creazione bottoni
        JButton playerButton = createMenuButton("Manage Players", "Add, remove or edit players");
        JButton coachButton = createMenuButton("Manage Coaches", "Add, remove or edit coaches");
        JButton gameButton = createMenuButton("Manage Games", "Add, remove or edit games");
        JButton backButton = createMenuButton("Back to Login", "Return to login screen");

        // Aggiunta action listeners
        playerButton.addActionListener(e -> managePlayers());
        coachButton.addActionListener(e -> manageCoaches());
        gameButton.addActionListener(e -> manageGames());
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });

        // Aggiunta bottoni al pannello con spaziatura
        buttonPanel.add(playerButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(coachButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(gameButton);
        buttonPanel.add(Box.createVerticalStrut(10));
        buttonPanel.add(backButton);

        // Aggiunta pannello bottoni al lato OVEST
        mainPanel.add(buttonPanel, BorderLayout.WEST);

        // Status bar (Sud)
        JLabel statusLabel = new JLabel("NBA Management System - Administrator Mode", SwingConstants.CENTER);
        statusLabel.setBorder(BorderFactory.createEtchedBorder());
        mainPanel.add(statusLabel, BorderLayout.SOUTH);

        setContentPane(mainPanel);
        setVisible(true);
    }

    private JPanel createBackgroundPanel() {
        return new JPanel(new BorderLayout()) {
            private final Image backgroundImage = new ImageIcon("NBA_Management/IMAGES/adminPageBackground.jpg")
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
        button.setPreferredSize(new Dimension(200, 60));
        button.setFocusPainted(false);
        return button;
    }

    private void managePlayers() {
        String[] options = { "Add", "Remove", "Cancel" };
        int choice = JOptionPane.showOptionDialog(this,
                "Player Management - Choose action:",
                "Player Management",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) { // Add
            addPlayer();
        } else if (choice == 1) { // Remove
            removePlayer();
        }
    }

    private void addPlayer() {
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField ageField = new JTextField();
        String[] positions = { "PG", "SG", "SF", "PF", "C" };
        JComboBox<String> positionCombo = new JComboBox<>(positions);
        String[] categories = { "Superstar", "AllStar", "RolePlayer", "BenchPlayer" };
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        JTextField ratingField = new JTextField();
        JTextField experienceField = new JTextField();

        Object[] message = {
                "Name:", nameField,
                "Surname:", surnameField,
                "Age:", ageField,
                "Position:", positionCombo,
                "Category:", categoryCombo,
                "Rating:", ratingField,
                "Experience (years):", experienceField,
        };

        int option = JOptionPane.showConfirmDialog(this,
                message,
                "Add New Player",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                model.addPlayer(
                        nameField.getText(),
                        surnameField.getText(),
                        Integer.parseInt(ageField.getText()),
                        positionCombo.getSelectedItem().toString(),
                        categoryCombo.getSelectedItem().toString(),
                        Double.parseDouble(ratingField.getText()),
                        Integer.parseInt(experienceField.getText()),
                        true); // Player viene aggiunto sempre come free agent
                JOptionPane.showMessageDialog(this, "Player added successfully!");
            } catch (NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Invalid number format: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(this,
                        "Database error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removePlayer() {
        try {
            List<Player> players = model.getAllPlayers();
            if (players.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No players available to remove");
                return;
            }
            String[] playerNames = players.stream()
                    .map(p -> p.getIdPlayer() + ": " + p.getName() + " " + p.getSurname())
                    .toArray(String[]::new);

            String selected = (String) JOptionPane.showInputDialog(this,
                    "Select player to remove:",
                    "Remove Player",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    playerNames,
                    playerNames[0]);

            if (selected != null) {
                int playerId = Integer.parseInt(selected.split(":")[0]);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove this player?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    model.removePlayer(playerId);
                    JOptionPane.showMessageDialog(this, "Player removed successfully!");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manageCoaches() {
        String[] options = { "Add", "Remove", "Cancel" };
        int choice = JOptionPane.showOptionDialog(this,
                "Coach Management - Choose action:",
                "Coach Management",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) { // Add
            addCoach();
        } else if (choice == 1) { // Remove
            removeCoach();
        }
    }

    private void addCoach() {
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField experienceField = new JTextField();

        Object[] message = {
                "Name:", nameField,
                "Surname:", surnameField,
                "Salary:", salaryField,
                "Experience (years):", experienceField,
        };

        int option = JOptionPane.showConfirmDialog(this,
                message,
                "Add New Coach",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                model.addCoach(
                        nameField.getText(),
                        surnameField.getText(),
                        Double.parseDouble(salaryField.getText()),
                        Integer.parseInt(experienceField.getText()),
                        true); // Coach viene aggiunto sempre come 'free'
                JOptionPane.showMessageDialog(this, "Coach added successfully!");
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeCoach() {
        try {
            List<Coach> coaches = model.getAllCoaches();
            if (coaches.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No coaches available to remove");
                return;
            }
            String[] coachNames = coaches.stream()
                    .map(c -> c.getIdCoach() + ": " + c.getName() + " " + c.getSurname())
                    .toArray(String[]::new);

            String selected = (String) JOptionPane.showInputDialog(this,
                    "Select coach to remove:",
                    "Remove Coach",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    coachNames,
                    coachNames[0]);

            if (selected != null) {
                int coachId = Integer.parseInt(selected.split(":")[0]);
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove this coach?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeCoach(coachId);
                    JOptionPane.showMessageDialog(this, "Coach removed successfully!");
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void manageGames() {
        String[] options = { "Add", "Remove", "Cancel" };
        int choice = JOptionPane.showOptionDialog(this,
                "Game Management - Choose action:",
                "Game Management",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                options,
                options[0]);

        if (choice == 0) { // Add
            addGame();
        } else if (choice == 1) { // Remove
            removeGame();
        }
    }

    private void addGame() {
        JTextField team1NameField = new JTextField();
        JTextField team2NameField = new JTextField();
        JTextField resultField = new JTextField();
        JTextField stadiumField = new JTextField();
        JTextField dateField = new JTextField();

        Object[] message = {
                "Home Team Name:", team1NameField,
                "Away Team Name:", team2NameField,
                "Result (e.g., 100-98):", resultField,
                "Stadium ID:", stadiumField,
                "Date (YYYY-MM-DD):", dateField
        };

        int option = JOptionPane.showConfirmDialog(this,
                message,
                "Add New Game",
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                String team1Name = team1NameField.getText();
                String team2Name = team2NameField.getText();

                if (team1Name.equalsIgnoreCase(team2Name)) {
                    JOptionPane.showMessageDialog(this, "Home team and away team cannot be the same.", "Input Error", JOptionPane.ERROR_MESSAGE);
                    return; 
                }

                Team team1 = model.getTeamByName(team1Name);
                Team team2 = model.getTeamByName(team2Name);

                if (team1 == null || team2 == null) {
                    String missingTeam = (team1 == null) ? team1Name : team2Name;
                    throw new NoSuchElementException("Team not found: " + missingTeam);
                }

                model.addGame(
                        team1.getIdTeam(), 
                        team2.getIdTeam(), 
                        resultField.getText(),
                        Integer.parseInt(stadiumField.getText()),
                        java.sql.Date.valueOf(dateField.getText()).toLocalDate());
                JOptionPane.showMessageDialog(this, "Game added successfully!");
            } catch (SQLException | NumberFormatException | DateTimeParseException | NoSuchElementException ex) {
                JOptionPane.showMessageDialog(this,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeGame() {
        try {
            List<Game> games = model.getAllGames();
            if (games.isEmpty()) {
                JOptionPane.showMessageDialog(this, "No games available to remove");
                return;
            }

            // MODIFICA: Prepara i nomi delle partite con i nomi completi delle squadre.
            // Questo richiede che il Model abbia un metodo getTeamById(int).
            String[] gameNames = new String[games.size()];
            for (int i = 0; i < games.size(); i++) {
                Game g = games.get(i);
                try {
                    // Si assume che esista model.getTeamById(int) nel model
                    Team team1 = model.getTeamById(g.getIdTeam1());
                    Team team2 = model.getTeamById(g.getIdTeam2());

                    // Se un team non viene trovato (improbabile con DB consistente), mostra l'ID come fallback.
                    String team1Name = (team1 != null) ? team1.getCity() + " " + team1.getName() : "ID: " + g.getIdTeam1();
                    String team2Name = (team2 != null) ? team2.getCity() + " " + team2.getName() : "ID: " + g.getIdTeam2();
                    
                    gameNames[i] = g.getIdGame() + ": " + team1Name + " vs " + team2Name + " on " + g.getDate();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this,
                            "Database error while fetching team names: " + ex.getMessage(),
                            "Error",
                            JOptionPane.ERROR_MESSAGE);
                    return; // Esce dal metodo se c'è un errore DB
                }
            }

            String selected = (String) JOptionPane.showInputDialog(this,
                    "Select game to remove:",
                    "Remove Game",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    gameNames,
                    gameNames[0]);

            if (selected != null) {
                int gameId = Integer.parseInt(selected.split(":")[0]);

                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove this game?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION);

                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeGame(gameId);
                    JOptionPane.showMessageDialog(this, "Game removed successfully!");
                }
            }
        } catch (SQLException ex) {
            // Questo catch gestisce gli errori in model.getAllGames() o model.removeGame()
            JOptionPane.showMessageDialog(this,
                    "Error: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
