package sample;

import javafx.animation.AnimationTimer;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

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
        Hero.counter=0;
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



       timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                try {
                    gameState.loadDynamicElementsFromStream(server.in);
                } catch (IOException e) {
                    e.printStackTrace();
                }

                for (Hero hero : heroes) {
                    try {
                        Movement.moveHeroTo(hero ,hero.pos_x, hero.pos_y);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        };
        timer.start();


    }
}
