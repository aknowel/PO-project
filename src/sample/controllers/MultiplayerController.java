package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Locale;
import java.net.*;
import java.io.*;

public class MultiplayerController {
    Stage stage;
    AnchorPane root;
    Scene scene;
    StringBuilder typed_address;

    public void create_server(ActionEvent event) throws IOException {
        Runnable serverThread = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(23456);
                System.out.println("Server created");
                Socket server = serverSocket.accept();
                System.out.println("socket accepted");

                DataInputStream in = new DataInputStream(server.getInputStream());
                DataOutputStream out = new DataOutputStream(server.getOutputStream());

                System.out.println(in.readUTF());
                out.writeUTF("teraz ja coś mówię");

                System.out.println("server exited");
            } catch (IOException e) {
                System.out.println(e);
            }
        };
        Thread server_thread = new Thread(serverThread);
        server_thread.start();
    }
    public void connect_to_server(ActionEvent event) throws IOException {
        Socket client = new Socket("localhost", 23456);
        System.out.println("client created");

        DataInputStream in = new DataInputStream(client.getInputStream());
        DataOutputStream out = new DataOutputStream(client.getOutputStream());

        out.writeUTF("hello here");
        System.out.println(in.readUTF());

        System.out.println("client exited");
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
}
