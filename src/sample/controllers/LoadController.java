package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.Box;
import sample.Game;
import sample.Villain;
import sample.Weapon;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;


public class LoadController {
    @FXML
    TextField save1;
    @FXML
    TextField save2;
    @FXML
    TextField save3;
    Stage stage;
    @FXML
    AnchorPane anchorPane;
    AnchorPane root;
    Scene scene;
    File save;
    File dates;
    Scanner scanner;
    Game game;
    Pane board;
    Label label=new Label("Save does not exist!");
    @FXML
    public void initialize() throws FileNotFoundException {
        dates=new File("src/resources/other/dates.txt");
        String text1="",text2="",text3="";
        int id;
        if(dates.exists()) {
            scanner=new Scanner(dates);
            while (scanner.hasNext()) {
                id = Integer.parseInt(scanner.next());
                switch (id) {
                    case 0 -> text1 = scanner.next();
                    case 1 -> text2 = scanner.next();
                    case 2 -> text3 = scanner.next();
                }
            }
            File file=new File("src/resources/other/save1.txt");
            if(!text1.equals("") && file.exists()) {
                save1.setText(text1.substring(0, 19).replace('T', ' '));
            }
            file=new File("src/resources/other/save2.txt");
            if(!text2.equals("") && file.exists()) {
                save2.setText(text2.substring(0, 19).replace('T', ' '));
            }
            file=new File("src/resources/other/save3.txt");
            if (!text3.equals("") && file.exists()) {
                save3.setText(text3.substring(0, 19).replace('T', ' '));
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
    public void loadSave1(ActionEvent event) throws FileNotFoundException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        save=new File("src/resources/other/save1.txt");
        if(save.exists()) {
            scanner = new Scanner(save);
            load(stage);
        }
        else
        {
            anchorPane.getChildren().remove(label);
            setLabel(label,Color.RED,261);
            anchorPane.getChildren().add(label);
        }
    }
    public void loadSave2(ActionEvent event) throws FileNotFoundException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        save=new File("src/resources/other/save2.txt");
        if(save.exists()) {
            scanner = new Scanner(save);
            load(stage);
        }
        else
        {
            anchorPane.getChildren().remove(label);
            setLabel(label,Color.RED,334);
            anchorPane.getChildren().add(label);
        }
    }
    public void loadSave3(ActionEvent event) throws FileNotFoundException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        save=new File("src/resources/other/save3.txt");
        if(save.exists()) {
            scanner = new Scanner(save);
            load(stage);
        }
        else
        {
            anchorPane.getChildren().remove(label);
            setLabel(label,Color.RED,405);
            anchorPane.getChildren().add(label);
        }
    }
    private void load(Stage stage)
    {
        board=new Pane();
        double mode=Double.parseDouble(scanner.next());
        double x,y;
        x=Double.parseDouble(scanner.next());
        y=Double.parseDouble(scanner.next());
        game=new Game(x,y,board);
        Weapon weapon;
        int length=Integer.parseInt(scanner.next());
        for(int i=0;i<length;i++)
        {
            int id=Integer.parseInt(scanner.next());
            if(id==1)
            {
                weapon= Weapon.newHammer(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
                weapon.relocate(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
            }
            else
            {
                weapon= Weapon.newSuperHammer(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
                weapon.relocate(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
            }
            game.weaponsHero.add(weapon);
            board.getChildren().add(weapon);
        }
        length=Integer.parseInt(scanner.next());
        for(int i=0;i<length;i++)
        {
            int id=Integer.parseInt(scanner.next());
            weapon= Weapon.newRedBall(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
            weapon.relocate(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
            game.weaponsVillain.add(weapon);
            board.getChildren().add(weapon);
        }
        Villain villain=null;
        length=Integer.parseInt(scanner.next());
        for(int i=0;i<length;i++)
        {
            int id=Integer.parseInt(scanner.next());
            double hp=Double.parseDouble(scanner.next());
            switch (id) {
                case 0 -> {
                    villain = Villain.newSkull(mode);
                    villain.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                    villain.setHP(hp);
                }
                 case 1 ->
                {
                    villain = Villain.newPredator(mode);
                    villain.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                    villain.setHP(hp);
                    game.shootingVillains.add(villain);
                }
                case 2 -> {
                            villain = Villain.newSpider(mode);
                            villain.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                            villain.setHP(hp);
                        }
                        case 3->
                {
                    villain = Villain.getNewBoss(mode);
                    villain.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                    villain.setHP(hp);
                }
            }
            game.villains.add(villain);
            board.getChildren().add(villain);
        }
        Box box;
        length=Integer.parseInt(scanner.next());
        for(int i=0;i<length;i++)
        {
            int id=Integer.parseInt(scanner.next());
            if(id==0)
            {
                box=Box.newEmptyBox();
                box.relocate(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
            }
            else
            {
                box=Box.newUpgradeBox();
                box.relocate(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
            }
            game.boxes.add(box);
            board.getChildren().add(box);
        }
        game.modifier=Integer.parseInt(scanner.next());
        game.villainCounter=Integer.parseInt(scanner.next());
        game.score=Integer.parseInt(scanner.next());
        game.lives=Integer.parseInt(scanner.next());
        game.isBoss=Boolean.parseBoolean(scanner.next());
        game.upgrade=Boolean.parseBoolean(scanner.next());
        game.time=Integer.parseInt(scanner.next());
        game.play(stage,mode);
        System.out.println("nie");
    }
    private void setLabel(Label text, Color color, double y)
    {
        text.setFont(Font.font("Verdana",16));
        text.setTextFill(color);
        text.setStyle("-fx-background-color: lightblue;");
        text.relocate(873, y);
    }
}
