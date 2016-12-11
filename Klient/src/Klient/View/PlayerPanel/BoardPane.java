package Klient.View.PlayerPanel;

import Klient.ClientState;
import Klient.View.ClientScene;
import Klient.View.MainBoard;

import javafx.animation.FadeTransition;
import javafx.geometry.Pos;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.util.Duration;

import java.io.PrintWriter;

/**
 * Created by arek on 12/3/16.
 * Class that creates the main board and acts as the middleman between main scene and board
 */
public class BoardPane extends GridPane {

    public BoardPane() {
        this.setPrefSize(866,768);
        this.setAlignment(Pos.CENTER);
        this.setStyle("-fx-background-color: #EFECCA");
        prepareBoardPane();
    }

    /**
     * Method that initializes BoardPane GUI
     */
    private void prepareBoardPane() {
        //TODO Refactor MainBoard to a factory method
        MainBoard board = new MainBoard(ClientState.getInstance().getSize(), ClientState.getInstance().getSize());

        this.getChildren().add(board);
    }
}
