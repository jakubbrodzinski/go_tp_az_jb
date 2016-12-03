package Klient.View.ConnectionPanel;

import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Font;

/**
 * Created by arek on 12/3/16.
 */
public class RadioBox extends GridPane {
    public RadioBox() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefSize(500,280);
        this.setPadding(new Insets(10,10,10,10));
        this.setVgap(10);
        this.setHgap(50);
        this.getColumnConstraints().add(new ColumnConstraints(250));
        prepareRadioBox();
    }
    private void prepareRadioBox() {
        VBox components = new VBox();
        components.setAlignment(Pos.CENTER_LEFT);
        components.setSpacing(20);
        final ToggleGroup group = new ToggleGroup();

        RadioButton newTableButton = new RadioButton("Nowa gra");
        newTableButton.setUserData("NewGame");
        newTableButton.setSelected(true);
        newTableButton.requestFocus();
        newTableButton.setFont(new Font(20));
        newTableButton.setToggleGroup(group);

        RadioButton joinTableButton = new RadioButton("Dołącz do gry");
        joinTableButton.setUserData("JoinGame");
        joinTableButton.setFont(new Font(20));
        joinTableButton.setToggleGroup(group);

        components.getChildren().add(newTableButton);

        components.getChildren().add(joinTableButton);
        GridPane.setConstraints(components, 0, 0);
        this.getChildren().add(components);

        TextField tableInput = new TextField();
        tableInput.setPromptText("Podaj numer stołu, do którego chcesz dołączyć");
        tableInput.setVisible(false);

        GridPane.setConstraints(tableInput, 0, 3);
        GridPane.setHalignment(tableInput, HPos.RIGHT);
        GridPane.setValignment(tableInput, VPos.BOTTOM);
        GridPane.setColumnSpan(tableInput, 2);

        this.getChildren().add(tableInput);


        ImageView image = new ImageView();
        GridPane.setConstraints(image, 1, 0);
        image.setImage(new Image(getClass().getResourceAsStream("NewGame.png")));
        this.getChildren().add(image);
        System.out.println(group.getSelectedToggle());
        group.selectedToggleProperty().addListener(new ChangeListener<Toggle>() {
            @Override
            public void changed(ObservableValue<? extends Toggle> observable, Toggle oldValue, Toggle newValue) {
                if(group.selectedToggleProperty() != null) {
                    final Image icon = new Image(
                            getClass().getResourceAsStream(
                                    group.getSelectedToggle().getUserData().toString() +
                                            ".png"));
                    image.setImage(icon);
                }
                if(group.getSelectedToggle() == joinTableButton) {
                    tableInput.setVisible(true);
                }
                else {
                    tableInput.setVisible(false);
                }
            }
        });


    }
 }
