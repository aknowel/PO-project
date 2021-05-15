package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Game;

public class MenuController {

    @FXML
    private Stage stage;
    private AnchorPane root;
    private Scene scene;
    public static double mode=0D;
    public void playGame(ActionEvent event)
    {
        Pane board=new Pane();
        Game main=new Game(board,mode);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        main.play(stage);
    }
    public void loadGame(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/loadGame.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Load Game");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stage.show();
    }
    public void selectOptions(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/options.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Options");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stage.show();
    }
    public void selectAchievements(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/achievement.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Achievement");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stage.show();
    }
    public void exitGame(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }

}
