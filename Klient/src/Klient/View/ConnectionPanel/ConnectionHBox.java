package Klient.View.ConnectionPanel;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;

/**
 * Created by arek on 12/3/16.
 */
public class ConnectionHBox extends GridPane {
    public ConnectionHBox() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-background-color: #e1debc");
        this.setPadding(new Insets(50,10,80,10));
        this.setVgap(10);
        prepareConnectionHBox();
    }
    private void prepareConnectionHBox() {
        RadioBox radioBox = new RadioBox();
        Button confirmationButton = new Button("Zatwierdź");
        confirmationButton.setPrefSize(150,90);
        confirmationButton.setStyle("-fx-font-size: 20");

        GridPane.setConstraints(radioBox, 0, 0);
        this.getChildren().add(radioBox);

        GridPane.setConstraints(confirmationButton, 0, 2);
        GridPane.setValignment(confirmationButton, VPos.BASELINE);
        GridPane.setHalignment(confirmationButton, HPos.CENTER);
        this.getChildren().add(confirmationButton);

    }
}
