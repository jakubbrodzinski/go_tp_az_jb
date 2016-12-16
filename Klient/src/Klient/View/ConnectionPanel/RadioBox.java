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
 * Class that stores/animates choice buttons and creates RadioBox GUI
 */
public class RadioBox extends GridPane {

    /**
     * TextField getting/storing Join room number input
     */
    private TextField tableInput = new TextField();
    /**
     * TextField getting/storing New game board size
     */
    private TextField sizeInput = new TextField();
    /**
     * ToggleGroup that stores all choice buttons
     */
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

    /**
     * Method that initializes RadioBox GUI and sets all EventHandlers
     */
    private void prepareRadioBox() {
        //Additional VBox that stores the Buttons
        VBox components = new VBox();
        components.setAlignment(Pos.CENTER_LEFT);
        components.setSpacing(20);

        //Button for new game
        RadioButton newTableButton = new RadioButton("Nowa gra");
        newTableButton.setUserData("NewGame");
        newTableButton.setSelected(true);
        newTableButton.requestFocus();
        newTableButton.setFont(new Font(20));
        newTableButton.setToggleGroup(group);

        //Button for joining game
        RadioButton joinTableButton = new RadioButton("Dołącz do gry");
        joinTableButton.setUserData("JoinGame");
        joinTableButton.setFont(new Font(20));
        joinTableButton.setToggleGroup(group);

        components.getChildren().add(newTableButton);

        components.getChildren().add(joinTableButton);


        GridPane.setConstraints(components, 0, 0);
        this.getChildren().add(components);

        //Stylizing the prompts and TextFields
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

        //Animating the Radiobox
        ImageView image = new ImageView();
        GridPane.setConstraints(image, 1, 0);
        //Initial image is New Game, because new game button is the default toggle
        image.setImage(new Image(getClass().getResourceAsStream("NewGame.png")));
        this.getChildren().add(image);
        //Listener for changing the toggle - it changes the image near the toggled button
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

    /**
     * Accessor for content of Join Table Textfield
     * @return String with table number
     */
    public String getTableInputContent() {
        return tableInput.getText();
    }

    /**
     * Accesor for content of New Game Textfield
     * @return String with size of new game board
     */
    public String getSizeInputContent() {
        return sizeInput.getText();
    }

    /**
     * Accessor for getting descriptions of toggled button
     * @return String with button description
     */
    public String getToggledButton() {
        return (String) group.getSelectedToggle().getUserData();
    }
 }
