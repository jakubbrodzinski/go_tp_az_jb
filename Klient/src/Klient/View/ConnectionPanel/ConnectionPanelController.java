package Klient.View.ConnectionPanel;

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
                    out.println("CREATE-"+"13");
                    ((Button) object).getScene().getWindow().hide();
                }
                else {
                    out.println("JOIN-"+((ConnectionHBox) ((Button) object).getParent()).getRadioBox().getTextFieldContent());
                    ((Button) object).getScene().getWindow().hide();
                }
        }
    }
}
