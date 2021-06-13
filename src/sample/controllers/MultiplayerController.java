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
    static String ad="";
    Alert alert;

    private final String style = "-fx-effect: dropshadow(gaussian, rgba(229, 3, 0, 1), 25, 0.5, 0.0, 0.0);";
    public static double mode = 0D;
    private static int chosenHero = 2;
    public static Thread serverThread;

    @FXML
    public void initialize() throws FileNotFoundException {
        address.setText("172.16.15.19");
    }

    public void create_server(ActionEvent event) throws IOException {
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/chooseHero.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Choose Hero");
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.show();
    }

    public void connect_to_server(ActionEvent event) throws IOException {
        ad=address.getText();
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/chooseHero2.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Choose Hero");
        } catch (Exception e) {
            e.printStackTrace();
        }
        stage.show();
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
