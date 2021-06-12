/*package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.*;

import java.util.Locale;
import java.net.*;
import java.io.*;

public class MultiplayerController {
    @FXML
    AnchorPane pane;
    @FXML
    StackPane hero1;
    @FXML
    StackPane hero2;
    @FXML
    StackPane hero3;

    Stage stage;
    AnchorPane root;
    Scene scene;
    StringBuilder typed_address;

    private final String style="-fx-effect: dropshadow(gaussian, rgba(229, 3, 0, 1), 25, 0.5, 0.0, 0.0);";
    public static double mode=0D;
    private static int chosenHero=2;

    public void create_server(ActionEvent event) throws IOException {
        Server.serverCreated = true;
        Pane pane = new Pane();
        GameState gameState = new GameState();
        GameAsServer main = new GameAsServer(pane, gameState);

        Runnable serverThread = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(23456);
                System.out.println("Server created");
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("socket accepted");

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    Client client = new Client();
                    client.socket = socket;
                    client.in = in;
                    client.out = out;

                    main.clients.add(client);
                    gameState.writeStaticElementsToStream(out);
                }
            } catch (IOException e) {
                System.out.println(e);
            }
        };
        Thread server_thread = new Thread(serverThread);
        server_thread.start();

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        main.play(stage);
    }
    public void connect_to_server(ActionEvent event) throws IOException {
        Socket socket = new Socket("172.16.15.19", 23456);
        System.out.println("client created");

        DataInputStream in = new DataInputStream(socket.getInputStream());
        DataOutputStream out = new DataOutputStream(socket.getOutputStream());

        Server server = new Server();
        server.socket = socket;
        server.in = in;
        server.out = out;

        Pane pane = new Pane();
        GameState gameState = new GameState();
        gameState.loadStaticElementsFromStream(in);
        GameAsClient main = new GameAsClient(pane, gameState, server);

        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        main.play(stage);
    }
    public void key_typed() {
        System.out.println("key_typed");
    }
    public void returnMenu(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/menu.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stage.show();
    }
}*/
