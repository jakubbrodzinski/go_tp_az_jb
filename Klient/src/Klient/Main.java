package Klient;

import Klient.View.ClientScene;
import Klient.View.ConnectionBoxScene;
import Klient.View.ConnectionPanel.ConnectionBoxClosingController;
import Klient.View.MainBoard;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.collections.ObservableList;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;

public class Main extends Application {

    private BufferedReader in;
    private PrintWriter out;
    private ArrayList<Node> mylist;

    @Override
    public void start(Stage primaryStage) throws Exception{

        connectToServer();
        FlowPane root = new FlowPane();
        root.setAlignment(Pos.TOP_RIGHT);
        primaryStage.setTitle("GoGAME");
        primaryStage.setScene(new ClientScene(root, 1266, 768, out));
        primaryStage.setResizable(false);
        primaryStage.show();



        Stage initialStage = new Stage();
        GridPane initialRoot = new GridPane();
        initialRoot.setAlignment(Pos.CENTER);
        initialStage.setTitle("Połączenie z serwerem");
        initialStage.setScene(new ConnectionBoxScene(initialRoot, 600, 350, out));
        initialStage.initOwner(primaryStage);
        initialStage.initModality(Modality.APPLICATION_MODAL);
        initialStage.setOnCloseRequest(new ConnectionBoxClosingController());
        initialStage.showAndWait();

        mylist = getAllNodes(root);

        play();

    }
    public void connectToServer() throws IOException {
        Socket socket = new Socket("localhost", 8901);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        out = new PrintWriter(socket.getOutputStream(), true);
        //ClientPrintWriter mywriter = ClientPrintWriter.getInstance();
        //out = mywriter.getPrintWriter();
    }
    public void play() throws IOException {
        String response;
        ClientState state = ClientState.getInstance();
        response = in.readLine();
        state.setPlayerColor(response.substring(0,5));
        state.setCurrentTurnColor("BLACK");
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {
                    while(true) {
                        final String responseInner = in.readLine();
                        System.out.println(responseInner);
                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                ((MainBoard) mylist.get(0)).redraw(responseInner);
                                ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
                                //System.out.println("Currentkolor to: " + ClientState.getInstance().getCurrentTurnColor());
                                //System.out.println("getPLayercolor to: " + ClientState.getInstance().getPlayerColor());
                            }
                        });
                    }
                }
                catch(IOException exception) {
                    System.out.println("ZLASALXASD");
                }
            }

        }).start();

    }
    public static ArrayList<Node> getAllNodes(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllDescendents(root, nodes);
        return nodes;
    }

    private static void addAllDescendents(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if(node instanceof MainBoard)
            nodes.add(node);
            if (node instanceof Parent)
                addAllDescendents((Parent)node, nodes);
        }
    }
    private static MainBoard search(Parent parent) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if(node instanceof MainBoard)
                return (MainBoard) node;
        }
        return null;
    }


    public static void main(String[] args) {
        launch(args);
    }
}
