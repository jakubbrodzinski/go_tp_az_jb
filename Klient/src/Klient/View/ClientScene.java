package Klient.View;

import Klient.View.PlayerPanel.BoardPane;
import Klient.View.PlayerPanel.PlayerVBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import java.io.PrintWriter;

/**
 * Created by arek on 12/2/16.
 */
public class ClientScene extends Scene {
    private Pane root;
    private PrintWriter out;

    public ClientScene(Parent root, double width, double height, PrintWriter out) {
        super(root, width, height);
        this.root = (Pane)root;
        this.out = out;
        //System.out.println("Out w ClientScene: " + this.out);
        prepareGUI();
    }

    private void prepareGUI() {
        PlayerVBox playerVBox = new PlayerVBox();
        BoardPane testPane = new BoardPane(out);
        root.setStyle("-fx-background-color: #EFECCA");

        root.getChildren().add(testPane);
        root.getChildren().add(playerVBox);
    }
}
