package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.*;

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
    @FXML
    TextField address;
    Alert alert;

    private final String style = "-fx-effect: dropshadow(gaussian, rgba(229, 3, 0, 1), 25, 0.5, 0.0, 0.0);";
    public static double mode = 0D;
    private static int chosenHero = 2;

    @FXML
    public void initialize() throws FileNotFoundException {
        address.setText("172.16.15.19");
    }

    public void create_server(ActionEvent event) throws IOException {
        Pane board = new Pane();
        Game main = new Game(board, mode, 0, 10, chosenHero);

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
                    main.gameState.writeStaticElementsToStream(out);
                }
            } catch (Throwable e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error! Cannot connect create server! Try again!");
                alert.showAndWait();
                System.out.println(e);
            }
        };
        Thread server_thread = new Thread(serverThread);
        server_thread.start();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main.play(stage);
    }

    public void connect_to_server(ActionEvent event) throws IOException {
        try {
            Socket socket = new Socket(address.getText(), 23456);
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
            GameClient main = new GameClient(pane, gameState, server);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main.play(stage);
        } catch (Throwable e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error! Cannot connect to server! Try again!");
            alert.showAndWait();
            e.printStackTrace();
        }
    }

    public void key_typed() {
        System.out.println("key_typed");
    }

    public void returnMenu(ActionEvent event) {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/menu.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Menu");
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.show();
    }
}
