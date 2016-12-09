package Klient.View.ConnectionPanel;

import Klient.ClientState;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;

import java.io.PrintWriter;

/**
 * Created by arek on 12/7/16.
 */
public class ConnectionPanelController implements EventHandler<MouseEvent> {
    private PrintWriter out;
    public ConnectionPanelController(PrintWriter out) {
        this.out = out;
    }
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
