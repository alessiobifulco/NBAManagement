package core;

import javax.swing.*;


public class View {

    private final JFrame frame;
    public Model model; 

    public View(Runnable onClose) {
        this.frame = new JFrame();
 
    }

    public void setController(Controller controller) {
    }

    public void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(frame, message, "Errore", JOptionPane.ERROR_MESSAGE);
    }
}
