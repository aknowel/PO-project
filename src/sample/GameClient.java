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
import java.util.LinkedList;

public class GameClient extends Game {

    public Server server;

    boolean goNorth = false;
    boolean goSouth = false;
    boolean goWest = false;
    boolean goEast = false;

    LinkedList<Weapon> newWeapons=new LinkedList<>();

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
            if (event.getCode().equals(KeyBinds.W)) goNorth = true;
            else if (event.getCode().equals(KeyBinds.S)) goSouth = true;
            else if (event.getCode().equals(KeyBinds.A)) goWest = true;
            else if (event.getCode().equals(KeyBinds.D)) goEast = true;
            else if(event.getCode().equals(KeyBinds.SPACE)) heroes.get(1).heroSkill = true;
            /*else if (event.getCode().equals(KeyBinds.P)) {
                if (!pause & !stop) {
                    timer.stop();
                    pause = true;
                } else if (!stop) {
                    board.getChildren().remove(root);
                    pause = false;
                    timer.start();
                }
            }*/
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyBinds.W)) goNorth = false;
            else if (event.getCode().equals(KeyBinds.S)) goSouth = false;
            else if (event.getCode().equals(KeyBinds.A)) goWest = false;
            else if (event.getCode().equals(KeyBinds.D)) goEast = false;
        });
        scene.setOnMouseClicked(event -> {
            Weapon newWeapon = new Hammer(event.getSceneX() - heroes.get(1).getLayoutX(), event.getSceneY() - heroes.get(1).getLayoutY());
            newWeapons.add(newWeapon);
        });

       timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    gameState.loadDynamicElementsFromStream(server.in);

                    int dx = 0;
                    int dy = 0;
                    if (goNorth) dy -= 1;
                    if (goSouth) dy += 1;
                    if (goEast) dx += 1;
                    if (goWest) dx -= 1;
                    double length = Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
                    if (length > 0) {
                        dx /= length;
                        dy /= length;
                        dx *= 3;
                        dy *= 3;
                    }
                    server.out.writeDouble(dx);
                    server.out.writeDouble(dy);
                    int size=newWeapons.size();
                    server.out.writeInt(size);
                    for(int i=0;i<size;i++)
                    {
                        server.out.writeDouble(newWeapons.get(i).getLayoutX());
                        server.out.writeDouble(newWeapons.get(i).getLayoutY());
                    }
                    newWeapons.clear();
                    if(heroes.get(1).heroSkill)
                    {
                        server.out.writeBoolean(true);
                        heroes.get(1).heroSkill=false;
                    }
                    else
                    {
                        server.out.writeBoolean(false);
                    }
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
