package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;

public abstract class Hero extends ImageView {
    boolean goNorth, goSouth, goEast, goWest;
    HpBar hpBar=new HpBar();
    double pos_x;
    double pos_y;
    double dx;
    double dy;
    public int hp;
    final public int maxHp=10;
    protected int id=2;

    Hero(double x,double y, int hp) {
        super("resources/Images/Thor.png");
        goNorth = goSouth = goEast = goWest = false;
        pos_x = x;
        pos_y = y;
        this.hp = hp;
        changeHpBar();
    }
    /*Hero(String path_to_image)
    {
        super(path_to_image);
        goNorth = goSouth = goEast = goWest = false;
        pos_x = getLayoutX();
        pos_y = getLayoutY();
        hp = 10;
    }*/
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
    public String toString()
    {
        return getLayoutX()+ " " + getLayoutY() + " " + hp + " " + id;
    }
    public static class Thor extends Hero{
        public Thor(double x,double y, int hp)
        {
            super(x, y, hp);
        }
    }
    public static class Warrior extends Hero{
        public Warrior(double x,double y, int hp)
        {
            super(x, y, hp);
            id=1;
            this.setImage(new Image("resources/Images/Warrior.png"));
        }
    }
    public static class Assassin extends Hero{
        public Assassin(double x,double y, int hp)
        {
            super(x, y, hp);
            id=3;
            this.setImage(new Image("resources/Images/Assassin.png"));
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
