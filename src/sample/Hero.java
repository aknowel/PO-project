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
    double pos_x;
    double pos_y;
    double dx;
    double dy;
    public int hp;
    Sword sword;
    final public int maxHp=10;
    protected int id=2;
    static int swordCounter=0;
    boolean side;
    boolean top;
    Hero(double x,double y, int hp) {
        super("resources/Images/Thor.png");
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
    void checkHitHero(Game game)
    {
        Iterator<Weapon> x=game.weaponsVillain.iterator();
        while(x.hasNext()){
            Node currentWeapon=x.next();
            if (currentWeapon.getBoundsInParent().intersects(getBoundsInParent())){
                hp -= 1;
                changeHpBar();
                game.hp_texts.get(this).setText("HP: " + hp);
                Game.game.board.getChildren().remove(currentWeapon);
                x.remove();
            }
        }
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
    public void shout()
    {
        Sounds sounds=new Sounds();
        sounds.playHuh();
    }
    public abstract void weapon(MouseEvent event);
    public abstract void skill();
    static boolean isWarrior(Hero hero)
    {
        return hero instanceof Warrior;
    }
    public String toString()
    {
        return getLayoutX()+ " " + getLayoutY() + " " + hp + " " + id;
    }
    public static class Thor extends Hero{
        public Thor(double x,double y, int hp)
        {
            super(x, y, hp);
        }
        @Override
        public void weapon(MouseEvent event)
        {
            Weapon newWeapon;
            if (Game.game.upgrade <= 0) {
                newWeapon = new Hammer(event.getSceneX() - Game.game.heroes.get(0).getLayoutX(), event.getSceneY() - Game.game.heroes.get(0).getLayoutY());
            } else {
                newWeapon = new SuperHammer(event.getSceneX() - Game.game.heroes.get(0).getLayoutX(), event.getSceneY() - Game.game.heroes.get(0).getLayoutY());
                Game.game.upgrade--;
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

        }
    }
    public static class Warrior extends Hero{
        public Warrior(double x,double y, int hp)
        {
            super(x, y, hp);
            id=1;
            sword=new Sword();
            this.setImage(new Image("resources/Images/Warrior.png"));
        }
        public void weapon(MouseEvent event)
        {
            if(!Game.game.sword)
            {
                Game.game.sword=true;
                side= !(event.getSceneX() >= this.getLayoutX() + this.getBoundsInLocal().getWidth() / 2);
                if((event.getSceneX() - this.getLayoutX() <= 100) && (this.getLayoutX() - event.getSceneX() <= 75))
                {
                    top=true;
                }
                else
                {
                    top=false;
                }
                if(!top) {
                    if (side) {
                        sword.setImage(new Image("resources/Images/Meele/Sword2.png"));
                    } else {
                        sword.setImage(new Image("resources/Images/Meele/Sword.png"));
                    }
                }
                else
                {
                    if(event.getSceneY()>=this.getLayoutY())
                    {
                        sword.setImage(new Image("resources/Images/Meele/Sword260.png"));
                    }
                    else
                    {
                        sword.setImage(new Image("resources/Images/Meele/Sword30.png"));
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
            if (Game.game.sword) {
                if (!side) {
                    if(top)
                    {
                        if (swordCounter == 15) {
                            sword.setImage(new Image("resources/Images/Meele/Sword290.png"));
                        } else if (swordCounter == 30) {
                            sword.setImage(new Image("resources/Images/Meele/Sword90.png"));
                        } else if (swordCounter == 45) {
                            sword.setImage(new Image("resources/Images/Meele/Sword60.png"));
                        } else if (swordCounter == 60) {
                            Game.game.board.getChildren().remove(Game.game.heroes.get(0).sword);
                            swordCounter = 0;
                            Game.game.sword = false;
                        }
                    }
                    else {
                        if (swordCounter == 15) {
                            sword.setImage(new Image("resources/Images/Meele/Sword30.png"));
                        } else if (swordCounter == 30) {
                            sword.setImage(new Image("resources/Images/Meele/Sword60.png"));
                        } else if (swordCounter == 45) {
                            sword.setImage(new Image("resources/Images/Meele/Sword90.png"));
                        } else if (swordCounter == 60) {
                            Game.game.board.getChildren().remove(Game.game.heroes.get(0).sword);
                            swordCounter = 0;
                            Game.game.sword = false;
                        }
                    }
                } else {
                    if(!top) {
                        if (swordCounter == 15) {
                            sword.setImage(new Image("resources/Images/Meele/Sword230.png"));
                        } else if (swordCounter == 30) {
                            sword.setImage(new Image("resources/Images/Meele/Sword260.png"));
                        } else if (swordCounter == 45) {
                            sword.setImage(new Image("resources/Images/Meele/Sword290.png"));
                        } else if (swordCounter == 60) {
                            Game.game.board.getChildren().remove(Game.game.heroes.get(0).sword);
                            swordCounter = 0;
                            Game.game.sword = false;
                        }
                    }
                    else
                    {
                        if (swordCounter == 15) {
                            sword.setImage(new Image("resources/Images/Meele/Sword.png"));
                        } else if (swordCounter == 30) {
                            sword.setImage(new Image("resources/Images/Meele/Sword2.png"));
                        } else if (swordCounter == 45) {
                            sword.setImage(new Image("resources/Images/Meele/Sword230.png"));
                        } else if (swordCounter == 60) {
                            Game.game.board.getChildren().remove(Game.game.heroes.get(0).sword);
                            swordCounter = 0;
                            Game.game.sword = false;
                        }
                    }
                }
                swordCounter++;
            }
        }
    }
    public static class Assassin extends Hero{
        public Assassin(double x,double y, int hp)
        {
            super(x, y, hp);
            id=3;
            this.setImage(new Image("resources/Images/Assassin.png"));
        }
        @Override
        public void weapon(MouseEvent event)
        {

        }
        @Override
        public void skill()
        {

        }
    }
    static public Hero getNewHero(double x, double y, int hp, int id)
    {
        return switch (id)
        {
            case 1->new Warrior(x, y, hp);
            case 2->new Thor(x, y, hp);
            default->new Assassin(x, y, hp);
        };
    }
}
