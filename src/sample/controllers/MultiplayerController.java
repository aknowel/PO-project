package sample.controllers;


import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.util.Locale;

public class MultiplayerController {
    Stage stage;
    AnchorPane root;
    Scene scene;
    StringBuilder typed_address;

    public void create_server(ActionEvent event) {
        System.out.println("create_server");
    }
    public void connect_to_server(ActionEvent event) {
        System.out.println("connect_to_server");
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
