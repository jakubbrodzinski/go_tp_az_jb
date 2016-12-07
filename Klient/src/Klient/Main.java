package Klient;

import Klient.View.ClientScene;
import Klient.View.ConnectionBoxScene;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

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


        Stage initialStage = new Stage();
        GridPane initialRoot = new GridPane();
        initialRoot.setAlignment(Pos.CENTER);
        initialStage.setTitle("Połączenie z serwerem");
        initialStage.setScene(new ConnectionBoxScene(initialRoot, 600, 350));
        initialStage.initOwner(primaryStage);
        initialStage.initModality(Modality.APPLICATION_MODAL);
        initialStage.show();
        //initialStage.close();
        //System.out.println(root.getChildren());
        connectToServer();
        play();

    }
    public void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 8901);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
    }
    public void play() throws IOException{
        String response;
        while(true) {
            response = in.readLine();
            System.out.println(response);
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
