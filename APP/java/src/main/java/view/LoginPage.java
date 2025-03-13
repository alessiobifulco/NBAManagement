package view;

import core.View;
import javax.swing.*;
import java.awt.*;

public class LoginPage extends JFrame {

    public LoginPage(View view) {
        setTitle("Login");
        setSize(1000, 1000);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JButton loginButton = new JButton("Login");
        JButton adminButton = new JButton("Administrator");

        loginButton.addActionListener(e -> {
            new TeamPage(view); // Apre la TeamPage
            dispose(); // Chiude la LoginPage
        });

        adminButton.addActionListener(e -> {
            new AdministratorPage(view); // Apre la AdministratorPage
            dispose(); // Chiude la LoginPage
        });

        add(loginButton);
        add(adminButton);
        setVisible(true);
    }
}
