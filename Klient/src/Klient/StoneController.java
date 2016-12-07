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
            if(clientState.getCurrentTurnColor() == clientState.getPlayerColor()) {
                ((Stone) object).setOpacity(1);
                ((Stone) object).setVisible(true);
                if(clientState.getPlayerColor() == "WHITE") {
                    ((Stone) object).setFill(Color.WHITE);
                }
                else {
                    ((Stone) object).setFill(Color.BLACK);
                }
                StoneLocation location = StoneLocationParser.parseStoneLocation((int)((Stone) object).getCenterX(), (int)((Stone) object).getCenterY());
                System.out.println(location.getX() + location.getY());
                out.println(location.getX() + location.getY());
            }
        }
    }
}
