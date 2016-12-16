package Klient.View;

/**
 * Created by arek on 12/11/16.
 * Interface that states which methods are needed for the Application to be fully operational
 */
public interface GameBoardInterface {
    /**
     * Method that redraws the board every time a "CHANGE" or "CORRECT" signal is received from the server
     * @param command Signal from the server
     */
    void redraw(String command);

    /**
     * Method that redraws the board every time the game is paused and the territories and dead stones are being chosen
     * @param command Signal from the server
     */
    void redrawTerritories(String command);

    /**
     * Method that changes the visual effect of the board, that indicates that it is user's turn
     */
    void changePlayerEffectOn();

    /**
     * Method that changes the visual effect of the board, that indicates that it is not user's turn
     */
    void changePlayerEffectOff();

    /**
     * Method that backups the board when the game is paused and the territories and dead stones are chosen
     */
    void backupBoard();

    /**
     * Method that restores the board to the state from before the PAUSED state
     */
    void restoreBoard();
}
