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
    public abstract boolean skill();
    public static void specialObjectsSkills()
    {
        Game.game.specialObjects.removeIf(SpecialObject::skill);
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
    public boolean skill()
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
            return true;
        }
        return false;
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
    public boolean skill()
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
                 return true;
             }
            else
             {
                 Iterator<Villain> it = Game.game.villains.iterator();
                 while (it.hasNext()) {
                     Villain currentVillain = it.next();
                     currentVillain.checkHitVillainBySpecialO(this, it,true);
                 }
                     for(Hero hero:Game.game.heroes)
                     {
                         if(hero.SObjInvulnerability<0)
                         {
                             hero.checkHitBySObj(this);
                             hero.SObjInvulnerability=50;
                         }
                     }
             }
            duration++;
        }
        return false;
    }
}
class SpiderWeb extends SpecialObject
{
    SpiderWeb()
    {
        super(new Image("resources/Images/SpecialObjects/SpiderWeb.png"));
    }
    @Override
    public boolean skill()
    {
        for(Hero hero:Game.game.heroes)
        {
            if(!hero.slow && hero.getBoundsInParent().intersects(this.getBoundsInParent()))
            {
                hero.slow=true;
                //here slow Hero
                hero.slowDuration=0;
            }
            else if(hero.slow)
            {
                if(hero.slowDuration>500)
                {
                    hero.slow=false;
                }
                else
                {
                    hero.slowDuration++;
                }
            }
        }
        return false;
    }
}
class Swamp extends SpecialObject
{
    Swamp()
    {
        super(new Image("resources/Images/SpecialObjects/SpiderWeb.png"));
    }
    @Override
    public boolean skill()
    {
        for(Hero hero:Game.game.heroes)
        {
            if(!hero.slow && hero.getBoundsInParent().intersects(this.getBoundsInParent()))
            {
                hero.slow=true;
                //here slow Hero
                hero.slowDuration=0;
            }
            else if(hero.slow)
            {
                if(hero.slowDuration>500)
                {
                    hero.slow=false;
                }
                else
                {
                    hero.slowDuration++;
                }
            }
        }
        return false;
    }
}
class SpeedUp extends SpecialObject
{
    SpeedUp()
    {
        super(new Image("resources/Images/SpecialObjects/SpiderWeb.png"));
    }
    @Override
    public boolean skill()
    {
        for(Hero hero:Game.game.heroes)
        {
            if(!hero.speedUp && hero.getBoundsInParent().intersects(this.getBoundsInParent()))
            {
                hero.speedUp=true;
                //here speedup Hero
            }
        }
        return false;
    }

}
class Fire extends SpecialObject {
    Fire()
    {
        super(new Image("resources/Images/SpecialObjects/Fire.png"));
    }
    @Override
    public boolean skill()
    {
        Iterator<Villain> it=Game.game.villains.iterator();
        while(it.hasNext())
        {
            Villain currentVillain=it.next();
            currentVillain.checkHitVillainBySpecialO(this,it,false);
        }
        for(Hero hero:Game.game.heroes)
        {
            if(hero.SObjInvulnerability<0)
            {
                hero.checkHitBySObj(this);
                hero.SObjInvulnerability=50;
            }
        }
        return false;
    }
}
class Bomb extends SpecialObject {
    double x;
    double y;
    int duration=0;
    ImageView boom=new ImageView("resources/Images/SpecialObjects/ExplosionSmall.png");
    Bomb(double x, double y) {
        super(new Image("resources/Images/SpecialObjects/Bomb.png"));
        this.x = x;
        this.y = y;
        lifetime=80;
    }

    @Override
    public boolean skill() {
        if(lifetime>0)
        {
            double dd=Math.sqrt(x*x+y*y);
            if (getLayoutX()<= Game.W && getLayoutX()>=0 && getLayoutY()<= Game.H && getLayoutY()>=0){
                for(Hero hero:Game.game.heroes)
                {
                    if(hero.getBoundsInParent().intersects(getBoundsInParent()))
                    {
                        lifetime=0;
                        boom.relocate(this.getLayoutX()-5,this.getLayoutY()-8);
                        Game.game.board.getChildren().add(boom);
                        Game.game.board.getChildren().remove(this);
                        hero.checkHitBySObj(boom);
                        duration=1;
                    }
                }
                relocate(getLayoutX() + 10*x/dd, getLayoutY() + 10 * y / dd);
            }
            else {
                Game.game.specialObjects.remove(this);
                Game.game.board.getChildren().remove(this);
            }
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
            else if(duration==50)
            {
                boom.setImage(new Image("resources/Images/SpecialObjects/ExplosionBig.png"));
                boom.relocate(boom.getLayoutX()-10, boom.getLayoutY()-6);
            }
            else if(duration==100)
            {
                Game.game.board.getChildren().remove(boom);
                return true;
            }
            else
            {
                    for(Hero hero:Game.game.heroes)
                    {
                        if(hero.SObjInvulnerability<0)
                        {
                            hero.checkHitBySObj(this);
                            hero.SObjInvulnerability=50;
                        }
                    }
            }
            duration++;
        }
        return false;
    }
}
class ShockWave extends SpecialObject {
    ShockWave() {
        super(new Image("resources/Images/SpecialObjects/BrownCircle.png"));
        setOpacity(0.5);
        lifetime=400;
    }

    @Override
    public boolean skill() {
        if(lifetime==300)
        {
            double size=getBoundsInLocal().getWidth();
            this.setImage(new Image("resources/Images/SpecialObjects/BrownCircleBig.png"));
            setOpacity(0.5);
            this.relocate(getLayoutX()+size/2-getBoundsInLocal().getWidth()/2,getLayoutY()+size/2-getBoundsInLocal().getHeight()/2);
        }
        else if(lifetime==0)
        {
            Game.game.board.getChildren().remove(this);
            return true;
        }
        else
        {
            for(Hero hero:Game.game.heroes)
            {
                if(hero.SObjInvulnerability<0)
                {
                    hero.checkHitBySObj(this);
                    hero.SObjInvulnerability=50;
                }
            }
        }
        lifetime--;
        return false;
    }
}
