package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.ArrayList;

public class Villain extends ImageView {
    protected int HP;
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
    public static Villain getNewVillain(int i)
    {
        return switch (i) {
            case 0 -> new Skull();
            case 1 -> new Predator();
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
    Skull()
        {
            super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/32/skull-3-icon.png"));
            this.HP=1;
            this.speed=-3;
        }
}
class Predator extends Villain{
    Predator()
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/32/predator-icon.png"));
        this.HP=2;
        this.speed=-2;
    }
}


