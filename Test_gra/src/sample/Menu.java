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
    Button play,exit,options;
    @Override
    public  void start(Stage stage) {
        VBox board=new VBox();
        play = new Button("Play");
        exit = new Button("Exit");
        options = new Button("Options");
        EventHandler<ActionEvent> playGame= event -> {
            Game main=new Game();
            main.play(stage,0D);
        };
        EventHandler<ActionEvent> exitGame= event -> stage.close();
        EventHandler<ActionEvent> selectoptions= event -> {
            Options option = new Options();
            option.start(stage);
        };
        play.setOnAction(playGame);
        exit.setOnAction(exitGame);
        options.setOnAction(selectoptions);
        board.getChildren().addAll(play,options,exit);
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
