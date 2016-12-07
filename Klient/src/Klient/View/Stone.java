package Klient.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Created by arek on 12/4/16.
 */
public class Stone extends Circle {
    public Stone(double x, double y) {
        super(x, y, 15);
        this.setOpacity(0);
    }
}
