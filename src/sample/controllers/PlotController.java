package sample.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Game;
import sample.Hero;

public class PlotController {
    Stage stage;

    public void go(ActionEvent event)
    {
        int score=Game.game.score;
        Pane board=new Pane();
        Hero h=Game.game.heroes.get(0);
        Game main=new Game(board,Game.game.mode, Game.game.round+1, h.hp, h.getHeroId());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        Game.game.score=score;
        main.play(stage);
    }
}
