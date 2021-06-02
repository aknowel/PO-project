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
    int hp;

    Hero() {
        super("resources/Images/Thor.png");
        goNorth = goSouth = goEast = goWest = false;
        pos_x = 20;
        pos_y = Game.H /2;
        hp = 10;
    }
    Hero(double x,double y, int hp) {
        super("resources/Images/Thor.png");
        goNorth = goSouth = goEast = goWest = false;
        pos_x = x;
        pos_y = y;
        this.hp = hp;
    }
    Hero(String path_to_image)
    {
        super(path_to_image);
        goNorth = goSouth = goEast = goWest = false;
        pos_x = getLayoutX();
        pos_y = getLayoutY();
        hp = 10;
    }
    void checkHitHero(Game game)
    {
        Iterator<Weapon> x=game.weaponsVillain.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
            if (currentWeapon.getBoundsInParent().intersects(getBoundsInParent())){
                hp -= 1;
                game.hp_texts.get(this).setText("HP: " + hp);
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
        return this.getLayoutX()+ " " + this.getLayoutY() + " " + this.hp;
    }
    static public Hero getNewThor(double x, double y, int hp)
    {
        return new Hero(x,y, hp);
    }
}
