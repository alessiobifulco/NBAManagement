import core.Controller;
import core.Model;
import core.View;
import data.DAOUtils;

public class Main {
    public static void main(String[] args) {
        // Collega al database NBA_System
        var connection = DAOUtils.localMySQLConnection("NBA_Management", "root", "BLS007ab&");
        // Stampa il messaggio di successo della connessione
        System.out.println("Connessione al database avvenuta con successo.");
        var model = Model.fromConnection(connection);
        var view = new View(() -> {
            try {
                connection.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
        var controller = new Controller(model, view);
        view.setController(controller);

    }
}
