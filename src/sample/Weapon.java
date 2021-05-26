package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Weapon extends ImageView {
    protected int id;
    protected double x;
    protected double y;

    Weapon(Image img){
        super(img);
    }
    static void newEnemyWeapon(Game game)
    {
        for (Villain currentVillain : game.shootingVillains) {
            double x = currentVillain.getLayoutX();
            double y = currentVillain.getLayoutY();
            double z = game.hero.getLayoutX();
            double v = game.hero.getLayoutY();
            Weapon newWeapon;
            if (currentVillain.getVillainId() == 1||currentVillain.getVillainId() == 3) {
                newWeapon = new RedBall(z - x, v - y);
            } else
            {
                newWeapon = new Star(z - x, v - y);
            }
            newWeapon.relocate(currentVillain.getLayoutX() , currentVillain.getLayoutY() );
            game.weaponsVillain.add(newWeapon);
            game.board.getChildren().add(newWeapon);
        }
    }
    public static Hammer newHammer(double x,double y)
    {
        return new Hammer(x,y);
    }
    public static SuperHammer newSuperHammer(double x,double y)
    {
        return new SuperHammer(x,y);
    }
    public static RedBall newRedBall(double x,double y)
    {
        return new RedBall(x,y);
    }
    public String toString()
    {
        return this.id + " " + this.x + " " + this.y + " " + this.getLayoutX()+ " " + this.getLayoutY();
    }
}
class Hammer extends Weapon
{
    Hammer()
    {
        super(new Image("resources/Images/Weapons/Hammer.png"));
    }
    Hammer(double x,double y)
    {
        super(new Image("resources/Images/Weapons/Hammer.png"));
        this.x=x;
        this.y=y;
        this.id=1;
    }
}
class SuperHammer extends Weapon
{
    SuperHammer()
    {
        super(new Image("resources/Images/Weapons/SuperHammer.png"));
    }
    SuperHammer(double x,double y)
    {
        super(new Image("resources/Images/Weapons/SuperHammer.png"));
        this.x=x;
        this.y=y;
        this.id=2;
    }
}
class RedBall extends Weapon
{
    RedBall()
    {
        super(new Image("resources/Images/Weapons/PredatorBullet.png"));
    }
    RedBall(double x,double y)
    {
        super(new Image("resources/Images/Weapons/PredatorBullet.png"));
        this.x=x;
        this.y=y;
        this.id=3;
    }
}
class Star extends Weapon
{
    Star()
    {
        super(new Image("resources/Images/Weapons/Star.png"));
    }
    Star(double x,double y)
    {
        super(new Image("resources/Images/Weapons/Star.png"));
        this.x=x;
        this.y=y;
        this.id=4;
    }
}