package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;

public abstract class SpecialObject extends ImageView {
    int lifetime;
    SpecialObject(Image img)
    {
        super(img);
    }
    public abstract void skill();
    public static void specialObjectsSkills()
    {
        for (SpecialObject specialObject : Game.game.specialObjects) {
            specialObject.skill();
        }
    }
}
class PoisonCloud extends SpecialObject {
    PoisonCloud()
    {
        super(new Image("resources/Images/PoisonCloud.png"));
        lifetime=1000;
    }
    @Override
    public void skill()
    {
        if(lifetime>0)
        {
            Iterator<Villain> it=Game.game.villains.iterator();
            while(it.hasNext())
            {
                Villain currentVillain=it.next();
                currentVillain.checkHitVillainBySpecialO(this,it);
            }
            lifetime--;
        }
        else
        {
            Game.game.board.getChildren().remove(this);
            Game.game.specialObjects.remove(this);
        }

    }
}

