package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public class Villain extends ImageView {
    protected Double HP;
    protected int speed;
    Villain()
    {

    }
    Villain(Image img)
    {
        super(img);
    }
     public void  hp()
    {
        HP--;
    }
    public static Villain getNewVillain(int i, Double mode)
    {
        return switch (i) {
            case 0 -> new Skull(mode);
            case 1 -> new Predator(mode);
            case 2-> new Boss(mode);
            default -> new Villain();
        };
    }
    public boolean isAlive()
    {
        return HP!=0;
    }
    int getSpeed()
    {
        return speed;
    }
}
class Skull extends Villain{
    Skull(Double mode)
        {
            super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/32/skull-3-icon.png"));
            this.HP= 1+mode;
            this.speed=-3;
        }
}
class Predator extends Villain{
    Predator(Double mode)
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/32/predator-icon.png"));
        this.HP=2+mode;
        this.speed=-2;
    }
}
class Boss extends Villain
{
    Boss(Double mode)
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/64/predator-icon.png"));
        this.HP=20*(mode+1);
        this.speed=-2;
    }
}



