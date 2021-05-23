package sample.controllers;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;

import java.io.File;

public class deleteController {
    @FXML
    AnchorPane root;
    File file;
    public void yes()
    {
        switch (LoadController.i)
        {
            case 1->
                    {
                        file=new File("src/resources/other/save1.txt");
                        if(file.exists())
                        {
                            file.delete();
                            LoadController.setLabel(LoadController.label3,Color.GREEN,261);
                            LoadController.pane.getChildren().add(LoadController.label3);
                        }
                        else
                        {
                            LoadController.setLabel(LoadController.label,Color.RED,261);
                            LoadController.pane.getChildren().add(LoadController.label);
                        }
                    }
            case 2-> {
                file = new File("src/resources/other/save2.txt");
                if (file.exists()) {
                    file.delete();
                    LoadController.setLabel(LoadController.label3,Color.GREEN,334);
                    LoadController.pane.getChildren().add(LoadController.label3);
                }
                else
                {
                    LoadController.setLabel(LoadController.label,Color.RED,334);
                    LoadController.pane.getChildren().add(LoadController.label);
                }
            }
            case 3->
                    {
                        file=new File("src/resources/other/save3.txt");
                        if(file.exists())
                        {
                            file.delete();
                            LoadController.setLabel(LoadController.label3,Color.GREEN,405);
                            LoadController.pane.getChildren().add(LoadController.label3);
                        }
                        else
                        {
                            LoadController.setLabel(LoadController.label,Color.RED,405);
                            LoadController.pane.getChildren().add(LoadController.label);
                        }
                    }
        }
        LoadController.pane.getChildren().remove(root);
        LoadController.deleted=true;
    }
    public void no()
    {
        LoadController.pane.getChildren().remove(root);
    }
}
