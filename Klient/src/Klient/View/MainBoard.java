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
    public Stone[][] getBoard() {
        return stones;
    }
    public void setBoard(Stone[][] newBoard) {
        this.stones = newBoard;
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
        System.out.println("result: "+result);
        return result;
    }
    public void colorGroup(ArrayList<Stone> group, Paint color) {

        for(Stone stone : group) {
            System.out.println("BYLEM TU");
            stone.setFill(color);
        }
    }
    public void removeStonesGroup(ArrayList<Stone> toBeRemoved) {
        for(Stone stone : toBeRemoved) {
            stone.setOpacity(0);
            stone.setFill(Color.AZURE);
        }
    }
    //znajdowanie terytoriów i usuwanie tego co jst w środku terytorium
    public void findTerritory() {
        ArrayList<Stone> alreadyTraversed = new ArrayList<>();
        ArrayList<ArrayList<Stone>> territory = new ArrayList<>();
        for(int i = 0; i < width; i++) {
            for (int j = 0; j < height; j++) {
                if (!(alreadyTraversed.contains(stones[i][j])) && (stones[i][j].getFill().equals(ClientState.getInstance().getPlayerColorPaint()))) {
                    alreadyTraversed.addAll(getStonesGroup(i, j));
                    territory.add(getStonesGroup(i, j));
                }
            }
        }

        System.out.println("Teyrotiurm to:" + territory);
        removeInnerParts(territory);
    }
    //usuwanie tego co jest w srodku terytorium
    private void removeInnerParts(ArrayList<ArrayList<Stone>> territory) {
        for(ArrayList<Stone> group : territory) {
            Stone[] helper = findBounds(group);
            System.out.println(helper);
            if(helper[0] != null && helper[1] != null) {
                if(helper[0].getCenterX()/35 < helper[1].getCenterX()/35 && helper[0].getCenterY()/35 > helper[1].getCenterY()/35) {
                    System.out.println("1");
                    for(int i = 0; i < helper[1].getCenterX()/35; i++) {
                        for(int j = 0; j < helper[0].getCenterY()/35; j++) {
                            if(!group.contains(stones[i][j])) {
                                stones[i][j].setOpacity(0);
                                stones[i][j].setFill(Color.AZURE);
                            }
                            else
                                break;
                        }
                    }

                }
               else if(helper[0].getCenterX()/35 < helper[1].getCenterX()/35 && helper[0].getCenterY()/35 < helper[1].getCenterY()/35) {
                    System.out.println("2");
                    for(int i = 0; i < helper[1].getCenterX()/35; i++) {
                        for(int j = height-1; j > 0; j--) {
                            if(!group.contains(stones[i][j])) {
                                stones[i][j].setOpacity(0);
                                stones[i][j].setFill(Color.AZURE);
                            }
                            else
                                break;
                        }
                    }
                }
                else if(helper[0].getCenterX()/35 > helper[1].getCenterX()/35 && helper[0].getCenterY()/35 > helper[1].getCenterY()/35) {
                    System.out.println("3");
                    for(int i = width - 1; i > 0; i--) {
                        for(int j = 0; j < helper[0].getCenterY()/35; j++) {
                            if(!group.contains(stones[i][j])) {
                                stones[i][j].setOpacity(0);
                                stones[i][j].setFill(Color.AZURE);
                            }
                            else
                                break;
                        }
                    }
                }
                else if(helper[0].getCenterX()/35 > helper[1].getCenterX()/35 && helper[0].getCenterY()/35 < helper[1].getCenterY()/35) {
                    System.out.println("4");
                    for(int i = width - 1; i > 0; i--) {
                        if(group.contains(stones[i][height-1]))
                            break;
                        for(int j = height-1; j > 0; j--) {
                            if(!group.contains(stones[i][j])) {
                                stones[i][j].setOpacity(0);
                                stones[i][j].setFill(Color.AZURE);
                            }
                            else
                                break;
                        }
                    }
                }

            }
        }

    }
    //znajdowanie końców grup
    private Stone[] findBounds(ArrayList<Stone> group) {
        Stone[] answer = new Stone[2];

        for(Stone stone : group) {
            if(stone.getCenterX()/35 == 0 || stone.getCenterX()/35 == width-1) {
                answer[0] = stone;
            }
            if(stone.getCenterY()/35 == 0 || stone.getCenterY()/35 == height-1) {
                answer[1] = stone;
            }
        }

        return answer;
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
               /* if(!(x + 1 >= width) && !(y + 1 >= height)) {
                    lookForGroup(x + 1, y + 1, result, alreadyTraversed);
                }
                if(!(x + 1 >= width) && !(y - 1 < 0)) {
                    lookForGroup(x + 1, y - 1, result, alreadyTraversed);
                }
                if(!(x - 1 < 0) && !(y + 1 >= height)) {
                    lookForGroup(x - 1, y + 1, result, alreadyTraversed);
                }
                if(!(x - 1 < 0) && !(y - 1 < 0)) {
                    lookForGroup(x - 1, y - 1, result, alreadyTraversed);
                }*/
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
