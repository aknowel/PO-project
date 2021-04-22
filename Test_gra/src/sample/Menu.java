package sample;


import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;

import javafx.scene.Scene;
import javafx.scene.control.Button;

import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Menu extends Application {
    public final double W=1280;
    private final double H=720;
    Button play,exit;
    @Override
    public  void start(Stage stage) {
        VBox board=new VBox();
        play = new Button("Play");
        exit = new Button("Exit");
        EventHandler<ActionEvent> playGame= event -> {
            Game main=new Game();
            main.start(stage);
        };
        EventHandler<ActionEvent> exitGame= event -> stage.close();
        play.setOnAction(playGame);
        exit.setOnAction(exitGame);
        board.getChildren().addAll(play,exit);
        board.setAlignment(Pos.CENTER);
        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();

    }
    public static void main(String[] args) {
        launch(args);
    }
}
