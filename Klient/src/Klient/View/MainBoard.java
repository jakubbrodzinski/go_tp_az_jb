package Klient.View;

import Klient.StoneController;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

import java.io.PrintWriter;

/**
 * Created by arek on 12/4/16.
 */
public class MainBoard extends Pane {
    private int width;
    private int height;
    private Stone[][] stones;
    private PrintWriter out;
    public MainBoard(int width, int height, PrintWriter out) {
        this.width = width;
        this.height = height;
        this.stones = new Stone[width][height];
        this.out = out;
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
                tempStone.setOnMouseClicked(new StoneController(out));
                stones[i][j] = tempStone;
                this.getChildren().add(tempStone);
            }
        }
    }
}
