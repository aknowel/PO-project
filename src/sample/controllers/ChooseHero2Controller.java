package sample.controllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;
import sample.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChooseHero2Controller {
    @FXML
    AnchorPane pane;
    @FXML
    StackPane hero1;
    @FXML
    StackPane hero2;
    @FXML
    StackPane hero3;
    Stage stage;
    AnchorPane root;
    Scene scene;
    Alert alert;
    private final String style="-fx-effect: dropshadow(gaussian, rgba(229, 3, 0, 1), 25, 0.5, 0.0, 0.0);";
    public static double mode=0D;
    private static int chosenHero=2;
    public static Thread serverThread;
    static int getHeroId()
    {
        return chosenHero;
    }
    public void chooseWarrior()
    {
        resetAll();
        hero1.setStyle(style);
        chosenHero=1;
    }
    public void chooseThor()
    {
        resetAll();
        hero2.setStyle(style);
        chosenHero=2;
    }
    public void chooseAssassin()
    {
        resetAll();
        hero3.setStyle(style);
        chosenHero=3;
    }
    public void play(ActionEvent event)
    {
        try {
            Socket socket = new Socket(MultiplayerController.ad, 23456);
            System.out.println("client created");

            DataInputStream in = new DataInputStream(socket.getInputStream());
            DataOutputStream out = new DataOutputStream(socket.getOutputStream());
            out.writeInt(chosenHero);
            Server server = new Server();
            server.socket = socket;
            server.in = in;
            server.out = out;

            Pane pane = new Pane();
            GameState gameState = new GameState();
            gameState.loadStaticElementsFromStream(in);
            GameClient main = new GameClient(pane, gameState, server);

            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            main.play(stage);
        } catch (Throwable e) {
            alert = new Alert(Alert.AlertType.ERROR);
            alert.setHeaderText("Error! Cannot connect to server! Try again!");
            alert.showAndWait();
            e.printStackTrace();
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
    private void resetAll()
    {
        resetStyle(hero1);
        resetStyle(hero2);
        resetStyle(hero3);
    }
    private void gamesCounter()
    {
        switch (chosenHero)
        {
            case 1 -> Counter.warriorGames();
            case 2 -> Counter.thorGames();
            case 3 -> Counter.assassinGames();
        }
    }
    private void resetStyle(Node node)
    {
        node.setStyle("");
    }
}
