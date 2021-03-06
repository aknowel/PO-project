package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Counter;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MenuController {

    @FXML
    private Stage stage;
    private AnchorPane root;
    private Scene scene;
    File file;
    List<String> list;
    Scanner scanner;
    PrintWriter writer;
    public void chooseMode(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/chooseMode.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Choose Mode");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stage.show();
    }
    public void loadGame(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/loadGame.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Load Game");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stage.show();
    }
    public void multiplayer(ActionEvent event) {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/multiplayer.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Multiplayer");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stage.show();
    }
    public void selectOptions(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/options.fxml"));
        try {
            root = fxmlLoader.load();
            scene = new Scene(root);
            stage.setScene(scene);
            stage.setTitle("Options");
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
        stage.show();
    }
    public void selectAchievements(ActionEvent event) throws IOException {
        achievements();
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
    public void exitGame(ActionEvent event) throws IOException {
        achievements();
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        stage.close();
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void achievements() throws IOException {
        file=new File("src/resources/other/achievement.txt");
        list=new ArrayList<>();
        if(file.exists()) {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                list.add(scanner.next());
            }
            scanner.close();
            file.delete();
            file.createNewFile();
            writer=new PrintWriter(file);
            if(list.size()==21) {
                for (int i = 0; i < list.size(); i++) {
                    Integer x = Integer.parseInt(list.get(i)) + Counter.list.get(i);
                    writer.println(x);
                }
            }
            else
            {
                for (int i = 0; i < Counter.list.size(); i++) {
                    Integer x = Counter.list.get(i);
                    writer.println(x);
                }
            }
        }
        else
        {
            file.createNewFile();
            writer=new PrintWriter(file);
            for (int i = 0; i < Counter.list.size(); i++) {
                Integer x = Counter.list.get(i);
                writer.println(x);
            }
        }
        writer.close();
        Counter.list= Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
    }

}
