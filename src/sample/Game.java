package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Random;


public class Game  {

    static final double W=1280, H=720;


    static final Random randomizer=new Random();

    Hero hero;
    final ArrayList<Weapon> weaponsHero= new ArrayList<>();
    final ArrayList<Weapon> weaponsVillain= new ArrayList<>();
    final ArrayList<Villain> villains= new ArrayList<>();
    final ArrayList<Villain> shootingVillains= new ArrayList<>();
    final ArrayList<Box> boxes= new ArrayList<>();
    public static Group board;
    public static AnimationTimer timer;
    Text scoreText, livesText;
    final int dWeapon=10;
    int modifier=150, villainCounter=modifier-1, score=0, lives=3;
    boolean goNorth, goSouth, goEast, goWest, isBoss=false,upgrade=false,stop=false;
    public static boolean pause=false;
    static int time=0;
    public static Double mode;
    AnchorPane root;
    Boss boss=null;
    Game game;


    public void play(Stage stage,Double mode){
        Game.mode =mode;
        game=this;
        Movement.game=game;
        board = new Group();
        hero = new Hero();
        scoreText= new Text(110, 10, "Score: " + score);
        livesText = new Text (170, 10, "Lives: " + lives);
        board.getChildren().addAll(hero, scoreText, livesText);
        Movement.moveHeroTo(20, H/2);

        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        stage.setScene(scene);

        stage.setTitle("Ragnarok");
        stage.show();

            scene.setOnKeyPressed(event -> {
                    if(event.getCode().equals(KeyBinds.W)) goNorth = true;
                    else if(event.getCode().equals(KeyBinds.S)) goSouth = true;
                    else if(event.getCode().equals(KeyBinds.A)) goWest = true;
                    else if(event.getCode().equals(KeyBinds.D)) goEast = true;
                    else if(event.getCode().equals(KeyBinds.P)) {
                        if(!pause & !stop)
                        {
                            timer.stop();
                            pause();
                            pause=true;
                        }
                        else if(!stop)
                        {
                            board.getChildren().remove(root);
                            pause=false;
                            timer.start();
                        }
                    }
            });

            scene.setOnKeyReleased(event -> {
                if(event.getCode().equals(KeyBinds.W)) goNorth = false;
                else if(event.getCode().equals(KeyBinds.S)) goSouth = false;
                else if(event.getCode().equals(KeyBinds.A)) goWest = false;
                else if(event.getCode().equals(KeyBinds.D)) goEast = false;
            });
            scene.setOnMouseClicked(event -> {
                Weapon newWeapon;
                if(!upgrade) {
                    newWeapon = new Hammer(event.getSceneX() - hero.getLayoutX(), event.getSceneY() - hero.getLayoutY());
                }
                else
                {
                    newWeapon = new SuperHammer(event.getSceneX() - hero.getLayoutX(), event.getSceneY() - hero.getLayoutY());
                }
                newWeapon.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(), hero.getLayoutY());
                weaponsHero.add(newWeapon);
                board.getChildren().add(newWeapon);
            });

            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    int dx = 0, dy = 0;
                    if (goNorth) dy -= 3;
                    if (goSouth) dy += 3;
                    if (goEast) dx += 3;
                    if (goWest) dx -= 3;
                    if (score < 0) {
                        villainCounter++;
                        Villain.newVillain(game);
                    } else if (villains.size() == 0) {
                        if (!isBoss) {
                            isBoss = true;
                            boss = Villain.getNewBoss(mode);
                            boss.relocate(W, Math.random() * (H - boss.getBoundsInLocal().getHeight()));
                            villains.add(boss);
                            shootingVillains.add(boss);
                            board.getChildren().add(boss);
                        } else {
                            isBossDefeat(timer);
                        }
                    }
                    Movement.moveHeroTo(hero.getLayoutX() + dx, hero.getLayoutY() + dy);
                    Movement.throwWeapon(dWeapon);
                    Movement.enemyWeapon(5);
                    if(time==32-8*mode) {
                        Weapon.newEnemyWeapon(game);
                        time=0;
                    }
                    else {
                        time++;
                    }
                    Movement.moveVillain();
                    Hero.checkHitHero(game);
                    Villain.checkHitVillain(game);
                    Box.checkBox(game);
                    gameOver(this);
                }
            };
            timer.start();
    }
    void gameOver(AnimationTimer timer)
    {
        if (lives <= 0) {
            timer.stop();
            stop=true;
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/resources/fxml/gameOver.fxml"));
            try {
                root = fxmlLoader.load();
                root.setLayoutX(445);
                root.setLayoutY(193);
                board.getChildren().add(root);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        }
    }
    private void pause()
    {
        FXMLLoader fxmlLoader=new FXMLLoader();
        fxmlLoader.setLocation(getClass().getResource("/resources/fxml/pause.fxml"));
        try {
            root = fxmlLoader.load();
            root.setLayoutX(445);
            root.setLayoutY(193);
            board.getChildren().add(root);
        }
        catch (Exception e)
        {
            e.printStackTrace();
        }
    }
    private void isBossDefeat(AnimationTimer timer)
    {
        if (!boss.isAlive()) {
            timer.stop();
            stop=true;
            FXMLLoader fxmlLoader=new FXMLLoader();
            fxmlLoader.setLocation(getClass().getResource("/resources/fxml/bossDefeat.fxml"));
            try {
                root = fxmlLoader.load();
                root.setLayoutX(340);
                root.setLayoutY(160);
                board.getChildren().add(root);
            }
            catch (Exception e)
            {
                e.printStackTrace();
            }
        } else {
            lives = 0;
        }
    }
}
