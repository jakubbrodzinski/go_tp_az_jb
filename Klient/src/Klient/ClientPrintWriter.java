package Klient;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by arek on 12/8/16.
 */
public class ClientPrintWriter {

    private PrintWriter out;
    private static ClientPrintWriter instance = new ClientPrintWriter();

    public ClientPrintWriter(){};

    public  void setInstance(OutputStream outputStream, boolean autoflush) {
        this.out = new PrintWriter(outputStream, autoflush);
    }
    public PrintWriter getPrintWriter() {
        return this.out;
    }
    public static ClientPrintWriter getInstance() {
        return instance;
    }
}
