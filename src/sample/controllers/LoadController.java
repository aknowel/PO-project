package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.time.LocalDateTime;


public class LoadController {
    @FXML
    TextField save1;
    @FXML
    TextField save2;
    @FXML
    TextField save3;
    Stage stage;
    AnchorPane root;
    Scene scene;
    @FXML
    public void initialize()
    {
        save1.setText(LocalDateTime.now().toString().substring(0,19).replace('T',' '));
        save2.setText(LocalDateTime.now().toString().substring(0,19).replace('T',' '));
        save3.setText(LocalDateTime.now().toString().substring(0,19).replace('T',' '));
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
    public void loadSave1()
    {

    }
    public void loadSave2()
    {

    }
    public void loadSave3()
    {

    }
}
