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
    static void newEnemyWeapon()
    {
        for (Villain currentVillain : Game.game.shootingVillains) {
            double x = currentVillain.getLayoutX();
            double y = currentVillain.getLayoutY();
            int i=Hero.minDistance(currentVillain);
            double z = Game.game.heroes.get(i).getLayoutX();
            double v = Game.game.heroes.get(i).getLayoutY();
            Weapon newWeapon;
            if (currentVillain.getVillainId() == 1||currentVillain.getVillainId() == 3) {
                newWeapon = new RedBall(z - x, v - y);
            } else
            {
                newWeapon = new Star(z - x, v - y);
            }
            newWeapon.relocate(currentVillain.getLayoutX() , currentVillain.getLayoutY() );
            Game.game.weaponsVillain.add(newWeapon);
            Game.game.board.getChildren().add(newWeapon);
        }
    }
    public static Weapon getNewWeapon(int id)
    {
        return switch(id) {
            case 1 -> new Hammer();
            case 2 -> new SuperHammer();
            case 3 -> new RedBall();
            case 4 -> new Star();
            case 6 -> new Shuriken();
            case 7 -> new Axe();
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
    }
    public static Weapon getNewWeapon(int id,double x,double y)
    {
        return switch(id) {
            case 1 -> new Hammer(x,y);
            case 2 -> new SuperHammer(x,y);
            case 3 -> new RedBall(x,y);
            case 4 -> new Star(x,y);
            case 6 -> new Shuriken(x,y);
            case 7 -> new Axe(x,y);
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
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
    public static Star newStar(double x,double y)
    {
        return new Star(x,y);
    }
    public static Shuriken newShuriken(double x,double y)
    {
        return new Shuriken(x,y);
    }
    public static Axe newAxe(double x,double y)
    {
        return new Axe(x,y);
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
class Sword extends Weapon
{
    Sword()
    {
        super(new Image("resources/Images/Melee/Sword.png"));
        this.id=5;
    }
}
class Shuriken extends Weapon
{
    Shuriken()
    {
        super(new Image("resources/Images/Weapons/Shuriken.png"));
    }
    Shuriken(double x,double y)
    {
        super(new Image("resources/Images/Weapons/Shuriken.png"));
        this.x=x;
        this.y=y;
        this.id=6;
    }
}
class Axe extends Weapon
{
    Axe()
    {
        super(new Image("resources/Images/Weapons/Axe.png"));
    }
    Axe(double x,double y)
    {
        super(new Image("resources/Images/Weapons/Axe.png"));
        this.x=x;
        this.y=y;
        this.id=7;
    }
}