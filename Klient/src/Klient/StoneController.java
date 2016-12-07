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
    private String clientState;

    public StoneController(String clientState, PrintWriter out) {
        this.out = out;
        this.clientState = clientState;
    }
    @Override
    public void handle(MouseEvent event) {
        Object object = event.getSource();
        if(object instanceof Stone) {
            ((Stone) object).setOpacity(1);
            ((Stone) object).setVisible(true);
            ((Stone) object).setFill(Color.WHITE);
            System.out.println(object);
        }
    }
}
