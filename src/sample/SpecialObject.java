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
    public static void specialObjectsSkills(GameAsServer gameAsServer)
    {
        for (SpecialObject specialObject : gameAsServer.specialObjects) {
            specialObject.skill();
        }
    }
}
class PoisonCloud extends SpecialObject {
    PoisonCloud()
    {
        super(new Image("resources/Images/SpecialObjects/PoisonCloud.png"));
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
                currentVillain.checkHitVillainBySpecialO(this,it,false);
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
class TNT extends SpecialObject {
    int duration=0;
    ImageView boom=new ImageView("resources/Images/SpecialObjects/ExplosionSmall.png");
    TNT()
    {
        super(new Image("resources/Images/SpecialObjects/Tnt.png"));
        lifetime=250;
    }
    @Override
    public void skill()
    {
        if(lifetime>0)
        {
            lifetime--;
        }
        else
        {
            if(duration==0)
            {
                boom.relocate(this.getLayoutX()-5,this.getLayoutY()-8);
                Game.game.board.getChildren().add(boom);
                Game.game.board.getChildren().remove(this);
            }
             else if(duration==75)
            {
                boom.setImage(new Image("resources/Images/SpecialObjects/ExplosionBig.png"));
                boom.relocate(boom.getLayoutX()-10, boom.getLayoutY()-6);
            }

            else if(duration==150)
            {
                boom.setImage(new Image("resources/Images/SpecialObjects/ExplosionGiga.png"));
                boom.relocate(boom.getLayoutX()-7, boom.getLayoutY()-10);
            }
            else if(duration==200)
             {
                 Game.game.board.getChildren().remove(boom);
                 Game.game.specialObjects.remove(this);
             }
            else
             {
                 Iterator<Villain> it = Game.game.villains.iterator();
                 while (it.hasNext()) {
                     Villain currentVillain = it.next();
                     currentVillain.checkHitVillainBySpecialO(this, it,true);
                 }
                 if(duration%49==0)
                 {
                     for(Hero hero:Game.game.heroes)
                     {
                         hero.checkHitByTNT(boom);
                     }
                 }
             }
            duration++;
        }

    }
}

