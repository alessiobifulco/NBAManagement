package view;

import core.Model;
import data.Contract;
import data.Player;
import data.Team;
import data.Trade;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;

public class TradePage extends JFrame {
    private final Model model;
    private final JFrame parentFrame;
    private final int idTeam;
    private DefaultListModel<Player> playerListModel;

    public TradePage(JFrame parentFrame, Model model, int idTeam) {
        this.model = model;
        this.parentFrame = parentFrame;
        this.idTeam = idTeam;
        initializeUI();
    }

    private void initializeUI() {
        getContentPane().removeAll();
        setTitle("Trade Management");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout()){
            private final Image backgroundImage = new ImageIcon("NBA_Management/IMAGES/lakersBackground.jpg").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };

        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));
        

        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 20));
        buttonPanel.setOpaque(false);
        

        JButton browseButton = new JButton("Browse Players");
        browseButton.addActionListener(e -> showBrowsePage());

        JButton proposeButton = new JButton("Propose Trade");
        proposeButton.addActionListener(e -> showProposeTradeDialog());

        JButton manageButton = new JButton("Manage Trades");
        manageButton.addActionListener(e -> showManageTradesDialog());

        JButton backButton = new JButton("Back to Team");
        backButton.addActionListener(e -> {
            new TeamPage(parentFrame, model, idTeam);
            dispose();
        });

        buttonPanel.add(browseButton);
        buttonPanel.add(proposeButton);
        buttonPanel.add(manageButton);
        buttonPanel.add(backButton);

        mainPanel.add(buttonPanel, BorderLayout.CENTER);
        add(mainPanel);

        revalidate();
        repaint();
        setVisible(true);
    }

    private void showBrowsePage() {
        getContentPane().removeAll();
        setLayout(new BorderLayout(10, 10));

        JPanel mainPanel = new JPanel(new BorderLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(15, 15, 15, 15));

        JPanel controlPanel = new JPanel(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(5, 5, 5, 5);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel teamLabel = new JLabel("Select Team:");
        JComboBox<Team> teamComboBox = new JComboBox<>();
        List<Team> teams = model.getTeamsExcludingLogged(idTeam);
        teams.forEach(teamComboBox::addItem);

        playerListModel = new DefaultListModel<>();
        JList<Player> playerList = new JList<>(playerListModel);
        playerList.setCellRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                    boolean isSelected, boolean cellHasFocus) {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                if (value instanceof Player) {
                    Player p = (Player) value;
                    setText(p.getName() + " " + p.getSurname() + " - " + p.getPosition() +
                            " | Rating: " + p.getRank() + " | Age: " + p.getAge() +
                            " | Exp: " + p.getExperience() + " yrs");
                }
                return this;
            }
        });
        JScrollPane playerScrollPane = new JScrollPane(playerList);

        JButton sortRatingButton = new JButton("Sort by Rating");
        JButton sortAgeButton = new JButton("Sort by Age");
        JButton sortPositionButton = new JButton("Sort by Position");
        JButton expiringButton = new JButton("Show Expiring Contracts");
        JButton backButton = new JButton("Back");

        teamComboBox.addActionListener(e -> {
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();
            if (selectedTeam != null) {
                try {
                    updatePlayerList(model.getActivePlayersByTeam(selectedTeam.getIdTeam()));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading players: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sortRatingButton.addActionListener(e -> {
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();
            if (selectedTeam != null) {
                try {
                    updatePlayerList(model.getPlayersSortedByRating(selectedTeam.getIdTeam()));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading players: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sortAgeButton.addActionListener(e -> {
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();
            if (selectedTeam != null) {
                try {
                    updatePlayerList(model.getPlayersSortedByAge(selectedTeam.getIdTeam()));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading players: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        sortPositionButton.addActionListener(e -> {
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();
            if (selectedTeam != null) {
                try {
                    updatePlayerList(model.getPlayersSortedByPosition(selectedTeam.getIdTeam()));
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading players: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        expiringButton.addActionListener(e -> {
            Team selectedTeam = (Team) teamComboBox.getSelectedItem();
            if (selectedTeam != null) {
                try {
                    List<Player> expiringPlayers = model.getExpiringContracts(selectedTeam.getIdTeam());
                    if (expiringPlayers.isEmpty()) {
                        JOptionPane.showMessageDialog(this, "No expiring contracts found",
                                "Info", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        updatePlayerList(expiringPlayers);
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(this, "Error loading expiring contracts: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        backButton.addActionListener(e -> initializeUI());

        gbc.gridx = 0;
        gbc.gridy = 0;
        controlPanel.add(teamLabel, gbc);

        gbc.gridx = 1;
        controlPanel.add(teamComboBox, gbc);

        gbc.gridx = 0;
        gbc.gridy = 1;
        controlPanel.add(sortRatingButton, gbc);

        gbc.gridx = 1;
        controlPanel.add(sortAgeButton, gbc);

        gbc.gridx = 2;
        controlPanel.add(sortPositionButton, gbc);

        gbc.gridx = 3;
        controlPanel.add(expiringButton, gbc);

        mainPanel.add(controlPanel, BorderLayout.NORTH);
        mainPanel.add(playerScrollPane, BorderLayout.CENTER);
        mainPanel.add(backButton, BorderLayout.SOUTH);

        add(mainPanel);
        revalidate();
        repaint();
    }

    private void showProposeTradeDialog() {
        JDialog dialog = new JDialog(this, "Propose Trade", true);
        dialog.setSize(800, 600);
        dialog.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.fill = GridBagConstraints.HORIZONTAL;

        JLabel titleLabel = new JLabel("Propose New Trade");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.gridwidth = 2;
        dialog.add(titleLabel, gbc);

        gbc.gridy++;
        gbc.gridwidth = 1;
        dialog.add(new JLabel("Your Player:"), gbc);

        JComboBox<Player> yourPlayerCombo = new JComboBox<>();
        try {
            List<Player> yourPlayers = model.getActivePlayersByTeam(idTeam);
            yourPlayers.forEach(yourPlayerCombo::addItem);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dialog, "Error loading your players: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
        yourPlayerCombo.setRenderer(new PlayerListRenderer());
        gbc.gridx = 1;
        dialog.add(yourPlayerCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        dialog.add(new JLabel("Opponent Team:"), gbc);

        JComboBox<Team> opponentTeamCombo = new JComboBox<>();
        List<Team> opponentTeams = model.getTeamsExcludingLogged(idTeam);
        opponentTeams.forEach(opponentTeamCombo::addItem);
        opponentTeamCombo.setRenderer(new TeamListRenderer());
        gbc.gridx = 1;
        dialog.add(opponentTeamCombo, gbc);

        gbc.gridx = 0;
        gbc.gridy++;
        dialog.add(new JLabel("Opponent Player:"), gbc);

        JComboBox<Player> opponentPlayerCombo = new JComboBox<>();
        opponentPlayerCombo.setRenderer(new PlayerListRenderer());
        gbc.gridx = 1;
        dialog.add(opponentPlayerCombo, gbc);

        JButton proposeButton = new JButton("Propose Trade");
        proposeButton.setEnabled(false);
        JButton cancelButton = new JButton("Cancel");

        gbc.gridx = 0;
        gbc.gridy++;
        gbc.gridwidth = 2;
        gbc.fill = GridBagConstraints.CENTER;
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        buttonPanel.add(proposeButton);
        buttonPanel.add(cancelButton);
        dialog.add(buttonPanel, gbc);

        // ActionListener per caricare i giocatori della squadra avversaria selezionata
        opponentTeamCombo.addActionListener(e -> {
            Team selectedTeam = (Team) opponentTeamCombo.getSelectedItem();
            if (selectedTeam != null) {
                opponentPlayerCombo.removeAllItems();
                try {
                    List<Player> players = model.getActivePlayersByTeam(selectedTeam.getIdTeam());
                    players.forEach(opponentPlayerCombo::addItem);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error loading players: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Abilita il pulsante solo quando entrambi i giocatori sono selezionati
        ActionListener selectionListener = e -> {
            boolean bothSelected = yourPlayerCombo.getSelectedItem() != null &&
                    opponentPlayerCombo.getSelectedItem() != null;
            proposeButton.setEnabled(bothSelected);
        };

        yourPlayerCombo.addActionListener(selectionListener);
        opponentPlayerCombo.addActionListener(selectionListener);

        // ActionListener per il pulsante di proposta
        proposeButton.addActionListener(e -> {
            Player yourPlayer = (Player) yourPlayerCombo.getSelectedItem();
            Player opponentPlayer = (Player) opponentPlayerCombo.getSelectedItem();
            Team opponentTeam = (Team) opponentTeamCombo.getSelectedItem();

            if (yourPlayer != null && opponentPlayer != null && opponentTeam != null) {
                try {
                    // Debug: stampa i giocatori selezionati
                    System.out.println("[DEBUG] Selected players:");
                    System.out.println(" - Your player: ID=" + yourPlayer.getIdPlayer() +
                            ", Name=" + yourPlayer.getName() + " " + yourPlayer.getSurname());
                    System.out.println(" - Opponent player: ID=" + opponentPlayer.getIdPlayer() +
                            ", Name=" + opponentPlayer.getName() + " " + opponentPlayer.getSurname());

                    Contract yourContract = model.getActiveContract(yourPlayer.getIdPlayer());
                    Contract opponentContract = model.getActiveContract(opponentPlayer.getIdPlayer());

                    // Debug: stampa i contratti
                    System.out.println("[DEBUG] Contracts retrieved:");
                    System.out.println(" - Your contract: " + (yourContract != null
                            ? "ID=" + yourContract.getIdContract() + ", PlayerID=" + yourContract.getIdPlayer()
                            : "null"));
                    System.out.println(" - Opponent contract: " + (opponentContract != null
                            ? "ID=" + opponentContract.getIdContract() + ", PlayerID=" + opponentContract.getIdPlayer()
                            : "null"));

                    if (yourContract == null || opponentContract == null) {
                        JOptionPane.showMessageDialog(dialog, "One or both players don't have active contracts",
                                "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    // Controllo esplicito prima di procedere
                    if (yourContract.getIdContract() == opponentContract.getIdContract()) {
                        String errorMsg = "ERROR: Same contract ID detected!\n" +
                                "Your contract ID: " + yourContract.getIdContract() + "\n" +
                                "Opponent contract ID: " + opponentContract.getIdContract() + "\n\n" +
                                "Your player ID: " + yourPlayer.getIdPlayer() + "\n" +
                                "Opponent player ID: " + opponentPlayer.getIdPlayer();

                        JOptionPane.showMessageDialog(dialog, errorMsg, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    int confirm = JOptionPane.showConfirmDialog(dialog,
                            "Confirm Trade Proposal:\n\n" +
                                    "You give: " + yourPlayer.getName() + " " + yourPlayer.getSurname()
                                    + " (Contract ID: " + yourContract.getIdContract() + ")\n" +
                                    "You receive: " + opponentPlayer.getName() + " " + opponentPlayer.getSurname()
                                    + " (Contract ID: " + opponentContract.getIdContract() + ")",
                            "Confirm Trade", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        model.proposeTrade(yourContract.getIdContract(), opponentContract.getIdContract());
                        JOptionPane.showMessageDialog(dialog, "Trade proposed successfully!");
                        dialog.dispose();
                    }
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(dialog, "Error proposing trade: " + ex.getMessage(),
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // ActionListener per il pulsante Cancel
        cancelButton.addActionListener(e -> dialog.dispose());

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showManageTradesDialog() {
        JDialog dialog = new JDialog(this, "Manage Trades", true);
        dialog.setSize(900, 600);
        dialog.setLayout(new BorderLayout(10, 10));

        try {
            JLabel titleLabel = new JLabel("Trade Offers");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 16));
            dialog.add(titleLabel, BorderLayout.NORTH);

            DefaultTableModel tableModel = new DefaultTableModel() {
                @Override
                public boolean isCellEditable(int row, int column) {
                    return false;
                }
            };
            tableModel.addColumn("Trade Proposal");
            tableModel.addColumn("Status");
            tableModel.addColumn("Date");

            List<Trade> trades = model.getTradesInProgress(idTeam);
            for (Trade trade : trades) {
                Contract contract1 = model.getContractById(trade.getIdContract1());
                Contract contract2 = model.getContractById(trade.getIdContract2());

                Player player1 = model.getPlayerById(contract1.getIdPlayer());
                Player player2 = model.getPlayerById(contract2.getIdPlayer());

                Team team1 = model.getTeamById(contract1.getIdTeam());
                Team team2 = model.getTeamById(contract2.getIdTeam());

                String proposal = player1.getName() + " " + player1.getSurname() + " (" + team1.getName() +
                        ") <-> " + player2.getName() + " " + player2.getSurname() + " (" + team2.getName() + ")";

                tableModel.addRow(new Object[] { proposal, trade.getState().toString(), trade.getDate() });
            }

            JTable tradeTable = new JTable(tableModel);
            tradeTable.setRowHeight(30);
            tradeTable.getColumnModel().getColumn(0).setPreferredWidth(500);
            tradeTable.setDefaultRenderer(Object.class, new TradeTableRenderer());
            tradeTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

            JScrollPane scrollPane = new JScrollPane(tradeTable);
            dialog.add(scrollPane, BorderLayout.CENTER);

            JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));

            JButton detailsButton = new JButton("View Details");
            JButton acceptButton = new JButton("Accept");
            JButton rejectButton = new JButton("Reject");
            JButton closeButton = new JButton("Close");

            ActionListener selectionListener = e -> {
                boolean hasSelection = tradeTable.getSelectedRow() >= 0;
                detailsButton.setEnabled(hasSelection);
                acceptButton.setEnabled(hasSelection);
                rejectButton.setEnabled(hasSelection);
            };

            tradeTable.getSelectionModel().addListSelectionListener(e -> selectionListener.actionPerformed(null));

            buttonPanel.add(detailsButton);
            buttonPanel.add(acceptButton);
            buttonPanel.add(rejectButton);
            buttonPanel.add(closeButton);

            detailsButton.addActionListener(e -> {
                int selectedRow = tradeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    showTradeDetails(trades.get(selectedRow));
                }
            });

            acceptButton.addActionListener(e -> {
                int selectedRow = tradeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Trade trade = trades.get(selectedRow);
                    int confirm = JOptionPane.showConfirmDialog(dialog,
                            "Are you sure you want to accept this trade?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            model.acceptTrade(trade.getIdTrade());
                            JOptionPane.showMessageDialog(dialog, "Trade accepted successfully!");
                            dialog.dispose();
                            showManageTradesDialog();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(dialog, "Error accepting trade: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            rejectButton.addActionListener(e -> {
                int selectedRow = tradeTable.getSelectedRow();
                if (selectedRow >= 0) {
                    Trade trade = trades.get(selectedRow);
                    int confirm = JOptionPane.showConfirmDialog(dialog,
                            "Are you sure you want to reject this trade?", "Confirm", JOptionPane.YES_NO_OPTION);

                    if (confirm == JOptionPane.YES_OPTION) {
                        try {
                            model.rejectTrade(trade.getIdTrade());
                            JOptionPane.showMessageDialog(dialog, "Trade rejected!");
                            dialog.dispose();
                            showManageTradesDialog();
                        } catch (SQLException ex) {
                            JOptionPane.showMessageDialog(dialog, "Error rejecting trade: " + ex.getMessage(),
                                    "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            });

            closeButton.addActionListener(e -> dialog.dispose());

            dialog.add(buttonPanel, BorderLayout.SOUTH);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(dialog, "Error loading trades: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            dialog.dispose();
        }

        dialog.setLocationRelativeTo(this);
        dialog.setVisible(true);
    }

    private void showTradeDetails(Trade trade) {
        JDialog detailsDialog = new JDialog(this, "Trade Details", true);
        detailsDialog.setSize(800, 500);
        detailsDialog.setLayout(new GridLayout(1, 2, 20, 10));
        detailsDialog.getContentPane().setBackground(Color.WHITE);

        try {
            Contract contract1 = model.getContractById(trade.getIdContract1());
            Contract contract2 = model.getContractById(trade.getIdContract2());

            Player player1 = model.getPlayerById(contract1.getIdPlayer());
            Player player2 = model.getPlayerById(contract2.getIdPlayer());

            Team team1 = model.getTeamById(contract1.getIdTeam());
            Team team2 = model.getTeamById(contract2.getIdTeam());

            // Pannello per il giocatore 1
            JPanel leftPanel = new JPanel(new BorderLayout());
            leftPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            leftPanel.setBackground(new Color(240, 240, 240));

            JLabel leftTitle = new JLabel(team1.getName() + " receives:", SwingConstants.CENTER);
            leftTitle.setFont(new Font("Arial", Font.BOLD, 14));
            leftPanel.add(leftTitle, BorderLayout.NORTH);

            JLabel playerLabel1 = new JLabel(player2.getName() + " " + player2.getSurname(), SwingConstants.CENTER);
            playerLabel1.setFont(new Font("Arial", Font.BOLD, 16));
            leftPanel.add(playerLabel1, BorderLayout.CENTER);

            leftPanel.add(createPlayerDetailsPanel(player2, contract2), BorderLayout.SOUTH);

            // Pannello per il giocatore 2
            JPanel rightPanel = new JPanel(new BorderLayout());
            rightPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
            rightPanel.setBackground(new Color(240, 240, 240));

            JLabel rightTitle = new JLabel(team2.getName() + " receives:", SwingConstants.CENTER);
            rightTitle.setFont(new Font("Arial", Font.BOLD, 14));
            rightPanel.add(rightTitle, BorderLayout.NORTH);

            JLabel playerLabel2 = new JLabel(player1.getName() + " " + player1.getSurname(), SwingConstants.CENTER);
            playerLabel2.setFont(new Font("Arial", Font.BOLD, 16));
            rightPanel.add(playerLabel2, BorderLayout.CENTER);

            rightPanel.add(createPlayerDetailsPanel(player1, contract1), BorderLayout.SOUTH);

            detailsDialog.add(leftPanel);
            detailsDialog.add(rightPanel);

            JPanel bottomPanel = new JPanel();
            bottomPanel.add(new JLabel("Trade Date: " + trade.getDate()));

            JButton closeButton = new JButton("Close");
            closeButton.addActionListener(e -> detailsDialog.dispose());
            bottomPanel.add(closeButton);

            detailsDialog.add(bottomPanel, BorderLayout.SOUTH);

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(detailsDialog, "Error loading trade details: " + e.getMessage(),
                    "Error", JOptionPane.ERROR_MESSAGE);
            detailsDialog.dispose();
        }

        detailsDialog.setLocationRelativeTo(this);
        detailsDialog.setVisible(true);
    }

    private JPanel createPlayerDetailsPanel(Player player, Contract contract) {
        JPanel panel = new JPanel(new GridLayout(0, 1, 5, 5));
        panel.setBorder(BorderFactory.createTitledBorder("Player Details"));
        panel.setBackground(Color.WHITE);

        panel.add(new JLabel("Position: " + player.getPosition()));
        panel.add(new JLabel("Rating: " + player.getRank()));
        panel.add(new JLabel("Age: " + player.getAge()));
        panel.add(new JLabel("Experience: " + player.getExperience() + " yrs"));
        panel.add(new JLabel("Category: " + player.getCategory()));
        panel.add(new JLabel("Contract Salary: $" + contract.getSalary()));
        panel.add(new JLabel("Contract Duration: " + contract.getYears() + " yrs"));
        panel.add(new JLabel("Contract Start: " + contract.getDateStart()));

        return panel;
    }

    private void updatePlayerList(List<Player> players) {
        playerListModel.clear();
        players.forEach(playerListModel::addElement);
    }

    private static class PlayerListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Player) {
                Player p = (Player) value;
                setText(p.getName() + " " + p.getSurname() + " - " + p.getPosition() +
                        " | Rating: " + p.getRank());
            }
            return this;
        }
    }

    private static class TeamListRenderer extends DefaultListCellRenderer {
        @Override
        public Component getListCellRendererComponent(JList<?> list, Object value, int index,
                boolean isSelected, boolean cellHasFocus) {
            super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
            if (value instanceof Team) {
                Team t = (Team) value;
                setText(t.getName() + " (" + t.getCity() + ")");
            }
            return this;
        }
    }

    private static class TradeTableRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(JTable table, Object value,
                boolean isSelected, boolean hasFocus, int row, int column) {
            super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);

            if (column == 1) {
                String status = (String) value;
                if ("Accettato".equals(status)) {
                    setBackground(new Color(144, 238, 144));
                    setForeground(Color.BLACK);
                } else if ("In corso".equals(status)) {
                    setBackground(new Color(255, 255, 153));
                    setForeground(Color.BLACK);
                } else if ("Rifiutato".equals(status)) {
                    setBackground(new Color(255, 182, 193));
                    setForeground(Color.BLACK);
                }
            } else {
                setBackground(isSelected ? table.getSelectionBackground() : table.getBackground());
                setForeground(isSelected ? table.getSelectionForeground() : table.getForeground());
            }

            return this;
        }
    }

    private JPanel createBackgroundPanel() {
        return new JPanel(new GridBagLayout()) {
            private final Image backgroundImage = new ImageIcon("METTI PERCORSO IMMAGINE QUI").getImage();

            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                g.drawImage(backgroundImage, 0, 0, getWidth(), getHeight(), this);
            }
        };
    }

}