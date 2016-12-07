package Klient;

/**
 * Created by arek on 12/7/16.
 */
public class ClientState {

    private String playerColor;
    private String currentTurnColor;
    private static ClientState instance = new ClientState();

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
    public static ClientState getInstance() {
        return instance;
    }
}
