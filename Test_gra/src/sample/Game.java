package sample;

import javafx.animation.AnimationTimer;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Game  {

    private static final double W=1280, H=720;


    private static final Random randomizer=new Random();

    private Hero hero;
    private final ArrayList<Weapon> weaponsHero= new ArrayList<>();
    private final ArrayList<Weapon> weaponsVillain= new ArrayList<>();
    private final ArrayList<Villain> villains= new ArrayList<>();
    private final ArrayList<Villain> shootingVillains= new ArrayList<>();
    private Group board;
    Text scoreText, livesText;
    private final int dWeapon=10;
    private int modifier=150, villainCounter=modifier-1, score=0, lives=10000;
    boolean goNorth, goSouth, goEast, goWest, isBoss=false;
    private static int time=0;
    Boss boss=null;


    public void play(Stage stage,Double mode){

        board = new Group();
        hero = new Hero();
        scoreText= new Text(110, 10, "Score: " + score);
        livesText = new Text (170, 10, "Lives: " + lives);
        board.getChildren().addAll(hero, scoreText, livesText);
        moveHeroTo(20, H/2);

        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        stage.setScene(scene);

        stage.setTitle("Ragnarok");
        stage.show();

            scene.setOnKeyPressed(event -> {
                switch (event.getCode()) {
                    case W -> goNorth = true;
                    case S -> goSouth = true;
                    case A -> goWest = true;
                    case D -> goEast = true;
                }
            });

            scene.setOnKeyReleased(event -> {
                switch (event.getCode()) {
                    case W -> goNorth = false;
                    case S -> goSouth = false;
                    case A -> goWest = false;
                    case D -> goEast = false;
                }
            });
            scene.setOnMouseClicked(event -> {
                Weapon newWeapon;
                newWeapon = new Hammer( event.getSceneX() - hero.getLayoutX(), event.getSceneY() - hero.getLayoutY());
                newWeapon.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(), hero.getLayoutY());
                weaponsHero.add(newWeapon);
                board.getChildren().add(newWeapon);
            });

            AnimationTimer timer = new AnimationTimer() {
                @Override
                public void handle(long now) {
                    int dx = 0, dy = 0;
                    if (goNorth) dy -= 3;
                    if (goSouth) dy += 3;
                    if (goEast) dx += 3;
                    if (goWest) dx -= 3;
                    if (score < 50) {
                        villainCounter++;
                        if (villainCounter % modifier == 0) {
                            Villain newVillain = Villain.getNewVillain(randomizer.nextInt(7),mode);
                            int r = randomizer.nextInt(4);
                            switch (r) {
                                case 0 -> {
                                    newVillain.relocate(W-30d, Math.random() * (H - newVillain.getBoundsInLocal().getHeight()));
                                    modifier--;
                                }
                                case 1 -> {
                                    newVillain.relocate(0, Math.random() * (H - newVillain.getBoundsInLocal().getHeight()));
                                    modifier--;
                                }
                                case 2 -> newVillain.relocate(Math.random() * (W - newVillain.getBoundsInLocal().getWidth()), H-30);
                                case 3 -> newVillain.relocate(Math.random() * (W - newVillain.getBoundsInLocal().getWidth()), 0);
                            }
                            villains.add(newVillain);
                            board.getChildren().add(newVillain);
                            if(newVillain instanceof Predator)
                            {
                                shootingVillains.add(newVillain);
                            }
                        }
                    } else if (villains.size() == 0) {
                        if (!isBoss) {
                            isBoss = true;
                            boss = Villain.getNewBoss(mode);
                            boss.relocate(W, Math.random() * (H - boss.getBoundsInLocal().getHeight()));
                            villains.add(boss);
                            shootingVillains.add(boss);
                            board.getChildren().add(boss);
                        } else {
                            if (!boss.isAlive()) {
                                Text bossDeafeat = new Text(400, H / 2, "Boss Defeated! Congratulations!");
                                bossDeafeat.setFill(Color.GREEN);
                                bossDeafeat.setFont(Font.font("Verdana", 30));
                                board.getChildren().add(bossDeafeat);
                                this.stop();
                                Button returnMenu = new Button("Return to the MENU");
                                EventHandler<ActionEvent> MENU= event ->
                                {
                                    Menu menu=new Menu();
                                    menu.start(stage);
                                };
                                returnMenu.setOnAction(MENU);
                                returnMenu.setLayoutX(W/2-70);
                                returnMenu.setLayoutY(H/2+50);
                                Button restartGame = new Button("Restart Game");
                                EventHandler<ActionEvent> restart= event ->
                                {
                                    Game game=new Game();
                                    game.play(stage,mode);
                                };
                                restartGame.setOnAction(restart);
                                restartGame.setLayoutX(W/2-50);
                                restartGame.setLayoutY(H/2+25);
                                board.getChildren().addAll(restartGame,returnMenu);
                            } else {
                                lives = 0;
                            }
                        }
                    }
                    moveHeroTo(hero.getLayoutX() + dx, hero.getLayoutY() + dy);
                    throwWeapon(dWeapon);
                    enemyWeapon(5);
                    if(time==32-8*mode) {
                        newEnemyWeapon();
                        time=0;
                    }
                    else {
                        time++;
                    }
                    moveVillain();
                    checkHitHero();
                    checkHitVillain();
                    if (lives == 0) {
                        Text gameOver = new Text(W / 2 - 100, H / 2, "GAME OVER");
                        gameOver.setFill(Color.RED);
                        gameOver.setFont(Font.font("Verdana", 30));
                        board.getChildren().add(gameOver);
                        this.stop();
                        Button returnMenu = new Button("Return to the MENU");
                        EventHandler<ActionEvent> MENU= event ->
                        {
                            Menu menu=new Menu();
                            menu.start(stage);
                        };
                        returnMenu.setOnAction(MENU);
                        returnMenu.setLayoutX(W/2-70);
                        returnMenu.setLayoutY(H/2+50);
                        Button restartGame = new Button("Restart Game");
                        EventHandler<ActionEvent> restart= event ->
                        {
                            Game game=new Game();
                            game.play(stage,mode);
                        };
                        restartGame.setOnAction(restart);
                        restartGame.setLayoutX(W/2-50);
                        restartGame.setLayoutY(H/2+25);
                        board.getChildren().addAll(restartGame,returnMenu);
                    }
                }
            };
            timer.start();
    }

    private void moveHeroTo (double x, double y){
        if (x>=0 && x<=W-hero.getBoundsInLocal().getWidth() && y>=0 && y<=H-hero.getBoundsInLocal().getHeight())
            hero.relocate(x, y);
        else if (x>=0 && x<=W-hero.getBoundsInLocal().getWidth())
            hero.relocate(x, hero.getLayoutY());
        else if (y>=0 && y<=H-hero.getBoundsInLocal().getHeight())
            hero.relocate(hero.getLayoutX(), y);
    }

    private void moveVillain (){
        double d;
        Iterator<Villain> it=villains.iterator();
        while(it.hasNext()){
            Villain currentvillain=it.next();
            d=currentvillain.getSpeed();
            if(hero.getBoundsInParent().intersects(currentvillain.getBoundsInParent())) {
                lives--;
                livesText.setText("Lives: " + lives);
                it.remove();
                board.getChildren().remove(currentvillain);
                continue;
            }
            double x=currentvillain.getLayoutX();
            double y=currentvillain.getLayoutY();
            double z=hero.getLayoutX();
            double v=hero.getLayoutY();
            double dd=Math.sqrt((x-z)*(x-z)+(y-v)*(y-v));
            currentvillain.relocate(currentvillain.getLayoutX() +d*(x-z)/dd, currentvillain.getLayoutY()+d*(y-v)/dd);
        }
    }
    private void throwWeapon(double d){
        Iterator<Weapon> z=weaponsHero.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            if (x.getLayoutX()<W && x.getLayoutX()>0 && x.getLayoutY()<H && x.getLayoutY()>0){
                double dd=Math.sqrt(x.x*x.x+x.y*x.y);
                x.relocate(x.getLayoutX() + d*x.x/dd, x.getLayoutY() + d * x.y / dd);
            }
            else {
                z.remove();
                board.getChildren().remove(x);
            }
        }
    }
    private void checkHitVillain(){
        Iterator<Weapon> x=weaponsHero.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
            Iterator<Villain> y=villains.iterator();
            while(y.hasNext()){
                Villain currentVillain=y.next();
                if (currentWeapon.getBoundsInParent().intersects(currentVillain.getBoundsInParent())){
                    currentVillain.hp();
                    board.getChildren().remove(currentWeapon);
                    x.remove();
                    if(!currentVillain.isAlive()) {
                        board.getChildren().remove(currentVillain);
                        y.remove();
                        score++;
                        scoreText.setText("Score: " + score);
                        if(currentVillain instanceof Predator)
                        {
                            shootingVillains.remove(currentVillain);
                        }
                    }
                }
            }
        }
    }
    public void enemyWeapon(double d)
    {
        Iterator<Weapon> z=weaponsVillain.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            if (x.getLayoutX()<=W && x.getLayoutX()>=0 && x.getLayoutY()<=H && x.getLayoutY()>=0){
                double dd=Math.sqrt(x.x*x.x+x.y*x.y);
                x.relocate(x.getLayoutX() + d*x.x/dd, x.getLayoutY() + d * x.y / dd);
            }
            else {
                z.remove();
                board.getChildren().remove(x);
            }
        }
    }
    private void checkHitHero()
    {
        Iterator<Weapon> x=weaponsVillain.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
                if (currentWeapon.getBoundsInParent().intersects(hero.getBoundsInParent())){
                    lives--;
                    livesText.setText("Lives: " + lives);
                    board.getChildren().remove(currentWeapon);
                    x.remove();
            }
        }
    }
    private void newEnemyWeapon()
    {
        for (Villain currentVillain : shootingVillains) {
            double x = currentVillain.getLayoutX();
            double y = currentVillain.getLayoutY();
            double z = hero.getLayoutX();
            double v = hero.getLayoutY();
            Weapon newWeapon = new RedBall( z-x,v-y );
            newWeapon.relocate(currentVillain.getLayoutX() , currentVillain.getLayoutY() );
            weaponsVillain.add(newWeapon);
            board.getChildren().add(newWeapon);
        }
    }
}
