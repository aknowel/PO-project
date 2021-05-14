package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import sample.Box;
import sample.Game;
import sample.Villain;
import sample.Weapon;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.time.LocalDateTime;
import java.util.Scanner;


public class SaveGameController {
    @FXML
    AnchorPane root;
    @FXML
    Button save1;
    @FXML
    Button save2;
    @FXML
    Button save3;
    @FXML
    Label date1;
    @FXML
    Label date2;
    @FXML
    Label date3;
    File save;
    File dates;
    Scanner scanner;
    public void initialize() throws FileNotFoundException {
        dates=new File("src/resources/other/dates.txt");
        String text1="",text2="",text3="";
        int id;
        if(dates.exists()) {
            scanner=new Scanner(dates);
            while(scanner.hasNext()) {
                id = Integer.parseInt(scanner.next());
                switch (id) {
                    case 0 -> text1 = scanner.next();
                    case 1 -> text2 = scanner.next();
                    case 2 -> text3 = scanner.next();
                }
            }
            File file=new File("src/resources/other/save1.txt");
            if(!text1.equals("") && file.exists()) {
                date1.setText(text1.substring(0, 19).replace('T', ' '));
            }
            file=new File("src/resources/other/save2.txt");
            if(!text2.equals("") && file.exists()) {
                date2.setText(text2.substring(0, 19).replace('T', ' '));
            }
            file=new File("src/resources/other/save3.txt");
            if (!text3.equals("") && file.exists()) {
                date3.setText(text3.substring(0, 19).replace('T', ' '));
            }
        }
    }
    public void back()
    {
        Game.game.stop=false;
        Game.game.board.getChildren().remove(root);
    }
    public void setSave1() throws IOException {
        save=new File("src/resources/other/save1.txt");
        date(0);
        save.delete();
        save.createNewFile();
        getSource(save);
        System.out.println("tak");
    }
    public void setSave2() throws IOException {
        save=new File("src/resources/other/save2.txt");
        date(1);
        save.delete();
        save.createNewFile();
        getSource(save);
    }
    public void setSave3() throws IOException {
        save=new File("src/resources/other/save3.txt");
        date(2);
        save.delete();
        save.createNewFile();
        getSource(save);
    }
    private void getSource(File save)
    {
        try {
            PrintWriter writer;
            writer = new PrintWriter(save);
            writer.println(Game.game.mode);
            writer.println(Game.game.hero);
            writer.println(Game.game.weaponsHero.size());
            for(Weapon weapon:Game.game.weaponsHero) {
                writer.println(weapon);
            }
            writer.println(Game.game.weaponsVillain.size());
            for(Weapon weapon:Game.game.weaponsVillain) {
                writer.println(weapon);
            }
            writer.println(Game.game.villains.size());
            for(Villain v:Game.game.villains) {
                writer.println(v);
            }
            writer.println(Game.game.boxes.size());
            for(Box b:Game.game.boxes) {
                writer.println(b);
            }
            writer.println(Game.game.modifier);
            writer.println(Game.game.villainCounter);
            writer.println(Game.game.score);
            writer.println(Game.game.lives);
            writer.println(Game.game.isBoss);
            writer.println(Game.game.upgrade);
            writer.println(Game.game.time);
            writer.close();
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
    public void date(int i) throws IOException {
        dates=new File("src/resources/other/dates.txt");
        String text1="",text2="",text3="";
        int id;
        if(dates.exists()) {
            scanner=new Scanner(dates);
            while(scanner.hasNext()) {
                id = Integer.parseInt(scanner.next());
                switch (id) {
                        case 0 -> text1 = scanner.next();
                        case 1 -> text2 = scanner.next();
                        case 2 -> text3 = scanner.next();
                    }
                }
            String text=LocalDateTime.now().toString();
            switch (i)
            {
                case 0 -> {
                    date1.setText(text.substring(0, 19).replace('T', ' '));
                    text1=text;
                }
                case 1 -> {
                    date2.setText(text.substring(0, 19).replace('T', ' '));
                    text2=text;
                }
                case 2 -> {
                    date3.setText(text.substring(0, 19).replace('T', ' '));
                    text3=text;
                }
            }
            PrintWriter writer;
            writer = new PrintWriter(dates);
            if(!text1.equals("")) {
                writer.println(0);
                writer.println(text1);
            }
            if(!text2.equals("")) {
                writer.println(1);
                writer.println(text2);
            }
            if(!text3.equals("")) {
                writer.println(2);
                writer.println(text3);
            }
            writer.close();
        }
        else
        {
            dates.createNewFile();
            PrintWriter writer;
            writer = new PrintWriter(dates);
            writer.println(i);
            String text=LocalDateTime.now().toString();
            writer.println(text);
            writer.close();
            switch (i)
            {
                case 0 -> date1.setText(text.substring(0, 19).replace('T', ' '));
                case 1->  date2.setText(text.substring(0, 19).replace('T', ' '));
                case 2 -> date3.setText(text.substring(0, 19).replace('T', ' '));
            }
        }
    }
}
