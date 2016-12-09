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
 */
public class BoardPane extends GridPane {
    private PrintWriter out;

    public BoardPane(PrintWriter out) {
        this.setPrefSize(866,768);
        this.setAlignment(Pos.CENTER);
        this.out = out;
        this.setStyle("-fx-background-color: #EFECCA");
        prepareBoardPane();
    }
    private void prepareBoardPane() {
        //TODO Refactor MainBoard to a factory method
        MainBoard board = new MainBoard(ClientState.getInstance().getSize(), ClientState.getInstance().getSize(), out);

        this.getChildren().add(board);
    }

    public void changePlayerEffectOn() {

        FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
        ft.setFromValue(0.5);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();
    }
    public void changePlayerEffectOff() {
        FadeTransition ft = new FadeTransition(Duration.millis(1000), this);
        ft.setFromValue(1);
        ft.setToValue(0.5);
        ft.setCycleCount(1);
        ft.play();
    }
}
