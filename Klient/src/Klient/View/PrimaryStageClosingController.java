package Klient.View;

import Klient.ClientPrintWriter;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import java.io.PrintWriter;

/**
 * Created by arek on 12/9/16.
 */
public class PrimaryStageClosingController implements EventHandler<WindowEvent> {

    private PrintWriter out = ClientPrintWriter.getInstance().getPrintWriter();

    @Override
    public void handle(WindowEvent event) {
        //Platform.exit();
        out.println("QUIT");
        System.exit(1);
    }
}
