package Klient.View;

import Klient.View.PlayerPanel.BoardPane;
import Klient.View.PlayerPanel.PlayerVBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

/**
 * Created by arek on 12/2/16.
 * Main scene class that stores the board and game information
 */
public class ClientScene extends Scene {
    /**
     * Pane that the components belong to
     */
    private Pane root;

    public ClientScene(Parent root, double width, double height) {
        super(root, width, height);
        this.root = (Pane)root;

        prepareGUI();
    }

    /**
     * Method that initializes the ClientScene GUI
     */
    private void prepareGUI() {
        PlayerVBox playerVBox = new PlayerVBox();
        BoardPane testPane = new BoardPane();
        root.setStyle("-fx-background-color: #EFECCA");
        root.getChildren().add(testPane);
        root.getChildren().add(playerVBox);
    }
}
