package Klient.View.PlayerPanel;

import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.layout.VBox;

import java.io.IOException;

/**
 * Created by arek on 12/3/16.
 * Class that creates the most right oriented panel with game informations and navigation buttons
 */
public class PlayerVBox extends VBox {
    public PlayerVBox() {
        preparePlayerVBox();
    }

    /**
     * Method initializing the PlayerVBox GUI - loading the GUI from FXML
     */
    private void preparePlayerVBox() {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("PlayerVBox.fxml"));
        try {
            this.getChildren().setAll((Node)loader.load());
        }
        catch(IOException e) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Błąd inicjalizacji interfejsu graficznego");
            alert.setHeaderText("Błąd inicjalizacji komponentu PlayerVBox");
            alert.setContentText("Wystąpił błąd w czasie inicjalizacji komponentu PlayerVBox. Sprawdź, czy plik FXML nie jest uszkodzony.");
            alert.showAndWait();
            System.exit(1);
        }
    }
}
