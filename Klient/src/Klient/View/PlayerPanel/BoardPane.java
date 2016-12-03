package Klient.View.PlayerPanel;

import javafx.embed.swing.SwingNode;
import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;

import javax.swing.*;

/**
 * Created by arek on 12/3/16.
 */
public class BoardPane extends GridPane {
    public BoardPane() {
        this.setPrefSize(866,768);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: aqua");
        prepareBoardPane();
    }
    private void prepareBoardPane() {
        SwingNode board = new SwingNode();
        createSwingComponent(board);
        this.getChildren().add(board);
    }
    private void createSwingComponent(SwingNode swingNode) {
        swingNode.setContent(new JButton("PROBKA"));
    }
}
