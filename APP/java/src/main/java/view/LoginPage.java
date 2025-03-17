package view;

import core.Model;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;

public class LoginPage {

    private final JFrame frame;
    private final Model model;

    public LoginPage(JFrame mainFrame, Model model) {
        this.frame = mainFrame;
        this.model = model;
        initializeComponents();
    }

    private void initializeComponents() {
        // Creazione del pannello principale
        JPanel loginPanel = new JPanel();
        loginPanel.setLayout(new GridBagLayout());

        // Crea i bottoni per Login e Administrator
        JButton loginButton = new JButton("Login");
        JButton adminButton = new JButton("Administrator");

        // Aggiungi l'ActionListener per il bottone Login
        loginButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Finestra di dialogo per inserire credenziali
                JPanel dialogPanel = new JPanel(new GridBagLayout());
                JLabel emailLabel = new JLabel("Email:");
                JTextField emailField = new JTextField(20);
                JLabel passwordLabel = new JLabel("Password:");
                JPasswordField passwordField = new JPasswordField(20);
                JButton submitButton = new JButton("Login");

                // Aggiungi l'ActionListener per il submitButton
                submitButton.addActionListener(new ActionListener() {
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        String email = emailField.getText();
                        String password = new String(passwordField.getPassword());

                        try {
                            // Verifica le credenziali con il Model
                            if (model.registerGMAccess(email, password)) {
                                // Se le credenziali sono valide, passa alla TeamPage
                                
                                new TeamPage(frame, model, model.getTeamIdByGM(email, password));
                                frame.dispose();  // Chiudi la finestra di login
                            } else {
                                // Se le credenziali sono errate, mostra un messaggio di errore
                                JOptionPane.showMessageDialog(frame, "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
                            }
                        } catch (SQLException ex) {
                            ex.printStackTrace();
                            JOptionPane.showMessageDialog(frame, "Errore nel tentativo di login", "Errore", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                });

                dialogPanel.add(emailLabel);
                dialogPanel.add(emailField);
                dialogPanel.add(passwordLabel);
                dialogPanel.add(passwordField);
                dialogPanel.add(submitButton);

                // Mostra la finestra di dialogo
                int option = JOptionPane.showConfirmDialog(frame, dialogPanel, "Inserisci credenziali", JOptionPane.OK_CANCEL_OPTION);
                if (option == JOptionPane.CANCEL_OPTION) {
                    // Gestisci il caso in cui venga annullato
                    JOptionPane.showMessageDialog(frame, "Login annullato");
                }
            }
        });

        // Aggiungi l'ActionListener per il bottone Administrator
        adminButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // Passa direttamente alla AdministratorPage
                new AdministratorPage(frame, model);
                frame.dispose();  // Chiudi la finestra di login
            }
        });

        // Aggiungi i bottoni al pannello
        loginPanel.add(loginButton);
        loginPanel.add(adminButton);

        // Modifica la finestra e aggiungi il pannello di login
        frame.getContentPane().removeAll();
        frame.add(loginPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();

        // Rendi visibile la finestra
        frame.setVisible(true);
    }
}
