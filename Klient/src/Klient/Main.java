package Klient;

import Klient.View.ClientScene;
import Klient.View.ConnectionBoxScene;
import Klient.View.ConnectionPanel.ConnectionBoxClosingController;
import Klient.View.MainBoard;
import Klient.View.PrimaryStageClosingController;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.layout.*;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Optional;

public class Main extends Application {

    private BufferedReader in;
    //TODO jeżeli się nie przpda bedzie mozna wyrzucic, bo w clientprintwriter singletonie jest
    private PrintWriter out;
    private Socket socket;
    private ArrayList<Node> mylist;

    @Override
    public void start(Stage primaryStage) throws Exception{

        connectToServer();

        Stage otherStage;

        FlowPane rooto = new FlowPane();
        rooto.setStyle("-fx-background-color: #EFECCA");
        otherStage = new Stage();
        otherStage.setTitle("GoGAME");
        otherStage.setScene(new Scene(rooto, 1266, 768));
        otherStage.show();

        Stage initialStage = new Stage();
        GridPane initialRoot = new GridPane();
        initialRoot.setAlignment(Pos.CENTER);
        initialStage.setTitle("Połączenie z serwerem");
        initialStage.setScene(new ConnectionBoxScene(initialRoot, 600, 350));
        initialStage.initOwner(primaryStage);
        initialStage.initModality(Modality.APPLICATION_MODAL);
        initialStage.setOnCloseRequest(new ConnectionBoxClosingController());
        initialStage.showAndWait();


        String response;
        response = in.readLine();
        String[] initialCommand = response.split("-");
        ClientState.getInstance().setPlayerColor(initialCommand[3]);
        ClientState.getInstance().setCurrentTurnColor("BLACK");
        ClientState.getInstance().setSize(initialCommand[4]);
        ClientState.getInstance().setWhitePoints(initialCommand[1]);
        ClientState.getInstance().setBlackPoints(initialCommand[2]);

        FlowPane root = new FlowPane();
        root.setAlignment(Pos.TOP_RIGHT);
        primaryStage.setTitle("GoGAME " + initialCommand[5]);
        primaryStage.show();
        primaryStage.setScene(new ClientScene(root, 1266, 768));
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(new PrimaryStageClosingController());
        otherStage.hide();

        mylist = getAllBoards(root);

        play();

    }
    @Override
    public void stop() {
        try {
            socket.close();
        }
        catch(IOException ex) {
            System.out.println("Cannot close socket");
        }
    }
    private void connectToServer() throws IOException {
        socket = new Socket("localhost", 8901);
        in = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        ClientPrintWriter.getInstance().setInstance(socket.getOutputStream(), true);
        //TODO Refactor code -> Decouple the code by removing "out" passing
        out = ClientPrintWriter.getInstance().getPrintWriter();
    }
    private void play() throws IOException {

        if(!ClientState.getInstance().getPlayerColor().equals(ClientState.getInstance().getCurrentTurnColor()))
            ((MainBoard) mylist.get(0)).changePlayerEffectOff();
        new Thread(new Runnable() {

            @Override
            public void run() {
                try {

                    while(true) {
                        final String responseInner = in.readLine();
                        System.out.println(responseInner);
                        if(responseInner.startsWith("QUIT")) {
                            Platform.exit();
                            System.exit(1);
                            break;
                        }

                        Platform.runLater(new Runnable() {
                            @Override
                            public void run() {
                                if(responseInner.contains("CHANGE") || responseInner.contains("CORRECT")) {
                                    ((MainBoard) mylist.get(0)).redraw(responseInner);
                                    ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
                                    if(responseInner.contains("CHANGE"))
                                        ((MainBoard) mylist.get(0)).changePlayerEffectOn();
                                    else
                                        ((MainBoard) mylist.get(0)).changePlayerEffectOff();
                                }
                                else if(responseInner.contains("WRONG")) {
                                    //Do nothing
                                }
                                else if(responseInner.contains("QUIT")) {
                                    Platform.exit();
                                    System.exit(1);
                                }
                                else if(responseInner.equals("PASS")) {
                                    if(ClientState.getInstance().getPlayerColor().equals(ClientState.getInstance().getCurrentTurnColor())) {
                                        ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
                                        ((MainBoard) mylist.get(0)).changePlayerEffectOff();
                                        //((MainBoard) mylist.get(0)).findTerritory();
                                    }
                                    else {
                                        ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
                                        ((MainBoard) mylist.get(0)).changePlayerEffectOn();
                                        //((MainBoard) mylist.get(0)).findTerritory();
                                    }
                                }
                                else if(responseInner.startsWith("BLACK")) {
                                    ((MainBoard) mylist.get(0)).backupBoard();
                                    ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
                                    ClientState.getInstance().setIsPaused("PAUSED");
                                    ((MainBoard) mylist.get(0)).redrawTerritories(responseInner);
                                    if(ClientState.getInstance().getCurrentTurnColor().equals(ClientState.getInstance().getPlayerColor())) {
                                        ((MainBoard) mylist.get(0)).changePlayerEffectOn();
                                        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
                                        alert.initModality(Modality.NONE);
                                        alert.setTitle("GRA ZATRZYMANA");
                                        alert.setHeaderText("Wybierz pola, które chcesz wysłać");
                                        alert.setContentText("LEGENDA");

                                        ButtonType buttonAccept = new ButtonType("Zaproponuj");
                                        ButtonType buttonDeny = new ButtonType("Odrzuć");
                                        alert.getButtonTypes().setAll(buttonAccept, buttonDeny);

                                        Optional<ButtonType> result = alert.showAndWait();
                                        if(result.get() == buttonAccept) {
                                            out.println(((MainBoard) mylist.get(0)).parseTerritories());
                                            ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
                                            ((MainBoard) mylist.get(0)).changePlayerEffectOff();
                                        }
                                        else if(result.get() == buttonDeny) {
                                            out.println("DENY");
                                        }

                                    }
                                    else {
                                        ((MainBoard) mylist.get(0)).changePlayerEffectOff();
                                    }
                                }
                                else if(responseInner.startsWith("PROPOSITION")) {
                                    ClientState.getInstance().setCurrentTurnColor(ClientState.getInstance().changePlayers());
                                    ((MainBoard) mylist.get(0)).changePlayerEffectOn();
                                }
                                else if(responseInner.startsWith("CONCEDE")) {
                                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                    alert.setTitle("KONIEC GRY");
                                    alert.setHeaderText("Przeciwnik się poddał!");
                                    alert.setContentText("Wygrana!");
                                    alert.showAndWait();
                                    Platform.exit();
                                    System.exit(1);
                                }
                                else if(responseInner.startsWith("POINTS")) {
                                    String[] helper = responseInner.split("-");
                                    ClientState.getInstance().setWhitePoints(helper[1]);
                                    ClientState.getInstance().setBlackPoints(helper[2]);
                                }
                            }
                        });
                    }
                }
                catch(IOException exception) {
                    System.out.println("UNEXPECTED SERVER REPONSE");
                }
            }

        }).start();

    }
    public static ArrayList<Node> getAllBoards(Parent root) {
        ArrayList<Node> nodes = new ArrayList<Node>();
        addAllBoards(root, nodes);
        return nodes;
    }

    private static void addAllBoards(Parent parent, ArrayList<Node> nodes) {
        for (Node node : parent.getChildrenUnmodifiable()) {
            if(node instanceof MainBoard)
                nodes.add(node);
            if (node instanceof Parent) {
                addAllBoards((Parent) node, nodes);
            }
        }
    }


    public static void main(String[] args) {
        launch(args);
    }
}
