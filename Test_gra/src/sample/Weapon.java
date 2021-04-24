package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Weapon extends ImageView {
    protected double x;
    protected double y;
    Weapon(Image img){
        super(img);
    }

}
class Hammer extends Weapon
{
    Hammer(double x,double y)
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/windows-8/16/Cultures-Thor-Hammer-icon.png"));
        this.x=x;
        this.y=y;
    }
}
class SuperHammer extends Weapon
{
    SuperHammer(double x,double y)
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/windows-8/24/Cultures-Thor-Hammer-icon.png"));
        this.x=x;
        this.y=y;
    }
}
class RedBall extends Weapon
{
    RedBall(double x,double y)
    {
        super(new Image("https://icons.iconarchive.com/icons/sekkyumu/developpers/16/Red-Ball-icon.png"));
        this.x=x;
        this.y=y;
    }
}