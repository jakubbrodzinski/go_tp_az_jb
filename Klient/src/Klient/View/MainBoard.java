package Klient.View;

import Klient.ClientState;
import Klient.StoneController;
import Klient.StoneLocation;
import Klient.StoneLocationParser;
import javafx.animation.FadeTransition;
import javafx.scene.effect.InnerShadow;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Line;
import javafx.util.Duration;

import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Created by arek on 12/4/16.
 */
public class MainBoard extends Pane implements GameBoardInterface {
    private int width;
    private int height;
    private Stone[][] stones;
    private Stone[][] backup;
    private ArrayList<Stone> whiteTerritory = new ArrayList<>();
    private ArrayList<Stone> blackTerritory = new ArrayList<>();
    private ArrayList<Stone> deadStonesBlack = new ArrayList<>();
    private ArrayList<Stone> deadStonesWhite = new ArrayList<>();
    public MainBoard(int width, int height) {
        this.width = width;
        this.height = height;
        this.stones = new Stone[width][height];
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
                tempStone.setOnMouseClicked(new StoneController());
                stones[i][j] = tempStone;
                this.getChildren().add(tempStone);
            }
        }
    }
    public void redraw(String command) {
        String[] commands = command.split("-");
        ClientState.getInstance().setBlackPoints(commands[2]);
        ClientState.getInstance().setWhitePoints(commands[1]);
        if(commands[3].equals("CHANGE")) {
            StoneLocation location = StoneLocationParser.parsetoStoneLocation(commands[4], commands[5]);
            stones[location.getxInt()][location.getY()].setOpacity(1);
            stones[location.getxInt()][location.getY()].setFill(ClientState.getInstance().getCurrentColorPlaying());
            for(int i = 6; i < commands.length; i+=2) {
                location = StoneLocationParser.parsetoStoneLocation(commands[i], commands[i+1]);
                stones[location.getxInt()][location.getY()].setOpacity(0);
                stones[location.getxInt()][location.getY()].setFill(Color.AZURE);
            }
        }
        if(commands[3].equals("CORRECT")) {
            StoneLocation location = StoneLocationParser.parsetoStoneLocation(commands[4], commands[5]);
            stones[location.getxInt()][location.getY()].setOpacity(1);
            stones[location.getxInt()][location.getY()].setFill(ClientState.getInstance().getCurrentColorPlaying());
            for(int i = 6; i < commands.length; i+=2) {
                location = StoneLocationParser.parsetoStoneLocation(commands[i], commands[i+1]);
                stones[location.getxInt()][location.getY()].setOpacity(0);
                stones[location.getxInt()][location.getY()].setFill(Color.AZURE);
            }
        }
    }
    //przy zrzucie przy poddawaniu sie
    public void backupBoard() {
        backup =  new Stone[width][height];
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                backup[i][j] = new Stone(35 * i, 35 * j);
                backup[i][j].setFill(stones[i][j].getFill());
                backup[i][j].setOpacity(stones[i][j].getOpacity());
            }
        }
    }
    public void restoreBoard() {
        for(int i = 0; i < width; i++) {
            for(int j = 0; j < height; j++) {
                stones[i][j].setFill(backup[i][j].getFill());
                stones[i][j].setOpacity(backup[i][j].getOpacity());
            }
        }
    }

    public void redrawTerritories(String command) {
        String[] splitcommands = command.split("-");
        for(int i = 0; i < splitcommands.length; i++) {
            if(splitcommands[i].equals("BLACK")) {
                i++;
                while(!splitcommands[i].equals("WHITE")) {
                    StoneLocation location = StoneLocationParser.parsetoStoneLocation(splitcommands[i], splitcommands[i+1]);
                    stones[location.getxInt()][location.getY()].setOpacity(0.6);
                    stones[location.getxInt()][location.getY()].setFill(Color.GRAY);
                    blackTerritory.add(stones[location.getxInt()][location.getY()]);
                    i+=2;
                }
            }
            if(splitcommands[i].equals("WHITE")) {
                i++;
                while(!splitcommands[i].equals("BLACKP")) {
                    StoneLocation location = StoneLocationParser.parsetoStoneLocation(splitcommands[i], splitcommands[i+1]);
                    stones[location.getxInt()][location.getY()].setOpacity(0.6);
                    stones[location.getxInt()][location.getY()].setFill(Color.WHITESMOKE);
                    whiteTerritory.add(stones[location.getxInt()][location.getY()]);
                    i+=2;
                }
            }
            if(splitcommands[i].equals("BLACKP")) {
                i++;
                while(!splitcommands[i].equals("WHITEP")) {
                    StoneLocation location = StoneLocationParser.parsetoStoneLocation(splitcommands[i], splitcommands[i+1]);
                    stones[location.getxInt()][location.getY()].setOpacity(1);
                    stones[location.getxInt()][location.getY()].setFill(Color.DARKRED);
                    deadStonesBlack.add(stones[location.getxInt()][location.getY()]);
                    i+=2;
                }
            }
            if(splitcommands[i].equals("WHITEP")) {
                i++;
                while(i < splitcommands.length) {
                    StoneLocation location = StoneLocationParser.parsetoStoneLocation(splitcommands[i], splitcommands[i+1]);
                    stones[location.getxInt()][location.getY()].setOpacity(1);
                    stones[location.getxInt()][location.getY()].setFill(Color.RED);
                    deadStonesWhite.add(stones[location.getxInt()][location.getY()]);
                    i+=2;
                }
            }
        }
    }
    public ArrayList<Stone> getWhiteTerritory() {
        return whiteTerritory;
    }
    public ArrayList<Stone> getBlackTerritory() {
        return blackTerritory;
    }
    public ArrayList<Stone> getDeadStonesWhite() {
        return deadStonesWhite;
    }
    public ArrayList<Stone> getDeadStonesBlack() {
        return deadStonesBlack;
    }
    public String parseTerritories() {
        String answer = "";
        answer += "PROPOSITION-";
        answer += "BLACK-";
        for(Stone stone : blackTerritory) {
            StoneLocation location = StoneLocationParser.parseStoneLocation((int) stone.getCenterX(), (int) stone.getCenterY());
            answer = answer + location.getX() + Integer.toString(location.getY()) + "-";
        }
        answer += "WHITE-";
        for(Stone stone : whiteTerritory) {
            StoneLocation location = StoneLocationParser.parseStoneLocation((int) stone.getCenterX(), (int) stone.getCenterY());
            answer = answer + location.getX() + Integer.toString(location.getY()) + "-";
        }
        answer += "BLACKP-";
        for(Stone stone : deadStonesBlack) {
            StoneLocation location = StoneLocationParser.parseStoneLocation((int) stone.getCenterX(), (int) stone.getCenterY());
            answer = answer + location.getX() +  Integer.toString(location.getY()) + "-";
        }
        answer += "WHITEP-";
        for(Stone stone : deadStonesWhite) {
            StoneLocation location = StoneLocationParser.parseStoneLocation((int) stone.getCenterX(), (int) stone.getCenterY());
            answer = answer + location.getX() +  Integer.toString(location.getY()) + "-";
        }
        answer = answer.substring(0, answer.length()-1);
        System.out.println(answer);
        return answer;

    }
    //Grupowanie kamieni
    public ArrayList<Stone> getStonesGroup(int x, int y) {
        ArrayList<Stone> result = new ArrayList<>();
        ArrayList<Stone> alreadyTraversed = new ArrayList<>();
        if(stones[x][y].getFill().equals(Color.AZURE)) {
            System.out.println("Puste pole");
        }
        else {
            result.add(stones[x][y]);
            lookForGroup(x, y, result, alreadyTraversed);
        }
        return result;
    }
    public void colorGroup(ArrayList<Stone> group, Paint color) {

        for(Stone stone : group) {
            stone.setFill(color);
        }
    }
    //szukanie grupek
    private void lookForGroup(int x, int y, ArrayList<Stone> result, ArrayList<Stone> alreadyTraversed) {
        if(stones[x][y].getFill().equals(result.get(0).getFill())) {
            if (!alreadyTraversed.contains(stones[x][y])) {
                alreadyTraversed.add(stones[x][y]);
                if (!(stones[x][y].equals(result.get(0)))){
                    result.add(stones[x][y]);
                }
                if(!(x + 1 >= width)) {
                    lookForGroup(x + 1, y, result, alreadyTraversed);
                }
                if(!(x - 1 < 0)) {
                    lookForGroup(x - 1, y, result, alreadyTraversed);
                }
                if(!(y + 1 >= height)) {
                    lookForGroup(x, y + 1, result, alreadyTraversed);
                }
                if(!(y - 1 < 0)) {
                    lookForGroup(x, y - 1, result, alreadyTraversed);
                }
            }
        }

    }
    public void changePlayerEffectOn() {
        FadeTransition ft = new FadeTransition(Duration.millis(500), this);
        ft.setFromValue(0.5);
        ft.setToValue(1);
        ft.setCycleCount(1);
        ft.play();
    }
    public void changePlayerEffectOff() {
        FadeTransition ft = new FadeTransition(Duration.millis(500), this);
        ft.setFromValue(1);
        ft.setToValue(0.5);
        ft.setCycleCount(1);
        ft.play();
    }
}
