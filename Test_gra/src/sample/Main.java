package sample;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
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
    private static final String VillainImageLoc0="https://icons.iconarchive.com/icons/icons8/halloween/32/skull-3-icon.png";
    private static final String VillainImageLoc1="https://icons.iconarchive.com/icons/icons8/halloween/32/predator-icon.png";

    private static final Random randomizer=new Random();

    private Image heroImage, weaponImage;
    private Image[] villainImages;
    private Node hero;
    private final ArrayList<Weapon> weapons= new ArrayList<>();
    private final ArrayList<Node> villains= new ArrayList<>();
    private Group board;
    Text scoreText, livesText;
    private final int dWeapon=10, dVillain=-4;
    private int modifier=150, villainCounter=modifier-1, score=0, lives=3;
    boolean goNorth, goSouth, goEast, goWest, throwing;
    boolean throwNorth, throwSouth, throwEast, throwWest;

    @Override
    public void start(Stage stage) throws Exception{
        board = new Group();
        heroImage = new Image(HeroImageLoc);
        weaponImage = new Image(HeroWeaponLoc);
        villainImages = new Image[2];
        villainImages[0] = new Image(VillainImageLoc0);
        villainImages[1] = new Image(VillainImageLoc1);
        hero = new ImageView(heroImage);

        scoreText= new Text(110, 10, "Score: " + score);
        livesText = new Text (170, 10, "Lives: " + lives);
        board.getChildren().addAll(hero, scoreText, livesText);
        moveHeroTo(20, H/2);

        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        scene.setOnKeyPressed (new EventHandler<>(){
            @Override
            public void handle (KeyEvent event){
            switch (event.getCode()){
                case UP: goNorth=true; throwNorth=true; throwSouth=false; break;
                case DOWN: goSouth=true; throwSouth=true; throwNorth=false; break;
                case LEFT: goWest=true;  throwWest=true; throwEast=false; break;
                case RIGHT: goEast=true; throwEast=true; throwWest=false; break;
                case SPACE: if (!throwing){
                    Weapon newWeapon;
                    if(throwNorth && throwWest) {
                        newWeapon = new Weapon(Weapon.Directions.NW, weaponImage);
                        newWeapon.relocate(hero.getLayoutX() - hero.getBoundsInLocal().getWidth(), hero.getLayoutY() - hero.getBoundsInLocal().getHeight()/2);
                    } else if (throwNorth && throwEast) {
                        newWeapon = new Weapon(Weapon.Directions.NE, weaponImage);
                        newWeapon.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(), hero.getLayoutY() + hero.getBoundsInLocal().getHeight()/2);
                    } else if (throwSouth && throwWest) {
                        newWeapon = new Weapon(Weapon.Directions.SW, weaponImage);
                        newWeapon.relocate(hero.getLayoutX() - hero.getBoundsInLocal().getWidth(), hero.getLayoutY() - hero.getBoundsInLocal().getHeight()/2);
                    } else if (throwSouth && throwEast){
                        newWeapon = new Weapon(Weapon.Directions.SE, weaponImage);
                        newWeapon.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(), hero.getLayoutY() + hero.getBoundsInLocal().getHeight()/2);
                    } else if (throwSouth) {
                        newWeapon = new Weapon(Weapon.Directions.S, weaponImage);
                        newWeapon.relocate(hero.getLayoutX(), hero.getLayoutY() + hero.getBoundsInLocal().getHeight()/2);
                    } else if (throwNorth) {
                        newWeapon = new Weapon(Weapon.Directions.N, weaponImage);
                        newWeapon.relocate(hero.getLayoutX(), hero.getLayoutY() - hero.getBoundsInLocal().getHeight()/2);
                    } else if (throwWest) {
                        newWeapon = new Weapon(Weapon.Directions.W, weaponImage);
                        newWeapon.relocate(hero.getLayoutX() - hero.getBoundsInLocal().getWidth(), hero.getLayoutY());
                    } else {
                        newWeapon = new Weapon(Weapon.Directions.E, weaponImage);
                        newWeapon.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(), hero.getLayoutY());
                    }
                    weapons.add(newWeapon);
                    board.getChildren().add(newWeapon);
                    throwing=true;
                }
                break;
            }
        }
        });

        scene.setOnKeyReleased(new EventHandler<>() {
            @Override
            public void handle(KeyEvent event) {
                switch (event.getCode()) {
                    case UP: goNorth = false; throwEast=false; throwWest=false;   break;
                    case DOWN: goSouth = false; throwEast=false; throwWest=false;  break;
                    case LEFT: goWest = false; throwNorth=false; throwSouth=false;   break;
                    case RIGHT: goEast = false; throwNorth=false; throwSouth=false;  break;
                    case SPACE: throwing = false; break;
                }
            }
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
                    Node newVillain = new ImageView(villainImages[randomizer.nextInt(2)]);
                    newVillain.relocate(W, Math.random()*(H-newVillain.getBoundsInLocal().getHeight()));
                    villains.add(newVillain);
                    board.getChildren().add(newVillain);
                }
                moveHeroTo(hero.getLayoutX()+dx, hero.getLayoutY()+dy);
                throwWeapon(dWeapon);
                moveVillain(dVillain);
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

    private void moveVillain (int d){
        for (int i=0; i<villains.size(); i++){
            if (villains.get(i).getLayoutX()>-villains.get(i).getBoundsInLocal().getWidth()){
                villains.get(i).relocate(villains.get(i).getLayoutX()+d, villains.get(i).getLayoutY());
            }
            else {
                board.getChildren().remove(villains.get(i));
                villains.remove(i);
                lives--;
                livesText.setText("Lives: " + lives);
            }
        }
    }
    private void throwWeapon(int d){
        Iterator<Weapon> z=weapons.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            if (x.getLayoutX()<W && x.getLayoutX()>0 && x.getLayoutY()<H && x.getLayoutY()>0){
               switch (x.dir){
                   case N: x.relocate(x.getLayoutX(), x.getLayoutY()+d); break;
                   case S: x.relocate(x.getLayoutX(), x.getLayoutY()-d); break;
                   case W: x.relocate(x.getLayoutX()-d, x.getLayoutY()); break;
                   case E: x.relocate(x.getLayoutX()+d, x.getLayoutY()); break;
                   case NE: x.relocate(x.getLayoutX()+d, x.getLayoutY()+d); break;
                   case SE: x.relocate(x.getLayoutX()+d, x.getLayoutY()-d); break;
                   case SW: x.relocate(x.getLayoutX()-d, x.getLayoutY()-d); break;
                   case NW: x.relocate(x.getLayoutX()-d, x.getLayoutY()+d); break;
               }
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
            Iterator<Node> y=villains.iterator();
            while(y.hasNext()){
                Node currentVillain=y.next();
                if (currentWeapon.getBoundsInParent().intersects(currentVillain.getBoundsInParent())){
                    board.getChildren().remove(currentVillain);
                    y.remove();
                    board.getChildren().remove(currentWeapon);
                    x.remove();
                    score++;
                    scoreText.setText("Score: " + score);
                }
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}
