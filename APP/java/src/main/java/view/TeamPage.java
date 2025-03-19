package view;

import core.Model;
import javax.swing.*;
import java.awt.*;
@SuppressWarnings("unused")

public class TeamPage extends JFrame {


    private final Model model;
    private final JFrame frame;
    private final int idTeam;


    public TeamPage(JFrame jFrame, Model model, int idTeam) {
        this.model = model;
        this.frame = jFrame;
        this.idTeam = idTeam;
        setTitle("Team");
        setExtendedState(JFrame.MAXIMIZED_BOTH); // Fullscreen
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());

        // Bottone per History (Storico delle operazioni)
        JButton historyButton = new JButton("History");
        historyButton.addActionListener(e -> {
            new HistoryPage(jFrame, model, idTeam);  // Passa alla pagina History
            dispose();
        });

        // Bottone per Staff (Gestione dello staff)
        JButton staffButton = new JButton("Staff");
        staffButton.addActionListener(e -> {
            new StaffPage(jFrame, model, idTeam);  // Passa alla pagina Staff
            dispose();
        });

        // Bottone per Trade (Proposte di scambio)
        JButton tradeButton = new JButton("Trade");
        tradeButton.addActionListener(e -> {
            new TradePage(jFrame, model, idTeam);  // Passa alla pagina Trade
            dispose();
        });

        // Bottone per tornare alla Login Page
        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            new LoginPage(jFrame, model);  // Passa alla pagina di login
            dispose();
        });

        // Aggiungi i bottoni alla finestra
        add(historyButton);
        add(staffButton);
        add(tradeButton);
        add(backButton);

        setVisible(true);
    }
}
