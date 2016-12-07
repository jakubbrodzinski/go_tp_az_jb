package Klient;

import Klient.View.ClientScene;
import Klient.View.ConnectionBoxScene;
import Klient.View.ConnectionPanel.ConnectionBoxClosingController;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;

public class Main extends Application {

    private BufferedReader in;
    private PrintWriter out;

    @Override
    public void start(Stage primaryStage) throws Exception{

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.TOP_RIGHT);
        primaryStage.setTitle("GoGAME");
        primaryStage.setScene(new ClientScene(root, 1266, 768));
        primaryStage.setResizable(false);
        primaryStage.show();

        connectToServer();

        Stage initialStage = new Stage();
        GridPane initialRoot = new GridPane();
        initialRoot.setAlignment(Pos.CENTER);
        initialStage.setTitle("Połączenie z serwerem");
        initialStage.setScene(new ConnectionBoxScene(initialRoot, 600, 350, out));
        initialStage.initOwner(primaryStage);
        initialStage.initModality(Modality.APPLICATION_MODAL);
        //initialStage.setOnCloseRequest(new ConnectionBoxClosingController());
        initialStage.showAndWait();
        //initialStage.close();
        //System.out.println(root.getChildren());

        //play();

    }
    public void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 8901);
        System.out.println("TU");
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    public void play() throws IOException{
        String response;
        ClientState state = ClientState.getInstance();
        while(true) {
            System.out.println("TU");
            response = in.readLine();
            System.out.println(response);
        }
    }
    public PrintWriter getOutput() {
        return out;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
