package Klient;

import Klient.View.Stone;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

import java.io.PrintWriter;

/**
 * Created by arek on 12/7/16.
 */
public class StoneController implements EventHandler<MouseEvent> {

    private PrintWriter out;
    private ClientState clientState = ClientState.getInstance();

    public StoneController(PrintWriter out) {
        this.out = out;
    }
    @Override
    public void handle(MouseEvent event) {
        Object object = event.getSource();
        if(object instanceof Stone) {
            if (((Stone) object).getFill().equals(Color.AZURE)) {
                if (clientState.getCurrentTurnColor().equals(clientState.getPlayerColor())) {


                /*if(clientState.getPlayerColor() == "WHITE") {
                    System.out.println("Bylem tu white");
                    ((Stone) object).setOpacity(1);
                    ((Stone) object).setVisible(true);
                    ((Stone) object).setFill(Color.WHITE);
                }
                else {
                    System.out.println("Bylem tu black");
                    ((Stone) object).setOpacity(1);
                    ((Stone) object).setVisible(true);
                    ((Stone) object).setFill(Color.BLACK);
                }*/
                    StoneLocation location = StoneLocationParser.parseStoneLocation((int) ((Stone) object).getCenterX(), (int) ((Stone) object).getCenterY());
                    //System.out.println(location.getX() + location.getY());
                    out.println("MOVE-" + location.getX() + location.getY());
                }
            }
        }
    }
}
