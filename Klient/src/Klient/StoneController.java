package Klient;

import Klient.View.MainBoard;
import Klient.View.Stone;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.PrintWriter;

/**
 * Created by arek on 12/7/16.
 * Core application controller class. It operates on stones and uses them in a different way depending on the current client state.
 */
public class StoneController implements EventHandler<MouseEvent> {

    /**
     * PrintWriter that sends signals to the server
     */
    private PrintWriter out = ClientPrintWriter.getInstance().getPrintWriter();
    /**
     * Variable storing clientState = interexchangeable with simple ClientState.getInstance()
     */
    private ClientState clientState = ClientState.getInstance();


    @Override
    public void handle(MouseEvent event) {
        Object object = event.getSource();
        if(object instanceof Stone) {
            if (!ClientState.getInstance().getIsPaused().equals("PAUSED")) {
                if (((Stone) object).getFill().equals(Color.AZURE)) {
                    if (clientState.getCurrentTurnColor().equals(clientState.getPlayerColor())) {
                        StoneLocation location = StoneLocationParser.parseStoneLocation((int) ((Stone) object).getCenterX(), (int) ((Stone) object).getCenterY());
                        System.out.println("PORUSZONO: " + location.getX() + location.getY());
                        out.println("MOVE-" + location.getX() + location.getY());
                    }
                }
            }
            else {
                if (ClientState.getInstance().getCurrentTurnColor().equals(ClientState.getInstance().getPlayerColor())) {
                    if (ClientState.getInstance().getPlayerColor().equals("WHITE")) {
                        if (((Stone) object).getFill().equals(Color.BLACK)) {

                            for(Stone stone : ((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35)) {
                                ((MainBoard) ((Stone) object).getParent()).getDeadStonesBlack().add(stone);

                            }

                            ((MainBoard) ((Stone) object).getParent()).colorGroup(((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35), Color.DARKRED);

                        } else if (((Stone) object).getFill().equals(Color.DARKRED)) {
                            for(Stone stone : ((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35)) {
                                ((MainBoard) ((Stone) object).getParent()).getDeadStonesBlack().remove(stone);
                            }
                            ((MainBoard) ((Stone) object).getParent()).colorGroup(((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35), Color.BLACK);

                        } else if (((Stone) object).getFill().equals(Color.AZURE)) {
                            ((MainBoard) ((Stone) object).getParent()).getWhiteTerritory().add((Stone) object);
                            ((Stone) object).setFill(Color.WHITESMOKE);
                            ((Stone) object).setOpacity(0.6);
                        } else if (((Stone) object).getFill().equals(Color.WHITESMOKE)) {
                            ((MainBoard) ((Stone) object).getParent()).getWhiteTerritory().remove((Stone) object);
                            ((Stone) object).setFill(Color.AZURE);
                            ((Stone) object).setOpacity(0);
                        }
                    } else if (ClientState.getInstance().getPlayerColor().equals("BLACK")) {
                        if (((Stone) object).getFill().equals(Color.WHITE)) {
                            for(Stone stone : ((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35)) {
                                ((MainBoard) ((Stone) object).getParent()).getDeadStonesWhite().add(stone);
                            }
                            ((MainBoard) ((Stone) object).getParent()).colorGroup(((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35), Color.RED);
                        } else if (((Stone) object).getFill().equals(Color.RED)) {
                            for(Stone stone : ((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35)) {
                                ((MainBoard) ((Stone) object).getParent()).getDeadStonesWhite().remove(stone);
                            }
                            ((MainBoard) ((Stone) object).getParent()).colorGroup(((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35), Color.WHITE);
                        } else if (((Stone) object).getFill().equals(Color.AZURE)) {
                            ((MainBoard) ((Stone) object).getParent()).getBlackTerritory().add((Stone) object);
                            ((Stone) object).setFill(Color.GRAY);
                            ((Stone) object).setOpacity(0.6);
                        } else if (((Stone) object).getFill().equals(Color.GRAY)) {
                            ((MainBoard) ((Stone) object).getParent()).getBlackTerritory().remove((Stone) object);
                            ((Stone) object).setFill(Color.AZURE);
                            ((Stone) object).setOpacity(0);
                        }
                    }
                }
            }
        }
    }
}
