package view;

import core.Model;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ItemEvent;
import java.sql.SQLException;
import java.util.regex.Pattern;

public class LoginPage {
    private final JFrame frame;
    private final Model model;

    public LoginPage(JFrame mainFrame, Model model) {
        this.frame = mainFrame;
        this.model = model;
        frame.setTitle("NBA Management System");
        frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLayout(new BorderLayout());

        initializeComponents();
    }

    private void initializeComponents() {
        // Pannello principale con layout
        JPanel mainPanel = new JPanel(new GridBagLayout());
        mainPanel.setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Pannello per i pulsanti
        JPanel buttonPanel = new JPanel(new GridLayout(2, 1, 10, 20));
        buttonPanel.setBorder(BorderFactory.createEmptyBorder(20, 100, 20, 100));

        // Bottone Login (stile sistema)
        JButton loginButton = new JButton("Login");
        loginButton.addActionListener(e -> showLoginDialog());

        // Bottone Administrator (stile sistema)
        JButton adminButton = new JButton("Administrator");
        adminButton.addActionListener(e -> {
            new AdministratorPage(frame, model);
            frame.dispose();
        });

        buttonPanel.add(loginButton);
        buttonPanel.add(adminButton);

        // Posizionamento componenti
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridwidth = GridBagConstraints.REMAINDER;
        gbc.anchor = GridBagConstraints.CENTER;

        // Titolo
        JLabel titleLabel = new JLabel("NBA Management System");
        titleLabel.setFont(new Font(titleLabel.getFont().getName(), Font.BOLD, 24));
        mainPanel.add(titleLabel, gbc);
        mainPanel.add(Box.createVerticalStrut(50), gbc);
        mainPanel.add(buttonPanel, gbc);

        frame.add(mainPanel, BorderLayout.CENTER);
        frame.revalidate();
        frame.repaint();
        frame.setVisible(true);
    }

    private void showLoginDialog() {
        JDialog loginDialog = new JDialog(frame, "Accesso GM", true);
        loginDialog.setLayout(new BorderLayout());
        loginDialog.setSize(400, 300);
        loginDialog.setLocationRelativeTo(frame);

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 10, 10, 10);
        gbc.anchor = GridBagConstraints.WEST;
        gbc.fill = GridBagConstraints.HORIZONTAL;

        // Email field
        JLabel emailLabel = new JLabel("Email:");
        JTextField emailField = new JTextField(20);
        gbc.gridx = 0;
        gbc.gridy = 0;
        contentPanel.add(emailLabel, gbc);
        gbc.gridy++;
        contentPanel.add(emailField, gbc);

        // Password field con toggle per mostrare/nascondere
        JLabel passwordLabel = new JLabel("Password:");
        JPasswordField passwordField = new JPasswordField(20);
        JCheckBox showPasswordCheck = new JCheckBox("Mostra password");
        showPasswordCheck.addItemListener(e -> {
            if (e.getStateChange() == ItemEvent.SELECTED) {
                passwordField.setEchoChar((char)0);
            } else {
                passwordField.setEchoChar('•');
            }
        });

        gbc.gridy++;
        contentPanel.add(passwordLabel, gbc);
        gbc.gridy++;
        contentPanel.add(passwordField, gbc);
        gbc.gridy++;
        contentPanel.add(showPasswordCheck, gbc);

        // Pulsanti (stile sistema)
        JPanel buttonPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        JButton loginBtn = new JButton("Login");
        JButton cancelBtn = new JButton("Annulla");

        loginBtn.addActionListener(e -> {
            String email = emailField.getText().trim();
            String password = new String(passwordField.getPassword()).trim();

            // Validazioni
            if (email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(loginDialog, 
                    "Inserisci sia email che password", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!isValidEmail(email)) {
                JOptionPane.showMessageDialog(loginDialog, 
                    "Formato email non valido", "Errore", JOptionPane.ERROR_MESSAGE);
                return;
            }

            try {
                if (model.registerGMAccess(email, password)) {
                    new TeamPage(frame, model, model.getTeamIdByGM(email, password));
                    loginDialog.dispose();
                    frame.dispose();
                } else {
                    JOptionPane.showMessageDialog(loginDialog, 
                        "Credenziali non valide", "Errore", JOptionPane.ERROR_MESSAGE);
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(loginDialog, 
                    "Errore nel tentativo di login", "Errore", JOptionPane.ERROR_MESSAGE);
            }
        });

        cancelBtn.addActionListener(e -> loginDialog.dispose());

        buttonPanel.add(loginBtn);
        buttonPanel.add(cancelBtn);

        loginDialog.add(contentPanel, BorderLayout.CENTER);
        loginDialog.add(buttonPanel, BorderLayout.SOUTH);
        loginDialog.setVisible(true);
    }

    private boolean isValidEmail(String email) {
        return Pattern.compile("^[\\w-.]+@([\\w-]+\\.)+[\\w-]{2,4}$")
                     .matcher(email)
                     .matches();
    }
}