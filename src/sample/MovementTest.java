package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;
import java.util.Random;

public abstract class MovementTest {
    static Backgorund curr=null;
    static void moveHeroTo (double x, double y) throws InstantiationException, IllegalAccessException {
        if (x >= 0 && x <= Game.W - Game.game.hero.getBoundsInLocal().getWidth() && y >= 0 && y <= Game.H - Game.game.hero.getBoundsInLocal().getHeight() && backgroundCheck(x,y,Game.game.hero)) {
               Game.game.hero.relocate(x, y);
        } else if (x >= 0 && x <= Game.W - Game.game.hero.getBoundsInLocal().getWidth() && backgroundCheck(x,y,Game.game.hero)) {
            Game.game.hero.relocate(x, Game.game.hero.getLayoutY());
        }
        else if (y>=0 && y<= Game.H -Game.game.hero.getBoundsInLocal().getHeight() && backgroundCheck(x,y,Game.game.hero)) {
            Game.game.hero.relocate(Game.game.hero.getLayoutX(), y);
        }
    }

    static void moveVillain () throws InstantiationException, IllegalAccessException {
        double d;
        Random random=new Random();
        Iterator<Villain> it=Game.game.villains.iterator();
        while(it.hasNext()){
            Villain currentvillain=it.next();
            d=currentvillain.getSpeed();
            if(Game.game.hero.getBoundsInParent().intersects(currentvillain.getBoundsInParent())) {
                Game.game.lives--;
                Game.game.livesText.setText("Lives: " + Game.game.lives);
                if(Villain.isShooting(currentvillain))
                {
                    Game.game.shootingVillains.remove(currentvillain);
                }
                it.remove();
                Game.game.board.getChildren().remove(currentvillain);
                continue;
            }
            double x=currentvillain.getLayoutX();
            double y=currentvillain.getLayoutY();
            double z=Game.game.hero.getLayoutX();
            double v=Game.game.hero.getLayoutY();
            double dd=Math.sqrt((x-z)*(x-z)+(y-v)*(y-v));
            boolean tt=villainBgCheck(x-z,y-v,currentvillain);
            if(tt) {
                currentvillain.relocate(currentvillain.getLayoutX() + d * (x - z) / dd, currentvillain.getLayoutY() + d * (y - v) / dd);
            }
            else {
                if(currentvillain.getLayoutY()>=curr.getLayoutY()) {
                    currentvillain.relocate(currentvillain.getLayoutX() + (5 + random.nextInt(10)) * Math.signum(x - z) + d * (x - z) / dd, currentvillain.getLayoutY() + (5 + random.nextInt(10))*Math.signum(y-v) + d * (y - v) / dd);
                }
                else
                {
                    currentvillain.relocate(currentvillain.getLayoutX() + (5 + random.nextInt(10)) * Math.signum(x - z) + d * (x - z) / dd, currentvillain.getLayoutY() + (5 + random.nextInt(10))*Math.signum(y-v) + d * (y - v) / dd);
                }
            }
        }
    }
    static void throwWeapon(double d) throws InstantiationException, IllegalAccessException {
        Iterator<Weapon> z=Game.game.weaponsHero.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            double dd=Math.sqrt(x.x*x.x+x.y*x.y);
            if (x.getLayoutX()<= Game.W && x.getLayoutX()>=0 && x.getLayoutY()<= Game.H && x.getLayoutY()>=0 && backgroundCheck(x.getLayoutX() + d*x.x/dd,x.getLayoutY() + d * x.y / dd,x)){
                x.relocate(x.getLayoutX() + d*x.x/dd, x.getLayoutY() + d * x.y / dd);
            }
            else {
                z.remove();
                Game.game.board.getChildren().remove(x);
            }
        }
    }
    public static void enemyWeapon(double d) throws InstantiationException, IllegalAccessException {
        Iterator<Weapon> z=Game.game.weaponsVillain.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            double dd=Math.sqrt(x.x*x.x+x.y*x.y);
            if (x.getLayoutX()<= Game.W && x.getLayoutX()>=0 && x.getLayoutY()<= Game.H && x.getLayoutY()>=0 && backgroundCheck(x.getLayoutX() + d*x.x/dd,x.getLayoutY() + d * x.y / dd,x)){
                x.relocate(x.getLayoutX() + d*x.x/dd, x.getLayoutY() + d * x.y / dd);
            }
            else {
                z.remove();
                Game.game.board.getChildren().remove(x);
            }
        }
    }
    private static boolean backgroundCheck(double x, double y, ImageView object) throws IllegalAccessException, InstantiationException {
        Iterator<Backgorund> it=Game.game.backgorundsObjects.iterator();
        ImageView test=object.getClass().newInstance();
        test.relocate(x,y);
        while(it.hasNext())
        {
            Backgorund current=it.next();
            if(current.getBoundsInParent().intersects(test.getBoundsInParent()))
            {
                return false;
            }
        }
        return true;
    }
    private static boolean villainBgCheck(double a,double b,Villain object) {
        Iterator<Backgorund> it=Game.game.backgorundsObjects.iterator();
        double c=a* object.getLayoutY()-b*object.getLayoutX();
        while(it.hasNext())
        {
            Backgorund current=it.next();
            double dd=Math.sqrt((object.getLayoutX()-current.getLayoutX())*(object.getLayoutX()-current.getLayoutX())+(object.getLayoutY()-current.getLayoutY())*(object.getLayoutY()-current.getLayoutY()));
            if(dd<=75)
            {
                if(current.getLayoutY()*a-b*current.getLayoutX()-c>0) {
                    if((b*current.getLayoutX()+c-a*current.getLayoutY()-a*current.getBoundsInLocal().getHeight()>=a*object.getBoundsInParent().getHeight())||(b*(current.getLayoutX()+current.getBoundsInLocal().getWidth())+c-a*current.getLayoutY()-a*current.getBoundsInLocal().getHeight()<=a*object.getBoundsInParent().getHeight())) {
                        curr=current;
                        return false;
                    }
                }
                else if(current.getLayoutY()*a-b*current.getLayoutX()-c<0)
                {
                    if((a*current.getBoundsInLocal().getHeight()-b*current.getLayoutX()-c>=a*object.getBoundsInParent().getHeight())||(a*current.getBoundsInLocal().getHeight()-b*(current.getLayoutX()+current.getBoundsInLocal().getWidth())-c<=a*object.getBoundsInParent().getHeight())){
                        curr=current;
                        return false;
                    }
                }
                else
                {
                    curr=current;
                    return false;
                }
            }
        }
        return true;
    }
}
