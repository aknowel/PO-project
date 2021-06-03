package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Game;

public class GameEndController {
    Stage stage;
    AnchorPane root;
    Scene scene;
    public void restartGame(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Pane board=new Pane();
        Game game=new Game(board,Game.game.mode, 1,10);
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
    public void continues(ActionEvent event)
    {
        int score=Game.game.score;
        Pane board=new Pane();
        Game main=new Game(board,Game.game.mode, Game.game.round+1,Game.game.heroes.get(0).hp);
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Game.game.score=score;
        main.play(stage);
    }
}
