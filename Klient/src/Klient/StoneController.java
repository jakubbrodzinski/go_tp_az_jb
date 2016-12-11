package Klient;

import Klient.View.MainBoard;
import Klient.View.Stone;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.PrintWriter;

/**
 * Created by arek on 12/7/16.
 */
public class StoneController implements EventHandler<MouseEvent> {

    private PrintWriter out = ClientPrintWriter.getInstance().getPrintWriter();
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
                if (((Stone) object).getFill().equals(ClientState.getInstance().getPlayerColorPaint())) {
                    ((MainBoard) ((Stone) object).getParent()).colorGroup(((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35), Color.ROYALBLUE);
                } else if (((Stone) object).getFill().equals(Color.ROYALBLUE)) {
                    ((MainBoard) ((Stone) object).getParent()).colorGroup(((MainBoard) ((Stone) object).getParent()).getStonesGroup((int) ((Stone) object).getCenterX() / 35, (int) ((Stone) object).getCenterY() / 35), ClientState.getInstance().getPlayerColorPaint());
                } else if (((Stone) object).getFill().equals(Color.AZURE)) {
                    ((Stone) object).setFill(Color.RED);
                    ((Stone) object).setOpacity(1);

                } else if (((Stone) object).getFill().equals(Color.RED)) {
                    ((Stone) object).setFill(Color.AZURE);
                    ((Stone) object).setOpacity(0);
                }
            }
        }
    }
}
