package Klient.View;

import Klient.View.PlayerPanel.PlayerVBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Created by arek on 12/2/16.
 */
public class ClientScene extends Scene {
    private Pane root;

    public ClientScene(Parent root, double width, double height) {
        super(root, width, height);
        this.root = (Pane)root;
        prepareGUI();
    }

    private void prepareGUI() {
        PlayerVBox playerVBox = new PlayerVBox();
        root.setStyle("-fx-background-color: #EFECCA");
        root.getChildren().add(playerVBox);
    }
}
