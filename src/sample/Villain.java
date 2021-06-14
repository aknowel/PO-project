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
    protected int cooldownSO=0;
    protected int id;
    Villain(Image img)
    {
        super(img);
        changeHpBar();
        Game.game.board.getChildren().add(hpBar);
    }
     public void hp()
    {
        HP--;
    }
    public void kill()
    {
        HP=0D;
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
    public static Boss getNewBombman(Double mode)
    {
        return new Bombman(mode);
    }
    public static Boss getNewOgreBoss(Double mode)
    {
        return new OgreBoss(mode);
    }
    public static Villain getNewVillain(int id)
    {
        Villain villain;
        switch (id) {
            case 0 -> villain=Villain.newSkull(0D);
            case 1 -> {
                villain=Villain.newPredator(0D);
                Game.game.shootingVillains.add(villain);
            }
            case 2 -> villain=Villain.newSpider(0D);
            case 4 -> villain=Villain.newZombie(0D);
            case 5 -> {
                villain = Villain.newWizard(0D);
                Game.game.shootingVillains.add(villain);
            }
            case 6 ->villain=Villain.newVampire(0D);
            case 7 -> villain=Villain.newOrc(0D);
            case 8 -> villain=Villain.newOgre(0D);
            case 9 -> villain=Villain.newMummy(0D);
            case 10 -> villain=Villain.newBat(0D);
            case 16 -> villain=Villain.newSpider2(0D);
            default -> throw new IllegalStateException("Unexpected value: " + id);
        }
        return villain;
    }
    public boolean isAlive()
    {
        return HP>0;
    }
    public abstract void changeHpBar();
    public void setHpBar()
    {
        hpBar.relocate(getLayoutX() + getBoundsInLocal().getWidth()/2-hpBar.getBoundsInLocal().getWidth()/2, getLayoutY() - hpBar.getBoundsInLocal().getHeight()-3);
    }
    public void setSpawn(int spawn)
    {
        if(this instanceof Boss) {
            Boss boss = (Boss) this;
            boss.spawn=spawn;
        }
    }
    public static void cooldownSODecrease()
    {
        for(Villain villain:Game.game.villains)
        {
            villain.cooldownSO--;
        }
    }
    Double getSpeed()
    {
        return speed;
    }
    static void checkHitVillains(){
        Iterator<Weapon> x=Game.game.weaponsHero.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
            Iterator<Villain> y=Game.game.villains.iterator();
            while(y.hasNext()){
                Villain currentVillain=y.next();
                currentVillain.checkHitVillainByWeapon( currentWeapon,y,x);
                }
            }
        }
    public void checkHitVillainByWeapon(Node currentObject,Iterator<Villain> y,Iterator<?> x)
    {
            if (currentObject.getBoundsInParent().intersects(getBoundsInParent())) {
                hp();
                changeHpBar();
                Game.game.board.getChildren().remove(currentObject);
                x.remove();
                if (!isAlive()) {
                    int i = Game.randomizer.nextInt(20);
                    if (i < 4) {
                        Box newBox = Box.getNewBox(i);
                        newBox.relocate(getLayoutX(), getLayoutY());
                        Game.game.boxes.add(newBox);
                        Game.game.board.getChildren().add(newBox);
                    }
                    Game.game.board.getChildren().remove(hpBar);
                    Game.game.board.getChildren().remove(this);
                    check(id);
                    y.remove();
                    Game.game.score++;
                    Game.game.counter++;
                    Game.game.scoreText.setText("Score: " + Game.game.score);
                    if (isShooting(this)) {
                        Game.game.shootingVillains.remove(this);
                        if (randomize.nextInt(2) == 1) {
                            shout();
                        }
                    }
                }
            }
    }
    public void checkHitVillainBySpecialO(Node currentObject,Iterator<Villain> y,boolean boomCheck)
    {
        if(cooldownSO<=0 || boomCheck) {
            if (currentObject.getBoundsInParent().intersects(getBoundsInParent())) {
                if(boomCheck)
                {
                    kill();
                }
                else {
                    hp();
                    changeHpBar();
                }
                if (!isAlive()) {
                    int i = Game.randomizer.nextInt(20);
                    if (i < 4) {
                        Box newBox = Box.getNewBox(i);
                        newBox.relocate(getLayoutX(), getLayoutY());
                        Game.game.boxes.add(newBox);
                        Game.game.board.getChildren().add(newBox);
                    }
                    Game.game.board.getChildren().remove(hpBar);
                    Game.game.board.getChildren().remove(this);
                    check(id);
                    y.remove();
                    Game.game.score++;
                    Game.game.counter++;
                    Game.game.scoreText.setText("Score: " + Game.game.score);
                    if (isShooting(this)) {
                        Game.game.shootingVillains.remove();
                        if (randomize.nextInt(2) == 1) {
                            shout();
                        }
                    }
                }
            }
            cooldownSO=100;
        }
    }
    public static void swordCheck()
    {
        Iterator<Villain> y=Game.game.villains.iterator();
        while(y.hasNext()) {
            Villain currentVillain = y.next();
            for(Hero hero: Game.game.heroes) {
                if (hero.sword.getBoundsInParent().intersects(currentVillain.getBoundsInParent())) {
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
                            if (randomize.nextInt(2) == 1) {
                                currentVillain.shout();
                            }
                        }
                    }
                }
            }
        }
    }
    public static void newVillain(boolean z)
    {
        if (Game.game.villainCounter % Game.game.modifier == 0) {
            Villain newVillain = Game.game.villainFactory.produce(Game.randomizer.nextInt((z) ? 10 : 7),Game.game.mode);
            int r = Game.randomizer.nextInt(4);
            switch (r) {
                case 0 -> {
                    newVillain.relocate(Game.W -30d, Math.random() * (Game.H - newVillain.getBoundsInLocal().getHeight()));
                    Game.game.modifier--;
                }
                case 1 -> {
                    newVillain.relocate(0, Math.random() * (Game.H - newVillain.getBoundsInLocal().getHeight()));
                    Game.game.modifier--;
                }
                case 2 -> newVillain.relocate(Math.random() * (Game.W - newVillain.getBoundsInLocal().getWidth()), Game.H - 30);
                case 3 -> newVillain.relocate(Math.random() * (Game.W - newVillain.getBoundsInLocal().getWidth()), 0);
            }
            Game.game.villains.add(newVillain);
            Game.game.board.getChildren().add(newVillain);
            if(newVillain instanceof ShootingVillains)
            {
                Game.game.shootingVillains.add(newVillain);
            }
            if(Game.game.modifier<5)
            {
                Game.game.modifier++;
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
    public static Spider2 newSpider2 (double mode) {
        return new Spider2(mode);
    }
    public static boolean isShooting(Villain villain)
    {
        return villain.id==1||villain.id==5;
    }
    public void setCooldownSO(int cooldownSO)
    {
        this.cooldownSO=cooldownSO;
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
            case 10 -> Counter.killedBat();
            case 9 -> Counter.killedMummy();
            case 8 -> Counter.killedOgre();
            case 7 -> Counter.killedOrc();
            case 5 -> Counter.killedVampire();
            case 6 -> Counter.killedWizard();
            case 4 -> Counter.killedZombie();
        }
    }
    public void shout()
    {
        Sounds sounds=new Sounds();
        sounds.playCritic();
    }
    public String toString()
    {
        return this.id + " "  + this.HP+ " "+ this.getLayoutX()+ " " + this.getLayoutY()+ " " + cooldownSO;
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
class Spider2 extends Villain
{
    Spider2(Double mode)
    {
        super(new Image("/resources/Images/Villains/Spider2.png"));
        this.HP=1+mode;
        this.speed=-2.5D;
        this.id=16;
    }
    @Override
    public void changeHpBar()
    {
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
    public String toString()
    {
        return this.id + " "  + this.HP+ " "+ this.getLayoutX()+ " " + this.getLayoutY()+ " " + cooldownSO+ " "+spawn;
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
            int i=Hero.minDistance(this);
            double z = Game.game.heroes.get(i).getLayoutX();
            double v = Game.game.heroes.get(i).getLayoutY();
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
class Bombman extends Boss
{
    Bombman(Double mode)
    {
        super(new Image("/resources/Images/Villains/Bomber.png"));
        this.HP=25*(mode+1);
        this.speed=-0.5D;
        this.id=14;
    }
    @Override
    public void skill()
    {
        if(spawn==0) {
            double x = getLayoutX();
            double y = getLayoutY();
            int i=Hero.minDistance(this);
            double z = Game.game.heroes.get(i).getLayoutX();
            double v = Game.game.heroes.get(i).getLayoutY();
            SpecialObject newObject = new Bomb(z-x,v-y);
            newObject.relocate(x, y);
            Game.game.specialObjects.add(newObject);
            Game.game.board.getChildren().add(newObject);
            spawn=45;
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
        else if(HP<=11)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar13.png"));
        else if(HP<=18)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarFull.png"));
    }
}
class OgreBoss extends Boss
{
    OgreBoss(Double mode)
    {
        super(new Image("/resources/Images/Villains/OgreBoss.png"));
        this.HP=70*(mode+1);
        this.speed=-1.2D;
        this.id=15;
    }
    @Override
    public void skill()
    {
        if(spawn==0) {
            SpecialObject newObject = new ShockWave();
            newObject.relocate(getLayoutX()+getBoundsInLocal().getWidth()/2-newObject.getBoundsInLocal().getWidth()/2,getLayoutY()+getBoundsInLocal().getHeight()/2-newObject.getBoundsInLocal().getHeight()/2);
            Game.game.specialObjects.add(newObject);
            Game.game.board.getChildren().add(newObject);
            spawn=45;
        }
        else
        {
            spawn--;
        }
    }
    @Override
    public void changeHpBar()
    {
        if(HP<=10)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarCritic.png"));
        else if(HP<=30)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar13.png"));
        else if(HP<=50)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarFull.png"));
    }
}

