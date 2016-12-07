package Klient.View.PlayerPanel;

import Klient.View.MainBoard;

import javafx.geometry.Pos;
import javafx.scene.layout.GridPane;

import java.io.PrintWriter;

/**
 * Created by arek on 12/3/16.
 */
public class BoardPane extends GridPane {
    private PrintWriter out;

    public BoardPane(PrintWriter out) {
        this.setPrefSize(866,768);
        this.setAlignment(Pos.CENTER);
        this.out = out;
        prepareBoardPane();
    }
    private void prepareBoardPane() {
        //TODO Refactor MainBoard to a factory method
        MainBoard board = new MainBoard(19, 19, out);
        this.getChildren().add(board);
    }

}
