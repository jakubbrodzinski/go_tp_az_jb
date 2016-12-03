package Klient.View.PlayerPanel;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;

/**
 * Created by arek on 12/3/16.
 */
public class PlayerPanelController {
    @FXML
    private void setOpacityOnEntered(MouseEvent event) {
        Node myNode = (Node)event.getSource();
        myNode.setOpacity(0.8);
    }
    @FXML
    private void setNormalOpacity(MouseEvent event) {
        Node myNode = (Node)event.getSource();
        myNode.setOpacity(0);
    }
}
