package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;
import java.util.Random;


public abstract class Villain extends ImageView {
    static Random randomize=new Random();
    protected Double HP=1000.0;
    protected Double speed;
    protected HpBar hpBar=new HpBar();
    protected int id;
    Villain(Image img)
    {
        super(img);
        changeHpBar();
        Game.game.board.getChildren().add(hpBar);
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
        return HP>0;
    }
    public abstract void changeHpBar();
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
                    currentVillain.changeHpBar();
                    Game.game.board.getChildren().remove(currentWeapon);
                    x.remove();
                    if(!currentVillain.isAlive()) {
                        int i= Game.randomizer.nextInt(4);
                        if(i<4)
                        {
                            Box newBox=Box.getNewBox(i);
                            newBox.relocate(currentVillain.getLayoutX(),currentVillain.getLayoutY());
                            game.boxes.add(newBox);
                            Game.game.board.getChildren().add(newBox);
                        }
                        Game.game.board.getChildren().remove(currentVillain.hpBar);
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
    public static void swordCheck()
    {
        Iterator<Villain> y=Game.game.villains.iterator();
        while(y.hasNext()) {
            Villain currentVillain = y.next();
            if (Game.game.heroes.get(0).sword.getBoundsInParent().intersects(currentVillain.getBoundsInParent())) {
                currentVillain.hp();
                currentVillain.changeHpBar();
                if (!currentVillain.isAlive()) {
                    int i = Game.randomizer.nextInt(20);
                    if (i < 3) {
                        Box newBox = Box.getNewBox(i);
                        newBox.relocate(currentVillain.getLayoutX(), currentVillain.getLayoutY());
                        Game.game.boxes.add(newBox);
                        Game.game.board.getChildren().add(newBox);
                    }
                    Game.game.board.getChildren().remove(currentVillain.hpBar);
                    Game.game.board.getChildren().remove(currentVillain);
                    check(currentVillain.id);
                    y.remove();
                    Game.game.score++;
                    Game.game.counter++;
                    Game.game.scoreText.setText("Score: " + Game.game.score);
                    if (isShooting(currentVillain)) {
                        Game.game.shootingVillains.remove(currentVillain);
                        currentVillain.shout();
                        if (randomize.nextInt(2) == 1) {
                            currentVillain.shout();
                        }
                    }
                }
            }
        }
    }
    public static void newVillain(Game game, boolean z)
    {
        if (game.villainCounter % game.modifier == 0) {
            Villain newVillain = game.villainFactory.produce(Game.randomizer.nextInt((z) ? 10 : 7),Game.game.mode);
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
                case 2 -> newVillain.relocate(Math.random() * (Game.W - newVillain.getBoundsInLocal().getWidth()), Game.H - 30);
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
        return this instanceof Boss;
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
    Skull(Double mode)
        {
            super(new Image("resources/Images/Villains/Skull.png"));
            this.HP= 3+mode;
            this.speed=-1D;
            this.id=0;
        }
        @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarCritic.png"));
        else if(HP<=2)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Predator extends ShootingVillains{
    Predator(Double mode)
    {
        super(new Image("/resources/Images/Villains/Predator.png"));
        this.HP=3+mode;
        this.speed=0D;
        this.id=1;
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarCritic.png"));
        else if(HP<=2)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Spider extends Villain
{
    Spider(Double mode)
    {
        super(new Image("/resources/Images/Villains/Spider.png"));
        this.HP=1+mode;
        this.speed=-2.5D;
        this.id=2;
    }
    @Override
    public void changeHpBar()
    {
        hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Zombie extends Villain
{
    Zombie(Double mode)
    {
        super(new Image("/resources/Images/Villains/Zombie.png"));
        this.HP=5+mode;
        this.speed=-0.5;
        this.id=4;
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarCritic.png"));
        else if(HP<=2)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else if(HP<=4)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Wizard extends ShootingVillains
{
    Wizard(Double mode)
    {
        super(new Image("/resources/Images/Villains/wizard.png"));
        this.HP=2+mode;
        this.speed=-1D;
        this.id=5;
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Vampire extends Villain
{
    Vampire(Double mode)
    {
        super(new Image("/resources/Images/Villains/vampireMini.png"));
        this.HP=2+mode;
        this.speed=-3D;
        this.id=6;
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Orc extends Villain
{
    Orc(Double mode)
    {
        super(new Image("/resources/Images/Villains/orc.png"));
        this.HP=5+mode;
        this.speed=-0.3D;
        this.id=7;
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarCritic.png"));
        else if(HP<=2)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else if(HP<=4)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Ogre extends Villain
{
    Ogre(Double mode)
    {
        super(new Image("/resources/Images/Villains/ogre.png"));
        this.HP=4+mode;
        this.speed=-1D;
        this.id=8;
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarCritic.png"));
        else if(HP<=2)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else if(HP<=3)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Mummy extends Villain
{
    Mummy(Double mode)
    {
        super(new Image("/resources/Images/Villains/Mummy.png"));
        this.HP=4+mode;
        this.speed=-1D;
        this.id=9;
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarCritic.png"));

        else if(HP<=2)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else if(HP<=3)
                hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
class Bat extends Villain
{
    Bat(Double mode)
    {
        super(new Image("/resources/Images/Villains/Bat.png"));
        this.HP=2+mode;
        this.speed=-3D;
        this.id=10;
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBar13.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/MiniHpBarFull.png"));
    }
}
abstract class Boss extends Villain
{
    int spawn=32;
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
    public void skill()
    {

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
    @Override
    public void changeHpBar()
    {
        if(HP<=3)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarCritic.png"));
        else if(HP<=8)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar13.png"));
        else if(HP<=13)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarFull.png"));
    }
}
class SpiderBoss extends Boss
{
    SpiderBoss(Double mode)
    {
        super(new Image("/resources/Images/Villains/SpiderBoss.png"));
        this.HP=40*(mode+1);
        this.speed=-1.75D;
        this.id=11;
    }
    @Override
    public void skill()
    {
        if(spawn==0) {
            Villain newVillain = new Spider(Game.game.mode);
            newVillain.relocate(this.getLayoutX(), this.getLayoutY());
            Game.game.villains.add(newVillain);
            Game.game.board.getChildren().add(newVillain);
            spawn=350;
        }
        else
        {
            spawn--;
        }
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=5)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarCritic.png"));
        else if(HP<=15)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar13.png"));
        else if(HP<=30)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarFull.png"));
    }
}
class VampireBoss extends Boss
{
    VampireBoss(Double mode)
    {
        super(new Image("/resources/Images/Villains/VampireBig.png"));
        this.HP=30*(mode+1);
        this.speed=-2.5D;
        this.id=12;
    }
    @Override
    public void skill()
    {
        if(spawn==0) {
            Villain newVillain = new Bat(Game.game.mode);
            newVillain.relocate(this.getLayoutX(), this.getLayoutY());
            Game.game.villains.add(newVillain);
            Game.game.board.getChildren().add(newVillain);
            spawn=350;
        }
        else
        {
            spawn--;
        }
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=5)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarCritic.png"));
        else if(HP<=12)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar13.png"));
        else if(HP<=22)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarFull.png"));
    }
}
class Loki extends Boss
{
    Loki(Double mode)
    {
        super(new Image("/resources/Images/Villains/Loki.png"));
        this.HP=60*(mode+1);
        this.speed=-2.5D;
        this.id=13;
    }
    @Override
    public void skill()
    {
        if (spawn==0) {
            double x = this.getLayoutX();
            double y = this.getLayoutY();
            double z = Game.game.heroes.get(0).getLayoutX();
            double v = Game.game.heroes.get(0).getLayoutY();
            Weapon newWeapon = new Star(v - y, x - z);
            newWeapon.relocate(this.getLayoutX() , this.getLayoutY() );
            Game.game.weaponsVillain.add(newWeapon);
            Game.game.board.getChildren().add(newWeapon);
            newWeapon = new Star(y - v, z - x);
            newWeapon.relocate(this.getLayoutX() , this.getLayoutY() );
            Game.game.weaponsVillain.add(newWeapon);
            Game.game.board.getChildren().add(newWeapon);
            newWeapon = new Star(x-z, y-v);
            newWeapon.relocate(this.getLayoutX() , this.getLayoutY() );
            Game.game.weaponsVillain.add(newWeapon);
            Game.game.board.getChildren().add(newWeapon);
            spawn=32;
        }
        else
        {
            spawn--;
        }
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=7)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarCritic.png"));
        else if(HP<=25)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar13.png"));
        else if(HP<=45)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarFull.png"));
    }
}


