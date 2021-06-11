package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
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
        Game game=new Game(board,Game.game.mode, 1, 10, Game.game.heroes.get(0).getHeroId());
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
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/plotWindow.fxml"));
        try
        {
            root = fxmlLoader.load();
            root.setLayoutX(340);
            root.setLayoutY(160);
            Label text=new Label();
            text.relocate(10, 10);
            String s=switch (Game.game.round)
                    {
                        case 1->PlotStrings.second;
                        case 2->PlotStrings.third;
                        case 3->PlotStrings.forth;

                        default -> throw new IllegalStateException("Unexpected value: " + Game.game.round);
                    };
            text.setText(s);
            text.setStyle("-fx-font-size: 20; -fx-font-style: italic");
            root.getChildren().add(text);
            Game.game.board.getChildren().add(root);
        } catch (Exception e) {
                e.printStackTrace();
        }
    }
}
