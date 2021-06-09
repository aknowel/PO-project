package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Game;


public class GameOverSurviController {
    Stage stage;
    AnchorPane root;
    Scene scene;
    @FXML
    Label label;
    public void initialize()
    {
        label.setText(label.getText()+Game.game.score);
    }
    public void restartGame(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Pane board=new Pane();
        Game game=new Game(board,Game.game.mode, 0,10, Game.game.heroes.get(0).getHeroId());
        game.play(stage);
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
