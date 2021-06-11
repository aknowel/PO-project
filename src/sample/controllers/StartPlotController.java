package sample.controllers;

import javafx.event.ActionEvent;
import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import sample.Game;
import sample.Hero;

public class StartPlotController {
    Stage stage;

    public void go(ActionEvent event)
    {
        Pane board=new Pane();
        Game main=new Game(board,ChooseModeController.mode, 1, 10, ChooseModeController.getHeroId());
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        main.play(stage);
    }
}
