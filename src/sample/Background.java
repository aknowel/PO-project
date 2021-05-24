package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Background extends ImageView {
    int id;
    Background(Image img)
    {
        super(img);
    }
    public static Cactus newCactus()
    {
        return new Cactus();
    }
    public static Barrel newBarrel()
    {
        return new Barrel();
    }
    public static Stone newStone()
    {
        return new Stone();
    }
    public static Crystal newCrystal()
    {
        return new Crystal();
    }
    public String toString()
    {
        return this.id+" "+this.getLayoutX()+ " " + this.getLayoutY();
    }
}
class Cactus extends Background
{
    Cactus()
    {
        super(new Image("/resources/Images/Background/Cacti.png"));
        this.id=1;
    }
}
class Barrel extends Background
{
    Barrel()
    {
        super(new Image("/resources/Images/Background/Barrel.png"));
        this.id=2;
    }
}
class Stone extends Background
{
    Stone()
    {
        super(new Image("/resources/Images/Background/Stone.png"));
        this.id=3;
    }
}
class Crystal extends Background
{
    Crystal()
    {
        super(new Image("/resources/Images/Background/Crystal.png"));
        this.id=4;
    }
}