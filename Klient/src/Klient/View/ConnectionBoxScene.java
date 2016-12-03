package Klient.View;

import Klient.View.ConnectionPanel.ConnectionHBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Created by arek on 12/3/16.
 */
public class ConnectionBoxScene extends Scene {
    private Pane initialRoot;
    public ConnectionBoxScene(Parent root, double width, double height) {
        super(root, width, height);
        initialRoot = (Pane)root;
        prepareGUI();
    }
    private void prepareGUI() {
        ConnectionHBox connectionHBox = new ConnectionHBox();
        connectionHBox.setPrefSize(600,400);
        initialRoot.setStyle("-fx-background-color: #EFECCA");
        initialRoot.getChildren().add(connectionHBox);
    }
}
