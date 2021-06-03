package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class HpBars extends ImageView {
    int id;
    double x;
    double y;
    HpBars(Image img)
    {
        super(img);
    }
    public String toString()
    {
        return this.id+" "+this.x+ " " + this.y;
    }
    public void setCoordinates(double x,double y)
    {
        this.x=x;
        this.y=y;
        this.relocate(x,y);
    }
}
class HpBarFull extends HpBars
{
    HpBarFull()
    {
        super(new Image("resources/Images/HpBars/HpBarFull.png"));
        id=1;
    }
}
class HpBar23 extends HpBars
{
    HpBar23()
    {
        super(new Image("resources/Images/HpBars/HpBar23.png"));
        id=2;
    }
}
class HpBar13 extends HpBars
{
    HpBar13()
    {
        super(new Image("resources/Images/HpBars/HpBar13.png"));
        id=3;
    }
}
class HpBarCritic extends HpBars
{
    HpBarCritic()
    {
        super(new Image("resources/Images/HpBars/HpBarCrit.png"));
        id=4;
    }
}
class MiniHpBarFull extends HpBars
{
    MiniHpBarFull()
    {
        super(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
        id=5;
    }
}
class MiniHpBar23 extends HpBars
{
    MiniHpBar23()
    {
        super(new Image("resources/Images/HpBars/MiniHpBar23.png"));
        id=6;
    }
}
class MiniHpBar13 extends HpBars
{
    MiniHpBar13()
    {
        super(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        id=7;
    }
}
class MiniHpBarCritic extends HpBars
{
    MiniHpBarCritic()
    {
        super(new Image("resources/Images/HpBars/MiniHpBarCrit.png"));
        id=8;
    }
}