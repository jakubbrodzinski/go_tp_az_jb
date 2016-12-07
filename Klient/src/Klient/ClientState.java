package Klient;

/**
 * Created by arek on 12/7/16.
 */
public class ClientState {

    private String playerColor;
    private String currentTurn;
    private static ClientState instance = new ClientState();

    private ClientState() {};

    public void setPlayerColor(String playerColor) {
        this.playerColor = playerColor;
    }
    public void setCurrentTurn(String currentTurn) {
        this.currentTurn = currentTurn;
    }
    public String getPlayerColor() {
        return playerColor;
    }
    public String getCurrentTurn() {
        return currentTurn;
    }
    public static ClientState getInstance() {
        return instance;
    }
}
