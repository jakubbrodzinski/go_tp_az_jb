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

    private TextField tableInput = new TextField();
    private TextField sizeInput = new TextField();
    private final ToggleGroup group = new ToggleGroup();

    public RadioBox() {
        this.setAlignment(Pos.CENTER_LEFT);
        this.setPrefSize(400,280);
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

        tableInput.setPromptText("Podaj numer stołu, do którego chcesz dołączyć");
        tableInput.setVisible(false);

        sizeInput.setPromptText("Podaj wielkość stołu, który chcesz utworzyć (9 lub 13 lub 19)");
        sizeInput.setPrefSize(400,100);
        sizeInput.setFont(Font.font(11));
        sizeInput.setVisible(true);

        GridPane.setConstraints(tableInput, 0, 3);
        GridPane.setHalignment(tableInput, HPos.RIGHT);
        GridPane.setValignment(tableInput, VPos.BOTTOM);
        GridPane.setColumnSpan(tableInput, 2);

        GridPane.setConstraints(sizeInput, 0, 3);
        GridPane.setHalignment(sizeInput, HPos.RIGHT);
        GridPane.setValignment(sizeInput, VPos.BOTTOM);
        GridPane.setColumnSpan(sizeInput, 2);

        this.getChildren().add(tableInput);
        this.getChildren().add(sizeInput);


        ImageView image = new ImageView();
        GridPane.setConstraints(image, 1, 0);
        image.setImage(new Image(getClass().getResourceAsStream("NewGame.png")));
        this.getChildren().add(image);
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
                    sizeInput.setVisible(false);
                }
                else {
                    tableInput.setVisible(false);
                    sizeInput.setVisible(true);
                }
            }
        });
    }
    public String getTableInputContent() {
        return tableInput.getText();
    }
    public String getSizeInputContent() {
        return sizeInput.getText();
    }
    public String getToggledButton() {
        return (String) group.getSelectedToggle().getUserData();
    }
 }
