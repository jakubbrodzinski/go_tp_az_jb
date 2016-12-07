package Klient.View;

import Klient.View.ConnectionPanel.ConnectionHBox;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

import java.io.PrintWriter;

/**
 * Created by arek on 12/3/16.
 */
public class ConnectionBoxScene extends Scene {

    private Pane initialRoot;
    private PrintWriter out;

    public ConnectionBoxScene(Parent root, double width, double height, PrintWriter out) {
        super(root, width, height);
        this.out = out;
        initialRoot = (Pane)root;
        prepareGUI();
    }
    private void prepareGUI() {
        ConnectionHBox connectionHBox = new ConnectionHBox(out);
        connectionHBox.setPrefSize(600,400);
        initialRoot.setStyle("-fx-background-color: #EFECCA");
        initialRoot.getChildren().add(connectionHBox);
    }
    public PrintWriter getOutput() {
        return out;
    }
}
