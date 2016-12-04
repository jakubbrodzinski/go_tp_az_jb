package Klient.View;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Line;

import java.util.ArrayList;
/**
 * Created by arek on 12/4/16.
 */
public class MainBoard extends Pane {
    private int width;
    private int height;
    private ArrayList<Stone> stones = new ArrayList<>();
    public MainBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.setPrefSize(35*(width-1), 35*(height-1));
        this.setStyle("-fx-background-color: #b7b496");
        drawGrid();
        drawBoard();
    }
    private void drawGrid() {
        Line gridLine;
        for(int i = 0; i < width; i++) {
            gridLine = new Line(35*i, 0, 35*i, 35*(height-1));
            this.getChildren().add(gridLine);
        }
        for(int i = 0; i < height; i++) {
            gridLine = new Line(0, 35*i, 35*(width-1), 35*i);
            this.getChildren().add(gridLine);
        }
    }
    private void drawBoard() {
        Stone tempStone;
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                tempStone = new Stone(35* i, 35* j);
                tempStone.setStyle("-fx-background-color: black");
                tempStone.setVisible(false);
                stones.add(tempStone);
                this.getChildren().add(tempStone);
            }
        }
    }
}
