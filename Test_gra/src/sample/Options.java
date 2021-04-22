package sample;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Options {
    Button easeMode,hardMode;
    Double mode;
    public final double W=1280;
    private final double H=720;
    void start(Stage stage)
    {
        VBox board=new VBox();
        easeMode = new Button("Easy");
        hardMode = new Button("Hard");
        EventHandler<ActionEvent> easyGame= event -> {
            mode = 0D;
            Game main=new Game();
            main.play(stage,mode);
        };
        EventHandler<ActionEvent> hardGame= event ->
        {
            mode = 1D;
            Game main=new Game();
            main.play(stage,mode);
        };
        easeMode.setOnAction(easyGame);
        hardMode.setOnAction(hardGame);
        board.getChildren().addAll(easeMode,hardMode);
        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        board.setAlignment(Pos.CENTER);
        stage.setScene(scene);
        stage.setTitle("Menu");
        stage.show();
    }
}
