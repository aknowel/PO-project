package sample;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Iterator;

public class Hero extends ImageView {
    boolean goNorth, goSouth, goEast, goWest;
    double pos_x;
    double pos_y;
    double dx;
    double dy;

    Hero() {
        super("resources/Images/Thor.png");
        goNorth = goSouth = goEast = goWest = false;
        pos_x = getLayoutX();
        pos_y = getLayoutY();
    }
    Hero(String path_to_image)
    {
        super(path_to_image);
        goNorth = goSouth = goEast = goWest = false;
        pos_x = getLayoutX();
        pos_y = getLayoutY();
    }
    static void checkHitHero(Game game)
    {
        Iterator<Weapon> x=game.weaponsVillain.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
            if (currentWeapon.getBoundsInParent().intersects(game.heroes.get(0).getBoundsInParent())){
                game.lives--;
                game.livesText.setText("Lives: " + game.lives);
                Game.game.board.getChildren().remove(currentWeapon);
                x.remove();
            }
        }
    }
    public void shout()
    {
        Sounds sounds=new Sounds();
        sounds.playHuh();
    }
    public String toString()
    {
        return this.getLayoutX()+ " " + this.getLayoutY();
    }
}
