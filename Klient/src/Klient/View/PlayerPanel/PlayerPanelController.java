package Klient.View.PlayerPanel;

import Klient.ClientPrintWriter;
import Klient.ClientState;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

/**
 * Created by arek on 12/3/16.
 */
public class PlayerPanelController {
    @FXML
    private void setOpacityOnEnteredWhite(MouseEvent event) {
        Node myNode = (Node)event.getSource();
        myNode.setOpacity(0.8);
        //getLabel((Parent)myNode).setText(ClientState.getInstance().getWhitePoints());
        getLabel((Parent)myNode).setText("10");
    }
    @FXML
    private void setOpacityOnEnteredBlack(MouseEvent event) {
        Node myNode = (Node)event.getSource();
        myNode.setOpacity(0.8);
        //getLabel((Parent)myNode).setText(ClientState.getInstance().getWhitePoints());
        getLabel((Parent)myNode).setText("15");
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
            Platform.exit();
            System.exit(1);
        }
    }
    private Label getLabel(Parent myNode) {
        for(Node n : myNode.getChildrenUnmodifiable()) {
            if(n instanceof Label) {
                return (Label)n;
            }
        }
        return null;
    }
}
