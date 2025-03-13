package view;

import core.View;
import javax.swing.*;
import java.awt.*;

public class AdministratorPage extends JFrame {

    public AdministratorPage(View view) {
        setTitle("Administrator");
        setSize(300, 150);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLayout(new FlowLayout());
        setExtendedState(JFrame.MAXIMIZED_BOTH);

        JButton backButton = new JButton("Back to Login");
        backButton.addActionListener(e -> {
            new LoginPage(view); // Torna alla LoginPage
            dispose(); // Chiude la finestra attuale
        });

        add(backButton);
        setVisible(true);
    }
}
