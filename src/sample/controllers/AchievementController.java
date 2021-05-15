package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class AchievementController {
    Stage stage;
    AnchorPane root;
    Scene scene;
    List<Label> list;
    List<String> slist;
    @FXML
    Label l1;
    @FXML
    Label l2;
    @FXML
    Label l3;
    @FXML
    Label l4;
    @FXML
    Label l5;
    @FXML
    Label l6;
    @FXML
    Label l7;
    @FXML
    Label l8;
    @FXML
    Label l9;
    File file;
    Scanner scanner;
    @FXML
    public void initialize() throws FileNotFoundException {
        file=new File("src/resources/other/achievement.txt");
        scanner=new Scanner(file);
        list=Arrays.asList(l1,l2,l3,l4,l5,l6,l7,l8,l9);
        slist=new ArrayList<>();
        while (scanner.hasNext())
        {
            slist.add(scanner.next());
        }
        if(file.exists() && slist.size()==9) {
            for (int i = 0; i < list.size(); i++) {
                String text = list.get(i).getText();
                list.get(i).setText(text + slist.get(i));
            }
        }
        else
        {
            for (int i = 0; i < list.size(); i++) {
                String text = list.get(i).getText();
                list.get(i).setText(text + 0);
            }
        }
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
}
