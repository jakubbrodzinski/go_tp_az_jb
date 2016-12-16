package Klient.View.ConnectionPanel;

import Klient.ClientPrintWriter;
import Klient.ClientState;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.PrintWriter;

/**
 * Created by arek on 12/7/16.
 * Main controller class for Connection Box that handles MouseEvents performed on RadioBox buttons.
 */
public class ConnectionPanelController implements EventHandler<MouseEvent> {
    /**
     * PrintWriter that sends signals to server with connection options: New Game/Join Game/Create game with bot
     */
    private PrintWriter out = ClientPrintWriter.getInstance().getPrintWriter();

    @Override
    public void handle(MouseEvent event) {
        Object object = event.getSource();
        if(object instanceof Button) {
            if(((Button) object).getParent() instanceof ConnectionHBox)
                if(((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getToggledButton() == "NewGame") {
                    if(((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getSizeInputContent().equals("9") || ((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getSizeInputContent().equals("13") || ((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getSizeInputContent().equals("19")) {
                        out.println("CREATE-" + ((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getSizeInputContent());
                        ClientState.getInstance().setSize(((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getSizeInputContent());
                        ((Button) object).getScene().getWindow().hide();
                    }
                    else if(((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getSizeInputContent().startsWith("CREATE")) {
                        out.println(((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getSizeInputContent());
                        ClientState.getInstance().setSize(((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getSizeInputContent().substring(7,9));
                        ((Button) object).getScene().getWindow().hide();
                    }
                }
                else {
                    if(!((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getTableInputContent().equals("")) {
                        out.println("JOIN-" + ((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getTableInputContent());
                        ((Button) object).getScene().getWindow().hide();
                    }
                }
        }
    }
}
