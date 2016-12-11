package Klient;

import java.io.OutputStream;
import java.io.PrintWriter;

/**
 * Created by arek on 12/8/16.
 * Singleton class for the PrintWriter class that sends singals to the servers
 */
public class ClientPrintWriter {

    /**
     * The main PrintWriter variable that is being used in the whole application
     */
    private PrintWriter out;
    /**
     * Initializing the global instance of the singleton class
     */
    private static ClientPrintWriter instance = new ClientPrintWriter();

    private ClientPrintWriter(){};

    /**
     * Setting the PrintWriter of the singleton class
     * @param outputStream PrintWriter demanded argument
     * @param autoflush PrintWritter demanded argumet
     */
    public  void setInstance(OutputStream outputStream, boolean autoflush) {
        this.out = new PrintWriter(outputStream, autoflush);
    }

    /**
     * Accessor for the singleton class PrintWriter
     * @return PrintWriter of the singleton class
     */
    public PrintWriter getPrintWriter() {
        return this.out;
    }

    /**
     * Accessor for the singleton class instance
     * @return Class instance
     */
    public static ClientPrintWriter getInstance() {
        return instance;
    }
}
