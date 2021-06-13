package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.EOFException;
import java.io.IOException;

public class GameClient extends Game {

    public Server server;

    public GameClient(Pane pane, GameState gameState, Server server) {
        super(pane);
        this.gameState = gameState;
        this.server = server;

        round = gameState.map_id;
        backgroundObjects = gameState.backgrounds;
        heroes = gameState.heroes;
        villains = gameState.villains;
        shootingVillains = gameState.shootingVillains;
        boxes = gameState.boxes;
        specialObjects = gameState.specialObjects;
        weaponsHero = gameState.weaponsHeroes;
        weaponsVillain = game.weaponsVillain;

        BackgroundSetter.setBackground(gameState.map_id);
        for (Background background : backgroundObjects) {
            game.board.getChildren().add(background);
        }
        for (SpecialObject specialObject : specialObjects) {
            game.board.getChildren().add(specialObject);
        }
    }


    public void play(Stage stage) {
        Counter.games();
        Counter.thorGames();
        Game.game.mode = mode;
        scoreText = new Text(W / 2, 30, "Score: " + score);
        for (Hero hero : heroes) {
            board.getChildren().add(hero);
        }
        scoreText.setFont(new Font(30));
        scoreText.relocate(W / 2 - scoreText.getBoundsInLocal().getWidth() / 2, 0);

        for (int i = 0; i < heroes.size(); i += 1) {
            hp_texts.put(heroes.get(i), new Text(170, 10, "HP: " + heroes.get(i).hp));
            hp_texts.get(heroes.get(i)).setFont(new Font(30));
            hp_texts.get(heroes.get(i)).setFill(Color.RED);
            hp_texts.get(heroes.get(i)).relocate(10 + 150 * i, 0);
            board.getChildren().add(hp_texts.get(heroes.get(i)));
            board.getChildren().add(heroes.get(i).hpBar);
        }

        board.getChildren().add(scoreText);

        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        stage.setScene(scene);
        stage.setTitle("Ragnarok");
        stage.show();

        scene.setOnMouseMoved(event -> {Mouse.x=event.getSceneX();
            Mouse.y=event.getSceneY();});
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyBinds.W)) heroes.get(1).goNorth = true;
            else if (event.getCode().equals(KeyBinds.S)) heroes.get(1).goSouth = true;
            else if (event.getCode().equals(KeyBinds.A)) heroes.get(1).goWest = true;
            else if (event.getCode().equals(KeyBinds.D)) heroes.get(1).goEast = true;
            else if(event.getCode().equals(KeyBinds.SPACE) && heroes.get(1).skillCooldown<=1) heroes.get(1).heroSkill = true;
            else if (event.getCode().equals(KeyBinds.P)) {
                if (!pause & !stop) {
                    timer.stop();
                    pause = true;
                } else if (!stop) {
                    board.getChildren().remove(root);
                    pause = false;
                    timer.start();
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyBinds.W)) heroes.get(1).goNorth = false;
            else if (event.getCode().equals(KeyBinds.S)) heroes.get(1).goSouth = false;
            else if (event.getCode().equals(KeyBinds.A)) heroes.get(1).goWest = false;
            else if (event.getCode().equals(KeyBinds.D)) heroes.get(1).goEast = false;
        });
        scene.setOnMouseClicked(event -> {
            if (!pause) {
                heroes.get(1).newWeapon(event);
            }
        });

       timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    gameState.loadDynamicElementsFromStream(server.in);
                    Hero h = heroes.get(1);
                    h.dx = 0;
                    h.dy = 0;
                    if (h.goNorth) h.dy -= 1;
                    if (h.goSouth) h.dy += 1;
                    if (h.goEast) h.dx += 1;
                    if (h.goWest) h.dx -= 1;
                    double length = Math.sqrt(Math.pow(h.dx, 2) + Math.pow(h.dy, 2));
                    if (length > 0) {
                        h.dx /= length;
                        h.dy /= length;
                        h.dx *= h.speed;
                        h.dy *= h.speed;
                    }
                    server.out.writeDouble(h.dx);
                    server.out.writeDouble(h.dy);
                } catch (EOFException e) {
                    gameOver(timer);
                }
                catch (IOException e) {
                    e.printStackTrace();
                }
            }
        };
        timer.start();

    }
}
