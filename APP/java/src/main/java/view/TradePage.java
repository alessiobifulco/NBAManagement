package view;

import core.Model;
import data.Player;
import data.Team;

import javax.swing.*;
import java.awt.*;
import java.util.List;

public class TradePage extends JFrame {

    private Model model;
    private JComboBox<Team> teamComboBox;
    private JList<Player> playerList;
    private DefaultListModel<Player> playerListModel;
    private JButton toBrowseButton, intoTradeButton;
    private int idTeam;

    public TradePage(JFrame jFrame, Model model, int idTeam) {
        this.model = model;
        this.idTeam = idTeam;
        setTitle("Marketplace");
        setExtendedState(JFrame.MAXIMIZED_BOTH);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Pulsanti principali
        toBrowseButton = new JButton("To Browse");
        intoTradeButton = new JButton("Into Trade");

        toBrowseButton.addActionListener(e -> loadBrowsePage());
        intoTradeButton.addActionListener(e -> {
            // Azioni per iniziare il trade, da implementare
        });

        add(toBrowseButton);
        add(intoTradeButton);

        setVisible(true);
    }

    private void loadBrowsePage() {
        remove(toBrowseButton);
        remove(intoTradeButton);

        teamComboBox = new JComboBox<>();
        List<Team> teams = model.getTeamsExcludingLogged(idTeam);
        teamComboBox.setModel(new DefaultComboBoxModel<>(teams.toArray(new Team[0])));

        // Renderer per mostrare il nome della squadra
        teamComboBox.setRenderer(new DefaultListCellRenderer() {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Team) {
                    value = ((Team) value).getName();
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
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                if (value instanceof Player) {
                    Player p = (Player) value;
                    value = p.getName() + " " + p.getSurname() + " - " + p.getPosition() + " - Rating: " + p.getRank();
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
}
