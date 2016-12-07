package Klient.View.ConnectionPanel;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

import java.io.PrintWriter;

/**
 * Created by arek on 12/3/16.
 */
public class ConnectionHBox extends GridPane {

    private RadioBox radioBox = new RadioBox();
    private PrintWriter out;

    public ConnectionHBox(PrintWriter out) {
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-background-color: #e1debc");
        this.setPadding(new Insets(50,10,80,10));
        this.setVgap(10);
        this.out = out;
        prepareConnectionHBox();
    }
    private void prepareConnectionHBox() {

        Button confirmationButton = new Button("Zatwierdź");
        confirmationButton.setPrefSize(150,90);
        confirmationButton.setStyle("-fx-font-size: 20");
        //System.out.println("Rodzic to:" + this.getParent());
        confirmationButton.setOnMouseClicked(new ConnectionPanelController(out));

        GridPane.setConstraints(radioBox, 0, 0);
        this.getChildren().add(radioBox);

        GridPane.setConstraints(confirmationButton, 0, 2);
        GridPane.setValignment(confirmationButton, VPos.BASELINE);
        GridPane.setHalignment(confirmationButton, HPos.CENTER);
        this.getChildren().add(confirmationButton);

    }
    public RadioBox getRadioBox() {
        return this.radioBox;
    }
    public PrintWriter getOutput() {
        return out;
    }
}
