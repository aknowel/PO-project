package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;
import java.util.Random;


public abstract class Villain extends ImageView {
    static Random randomize=new Random();
    protected Double HP;
    protected Double speed;
    protected int id;
    Villain(Image img)
    {
        super(img);
    }
     public void  hp()
    {
        HP--;
    }
    public static Boss getNewPredatorBoss(Double mode)
    {
        return new PredatorBoss(mode);
    }
    public static Boss getNewSpiderBoss(Double mode)
    {
        return new SpiderBoss(mode);
    }
    public static Boss getNewLoki(Double mode)
    {
        return new Loki(mode);
    }
    public static Boss getNewVampireBoss(Double mode)
    {
        return new VampireBoss(mode);
    }
    public boolean isAlive()
    {
        return HP!=0;
    }
    Double getSpeed()
    {
        return speed;
    }
    static void checkHitVillain(Game game){
        Iterator<Weapon> x=game.weaponsHero.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
            Iterator<Villain> y=game.villains.iterator();
            while(y.hasNext()){
                Villain currentVillain=y.next();
                if (currentWeapon.getBoundsInParent().intersects(currentVillain.getBoundsInParent())){
                    currentVillain.hp();
                    Game.game.board.getChildren().remove(currentWeapon);
                    x.remove();
                    if(!currentVillain.isAlive()) {
                        int i= Game.randomizer.nextInt(20);
                        if(i<3)
                        {
                            Box newBox=Box.getNewBox(i);
                            newBox.relocate(currentVillain.getLayoutX(),currentVillain.getLayoutY());
                            game.boxes.add(newBox);
                            Game.game.board.getChildren().add(newBox);
                        }
                        Game.game.board.getChildren().remove(currentVillain);
                        check(currentVillain.id);
                        y.remove();
                       game.score++;
                       game.counter++;
                        game.scoreText.setText("Score: " + game.score);
                        if(isShooting(currentVillain))
                        {
                            game.shootingVillains.remove(currentVillain);
                            currentVillain.shout();
                            if(randomize.nextInt(2)==1)
                            {
                                currentVillain.shout();
                            }
                        }
                    }
                }
            }
        }
    }
    public static void newVillain(Game game)
    {
        if (game.villainCounter % game.modifier == 0) {
            Villain newVillain = game.villainFactory.produce(Game.randomizer.nextInt(7),Game.game.mode);
            int r = Game.randomizer.nextInt(4);
            switch (r) {
                case 0 -> {
                    newVillain.relocate(Game.W -30d, Math.random() * (Game.H - newVillain.getBoundsInLocal().getHeight()));
                    game.modifier--;
                }
                case 1 -> {
                    newVillain.relocate(0, Math.random() * (Game.H - newVillain.getBoundsInLocal().getHeight()));
                    game.modifier--;
                }
                case 2 -> newVillain.relocate(Math.random() * (Game.W - newVillain.getBoundsInLocal().getWidth()), Game.H -30);
                case 3 -> newVillain.relocate(Math.random() * (Game.W - newVillain.getBoundsInLocal().getWidth()), 0);
            }
            game.villains.add(newVillain);
            Game.game.board.getChildren().add(newVillain);
            if(newVillain instanceof ShootingVillains)
            {
                game.shootingVillains.add(newVillain);
            }
        }
    }
    public void setHP(double HP)
    {
        this.HP=HP;
    }
    public static Skull newSkull(double mode)
    {
        return new Skull(mode);
    }
    public static Predator newPredator(double mode)
    {
        return new Predator(mode);
    }
    public static Spider newSpider(double mode)
    {
        return new Spider(mode);
    }
    public static Bat newBat(double mode)
    {
        return new Bat(mode);
    }
    public static Mummy newMummy(double mode)
    {
        return new Mummy(mode);
    }
    public static Ogre newOgre(double mode)
    {
        return new Ogre(mode);
    }
    public static Orc newOrc(double mode)
    {
        return new Orc(mode);
    }
    public static Vampire newVampire(double mode)
    {
        return new Vampire(mode);
    }
    public static Wizard newWizard(double mode)
    {
        return new Wizard(mode);
    }
    public static Zombie newZombie(double mode)
    {
        return new Zombie(mode);
    }
    public static boolean isShooting(Villain villain)
    {
        return villain.id==1||villain.id==5;
    }
    public int getVillainId()
    {
        return id;
    }
    public boolean isBoss()
    {
        return this.id==3;
    }
    public static void check(int id)
    {
        switch(id)
        {
            case 0 -> Counter.killedSkull();
            case 1 -> Counter.killedPredator();
            case 2 -> Counter.killedSpider();
        }
    }
    public void shout()
    {
        Sounds sounds=new Sounds();
        sounds.playCritic();
    }
    public String toString()
    {
        return this.id + " "  + this.HP+ " "+ this.getLayoutX()+ " " + this.getLayoutY();
    }
}
class Skull extends Villain{
    Skull()
    {
        super(new Image("resources/Images/Villains/Skull.png"));
    }
    Skull(Double mode)
        {
            super(new Image("resources/Images/Villains/Skull.png"));
            this.HP= 3+mode;
            this.speed=-1D;
            this.id=0;
        }
}
class Predator extends ShootingVillains{
    Predator()
    {
        super(new Image("/resources/Images/Villains/Predator.png"));
    }
    Predator(Double mode)
    {
        super(new Image("/resources/Images/Villains/Predator.png"));
        this.HP=3+mode;
        this.speed=0D;
        this.id=1;
    }
}
class Spider extends Villain
{
    Spider()
    {
        super(new Image("/resources/Images/Villains/Spider.png"));
    }
    Spider(Double mode)
    {
        super(new Image("/resources/Images/Villains/Spider.png"));
        this.HP=1+mode;
        this.speed=-2D;
        this.id=2;
    }
}
class Zombie extends Villain
{
    Zombie()
    {
        super(new Image("/resources/Images/Villains/Zombie.png"));
    }
    Zombie(Double mode)
    {
        super(new Image("/resources/Images/Villains/Zombie.png"));
        this.HP=4+mode;
        this.speed=-0.5;
        this.id=4;
    }
}
class Wizard extends ShootingVillains
{
    Wizard()
    {
        super(new Image("/resources/Images/Villains/wizard.png"));
    }
    Wizard(Double mode)
    {
        super(new Image("/resources/Images/Villains/wizard.png"));
        this.HP=1+mode;
        this.speed=-1D;
        this.id=5;
    }
}
class Vampire extends Villain
{
    Vampire()
    {
        super(new Image("/resources/Images/Villains/vampireMini.png"));
    }
    Vampire(Double mode)
    {
        super(new Image("/resources/Images/Villains/vampireMini.png"));
        this.HP=2+mode;
        this.speed=-3D;
        this.id=6;
    }
}
class Orc extends Villain
{
    Orc()
    {
        super(new Image("/resources/Images/Villains/orc.png"));
    }
    Orc(Double mode)
    {
        super(new Image("/resources/Images/Villains/orc.png"));
        this.HP=5+mode;
        this.speed=-0.3D;
        this.id=7;
    }
}
class Ogre extends Villain
{
    Ogre()
    {
        super(new Image("/resources/Images/Villains/ogre.png"));
    }
    Ogre(Double mode)
    {
        super(new Image("/resources/Images/Villains/ogre.png"));
        this.HP=4+mode;
        this.speed=-1D;
        this.id=8;
    }
}
class Mummy extends Villain
{
    Mummy()
    {
        super(new Image("/resources/Images/Villains/Mummy.png"));
    }
    Mummy(Double mode)
    {
        super(new Image("/resources/Images/Villains/Mummy.png"));
        this.HP=4+mode;
        this.speed=-1D;
        this.id=9;
    }
}
class Bat extends Villain
{
    Bat()
    {
        super(new Image("/resources/Images/Villains/Bat.png"));
    }
    Bat(Double mode)
    {
        super(new Image("/resources/Images/Villains/Bat.png"));
        this.HP=2+mode;
        this.speed=-3D;
        this.id=10;
    }
}
abstract class Boss extends Villain
{
    Boss(Image img)
    {
        super(img);
    }
    @Override
    public void shout()
    {
        Sounds sounds=new Sounds();
        if(randomize.nextInt(2)==1) {
            sounds.playBossIntro();
        }
        else
        {
            sounds.playBossLaugh();
        }
    }
}
abstract class ShootingVillains extends Villain
{
    ShootingVillains(Image img)
    {
        super(img);
    }
}
class PredatorBoss extends Boss
{
    PredatorBoss(Double mode)
    {
        super(new Image("/resources/Images/Villains/PredatorBoss.png"));
        this.HP=20*(mode+1);
        this.speed=-3D;
        this.id=3;
    }
}
class SpiderBoss extends Boss
{
    SpiderBoss(Double mode)
    {
        super(new Image("/resources/Images/Villains/SpiderBoss.png"));
        this.HP=40*(mode+1);
        this.speed=-2D;
        this.id=11;
    }
}
class VampireBoss extends Boss
{
    VampireBoss(Double mode)
    {
        super(new Image("/resources/Images/Villains/VampireBig.png"));
        this.HP=30*(mode+1);
        this.speed=-4D;
        this.id=12;
    }
}
class Loki extends Boss
{
    Loki(Double mode)
    {
        super(new Image("/resources/Images/Villains/Loki.png"));
        this.HP=60*(mode+1);
        this.speed=-2D;
        this.id=13;
    }
}


