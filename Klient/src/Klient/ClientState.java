package Klient;

import javafx.scene.paint.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.awt.*;


/**
 * Created by arek on 12/7/16.
 */
public class ClientState {

    private String playerColor;
    private String currentTurnColor;
    private int size;
    private static ClientState instance = new ClientState();
    private String whitePoints;
    private String blackPoints;

    private ClientState() {};

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
    public void setCurrentTurnColor(String currentTurn) {
        this.currentTurnColor = currentTurn;
    }
    public String getPlayerColor() {
        return playerColor;
    }
    public String getCurrentTurnColor() {
        return currentTurnColor;
    }
    public Paint getPlayerColorPaint() {
        if(playerColor == "WHITE") {
            return Color.WHITE;
        }
        else {
            return Color.BLACK;
        }
    }
    public Paint getCurrentColorPlaying() {
        if(currentTurnColor == "WHITE") {
            return Color.WHITE;
        }
        else {
            return Color.BLACK;
        }
    }
    public String changePlayers() {
        if(currentTurnColor.equals("WHITE")) {
            return "BLACK";
        }
        else {
            return "WHITE";
        }
    }
    public void setSize(String command) {
        if(command.startsWith("9")) {
            size = 9;
        }
        else if(command.startsWith("13")) {
            size = 13;
        }
        else if(command.startsWith("19")) {
            size = 19;
        }
    }
    public int getSize() {
        return size;
    }
    //POINTS-punktybialych-punktyczarnych
    public void setWhitePoints(String points) {
        whitePoints = points;
    }
    public void setBlackPoints(String points) {
        blackPoints = points;
    }
    public String getWhitePoints() {
        return whitePoints;
    }
    public String getBlackPoints() {
        return blackPoints;
    }
    public static ClientState getInstance() {
        return instance;
    }
}
