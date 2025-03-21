package view;

import core.Model;
import data.Contract;
import data.Player;
import data.Team;
import data.Trade;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

@SuppressWarnings("unused")
public class TradePage extends JFrame {

    private Model model;
    private JFrame jFrame;
    private JComboBox<Team> teamComboBox;
    private JList<Player> playerList;
    private DefaultListModel<Player> playerListModel;
    private JButton toBrowseButton, proposeTradeButton, updateTradeButton, goBackButton;
    private int idTeam;
    private Player playerToTrade, playerForTrade;

    public TradePage(JFrame jFrame, Model model, int idTeam) {
        this.model = model;
        this.idTeam = idTeam;
        this.jFrame = jFrame;
        setTitle("Marketplace");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Pulsanti principali
        toBrowseButton = new JButton("To Browse");
        proposeTradeButton = new JButton("Propose Trade");
        updateTradeButton = new JButton("Update Trade");
        goBackButton = new JButton("Go Back");

        toBrowseButton.addActionListener(e -> loadBrowsePage());
        proposeTradeButton.addActionListener(e -> openTradeProposalWindow());
        updateTradeButton.addActionListener(e -> openUpdateTradeWindow());

        goBackButton.addActionListener(e -> {
            new TeamPage(jFrame, model, idTeam);
            dispose();
        });

        add(toBrowseButton);
        add(proposeTradeButton);
        add(updateTradeButton);
        add(goBackButton);

        setVisible(true);
    }

    private void loadBrowsePage() {
        remove(toBrowseButton);
        remove(proposeTradeButton);
        remove(updateTradeButton);

        JButton goback = new JButton("Go Back");
        goback.addActionListener(e -> {
            new TradePage(jFrame, model, idTeam);
            dispose();
        });

        teamComboBox = new JComboBox<>();
        List<Team> teams = model.getTeamsExcludingLogged(idTeam);
        teamComboBox.setModel(new DefaultComboBoxModel<>(teams.toArray(new Team[0])));

        // Renderer per mostrare il nome della squadra
        teamComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                if (value instanceof Team) {
                    value = ((Team) value).getName() + " (" + ((Team) value).getCity() + ")";
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        teamComboBox.addActionListener(e -> {
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();
            if (selectedTeam != null) {
                List<Player> players = model.getPlayersByTeam(selectedTeam.getIdTeam());
                updatePlayerList(players);
            }
        });

        playerListModel = new DefaultListModel<>();
        playerList = new JList<>(playerListModel);

        // Renderer per mostrare il nome e dettagli del giocatore
        playerList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected,
                    boolean cellHasFocus) {
                if (value instanceof Player) {
                    Player p = (Player) value;
                    value = p.getName() + " " + p.getSurname() + " - " + p.getPosition() + " - Rating: " + p.getRank()
                            + " - Age: " + p.getAge() + " - Experience: " + p.getExperience() + " - Category: "
                            + p.getCategory();
                }
                return super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            }
        });

        JScrollPane playerScrollPane = new JScrollPane(playerList);

        JButton sortByRatingButton = new JButton("Sort by Rating");
        sortByRatingButton.addActionListener(e -> sortPlayersByRating());

        JButton sortByAgeButton = new JButton("Sort by Age");
        sortByAgeButton.addActionListener(e -> sortPlayersByAge());

        JButton sortByPositionButton = new JButton("Sort by Position");
        sortByPositionButton.addActionListener(e -> sortPlayersByPosition());

        add(new JLabel("Select Team"));
        add(teamComboBox);
        add(sortByRatingButton);
        add(sortByAgeButton);
        add(sortByPositionButton);
        add(playerScrollPane);
        add(goback);

        revalidate();
        repaint();
    }

    private void updatePlayerList(List<Player> sortedPlayers) {
        playerListModel.clear();
        for (Player player : sortedPlayers) {
            playerListModel.addElement(player);
        }
    }

    private void sortPlayersByRating() {
        List<Player> sortedPlayers = model.getPlayersSortedByRating(idTeam);
        updatePlayerList(sortedPlayers);
    }

    private void sortPlayersByAge() {
        List<Player> sortedPlayers = model.getPlayersSortedByAge(idTeam);
        updatePlayerList(sortedPlayers);
    }

    private void sortPlayersByPosition() {
        List<Player> sortedPlayers = model.getPlayersSortedByPosition(idTeam);
        updatePlayerList(sortedPlayers);
    }

    private void openTradeProposalWindow() {

        JDialog tradeDialog = new JDialog(this, "Propose Trade", true);
        tradeDialog.setSize(new Dimension(800, 600));
        tradeDialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);

        JLabel yourPlayerLabel = new JLabel("Your Player:");
        JComboBox<Player> playerToTradeBox = new JComboBox<>(model.getPlayersByTeam(idTeam).toArray(new Player[0]));

        JLabel selectTeamLabel = new JLabel("Select Team:");
        JComboBox<Team> teamComboBox = new JComboBox<>(model.getTeamsExcludingLogged(idTeam).toArray(new Team[0]));

        JLabel opponentPlayerLabel = new JLabel("Opponent Player:");
        JComboBox<Player> playerForTradeBox = new JComboBox<>();
        JButton cancelButton = new JButton("Cancel");
        JButton proposeButton = new JButton("Propose");
        proposeButton.setEnabled(false);

        playerToTradeBox.addActionListener(e -> {
            Player selectedPlayer = (Player) playerToTradeBox.getSelectedItem();
            if (selectedPlayer != null) {
                int confirmation = JOptionPane.showConfirmDialog(tradeDialog, "Confirm selection:\n" +
                        selectedPlayer.getName() + " " + selectedPlayer.getSurname(),
                        "Confirm Player Selection", JOptionPane.OK_CANCEL_OPTION);
                if (confirmation != JOptionPane.OK_OPTION) {
                    playerToTradeBox.setSelectedItem(null);
                }
            }
        });

        teamComboBox.addActionListener(e -> {
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();
            if (selectedTeam != null) {
                playerForTradeBox.setModel(new DefaultComboBoxModel<>(
                        model.getPlayersByTeam(selectedTeam.getIdTeam()).toArray(new Player[0])));
            }
        });

        playerToTradeBox.addActionListener(e -> checkSelection(proposeButton, playerToTradeBox, playerForTradeBox));
        playerForTradeBox.addActionListener(e -> {
            Player selectedPlayer = (Player) playerForTradeBox.getSelectedItem();
            if (selectedPlayer != null) {
                int confirmation = JOptionPane.showConfirmDialog(tradeDialog, "Confirm selection:\n" +
                        selectedPlayer.getName() + " " + selectedPlayer.getSurname(),
                        "Confirm Player Selection", JOptionPane.OK_CANCEL_OPTION);
                if (confirmation == JOptionPane.OK_OPTION) {
                    checkSelection(proposeButton, playerToTradeBox, playerForTradeBox);
                } else {
                    playerForTradeBox.setSelectedItem(null);
                }
            }
        });

        proposeButton.addActionListener(e -> {
            playerToTrade = (Player) playerToTradeBox.getSelectedItem();
            playerForTrade = (Player) playerForTradeBox.getSelectedItem();
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();

            if (playerToTrade != null && playerForTrade != null && selectedTeam != null) {
                int confirmation = JOptionPane.showConfirmDialog(tradeDialog, "Confirm Trade:\n" +
                        "Your Player: " + playerToTrade.getName() + " " + playerToTrade.getSurname() + "\n" +
                        "Opponent Player: " + playerForTrade.getName() + " " + playerForTrade.getSurname(),
                        "Confirm Trade", JOptionPane.OK_CANCEL_OPTION);
                if (confirmation == JOptionPane.OK_OPTION) {
                    try {
                        model.proposeTrade(playerToTrade, playerForTrade, selectedTeam);
                        JOptionPane.showMessageDialog(tradeDialog, "Trade proposal submitted!");
                        tradeDialog.dispose();
                    } catch (SQLException ex) {
                        JOptionPane.showMessageDialog(tradeDialog, "Error proposing trade: " + ex.getMessage(), "Error",
                                JOptionPane.ERROR_MESSAGE);
                    }
                }
            }
        });

        cancelButton.addActionListener(e -> {

            tradeDialog.dispose();
        });

        gbc.gridx = 0;
        gbc.gridy = 0;
        tradeDialog.add(selectTeamLabel, gbc);
        gbc.gridx = 1;
        tradeDialog.add(teamComboBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 1;
        tradeDialog.add(yourPlayerLabel, gbc);
        gbc.gridx = 1;
        tradeDialog.add(playerToTradeBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 2;
        tradeDialog.add(opponentPlayerLabel, gbc);
        gbc.gridx = 1;
        tradeDialog.add(playerForTradeBox, gbc);
        gbc.gridx = 0;
        gbc.gridy = 3;
        tradeDialog.add(cancelButton, gbc);
        gbc.gridx = 1;
        tradeDialog.add(proposeButton, gbc);

        tradeDialog.setVisible(true);
    }

    private void checkSelection(JButton proposeButton, JComboBox<Player> playerToTradeBox,
            JComboBox<Player> playerForTradeBox) {
        proposeButton
                .setEnabled(playerToTradeBox.getSelectedItem() != null && playerForTradeBox.getSelectedItem() != null);
    }

    private void openUpdateTradeWindow() {
        JDialog updateDialog = new JDialog(this, "Update Trade", true);
        updateDialog.setSize(new Dimension(600, 400));
        updateDialog.setLayout(new BorderLayout());

        DefaultListModel<Trade> tradeListModel = new DefaultListModel<>();

        try {
            List<Trade> tradesInProgress = model.getTradesInProgress(idTeam);
            tradesInProgress.forEach(tradeListModel::addElement);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error fetching trades: " + e.getMessage(), "Error",
                    JOptionPane.ERROR_MESSAGE);
            e.printStackTrace();
        }

        JList<Trade> tradeList = new JList<>(tradeListModel);
        JScrollPane scrollPane = new JScrollPane(tradeList);

        JButton acceptButton = new JButton("Accept");
        JButton rejectButton = new JButton("Reject");
        JButton cancelButton = new JButton("Cancel");

        acceptButton.addActionListener(e -> {
            Trade selectedTrade = tradeList.getSelectedValue();
            if (selectedTrade != null) {
                try {
                    model.acceptTrade(selectedTrade);
                    JOptionPane.showMessageDialog(updateDialog, "Trade accepted!");
                    updateDialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(updateDialog, "Error accepting trade: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        rejectButton.addActionListener(e -> {
            Trade selectedTrade = tradeList.getSelectedValue();
            if (selectedTrade != null) {
                try {
                    model.rejectTrade(selectedTrade);
                    JOptionPane.showMessageDialog(updateDialog, "Trade rejected!");
                    updateDialog.dispose();
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(updateDialog, "Error rejecting trade: " + ex.getMessage(), "Error",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        cancelButton.addActionListener(e -> updateDialog.dispose());

        JPanel buttonPanel = new JPanel();
        buttonPanel.add(acceptButton);
        buttonPanel.add(rejectButton);
        buttonPanel.add(cancelButton);

        updateDialog.add(scrollPane, BorderLayout.CENTER);
        updateDialog.add(buttonPanel, BorderLayout.SOUTH);

        updateDialog.setVisible(true);
    }
}
