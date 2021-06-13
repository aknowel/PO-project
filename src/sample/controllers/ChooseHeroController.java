package sample.controllers;

import com.sun.tools.javac.Main;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.*;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class ChooseHeroController {
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

    public static boolean clientJoined = false;
    public static Hero newHero = null;

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
        Pane board = new Pane();
        Game main = new Game(board, mode, 0, 10, chosenHero);
        Game.isServerRunning = true;

        Runnable serverRunnable = () -> {
            try {
                ServerSocket serverSocket = new ServerSocket(23456);
                System.out.println("Server created");
                while (true) {
                    Socket socket = serverSocket.accept();
                    System.out.println("socket accepted");

                    DataInputStream in = new DataInputStream(socket.getInputStream());
                    DataOutputStream out = new DataOutputStream(socket.getOutputStream());

                    Client client = new Client();
                    client.socket = socket;
                    client.in = in;
                    client.out = out;
                    client.hero = Hero.getNewHero(0, 0, 10, client.in.readInt());

                    main.clients.add(client);
                    main.heroes.add(client.hero);

                    clientJoined = true;
                    newHero = client.hero;

                    main.gameState.writeStaticElementsToStream(out);
                    Menu.screen_refresh_divisor = 1;
                }
            }
            catch (IOException e) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setHeaderText("Error! Cannot create server! Try again!");
                alert.showAndWait();
                System.out.println(e);
            }
        };
        serverThread = new Thread(serverRunnable);
        serverThread.start();

        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        main.play(stage);
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
