package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;


public class MultiplayerController {

    Stage stage;
    AnchorPane root;
    Scene scene;
    @FXML
    TextField address;
    static String ad="";

    public static double mode = 0D;

    @FXML
    public void initialize()  {
        address.setText("172.16.15.19");
    }

    public void create_server(ActionEvent event)  {
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

    public void connect_to_server(ActionEvent event)  {
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
