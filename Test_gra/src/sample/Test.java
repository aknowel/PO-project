package sample;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Pos;

import javafx.scene.Parent;
import javafx.scene.Scene;

import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

import java.io.PrintWriter;
import java.io.StringWriter;

public class Test extends Application {
    public final double W=1280;
    private final double H=720;
    @Override
    public  void start(Stage stage) throws  Exception{
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/menu.fxml"));
        try {
            AnchorPane root = fxmlLoader.load();

            Scene scene = new Scene(root);
            stage.setScene(scene);
            //String css=this.getClass().getResource("src/resources/menu.css").toExternalForm();
            stage.setTitle("Menu");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        //scene.getStylesheets().add(css);
        stage.show();
    }
    public static void main(String[] args) {
        launch(args);
    }
}
