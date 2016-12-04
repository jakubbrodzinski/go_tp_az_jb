package Klient.View.PlayerPanel;

import Klient.View.MainBoard;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

/**
 * Created by arek on 12/3/16.
 */
public class BoardPane extends GridPane {
    public BoardPane() {
        this.setPrefSize(866,768);
        this.setAlignment(Pos.CENTER);
        prepareBoardPane();
    }
    private void prepareBoardPane() {
        MainBoard board = new MainBoard(9, 9);
        this.getChildren().add(board);
    }

}
