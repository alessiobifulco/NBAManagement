package view;

import core.Model;
import data.Player;
import data.Coach;
import data.Observer;
import data.Game;
import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

public class AdministratorPage extends JFrame {
    private final Model model;

    public AdministratorPage(JFrame parentFrame, Model model) {
        this.model = model;
        initializeUI(parentFrame);
    }

    private void initializeUI(JFrame parentFrame) {
        setTitle("Administrator");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        // Back button to return to the Login page
        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            parentFrame.setVisible(true);
            dispose();
        });
        add(backButton);

        // Player management button
        JButton playerButton = new JButton("Manage Players");
        playerButton.addActionListener(e -> managePlayers());
        add(playerButton);

        // Coach management button
        JButton coachButton = new JButton("Manage Coaches");
        coachButton.addActionListener(e -> manageCoaches());
        add(coachButton);

        // Observer management button
        JButton observerButton = new JButton("Manage Observers");
        observerButton.addActionListener(e -> manageObservers());
        add(observerButton);

        // Game management button
        JButton gameButton = new JButton("Manage Games");
        gameButton.addActionListener(e -> manageGames());
        add(gameButton);

        setVisible(true);
    }

    private void managePlayers() {
        String[] options = {"Add", "Remove", "Cancel"};
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
        String[] positions = {"PG", "SG", "SF", "PF", "C"};
        JComboBox<String> positionCombo = new JComboBox<>(positions);
        String[] categories = {"Superstar", "AllStar", "RolePlayer", "BenchPlayer"};
        JComboBox<String> categoryCombo = new JComboBox<>(categories);
        JTextField ratingField = new JTextField();
        JTextField experienceField = new JTextField();
        JCheckBox freeAgentCheck = new JCheckBox("Free Agent");

        Object[] message = {
            "Name:", nameField,
            "Surname:", surnameField,
            "Age:", ageField,
            "Position:", positionCombo,
            "Category:", categoryCombo,
            "Rating:", ratingField,
            "Experience (years):", experienceField,
            freeAgentCheck
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
                    freeAgentCheck.isSelected()
                );
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
            String[] playerNames = players.stream()
                .map(p -> p.getIdPlayer() + ": " + p.getName() + " " + p.getSurname())
                .toArray(String[]::new);

            if (playerNames.length == 0) {
                JOptionPane.showMessageDialog(this, "No players available to remove");
                return;
            }

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
        String[] options = {"Add", "Remove", "Cancel"};
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
        JCheckBox freeCheck = new JCheckBox("Free");

        Object[] message = {
            "Name:", nameField,
            "Surname:", surnameField,
            "Salary:", salaryField,
            "Experience (years):", experienceField,
            freeCheck
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
                    freeCheck.isSelected()
                );
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
            String[] coachNames = coaches.stream()
                .map(c -> c.getIdCoach() + ": " + c.getName() + " " + c.getSurname())
                .toArray(String[]::new);

            if (coachNames.length == 0) {
                JOptionPane.showMessageDialog(this, "No coaches available to remove");
                return;
            }

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

    private void manageObservers() {
        String[] options = {"Add", "Remove", "Cancel"};
        int choice = JOptionPane.showOptionDialog(this, 
                "Observer Management - Choose action:", 
                "Observer Management", 
                JOptionPane.DEFAULT_OPTION, 
                JOptionPane.QUESTION_MESSAGE, 
                null, 
                options, 
                options[0]);

        if (choice == 0) { // Add
            addObserver();
        } else if (choice == 1) { // Remove
            removeObserver();
        }
    }

    private void addObserver() {
        JTextField nameField = new JTextField();
        JTextField surnameField = new JTextField();
        JTextField salaryField = new JTextField();
        JTextField experienceField = new JTextField();
        JCheckBox freeCheck = new JCheckBox("Free");

        Object[] message = {
            "Name:", nameField,
            "Surname:", surnameField,
            "Salary:", salaryField,
            "Experience (years):", experienceField,
            freeCheck
        };

        int option = JOptionPane.showConfirmDialog(this, 
                message, 
                "Add New Observer", 
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                model.addObserver(
                    nameField.getText(),
                    surnameField.getText(),
                    Double.parseDouble(salaryField.getText()),
                    Integer.parseInt(experienceField.getText()),
                    freeCheck.isSelected()
                );
                JOptionPane.showMessageDialog(this, "Observer added successfully!");
            } catch (SQLException | NumberFormatException ex) {
                JOptionPane.showMessageDialog(this, 
                        "Error: " + ex.getMessage(), 
                        "Error", 
                        JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void removeObserver() {
        try {
            List<Observer> observers = model.getAllObservers();
            String[] observerNames = observers.stream()
                .map(o -> o.getIdObserver() + ": " + o.getName() + " " + o.getSurname())
                .toArray(String[]::new);

            if (observerNames.length == 0) {
                JOptionPane.showMessageDialog(this, "No observers available to remove");
                return;
            }

            String selected = (String) JOptionPane.showInputDialog(this,
                    "Select observer to remove:",
                    "Remove Observer",
                    JOptionPane.QUESTION_MESSAGE,
                    null,
                    observerNames,
                    observerNames[0]);

            if (selected != null) {
                int observerId = Integer.parseInt(selected.split(":")[0]);
                
                int confirm = JOptionPane.showConfirmDialog(this,
                        "Are you sure you want to remove this observer?",
                        "Confirm Removal",
                        JOptionPane.YES_NO_OPTION);
                
                if (confirm == JOptionPane.YES_OPTION) {
                    model.removeObserver(observerId);
                    JOptionPane.showMessageDialog(this, "Observer removed successfully!");
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
        String[] options = {"Add", "Remove", "Cancel"};
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
        JTextField team1Field = new JTextField();
        JTextField team2Field = new JTextField();
        JTextField resultField = new JTextField();
        JTextField stadiumField = new JTextField();
        JTextField dateField = new JTextField();

        Object[] message = {
            "Team 1 ID:", team1Field,
            "Team 2 ID:", team2Field,
            "Result:", resultField,
            "Stadium ID:", stadiumField,
            "Date (YYYY-MM-DD):", dateField
        };

        int option = JOptionPane.showConfirmDialog(this, 
                message, 
                "Add New Game", 
                JOptionPane.OK_CANCEL_OPTION);

        if (option == JOptionPane.OK_OPTION) {
            try {
                model.addGame(
                    Integer.parseInt(team1Field.getText()),
                    Integer.parseInt(team2Field.getText()),
                    resultField.getText(),
                    Integer.parseInt(stadiumField.getText()),
                    java.sql.Date.valueOf(dateField.getText()).toLocalDate()
                );
                JOptionPane.showMessageDialog(this, "Game added successfully!");
            } catch (SQLException | IllegalArgumentException ex) {
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
            String[] gameNames = games.stream()
                .map(g -> g.getIdGame() + ": Team " + g.getIdTeam1() + " vs Team " + g.getIdTeam2() + " on " + g.getDate())
                .toArray(String[]::new);

            if (gameNames.length == 0) {
                JOptionPane.showMessageDialog(this, "No games available to remove");
                return;
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
            JOptionPane.showMessageDialog(this, 
                    "Error: " + ex.getMessage(), 
                    "Error", 
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}