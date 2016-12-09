package Klient.View.PlayerPanel;

import Klient.ClientPrintWriter;
import Klient.ClientState;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
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
    @FXML
    private void invokePass(MouseEvent event) {
        System.out.println("Tura : mojakolej " + ClientState.getInstance().getCurrentTurnColor() + ClientState.getInstance().getPlayerColor());
        if(ClientState.getInstance().getCurrentTurnColor().equals(ClientState.getInstance().getPlayerColor())) {
            ClientPrintWriter.getInstance().getPrintWriter().println("PASS");
            ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
        }
    }
    @FXML
    private void invokeConcede(MouseEvent event){
        if(ClientState.getInstance().getCurrentTurnColor().equals(ClientState.getInstance().getPlayerColor())) {
            ClientPrintWriter.getInstance().getPrintWriter().println("CONCEDE");
            ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("KONIEC GRY");
            alert.setContentText("Poddałeś się!");
            alert.showAndWait();
            (((Node)event.getSource())).getScene().getWindow().hide();
            System.exit(1);
        }
    }
}
