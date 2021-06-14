package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import sample.Counter;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.*;

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
    @FXML
    Label l10;
    @FXML
    Label l11;
    @FXML
    Label l12;
    @FXML
    Label l13;
    @FXML
    Label l14;
    @FXML
    Label l15;
    @FXML
    Label l16;
    @FXML
    Label l17;
    @FXML
    Label l18;
    @FXML
    Label l19;
    @FXML
    Label l20;
    @FXML
    Label l21;
    File file;
    Scanner scanner;
    Alert alert;
    @FXML
    public void initialize() throws FileNotFoundException {
        file=new File("src/resources/other/achievement.txt");
        list=Arrays.asList(l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,l11,l12,l13,l14,l15,l16,l17,l18,l19,l20,l21);
        slist=new ArrayList<>();
        if(file.exists()) {
            scanner = new Scanner(file);
            while (scanner.hasNext()) {
                slist.add(scanner.next());
            }
            scanner.close();
        }
        if(file.exists() && slist.size()==21) {
            for (int i = 0; i < list.size(); i++) {
                String text = list.get(i).getText();
                list.get(i).setText(text.replace(".",slist.get(i)));
                limit(list,i,slist);

            }
        }
        else
        {
            for (Label label : list) {
                String text = label.getText();
                label.setText(text.replace(".", "0"));
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
        alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure ?", ButtonType.YES,ButtonType.CANCEL);
        alert.setTitle("Reset");
        alert.setHeaderText("Reset achievements ?");
        alert.setX(750);
        alert.setY(350);
        Optional<ButtonType> result=alert.showAndWait();
        if(ButtonType.YES.equals(result.orElse(null)))
        {
            reset(event);
        }
    }
    private void reset(ActionEvent event) throws IOException {
        file=new File("src/resources/other/achievement.txt");
        if(file.exists()) {
            //noinspection ResultOfMethodCallIgnored
            file.delete();
            //noinspection ResultOfMethodCallIgnored
            file.createNewFile();
            writer = new PrintWriter(file);
            for (int i = 0; i < 9; i++) {
                writer.println(0);
            }
            writer.close();
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            FXMLLoader fxmlLoader = new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/resources/fxml/achievement.fxml"));
            try {
                root = fxmlLoader.load();
                scene = new Scene(root);
                stage.setScene(scene);
                stage.setTitle("Achievement");
            } catch (Exception e) {
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
