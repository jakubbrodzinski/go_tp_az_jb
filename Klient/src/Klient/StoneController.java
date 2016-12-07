package Klient;

import Klient.View.Stone;
import javafx.event.EventHandler;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

/**
 * Created by arek on 12/7/16.
 */
public class StoneController implements EventHandler<MouseEvent> {
    @Override
    public void handle(MouseEvent event) {
        Object object = event.getSource();
        System.out.println(object);
        if(object instanceof Stone) {
            ((Stone) object).setOpacity(1);
            ((Stone) object).setVisible(true);
            ((Stone) object).setFill(Color.WHITE);
        }
    }
}
