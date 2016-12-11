package Klient.View;

import Klient.ClientPrintWriter;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import java.io.PrintWriter;

/**
 * Created by arek on 12/9/16.
 * Application controller that handles the main window closing
 */
public class PrimaryStageClosingController implements EventHandler<WindowEvent> {

    /**
     * PrintWriter that sends the QUIT signal to server when the client is being closed
     */
    private PrintWriter out = ClientPrintWriter.getInstance().getPrintWriter();

    @Override
    public void handle(WindowEvent event) {
        out.println("QUIT");
        System.exit(1);
    }
}
