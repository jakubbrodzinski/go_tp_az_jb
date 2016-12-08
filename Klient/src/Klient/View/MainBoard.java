package Klient.View;

import Klient.ClientState;
import Klient.StoneController;
import Klient.StoneLocation;
import Klient.StoneLocationParser;
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
    public void redraw(String command) {
        String[] commands = command.split("-");
        if(commands[0].equals("CHANGE")) {
            StoneLocation location = StoneLocationParser.parsetoStoneLocation(commands[1], commands[2]);
            stones[location.getxInt()][location.getY()].setOpacity(1);
            stones[location.getxInt()][location.getY()].setFill(ClientState.getInstance().getCurrentColorPlaying());
            for(int i = 3; i < commands.length; i+=2) {
                location = StoneLocationParser.parsetoStoneLocation(commands[i], commands[i+1]);
                stones[location.getxInt()][location.getY()].setOpacity(0);
                stones[location.getxInt()][location.getY()].setFill(Color.AZURE);
            }
        }
        if(commands[0].equals("CORRECT")) {
            StoneLocation location = StoneLocationParser.parsetoStoneLocation(commands[1], commands[2]);
            stones[location.getxInt()][location.getY()].setOpacity(1);
            stones[location.getxInt()][location.getY()].setFill(ClientState.getInstance().getCurrentColorPlaying());
            for(int i = 3; i < commands.length; i+=2) {
                location = StoneLocationParser.parsetoStoneLocation(commands[i], commands[i+1]);
                stones[location.getxInt()][location.getY()].setOpacity(0);
                stones[location.getxInt()][location.getY()].setFill(Color.AZURE);
            }
        }
    }
}
