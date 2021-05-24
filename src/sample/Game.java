package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
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
    public int modifier=150, villainCounter=modifier-1, score=0, lives=100000,livesMax=10;
    public boolean goNorth, goSouth, goEast, goWest, isBoss=false,upgrade=false;
    public boolean pause=false,stop=false;
    public int time=0;
    public  Double mode;
    AnchorPane root;
    public Boss boss=null;
    public static Game game;
    static Random randomize=new Random();

    public Game(Pane board,Double mode) {
        this.mode=mode;
        this.board=board;
        game=this;
        hero = new Hero();
        try {
            MovementTest.moveHeroTo(20, H/2);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }
    public Game(double x,double y,Pane board,Double mode) {
        this.mode=mode;
        this.board=board;
        game=this;
        hero = new Hero();
        try {
            MovementTest.moveHeroTo(x,y);
        } catch (InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

    public void play(Stage stage){
        Counter.games();
        Counter.thorGames();
        Game.game.mode =mode;
        for (int j=0; j<2; j++)
            for(int i=0; i<3; i++)
            {
                Background b=((i+j)%2==0) ? new Cactus() : new Barrel();
                double x=(i+1)*W/5+W*randomize.nextDouble()/5;
                double y=(2*j+1)*H/6+H*randomize.nextDouble()/3;
                b.relocate(x, y);
                backgroundObjects.add(b);
                board.getChildren().add(b);
            }
        scoreText= new Text(110, 10, "Score: " + score);
        livesText = new Text (170, 10, "Lives: " + lives);
        board.getChildren().addAll(hero, scoreText, livesText);

        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        BackgroundImage myBI= new BackgroundImage(new Image("resources/Images/Background/sand_background.png",W,H,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
        board.setBackground(new javafx.scene.layout.Background(myBI));
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
                    if (!upgrade) {
                        newWeapon = new Hammer(event.getSceneX() - hero.getLayoutX(), event.getSceneY() - hero.getLayoutY());
                    } else {
                        newWeapon = new SuperHammer(event.getSceneX() - hero.getLayoutX(), event.getSceneY() - hero.getLayoutY());
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
                    if (score < 50) {
                        villainCounter++;
                        Villain.newVillain(game);
                    } else if (villains.size() == 0) {
                        if (!isBoss) {
                            isBoss = true;
                            boss = Villain.getNewPredatorBoss(mode);
                            boss.shout();
                            boss.relocate(W, Math.random() * (H - boss.getBoundsInLocal().getHeight()));
                            villains.add(boss);
                            shootingVillains.add(boss);
                            board.getChildren().add(boss);
                        } else {
                            isBossDefeat(timer);
                        }
                    }
                    try {
                        MovementTest.moveHeroTo(hero.getLayoutX() + dx, hero.getLayoutY() + dy);
                        MovementTest.throwWeapon(dWeapon);
                        MovementTest.enemyWeapon(5);
                        MovementTest.moveVillain();
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
