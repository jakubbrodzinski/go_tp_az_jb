package Klient.View.ConnectionPanel;

import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.Button;
import javafx.scene.layout.GridPane;


/**
 * Created by arek on 12/3/16.
 * Main GUI component of Connection Box (the windows with usage of which you connect to the game)
 */
public class ConnectionHBox extends GridPane {

    /**
     * RadioBox with option buttons
     */
    private RadioBox radioBox = new RadioBox();

    public ConnectionHBox() {
        this.setAlignment(Pos.TOP_CENTER);
        this.setStyle("-fx-background-color: #e1debc");
        this.setPadding(new Insets(50,10,80,10));
        this.setVgap(10);
        prepareConnectionHBox();
    }

    /**
     * Method that creates the GUI of the ConnectionHBox
     */
    private void prepareConnectionHBox() {

        Button confirmationButton = new Button("Zatwierdź");
        confirmationButton.setPrefSize(150,90);
        confirmationButton.setStyle("-fx-font-size: 20");
        confirmationButton.setOnMouseClicked(new ConnectionPanelController());

        GridPane.setConstraints(radioBox, 0, 0);
        this.getChildren().add(radioBox);

        GridPane.setConstraints(confirmationButton, 0, 2);
        GridPane.setValignment(confirmationButton, VPos.BASELINE);
        GridPane.setHalignment(confirmationButton, HPos.CENTER);
        this.getChildren().add(confirmationButton);

    }

    /**
     * Accessor method that returns panel with option buttons
     * @return RadioBox
     */
    public RadioBox getRadioBox() {
        return this.radioBox;
    }
}
