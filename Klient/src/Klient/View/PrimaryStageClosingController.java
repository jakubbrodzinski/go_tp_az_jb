package Klient.View;

import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.stage.WindowEvent;

import java.io.PrintWriter;

/**
 * Created by arek on 12/9/16.
 */
public class PrimaryStageClosingController implements EventHandler<WindowEvent> {

    private PrintWriter out;

    public PrimaryStageClosingController(PrintWriter out) {
        this.out = out;
    }
    @Override
    public void handle(WindowEvent event) {
        out.println("QUIT");

    }
}
