package sample;

import javafx.scene.Node;
import javafx.scene.image.ImageView;

import java.util.Iterator;

public class Hero extends ImageView {
    Hero()
    {
        super("resources/Images/Thor.png");
    }
    static void checkHitHero(Game game)
    {
        Iterator<Weapon> x=game.weaponsVillain.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
            if (currentWeapon.getBoundsInParent().intersects(game.hero.getBoundsInParent())){
                game.lives--;
                game.livesText.setText("Lives: " + game.lives);
                Game.game.board.getChildren().remove(currentWeapon);
                x.remove();
            }
        }
    }
    public String toString()
    {
        return this.getLayoutX()+ " " + this.getLayoutY();
    }
}
