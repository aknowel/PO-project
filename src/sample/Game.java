package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.LinkedList;
import java.util.Random;


public class Game  {

    static final double W=1280, H=720;


    static final Random randomizer=new Random();

    public Hero hero;
    public final LinkedList<Weapon> weaponsHero= new LinkedList<>();
    public final LinkedList<Weapon> weaponsVillain= new LinkedList<>();
    public final LinkedList<Villain> villains= new LinkedList<>();
    public final LinkedList<Villain> shootingVillains= new LinkedList<>();
    public final LinkedList<Background> backgroundObjects =new LinkedList<>();
    public final LinkedList<Box> boxes= new LinkedList<>();
    public Pane board;
    public AnimationTimer timer;
    public Text scoreText, livesText;
    public final int dWeapon=10;
    public int modifier=150, villainCounter=modifier-1, score=0, lives=10,livesMax=10;
    public boolean goNorth, goSouth, goEast, goWest, isBoss=false;
    public boolean pause=false,stop=false;
    public int time=0, upgrade=0;
    public final int round;
    public  Double mode;
    AnchorPane root;
    public Boss boss=null;
    public static Game game;
    static Random randomize=new Random();
    public VillainFactory villainFactory;
    public int counter=0;
    public Game(Pane board,Double mode, int round) {
        this.mode=mode;
        this.board=board;
        this.round=round;
        game=this;
        hero = new Hero();
        BackgroundSetter.setBackgroundObjects(round);
        BackgroundSetter.setBackground(round);
        villainFactory=VillainFactory.getVillainFactory(round);
        try {
            Movement.moveHeroTo(20, H/2);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }

    }
    public Game(double x,double y,Pane board,Double mode, int round) {
        this.mode=mode;
        this.board=board;
        this.round=round;
        game=this;
        hero = new Hero();
        BackgroundSetter.setBackground(round);
        villainFactory=VillainFactory.getVillainFactory(round);
        try {
            Movement.moveHeroTo(x,y);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void play(Stage stage){
        Counter.games();
        Counter.thorGames();
        Game.game.mode =mode;
        scoreText= new Text(W/2, 30, "Score: " + score);
        livesText = new Text (170, 10, "Lives: " + lives);
        board.getChildren().addAll(hero, scoreText, livesText);
        scoreText.setFont(new Font(30));
        livesText.setFont(new Font(30));
        livesText.setFill(Color.RED);
        scoreText.relocate(W/2-scoreText.getBoundsInLocal().getWidth()/2, 0);
        livesText.relocate(10, -3);
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
                if(!pause) {
                    Weapon newWeapon;
                    if (upgrade<=0) {
                        newWeapon = new Hammer(event.getSceneX() - hero.getLayoutX(), event.getSceneY() - hero.getLayoutY());
                    } else {
                        newWeapon = new SuperHammer(event.getSceneX() - hero.getLayoutX(), event.getSceneY() - hero.getLayoutY());
                        upgrade--;
                    }
                    newWeapon.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(), hero.getLayoutY());
                    weaponsHero.add(newWeapon);
                    board.getChildren().add(newWeapon);
                    Counter.thrownWeapon();
                    if(randomize.nextInt(5)==1)
                    {
                        hero.shout();
                    }
                }
            });

            timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    int dx = 0, dy = 0;
                    if (goNorth) dy -= 3;
                    if (goSouth) dy += 3;
                    if (goEast) dx += 3;
                    if (goWest) dx -= 3;
                    if (counter < 50) {
                        villainCounter++;
                        Villain.newVillain(game);
                    } else if (villains.size() == 0) {
                        if (!isBoss) {
                            isBoss = true;
                            boss = villainFactory.produceBoss(mode);
                            boss.shout();
                            boss.relocate(W, Math.random() * (H - boss.getBoundsInLocal().getHeight()));
                            villains.add(boss);
                            board.getChildren().add(boss);
                        } else {
                            isBossDefeat(timer);
                        }
                    }
                    if(boss.isAlive())
                    {
                       boss.skill();
                    }
                    try {
                        Movement.moveHeroTo(hero.getLayoutX() + dx, hero.getLayoutY() + dy);
                        Movement.throwWeapon(dWeapon);
                        Movement.enemyWeapon(5);
                        Movement.moveVillain();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if(time==32-8*mode) {
                        Weapon.newEnemyWeapon(game);
                        time=0;
                    }
                    else {
                        time++;
                    }
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
            Sounds sounds=new Sounds();
            sounds.playGameOver();
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
            Counter.victories();
            Counter.killedBoss();
            Sounds sounds=new Sounds();
            sounds.playWonGame();
            if(lives==livesMax)
            {
                Counter.deathless();
            }
            FXMLLoader fxmlLoader=new FXMLLoader();
            if(round<4) {
                fxmlLoader.setLocation(getClass().getResource("/resources/fxml/bossDefeat.fxml"));
            }
            else
            {
                fxmlLoader.setLocation(getClass().getResource("/resources/fxml/gameEnd.fxml"));
            }
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
            livesText.setText("Lives: " + lives);
        }
    }
}
