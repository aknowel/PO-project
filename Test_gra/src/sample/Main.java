package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Random;


public class Main extends Application {

    private static final double W=1280, H=720;
    private static final String HeroImageLoc="https://icons.iconarchive.com/icons/hopstarter/superhero-avatar/32/Avengers-Thor-icon.png";
    private static final String HeroWeaponLoc="https://icons.iconarchive.com/icons/icons8/windows-8/16/Cultures-Thor-Hammer-icon.png";


    private static final Random randomizer=new Random();

    private Image weaponImage;
    private Node hero;
    private final ArrayList<Weapon> weapons= new ArrayList<>();
    private final ArrayList<Villain> villains= new ArrayList<>();
    private Group board;
    Text scoreText, livesText;
    private final int dWeapon=10;
    private int modifier=150, villainCounter=modifier-1, score=0, lives=10000;
    boolean goNorth, goSouth, goEast, goWest;

    @Override
    public void start(Stage stage){
        board = new Group();
        Image heroImage = new Image(HeroImageLoc);
        weaponImage = new Image(HeroWeaponLoc);
        hero = new ImageView(heroImage);

        scoreText= new Text(110, 10, "Score: " + score);
        livesText = new Text (170, 10, "Lives: " + lives);
        board.getChildren().addAll(hero, scoreText, livesText);
        moveHeroTo(20, H/2);

        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        scene.setOnKeyPressed (event -> {
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
            newWeapon = new Weapon( weaponImage,event.getSceneX()-hero.getLayoutX(),event.getSceneY()-hero.getLayoutY());
            newWeapon.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(), hero.getLayoutY());
            weapons.add(newWeapon);
            board.getChildren().add(newWeapon);
        });
        stage.setScene(scene);
        stage.setTitle("Ragnarok");
        stage.show();

        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                int dx=0, dy=0;
                if (goNorth) dy-=3;
                if (goSouth) dy+=3;
                if (goEast) dx+=3;
                if (goWest) dx-=3;
                villainCounter++;
                if (villainCounter%modifier==0){
                    if(modifier>20) modifier--;
                    Villain newVillain = Villain.getNewVillain(randomizer.nextInt(2));
                    int r=randomizer.nextInt(4);
                    switch (r) {
                        case 0 -> newVillain.relocate(W, Math.random() * (H - newVillain.getBoundsInLocal().getHeight()));
                        case 1 -> newVillain.relocate(0, Math.random() * (H - newVillain.getBoundsInLocal().getHeight()));
                        case 2 -> newVillain.relocate(Math.random() * (W - newVillain.getBoundsInLocal().getWidth()), H);
                        case 3 -> newVillain.relocate(Math.random() * (W - newVillain.getBoundsInLocal().getWidth()), 0);
                    }
                    villains.add(newVillain);
                    board.getChildren().add(newVillain);
                }
                moveHeroTo(hero.getLayoutX()+dx, hero.getLayoutY()+dy);
                throwWeapon(dWeapon);
                moveVillain();
                checkHit();
                if (lives==0){
                    Text gameOver = new Text(W/2-50, H/2, "GAME OVER");
                    gameOver.setFill(Color.RED);
                    gameOver.setFont(Font.font("Verdana", 20));
                    board.getChildren().add(gameOver);
                    this.stop();
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
        Iterator<Weapon> z=weapons.iterator();
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
    private void checkHit(){
        Iterator<Weapon> x=weapons.iterator();
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
                    }
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}