package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.Counter;
import sample.Game;

public class ChooseModeController {
    @FXML
    AnchorPane pane;
    @FXML
    StackPane hero1;
    @FXML
    StackPane hero2;
    @FXML
    StackPane hero3;
    Stage stage;
    AnchorPane root;
    Scene scene;
    private final String style="-fx-effect: dropshadow(gaussian, rgba(229, 3, 0, 1), 25, 0.5, 0.0, 0.0);";
    public static double mode=0D;
    private static int chosenHero=2;
    static int getHeroId()
    {
        return chosenHero;
    }
    public void chooseWarrior()
    {
        resetAll();
        hero1.setStyle(style);
        chosenHero=1;
    }
    public void chooseThor()
    {
        resetAll();
        hero2.setStyle(style);
        chosenHero=2;
    }
    public void chooseAssassin()
    {
        resetAll();
        hero3.setStyle(style);
        chosenHero=3;
    }
    public void playStory(ActionEvent event)
    {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/plotStart.fxml"));
        gamesCounter();
        try
        {
            root = fxmlLoader.load();
            root.setLayoutX(324);
            root.setLayoutY(160);
            Label text=new Label();
            text.relocate(10, 10);
            text.setText(PlotStrings.first);
            text.setStyle("-fx-font-size: 20; -fx-font-style: italic");
            root.getChildren().add(text);
            pane.getChildren().add(root);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public void playSurvival(ActionEvent event)
    {
        Pane board=new Pane();
        Game main=new Game(board, mode, 0, 10, chosenHero);
        gamesCounter();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        main.play(stage);
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
    private void resetAll()
    {
        resetStyle(hero1);
        resetStyle(hero2);
        resetStyle(hero3);
    }
    private void gamesCounter()
    {
        switch (chosenHero)
        {
            case 1 -> Counter.warriorGames();
            case 2 -> Counter.thorGames();
            case 3 -> Counter.assassinGames();
        }
    }
    private void resetStyle(Node node)
    {
        node.setStyle("");
    }
}
