package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.stage.Stage;
import sample.KeyBinds;
import java.util.Locale;
import static java.lang.Character.isLetter;


public class KeyBindingsController {
    @FXML
    TextField textW;
    @FXML
    TextField textA;
    @FXML
    TextField textS;
    @FXML
    TextField textD;
    @FXML
    TextField textP;
    @FXML
    AnchorPane root;
    Stage stage;
    Scene scene;
    Label text;
    public KeyBindingsController()
    {

    }
    @FXML
    public void initialize()
    {
        textW.setText(KeyBinds.W.getName());
        textA.setText(KeyBinds.A.getName());
        textP.setText(KeyBinds.P.getName());
        textS.setText(KeyBinds.S.getName());
        textD.setText(KeyBinds.D.getName());
    }
    public void bindW() {
        String string = textW.getText();
        if(text!=null)
        {
            root.getChildren().remove(text);
        }
        if (string.length() > 1) {
            textW.setText(string.substring(0, 1));
            textW.positionCaret(string.length());
        }
        if(textW.getText().length()>0) {
            if(isLetter(textW.getText().charAt(0))) {
                if(check(KeyCode.valueOf(textW.getText().toUpperCase(Locale.ROOT)))) {
                    KeyBinds.W = KeyCode.valueOf(textW.getText().toUpperCase(Locale.ROOT));
                    text = new Label("Successfully added!");
                    setLabel(text, Color.GREEN, 277);
                }
                else
                {
                    textW.setText(KeyBinds.W.getName());
                    text = new Label("Error! This key has been already bound!");
                    setLabel(text, Color.RED, 277);
                }
            }
            else
            {
                textW.setText(KeyBinds.W.getName());
                text = new Label("Error! Please enter only letters!");
                setLabel(text,Color.RED, 277);
            }
            root.getChildren().add(text);
        }
    }
    public void bindA() {
        String string = textA.getText();
        if(text!=null)
        {
            root.getChildren().remove(text);
        }
        if (string.length() > 1) {
            textA.setText(string.substring(0, 1));
            textA.positionCaret(string.length());
        }
        if(textA.getText().length()>0) {
            if(isLetter(textA.getText().charAt(0)))
            {
                if(check(KeyCode.valueOf(textA.getText().toUpperCase(Locale.ROOT)))) {
                    KeyBinds.A = KeyCode.valueOf(textA.getText().toUpperCase(Locale.ROOT));
                    text = new Label("Successfully added!");
                    setLabel(text, Color.GREEN, 307);
                }
                else{
                    textA.setText(KeyBinds.A.getName());
                    text = new Label("Error! This key has been already bound!");
                    setLabel(text, Color.RED, 307);
                }
        }
        else
            {
                textA.setText(KeyBinds.A.getName());
                text = new Label("Error! Please enter only letters!");
                setLabel(text, Color.RED, 307);
            }
            root.getChildren().add(text);
        }
    }
    public void bindS() {
        String string = textS.getText();
        if(text!=null)
        {
            root.getChildren().remove(text);
        }
        if (string.length() > 1) {
            textS.setText(string.substring(0, 1));
            textS.positionCaret(string.length());
        }
        if(textS.getText().length()>0) {
            if(isLetter(textS.getText().charAt(0)))
            {
                if(check(KeyCode.valueOf(textS.getText().toUpperCase(Locale.ROOT)))) {
                    KeyBinds.S = KeyCode.valueOf(textS.getText().toUpperCase(Locale.ROOT));
                    text = new Label("Successfully added!");
                    setLabel(text, Color.GREEN, 335);
                }
                else
                {
                    textS.setText(KeyBinds.S.getName());
                    text = new Label("Error! This key has been already bound!");
                    setLabel(text, Color.RED, 335);
                }
            }
            else
            {
                textS.setText(KeyBinds.S.getName());
                text = new Label("Error! Please enter only letters!");
                setLabel(text, Color.RED, 335);
            }
            root.getChildren().add(text);
        }
    }
    public void bindD() {
        String string = textD.getText();
        if(text!=null)
        {
            root.getChildren().remove(text);
        }
        if (string.length() > 1) {
            textD.setText(string.substring(0, 1));
            textD.positionCaret(string.length());
        }
        if(textD.getText().length()>0) {
            if(isLetter(textD.getText().charAt(0)))
            {
                if(check(KeyCode.valueOf(textD.getText().toUpperCase(Locale.ROOT)))) {
                    KeyBinds.D = KeyCode.valueOf(textD.getText().toUpperCase(Locale.ROOT));
                    text = new Label("Successfully added!");
                    setLabel(text, Color.GREEN, 362);
                }
                else
                {
                    textD.setText(KeyBinds.D.getName());
                    text = new Label("Error! This key has been already bound!");
                    setLabel(text, Color.RED, 362);
                }
            }
            else
            {
                textD.setText(KeyBinds.D.getName());
                text = new Label("Error! Please enter only letters!");
                setLabel(text, Color.RED, 362);
            }
            root.getChildren().add(text);
        }
    }
    public void bindP() {
        String string = textP.getText();
        if(text!=null)
        {
            root.getChildren().remove(text);
        }
        if (string.length() > 1) {
            textP.setText(string.substring(0, 1));
            textP.positionCaret(string.length());
        }
        if(textP.getText().length()>0) {
            if(isLetter(textP.getText().charAt(0)))
            {
                if(check(KeyCode.valueOf(textP.getText().toUpperCase(Locale.ROOT)))) {
                    KeyBinds.P = KeyCode.valueOf(textP.getText().toUpperCase(Locale.ROOT));
                    text = new Label("Successfully added!");
                    setLabel(text, Color.GREEN, 413);
                }
                else
                {
                    textP.setText(KeyBinds.P.getName());
                    text = new Label("Error! This key has been already bound!");
                    setLabel(text, Color.RED, 413);
                }
            }
            else
            {
                textP.setText(KeyBinds.P.getName());
                text = new Label("Error! Please enter only letters!");
                setLabel(text, Color.RED, 413);
            }
            root.getChildren().add(text);
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
    public void resetBindings()
    {
        if(text!=null)
        {
            root.getChildren().remove(text);
        }
        KeyBinds.W=KeyCode.W;
        KeyBinds.A=KeyCode.A;
        KeyBinds.P=KeyCode.P;
        KeyBinds.S=KeyCode.S;
        KeyBinds.D=KeyCode.D;
        textW.setText(KeyBinds.W.getName());
        textA.setText(KeyBinds.A.getName());
        textP.setText(KeyBinds.P.getName());
        textS.setText(KeyBinds.S.getName());
        textD.setText(KeyBinds.D.getName());
    }
    private void setLabel(Label text, Color color, double y)
    {
        text.setFont(Font.font("Verdana",16));
        text.setTextFill(color);
        text.setStyle("-fx-background-color: lightblue;");
        text.relocate(731, y);
    }
    private boolean check(KeyCode code)
    {
        return !code.equals(KeyBinds.A) && !code.equals(KeyBinds.W) && !code.equals(KeyBinds.S) && !code.equals(KeyBinds.D) && !code.equals(KeyBinds.P);
    }
}
