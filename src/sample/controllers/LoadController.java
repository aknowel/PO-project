package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Optional;
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
    @FXML
    Button delete1;
    @FXML
    Button delete2;
    @FXML
    Button delete3;
    AnchorPane root;
    Scene scene;
    File save;
    File dates;
    Scanner scanner;
    Game game;
    Pane board;
    Label label=new Label("Save does not exist!");
    Label label2=new Label("Save is corrupted!");
    Label label3=new Label("Deleted successfully!");
    static int i=0;
    static AnchorPane pane;
    boolean deleted=false;
    File file;
    Alert alert;
    @FXML
    public void initialize() throws FileNotFoundException {
        LoadController.pane=anchorPane;
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
            scanner.close();
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
            load(stage,261);
        }
        else
        {
            anchorPane.getChildren().remove(label);
            anchorPane.getChildren().remove(label2);
            anchorPane.getChildren().remove(label3);
            setLabel(label,Color.RED,261);
            anchorPane.getChildren().add(label);
        }
    }
    public void loadSave2(ActionEvent event) throws FileNotFoundException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        save=new File("src/resources/other/save2.txt");
        if(save.exists()) {
            scanner = new Scanner(save);
            load(stage,334);
        }
        else
        {
            anchorPane.getChildren().remove(label);
            anchorPane.getChildren().remove(label2);
            anchorPane.getChildren().remove(label3);
            setLabel(label,Color.RED,334);
            anchorPane.getChildren().add(label);
        }
    }
    public void loadSave3(ActionEvent event) throws FileNotFoundException {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        save=new File("src/resources/other/save3.txt");
        if(save.exists()) {
            scanner = new Scanner(save);
            load(stage,405);
        }
        else
        {
            anchorPane.getChildren().remove(label);
            anchorPane.getChildren().remove(label2);
            anchorPane.getChildren().remove(label3);
            setLabel(label,Color.RED,405);
            anchorPane.getChildren().add(label);
        }
    }
    public void deleteSave1(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        delete(1);
        if(deleted) {
            dateEmpty(1);
        }
    }
    public void deleteSave2(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        delete(2);
        if(deleted) {
            dateEmpty(2);
        }
    }
    public void deleteSave3(ActionEvent event)
    {
        stage = (Stage)((Node)event.getSource()).getScene().getWindow();
        delete(3);
        if(deleted) {
            dateEmpty(3);
        }
    }
    private void delete(int i)
    {
        anchorPane.getChildren().remove(label);
        anchorPane.getChildren().remove(label2);
        anchorPane.getChildren().remove(label3);
        alert = new Alert(Alert.AlertType.CONFIRMATION,"Are you sure ?",ButtonType.YES,ButtonType.CANCEL);
        alert.setTitle("Delete");
        alert.setHeaderText("Delete save" + i + " ?");
        alert.setX(750);
        alert.setY(350);
        Optional<ButtonType> result=alert.showAndWait();
        LoadController.i =i;
        if(ButtonType.YES.equals(result.orElse(null)))
        {
            yes();
        }
    }
    private void dateEmpty(int i)
    {
        switch (i) {
            case 1 -> save1.setText("");
            case 2 -> save2.setText("");
            case 3 -> save3.setText("");
        }
        deleted=false;
    }
    private void load(Stage stage,double z)
    {
        try {
            board = new Pane();
            double mode = Double.parseDouble(scanner.next());
            int round = Integer.parseInt(scanner.next());
            double x, y;
            x = Double.parseDouble(scanner.next());
            y = Double.parseDouble(scanner.next());
            int heroHp=Integer.parseInt(scanner.next());
            int heroId=Integer.parseInt(scanner.next());
            Hero h=Hero.getNewHero(x, y, heroHp, heroId);
            h.dx=Double.parseDouble(scanner.next());
            h.dy=Double.parseDouble(scanner.next());
            h.swordCheck=Boolean.parseBoolean(scanner.next());
            h.counter =Integer.parseInt(scanner.next());
            h.side=Boolean.parseBoolean(scanner.next());
            h.top=Boolean.parseBoolean(scanner.next());
            h.barrierCheck=Boolean.parseBoolean(scanner.next());
            h.barrierTime=Integer.parseInt(scanner.next());
            h.upgrade=Integer.parseInt(scanner.next());
            h.skillCooldown=Integer.parseInt(scanner.next());
            h.heroSkill=Boolean.parseBoolean(scanner.next());
            h.slow=Boolean.parseBoolean(scanner.next());
            h.speedUp=Boolean.parseBoolean(scanner.next());
            h.speed=Double.parseDouble(scanner.next());
            h.slowDuration=Integer.parseInt(scanner.next());
            h.SObjInvulnerability=Integer.parseInt(scanner.next());
            game = new Game(board, mode, round, h);
            Weapon weapon;
            int length = Integer.parseInt(scanner.next());
            for (int i = 0; i < length; i++) {
                int id = Integer.parseInt(scanner.next());
                switch(id) {
                    case 1 -> weapon = Weapon.newHammer(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                    case 2 -> weapon = Weapon.newSuperHammer(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                    case 6 -> weapon = Weapon.newShuriken(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                    default -> weapon = Weapon.newAxe(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                }
                weapon.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                game.weaponsHero.add(weapon);
                board.getChildren().add(weapon);
            }
            length = Integer.parseInt(scanner.next());
            for (int i = 0; i < length; i++) {
                int id = Integer.parseInt(scanner.next());
                if(id==3) {
                    weapon = Weapon.newRedBall(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                }
                else
                {
                    weapon = Weapon.newStar(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                }
                weapon.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                game.weaponsVillain.add(weapon);
                board.getChildren().add(weapon);
            }
            Villain villain;
            length = Integer.parseInt(scanner.next());
            for (int i = 0; i < length; i++) {
                boolean t=false;
                int id = Integer.parseInt(scanner.next());
                double hp = Double.parseDouble(scanner.next());
                switch (id) {
                    case 0 -> villain = Villain.newSkull(mode);
                    case 1 -> {
                        villain = Villain.newPredator(mode);
                        game.shootingVillains.add(villain);
                    }
                    case 2 -> villain = Villain.newSpider(mode);
                    case 3 -> {
                        game.boss = Villain.getNewPredatorBoss(mode);
                        villain=game.boss;
                        t=true;
                    }
                    case 4 -> villain = Villain.newZombie(mode);
                    case 5 -> {
                        villain = Villain.newWizard(mode);
                        game.shootingVillains.add(villain);
                    }
                    case 6 -> villain = Villain.newVampire(mode);
                    case 7 -> villain = Villain.newOrc(mode);
                    case 8 -> villain = Villain.newOgre(mode);
                    case 9 -> villain = Villain.newMummy(mode);
                    case 10 -> villain = Villain.newBat(mode);
                    case 11 -> {
                        game.boss = Villain.getNewSpiderBoss(mode);
                        villain=game.boss;
                        t=true;
                    }
                    case 12 -> {
                        game.boss = Villain.getNewVampireBoss(mode);
                        villain=game.boss;
                        t=true;
                    }
                    case 13 -> {
                        game.boss = Villain.getNewLoki(mode);
                        villain=game.boss;
                        t=true;
                    }
                    case 14 -> {
                        game.boss = Villain.getNewBombman(mode);
                        villain=game.boss;
                        t=true;
                    }
                    case 15 -> {
                        game.boss = Villain.getNewOgreBoss(mode);
                        villain=game.boss;
                        t=true;
                    }
                    case 16 -> villain=Villain.newSpider2(mode);
                    default -> throw new IllegalStateException("Unexpected value: " + id);
                }
                villain.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                villain.setCooldownSO(Integer.parseInt(scanner.next()));
                if(t) {
                    villain.setSpawn(Integer.parseInt(scanner.next()));
                }
                villain.setHP(hp);
                game.villains.add(villain);
                board.getChildren().add(villain);
                villain.changeHpBar();
            }
            Box box;
            length = Integer.parseInt(scanner.next());
            for (int i = 0; i < length; i++) {
                int id = Integer.parseInt(scanner.next());
                switch(id) {
                    case 0 -> box = Box.newEmptyBox();
                    case 1 -> box = Box.newUpgradeBox();
                    case 2 -> box = Box.newHeartBox();
                    case 3 -> box= Box.newBarrierBox();
                    default -> throw new IllegalStateException("Unexpected value: " + id);
                }
                box.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                game.boxes.add(box);
                board.getChildren().add(box);
            }
            Background backgroundObj;
            length = Integer.parseInt(scanner.next());
            for (int i = 0; i < length; i++) {
                int id = Integer.parseInt(scanner.next());
                backgroundObj=Background.newBackground(id);
                backgroundObj.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                game.backgroundObjects.add(backgroundObj);
                board.getChildren().add(backgroundObj);
            }
            length = Integer.parseInt(scanner.next());
            for (int i = 0; i < length; i++) {
                int id = Integer.parseInt(scanner.next());
                SpecialObject specialObject;
                if(id!=6) {
                    specialObject = SpecialObject.getNewSpecialObject(id);
                }
                else
                {
                    specialObject=SpecialObject.getNewBomb(Double.parseDouble(scanner.next()),Double.parseDouble(scanner.next()));
                }
                specialObject.setLifetime(Integer.parseInt(scanner.next()));
                specialObject.setDuration(Integer.parseInt(scanner.next()));
                specialObject.relocate(Double.parseDouble(scanner.next()), Double.parseDouble(scanner.next()));
                game.specialObjects.add(specialObject);
                board.getChildren().add(specialObject);
            }
            game.modifier = Integer.parseInt(scanner.next());
            game.villainCounter = Integer.parseInt(scanner.next());
            game.score = Integer.parseInt(scanner.next());
            game.isBoss = Boolean.parseBoolean(scanner.next());
            //game.upgrade = Integer.parseInt(scanner.next());
            game.time = Integer.parseInt(scanner.next());
            game.counter=Integer.parseInt(scanner.next());
            game.play(stage);
        }
        catch (Exception e)
        {
            e.printStackTrace();
            anchorPane.getChildren().remove(label);
            anchorPane.getChildren().remove(label2);
            anchorPane.getChildren().remove(label3);
            setLabel(label2,Color.RED,z);
            anchorPane.getChildren().add(label2);
        }
        finally {
            scanner.close();
        }
    }
    private static void setLabel(Label text, Color color, double y)
    {
        text.setFont(Font.font("Verdana",16));
        text.setTextFill(color);
        text.setStyle("-fx-background-color: lightblue;");
        text.relocate(873, y);
    }
    @SuppressWarnings("ResultOfMethodCallIgnored")
    private void yes()
    {
        switch (LoadController.i)
        {
            case 1->
                    {
                        file=new File("src/resources/other/save1.txt");
                        if(file.exists())
                        {
                            file.delete();
                            LoadController.setLabel(label3,Color.GREEN,261);
                            LoadController.pane.getChildren().add(label3);
                        }
                        else
                        {
                            LoadController.setLabel(label,Color.RED,261);
                            LoadController.pane.getChildren().add(label);
                        }
                    }
            case 2-> {
                file = new File("src/resources/other/save2.txt");
                if (file.exists()) {
                    file.delete();
                    LoadController.setLabel(label3,Color.GREEN,334);
                    LoadController.pane.getChildren().add(label3);
                }
                else
                {
                    LoadController.setLabel(label,Color.RED,334);
                    LoadController.pane.getChildren().add(label);
                }
            }
            case 3->
                    {
                        file=new File("src/resources/other/save3.txt");
                        if(file.exists())
                        {
                            file.delete();
                            LoadController.setLabel(label3,Color.GREEN,405);
                            LoadController.pane.getChildren().add(label3);
                        }
                        else
                        {
                            LoadController.setLabel(label,Color.RED,405);
                            LoadController.pane.getChildren().add(label);
                        }
                    }
        }
        LoadController.pane.getChildren().remove(root);
        deleted=true;
    }
}
