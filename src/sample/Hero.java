package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;

import java.util.Iterator;
import java.util.Random;

public abstract class Hero extends ImageView {
    boolean goNorth, goSouth, goEast, goWest;
    HpBar hpBar=new HpBar();
    public double pos_x;
    public double pos_y;
    public double dx;
    public double dy;
    public int hp;
    Sword sword;
    public boolean swordCheck=false;
    final public int maxHp=10;
    public int id=2;
    public int counter;
    public boolean side;
    public boolean top;
    public boolean barrierCheck=false;
    public int barrierTime=0;
    public int upgrade=0;
    public int skillCooldown=0;
    public boolean heroSkill=false;
    public boolean slow=false;
    public boolean speedUp=false;
    public double speed=3;
    public int slowDuration=0;
    public int SObjInvulnerability=0;
    ImageView barrier=new ImageView(new Image("resources/Images/Boxes/Barrier.png"));

    Hero(double x,double y, int hp) {
        super("resources/Images/Heroes/Thor.png");
        goNorth = goSouth = goEast = goWest = false;
        pos_x = x;
        pos_y = y;
        this.hp = hp;
        changeHpBar();
    }
    public int getHeroId()
    {
        return id;
    }
    public void setHpBar()
    {
        hpBar.relocate(getLayoutX() + getBoundsInLocal().getWidth()/2-hpBar.getBoundsInLocal().getWidth()/2, getLayoutY() - hpBar.getBoundsInLocal().getHeight()-3);
    }
    void checkHitHero()
    {
        Iterator<Weapon> x=Game.game.weaponsVillain.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
            if (currentWeapon.getBoundsInParent().intersects(getBoundsInParent())){
                if(!barrierCheck) {
                    hp -= 1;
                    changeHpBar();
                    Game.game.hp_texts.get(this).setText("HP: " + hp);
                }else
                {
                    barrierCheck=false;
                    Game.game.board.getChildren().remove(barrier);
                }
                Game.game.board.getChildren().remove(currentWeapon);
                x.remove();
            }
        }
    }
    public void setBarrier()
    {
        if(barrierCheck) {
            Game.game.board.getChildren().remove(barrier);
            barrier.setOpacity(0.50);
            barrier.relocate(getLayoutX()-4.5,getLayoutY()-4);
            Game.game.board.getChildren().add(barrier);
        }
        else
        {
            Game.game.board.getChildren().remove(barrier);
        }
    }
    boolean checkHitBySObj(Node specialObj)
    {
        if (specialObj.getBoundsInParent().intersects(getBoundsInParent())){
            if(!barrierCheck) {
                hp -= 1;
                changeHpBar();
                Game.game.hp_texts.get(this).setText("HP: " + hp);
            }else
            {
                barrierCheck=false;
                Game.game.board.getChildren().remove(barrier);
            }
            return true;
        }
        return false;
    }
    protected void changeHpBar()
    {
        if(hp<=1)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarCritic.png"));
        else if(hp<=4)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar13.png"));
        else if(hp<=7)
            hpBar.setImage(new Image("resources/Images/HpBars/HpBar23.png"));
        else
            hpBar.setImage(new Image("resources/Images/HpBars/HpBarFull.png"));
    }
    public static void decreaseVulnerability()
    {
        for(Hero hero:Game.game.heroes)
        {
            hero.SObjInvulnerability--;
        }
    }
    public void shout()
    {
        Sounds sounds=new Sounds();
        sounds.playHuh();
    }
    public abstract void newWeapon(MouseEvent event);
    public abstract void skill();
    static boolean isWarrior(Hero hero)
    {
        return hero instanceof Warrior;
    }
    public static int minDistance(Node obj)
    {
        double m=-1;
        int i=0,j=0;
        for(Hero hero:Game.game.heroes)
        {
            double dd=Math.sqrt((hero.getLayoutX()-obj.getLayoutX())*((hero.getLayoutX()-obj.getLayoutX()))+(hero.getLayoutY()-obj.getLayoutY())*(hero.getLayoutY()-obj.getLayoutY()));
            if(m==-1 && dd<m)
            {
                j=i;
                m=dd;
            }
            i++;
        }
        return j;
    }
    public void setSword()
    {
        if (swordCheck) {
            Game.game.board.getChildren().add(sword);
            if (!side) {
                if(top)
                {
                    if(counter<15) {
                        sword.setImage(new Image("resources/Images/Melee/Sword260.png"));
                    }
                    else if (counter < 30) {
                        sword.setImage(new Image("resources/Images/Melee/Sword290.png"));
                    } else if (counter < 45) {
                        sword.setImage(new Image("resources/Images/Melee/Sword90.png"));
                    } else if (counter < 60) {
                        sword.setImage(new Image("resources/Images/Melee/Sword60.png"));
                    } else if (counter == 60) {
                        Game.game.board.getChildren().remove(sword);
                        counter = 0;
                        swordCheck = false;
                    }
                }
                else {
                    if(counter<15) {
                        sword.setImage(new Image("resources/Images/Melee/Sword.png"));
                    }
                    else if (counter < 30) {
                        sword.setImage(new Image("resources/Images/Melee/Sword30.png"));
                    } else if (counter < 45) {
                        sword.setImage(new Image("resources/Images/Melee/Sword60.png"));
                    } else if (counter < 60) {
                        sword.setImage(new Image("resources/Images/Melee/Sword90.png"));
                    } else if (counter == 60) {
                        Game.game.board.getChildren().remove(sword);
                        counter = 0;
                        swordCheck = false;
                    }
                }
            } else {
                if(!top) {
                    if(counter<15) {
                        sword.setImage(new Image("resources/Images/Melee/Sword2.png"));
                    }
                    if (counter < 30) {
                        sword.setImage(new Image("resources/Images/Melee/Sword230.png"));
                    } else if (counter <45) {
                        sword.setImage(new Image("resources/Images/Melee/Sword260.png"));
                    } else if (counter < 60) {
                        sword.setImage(new Image("resources/Images/Melee/Sword290.png"));
                    } else if (counter == 60) {
                        Game.game.board.getChildren().remove(sword);
                        counter = 0;
                        swordCheck = false;
                    }
                }
                else
                {
                    if(counter<15) {
                        sword.setImage(new Image("resources/Images/Melee/Sword260.png"));
                    }
                    else if (counter < 30) {
                        sword.setImage(new Image("resources/Images/Melee/Sword.png"));
                    } else if (counter < 45) {
                        sword.setImage(new Image("resources/Images/Melee/Sword2.png"));
                    } else if (counter < 60) {
                        sword.setImage(new Image("resources/Images/Melee/Sword230.png"));
                    } else if (counter == 60) {
                        Game.game.board.getChildren().remove(sword);
                        counter = 0;
                        swordCheck = false;
                    }
                }
            }
            counter++;
        }
    }
    public String toString()
    {
        return getLayoutX()+ " " + getLayoutY() + " " + hp + " " + id+" "+dx+" "+dy+" "+swordCheck+" "+counter+" "+side+" "+top+" "+barrierCheck+ " "
                + barrierTime+ " " + upgrade + " "+ skillCooldown+ " "+ heroSkill+ " "+ slow+ " "+ speedUp+ " "+speed+ " "+ slowDuration+ " "+SObjInvulnerability;
    }
    public static class Thor extends Hero{
        public Thor(double x,double y, int hp)
        {
            super(x, y, hp);
            id=2;
            skillCooldown=0;
        }
        @Override
        public void newWeapon(MouseEvent event)
        {
            Weapon newWeapon;
            if (Game.game.heroes.get(0).upgrade <= 0) {
                newWeapon = new Hammer(event.getSceneX() - Game.game.heroes.get(0).getLayoutX(), event.getSceneY() - Game.game.heroes.get(0).getLayoutY());
            } else {
                newWeapon = new SuperHammer(event.getSceneX() - Game.game.heroes.get(0).getLayoutX(), event.getSceneY() - Game.game.heroes.get(0).getLayoutY());
                Game.game.heroes.get(0).upgrade--;
            }
            newWeapon.relocate(
                    Game.game.heroes.get(0).getLayoutX() + Game.game.heroes.get(0).getBoundsInLocal().getWidth(), Game.game.heroes.get(0).getLayoutY());
            Game.game.weaponsHero.add(newWeapon);
            Game.game.board.getChildren().add(newWeapon);
            Counter.thrownWeapon();
            Random randomize=new Random();
            if (randomize.nextInt(5) == 1) {
                Game.game.heroes.get(0).shout();
            }
        }
        @Override
        public void skill()
        {
            if(heroSkill) {
                SpecialObject specialObject=new TNT();
                Game.game.specialObjects.add(specialObject);
                specialObject.relocate(pos_x,pos_y);
                Game.game.board.getChildren().add(specialObject);
                skillCooldown=150;
                heroSkill=false;
            }
            else
            {
                skillCooldown--;
            }
        }
    }
    public static class Warrior extends Hero{
        public Warrior(double x,double y, int hp)
        {
            super(x, y, hp);
            id=1;
            sword=new Sword();
            speed=4;
            this.setImage(new Image("resources/Images/Heroes/Warrior.png"));
        }
        public void newWeapon(MouseEvent event)
        {
            if(!swordCheck)
            {
                swordCheck=true;
                side= !(event.getSceneX() >= this.getLayoutX() + this.getBoundsInLocal().getWidth() / 2);
                top= (event.getSceneX() - this.getLayoutX() <= 100) && (this.getLayoutX() - event.getSceneX() <= 75);
                if(!top) {
                    if (side) {
                        sword.setImage(new Image("resources/Images/Melee/Sword2.png"));
                    } else {
                        sword.setImage(new Image("resources/Images/Melee/Sword.png"));
                    }
                }
                else
                {
                    if(event.getSceneY()>=this.getLayoutY())
                    {
                        sword.setImage(new Image("resources/Images/Melee/Sword260.png"));
                    }
                    else
                    {
                        sword.setImage(new Image("resources/Images/Melee/Sword30.png"));
                    }
                }
                if(top && event.getSceneY()<=this.getLayoutY())
                {
                    side=true;
                }
                else if(top && event.getSceneY()>this.getLayoutY())
                {
                    side=false;
                }
                Game.game.board.getChildren().add(Game.game.heroes.get(0).sword);
            }
        }
        @Override
        public void skill() {
            if(heroSkill && (skillCooldown<=0||Game.game.heroes.get(0).upgrade>0)) {
                Weapon newWeapon;
                newWeapon = new Axe(Mouse.x - Game.game.heroes.get(0).getLayoutX(), Mouse.y - Game.game.heroes.get(0).getLayoutY());
                newWeapon.relocate(
                        Game.game.heroes.get(0).getLayoutX() + Game.game.heroes.get(0).getBoundsInLocal().getWidth(), Game.game.heroes.get(0).getLayoutY());
                Game.game.weaponsHero.add(newWeapon);
                Game.game.board.getChildren().add(newWeapon);
                Counter.useSword();
                Random randomize = new Random();
                if (randomize.nextInt(5) == 1) {
                    Game.game.heroes.get(0).shout();
                }
                if(Game.game.heroes.get(0).upgrade>0)
                {
                    Game.game.heroes.get(0).upgrade--;
                }
                else{
                    skillCooldown = 45;
                }
                heroSkill=false;
            }
            else
            {
                skillCooldown--;
            }
        }
        void moveSword()
        {
            if (swordCheck) {
                if (!side) {
                    if(top)
                    {
                        if (counter == 15) {
                            sword.setImage(new Image("resources/Images/Melee/Sword290.png"));
                        } else if (counter == 30) {
                            sword.setImage(new Image("resources/Images/Melee/Sword90.png"));
                        } else if (counter == 45) {
                            sword.setImage(new Image("resources/Images/Melee/Sword60.png"));
                        } else if (counter == 60) {
                            Game.game.board.getChildren().remove(sword);
                            counter = 0;
                            swordCheck = false;
                        }
                    }
                    else {
                        if (counter == 15) {
                            sword.setImage(new Image("resources/Images/Melee/Sword30.png"));
                        } else if (counter == 30) {
                            sword.setImage(new Image("resources/Images/Melee/Sword60.png"));
                        } else if (counter == 45) {
                            sword.setImage(new Image("resources/Images/Melee/Sword90.png"));
                        } else if (counter == 60) {
                            Game.game.board.getChildren().remove(sword);
                            counter = 0;
                            swordCheck = false;
                        }
                    }
                } else {
                    if(!top) {
                        if (counter == 15) {
                            sword.setImage(new Image("resources/Images/Melee/Sword230.png"));
                        } else if (counter == 30) {
                            sword.setImage(new Image("resources/Images/Melee/Sword260.png"));
                        } else if (counter == 45) {
                            sword.setImage(new Image("resources/Images/Melee/Sword290.png"));
                        } else if (counter == 60) {
                            Game.game.board.getChildren().remove(sword);
                            counter = 0;
                           swordCheck = false;
                        }
                    }
                    else
                    {
                        if (counter == 15) {
                            sword.setImage(new Image("resources/Images/Melee/Sword.png"));
                        } else if (counter == 30) {
                            sword.setImage(new Image("resources/Images/Melee/Sword2.png"));
                        } else if (counter == 45) {
                            sword.setImage(new Image("resources/Images/Melee/Sword230.png"));
                        } else if (counter == 60) {
                            Game.game.board.getChildren().remove(sword);
                            counter = 0;
                            swordCheck = false;
                        }
                    }
                }
                counter++;
            }
        }
    }
    public static class Assassin extends Hero{
        public Assassin(double x,double y, int hp)
        {
            super(x, y, hp);
            id=3;
            this.setImage(new Image("resources/Images/Heroes/Assassin.png"));
        }
        @Override
        public void newWeapon(MouseEvent event)
        {
            if(counter==2) {
                Weapon newWeapon;
                newWeapon = new Shuriken(event.getSceneX() - Game.game.heroes.get(0).getLayoutX(), event.getSceneY() - Game.game.heroes.get(0).getLayoutY());
                newWeapon.relocate(
                        Game.game.heroes.get(0).getLayoutX() + Game.game.heroes.get(0).getBoundsInLocal().getWidth(), Game.game.heroes.get(0).getLayoutY());
                Game.game.weaponsHero.add(newWeapon);
                Game.game.board.getChildren().add(newWeapon);
                Counter.thrownShuriken();
                Random randomize = new Random();
                if (randomize.nextInt(5) == 1) {
                    Game.game.heroes.get(0).shout();
                }
                counter=0;
            }
            else
            {
                counter++;
            }
        }
        @Override
        public void skill()
        {
            if(heroSkill && (skillCooldown<=0||(Game.game.heroes.get(0).upgrade>0 && Game.game.heroes.get(0).upgrade%10==0))) {
                SpecialObject specialObject=new PoisonCloud();
                Game.game.specialObjects.add(specialObject);
                specialObject.relocate(pos_x,pos_y);
                Game.game.board.getChildren().add(specialObject);
                if(Game.game.heroes.get(0).upgrade>0)
                {
                    Game.game.heroes.get(0).upgrade=Game.game.heroes.get(0).upgrade-5;
                }
                else {
                    skillCooldown = 150;
                }
                heroSkill = false;
            }
            else
            {
                skillCooldown--;
            }
        }
    }
    static public Hero getNewHero(double x, double y, int hp, int id)
    {
        return switch (id)
                {
                    case 1->new Warrior(x, y, hp);
                    case 2->new Thor(x, y, hp);
                    case 3->new Assassin(x, y, hp);
                    default -> throw new IllegalStateException("Unexpected value: " + id);
                };
    }
}
