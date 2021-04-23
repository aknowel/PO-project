package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;


public abstract class Villain extends ImageView {
    protected Double HP;
    protected int speed;
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
            case 0, 1, 2 -> new Skull(mode);
            case 3, 4, 5 -> new Spider(mode);
            default -> new Predator(mode);
        };
    }
    public static Boss getNewBoss(Double mode)
    {
        return new Boss(mode);
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
            this.HP= 2+mode;
            this.speed=-1;
        }
}
class Predator extends Villain{
    Predator(Double mode)
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/32/predator-icon.png"));
        this.HP=3+mode;
        this.speed=0;
    }
}
class Spider extends Villain
{
    Spider(Double mode)
    {
        super(new Image("https://icons.iconarchive.com/icons/iconsmind/outline/32/Spider-icon.png"));
        this.HP=1+mode;
        this.speed=-2;
    }
}
class Boss extends Villain
{
    Boss(Double mode)
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/64/predator-icon.png"));
        this.HP=20*(mode+1);
        this.speed=-3;
    }
}



