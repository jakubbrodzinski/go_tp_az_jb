package Klient.View;

/**
 * Created by arek on 12/11/16.
 */
public interface GameBoardInterface {
    void redraw(String command);
    void redrawTerritories(String command);
    void changePlayerEffectOn();
    void changePlayerEffectOff();
    void backupBoard();
    void restoreBoard();
}
