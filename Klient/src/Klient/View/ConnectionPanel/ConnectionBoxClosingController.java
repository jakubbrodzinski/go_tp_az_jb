package Klient.View.ConnectionPanel;

import javafx.event.EventHandler;
import javafx.scene.control.Alert;
import javafx.stage.WindowEvent;

/**
 * Created by arek on 12/7/16.
 */
public class ConnectionBoxClosingController implements EventHandler<WindowEvent> {
    @Override
    public void handle(WindowEvent event) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Błąd połączenia");
        alert.setHeaderText("Nie wybrano opcji połączenia");
        alert.setContentText("Nie wybrano opcji połączenia i gra nie może zostać zainicjalizowana. Gra zostanie wyłączona.");
        alert.getDialogPane().setPrefSize(400, 200);
        alert.showAndWait();
        System.exit(1);
    }
}
