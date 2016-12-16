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
 * Main controller class for PlayerPanel, it stores the FXML methods that were added to the Scene Builder tool
 */
public class PlayerPanelController {
    /**
     * Method that changes opacity of Pane with white score, when mouse entered
     * @param event
     */
    @FXML
    private void setOpacityOnEnteredWhite(MouseEvent event) {
        Node myNode = (Node)event.getSource();
        myNode.setOpacity(0.8);
        getLabel((Parent)myNode).setText(ClientState.getInstance().getWhitePoints());
    }

    /**
     * Method that changes opacity of Pane with black score, when mouse entered
     * @param event
     */
    @FXML
    private void setOpacityOnEnteredBlack(MouseEvent event) {
        Node myNode = (Node)event.getSource();
        myNode.setOpacity(0.8);
        getLabel((Parent)myNode).setText(ClientState.getInstance().getBlackPoints());
    }

    /**
     * Method that changes opacity to normal after the mouse is off the Pane with scores (default opacity is 0 for Panes with scores)
     * @param event
     */
    @FXML
    private void setNormalOpacity(MouseEvent event) {
        Node myNode = (Node)event.getSource();
        myNode.setOpacity(0);
    }

    /**
     * Method that passes PASS signal to server, when "Pasuj" Button is clicked
     * @param event
     */
    @FXML
    private void invokePass(MouseEvent event) {
        //Cannot pass, when gamee is paused (meaning that pass has already been triggered)
        if(ClientState.getInstance().getIsPaused().equals("NOTPAUSED")) {
            if (ClientState.getInstance().getCurrentTurnColor().equals(ClientState.getInstance().getPlayerColor())) {
                ClientPrintWriter.getInstance().getPrintWriter().println("PASS");
            }
        }
    }

    /**
     * Method that passes CONCEDE signal to server, when "Poddaj się" Button is clicked
     * @param event
     */
    @FXML
    private void invokeConcede(MouseEvent event){
        if(ClientState.getInstance().getCurrentTurnColor().equals(ClientState.getInstance().getPlayerColor())) {

            ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setHeaderText("KONIEC GRY");
            alert.setContentText("Poddałeś się!");
            alert.showAndWait();
            ClientPrintWriter.getInstance().getPrintWriter().println("CONCEDE");
            Platform.exit();
            System.exit(1);
        }
    }

    /**
     * Method that looks for Labels in the Panes, used for locating the label with points
     * @param myNode Node to be searched
     * @return Label with points
     */
    private Label getLabel(Parent myNode) {
        for(Node n : myNode.getChildrenUnmodifiable()) {
            if(n instanceof Label) {
                return (Label)n;
            }
        }
        return null;
    }
}
