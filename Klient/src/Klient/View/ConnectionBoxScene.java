package Klient.View;

import Klient.View.ConnectionPanel.ConnectionHBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Created by arek on 12/3/16.
 * Secondary scene with the Connection Box, it is shown on the first opening of the client
 * Thanks to that class the connection can be made. It gets the connection details such as room number, new game or bot game from the user
 */
public class ConnectionBoxScene extends Scene {

    /**
     * Pane that all components of ConnectionBoxScene belong to
     */
    private Pane initialRoot;

    public ConnectionBoxScene(Parent root, double width, double height) {
        super(root, width, height);
        initialRoot = (Pane)root;
        prepareGUI();
    }

    /**
     * Method that initializes ConnectionBoxScene GUI
     */
    private void prepareGUI() {
        ConnectionHBox connectionHBox = new ConnectionHBox();
        connectionHBox.setPrefSize(600,400);
        initialRoot.setStyle("-fx-background-color: #EFECCA");
        initialRoot.getChildren().add(connectionHBox);
    }
}
