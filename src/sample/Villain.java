package sample;

import javafx.scene.Node;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;


public abstract class Villain extends ImageView {
    protected Double HP;
    protected int speed;
    protected int id;
    Villain(Image img)
    {
        super(img);
    }
     public void  hp()
    {
        HP--;
    }
    public static Villain getNewVillain(int i, Double mode)
    {
        return switch (i) {
            case 0, 1, 2 -> new Skull(mode);
            case 3, 4, 5 -> new Spider(mode);
            default -> new Predator(mode);
        };
    }
    public static Boss getNewBoss(Double mode)
    {
        return new Boss(mode);
    }
    public boolean isAlive()
    {
        return HP!=0;
    }
    int getSpeed()
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
                        if(i<2)
                        {
                            Box newBox=Box.getNewBox(i);
                            newBox.relocate(currentVillain.getLayoutX(),currentVillain.getLayoutY());
                            game.boxes.add(newBox);
                            Game.game.board.getChildren().add(newBox);
                        }
                        Game.game.board.getChildren().remove(currentVillain);
                        y.remove();

                       game.score++;
                        game.scoreText.setText("Score: " + game.score);
                        if(currentVillain instanceof Predator)
                        {
                            game.shootingVillains.remove(currentVillain);
                        }
                    }
                }
            }
        }
    }
    public static void newVillain(Game game)
    {
        if (game.villainCounter % game.modifier == 0) {
            Villain newVillain = Villain.getNewVillain(Game.randomizer.nextInt(7),Game.game.mode);
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
            if(newVillain instanceof Predator)
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
    public String toString()
    {
        return this.id + " "  + this.HP+ " "+ this.getLayoutX()+ " " + this.getLayoutY();
    }
}
class Skull extends Villain{
    Skull(Double mode)
        {
            super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/32/skull-3-icon.png"));
            this.HP= 2+mode;
            this.speed=-1;
            this.id=0;
        }
}
class Predator extends Villain{
    Predator(Double mode)
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/32/predator-icon.png"));
        this.HP=3+mode;
        this.speed=0;
        this.id=1;
    }
}
class Spider extends Villain
{
    Spider(Double mode)
    {
        super(new Image("https://icons.iconarchive.com/icons/iconsmind/outline/32/Spider-icon.png"));
        this.HP=1+mode;
        this.speed=-2;
        this.id=2;
    }
}
class Boss extends Villain
{
    Boss(Double mode)
    {
        super(new Image("https://icons.iconarchive.com/icons/icons8/halloween/64/predator-icon.png"));
        this.HP=20*(mode+1);
        this.speed=-3;
        this.id=3;
    }
}



