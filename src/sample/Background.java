package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Background extends ImageView {
    int id;
    Background(Image img)
    {
        super(img);
    }
    public static Background newBackground (int id) throws IllegalStateException
    {
        return switch(id) {
            case 1 -> new Cactus();
            case 2 -> new Barrel();
            case 3 -> new Stone();
            case 4 -> new Crystal();
            case 5 -> new Stone2();
            case 6 -> new Shrub();
            case 7 -> new MaleBust();
            case 8 -> new FemaleBust();
            default -> throw new IllegalStateException("Unexpected value: " + id);
        };
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
class Stone2 extends Background
{
    Stone2()
    {
        super(new Image("/resources/Images/Background/Stone2.png"));
        this.id=5;
    }
}
class Shrub extends Background
{
    Shrub()
    {
        super(new Image("/resources/Images/Background/Shrub.png"));
        this.id=6;
    }
}
class MaleBust extends Background
{
    MaleBust()
    {
        super(new Image("/resources/Images/Background/MaleBust.png"));
        this.id=7;
    }
}
class FemaleBust extends Background
{
    FemaleBust()
    {
        super(new Image("/resources/Images/Background/FemaleBust.png"));
        this.id=8;
    }
}