package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
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
    PrintWriter writer;
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
        list=Arrays.asList(l1,l2,l3,l4,l5,l6,l7,l8,l9);
        slist=new ArrayList<>();
        if(file.exists()) {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                slist.add(scanner.next());
            }
            scanner.close();
        }
        if(file.exists() && slist.size()==9) {
            for (int i = 0; i < list.size(); i++) {
                String text = list.get(i).getText();
                list.get(i).setText(text + slist.get(i));
                limit(list,i,slist);

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
    private void limit(List<Label> list, int i,List<String> slist)
    {
        if(Integer.parseInt(slist.get(i))>= Counter.limits.get(i).first)
        {
            if(Integer.parseInt(slist.get(i))>= Counter.limits.get(i).second) {
                if(Integer.parseInt(slist.get(i))>= Counter.limits.get(i).third) {
                    if(Integer.parseInt(slist.get(i))>= Counter.limits.get(i).fourth) {
                        list.get(i).setStyle("-fx-background-color: mediumpurple");
                    }
                    else
                    {
                        list.get(i).setStyle("-fx-background-color: MEDIUMAQUAMARINE");
                    }
                }
                else
                {
                    list.get(i).setStyle("-fx-background-color: gold");
                }
            }
            else
            {
                list.get(i).setStyle("-fx-background-color: silver");
            }
        }
        else
        {
            list.get(i).setStyle("-fx-background-color: chocolate");
        }
    }
    public void resetAchievements(ActionEvent event) throws IOException {
        file=new File("src/resources/other/achievement.txt");
        if(file.exists())
        {
            file.delete();
            file.createNewFile();
            writer=new PrintWriter(file);
            for(int i=0;i<9;i++)
            {
                writer.println(0);
            }
            writer.close();
            stage = (Stage)((Node)event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/resources/fxml/achievement.fxml"));
            try {
                root = fxmlLoader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Achievement");
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
            stage.show();
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
