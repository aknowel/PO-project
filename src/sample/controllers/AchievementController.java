package sample.controllers;

import javafx.fxml.FXML;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class AchievementController {
    @FXML
    ScrollPane root;
    @FXML
    VBox content;
    @FXML
    public void initialize()
    {
        content=new VBox();
        TextField[] fields=new TextField[20];
        for(int i=0;i<20;i++)
        {
            fields[i]=new TextField();
            fields[i].setS
        }
        for(TextField text:fields)
        content.getChildren().add(text);
        content.setAlignment(Pos.CENTER);
        root.setContent(content);
    }
}
