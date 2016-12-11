package Klient.View;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;


/**
 * Created by arek on 12/4/16.
 * Class of the stone. It is main component of the board. It can be either AZURE, BLACK, WHITE, DARKRED, RED, GRAY or WHITESMOKE
 * depending on the current Client state.
 * AZURE means it belongs to no one
 * BLACK means it belongs to black player
 * WHITE means it belongs to white player
 * DARKRED means it is black dead stone
 * RED means it is white dead stone
 * GRAY means it is black territory
 * WHITESMOKE mean it is white territory
 */
public class Stone extends Circle {
    public Stone(double x, double y) {
        super(x, y, 15);
        this.setOpacity(0);
        this.setFill(Color.AZURE);
    }
}
