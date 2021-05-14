package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Game;


public class PauseController {
    Stage stage;
    Scene scene;
    @FXML
    AnchorPane root;
    AnchorPane root2;
    public void resumeGame()
    {
        Game.game.pause=false;
        Game.game.board.getChildren().remove(root);
        Game.game.timer.start();
    }
    public void saveGame()
    {
        Game.game.stop=true;
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/saveGame.fxml"));
        try {
            root2 = fxmlLoader.load();
            root2.setLayoutX(445);
            root2.setLayoutY(193);
            Game.game.board.getChildren().add(root2);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    public void returnMenu(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/menu.fxml"));
        try {
            root2 = fxmlLoader.load();
            scene = new Scene(root2);
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
