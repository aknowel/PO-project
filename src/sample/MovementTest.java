package sample;

import javafx.scene.image.ImageView;

import java.util.Iterator;

public abstract class MovementTest {
    static Background curr=null;
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
        Iterator<Villain> it=Game.game.villains.iterator();
        while(it.hasNext()){
            Villain currentVillain=it.next();
            d=currentVillain.getSpeed();
            if(Game.game.hero.getBoundsInParent().intersects(currentVillain.getBoundsInParent())) {
                Game.game.lives--;
                Game.game.livesText.setText("Lives: " + Game.game.lives);
                if(Villain.isShooting(currentVillain))
                {
                    Game.game.shootingVillains.remove(currentVillain);
                }
                it.remove();
                Game.game.board.getChildren().remove(currentVillain);
                continue;
            }
            double x=currentVillain.getLayoutX();
            double y=currentVillain.getLayoutY();
            double z=Game.game.hero.getLayoutX();
            double v=Game.game.hero.getLayoutY();
            double dd=Math.sqrt((x-z)*(x-z)+(y-v)*(y-v));
            boolean tt=villainBgCheck(currentVillain);
            if(tt) {
                currentVillain.relocate(currentVillain.getLayoutX() + d * (x - z) / dd, currentVillain.getLayoutY() + d * (y - v) / dd);
            }
            else {
                double xx=curr.getLayoutX()+curr.getBoundsInLocal().getWidth()/2;
                double yy=curr.getLayoutY()+curr.getBoundsInLocal().getHeight()/2;
                double b=yy-y;
                double s=Math.signum(-Game.game.hero.getLayoutY()+currentVillain.getLayoutY());
                if (b == 0) {
                    if(!(Game.game.hero.getLayoutY()>=curr.getLayoutY()-Game.game.hero.getBoundsInLocal().getHeight() && Game.game.hero.getLayoutY()<=curr.getLayoutY()+curr.getBoundsInLocal().getHeight()+20)) {
                        if (xx < currentVillain.getLayoutX()) {
                            currentVillain.relocate(currentVillain.getLayoutX(), currentVillain.getLayoutY() - s * d);
                        } else {
                            currentVillain.relocate(currentVillain.getLayoutX(), currentVillain.getLayoutY() + s * d);
                        }
                    }
                    else
                    {
                        if (xx < currentVillain.getLayoutX()) {
                            currentVillain.relocate(currentVillain.getLayoutX(), currentVillain.getLayoutY() - d);
                        } else {
                            currentVillain.relocate(currentVillain.getLayoutX(), currentVillain.getLayoutY() + d);
                        }
                    }
                } else {
                    double a = Math.atan(-(x - xx) / (y - yy));
                    if(!(Game.game.hero.getLayoutY()>=curr.getLayoutY()-Game.game.hero.getBoundsInLocal().getHeight() && Game.game.hero.getLayoutY()<=curr.getLayoutY()+curr.getBoundsInLocal().getHeight()+20)) {
                        if (yy < currentVillain.getLayoutY()) {
                            currentVillain.relocate(currentVillain.getLayoutX() + s * d * Math.cos(a), currentVillain.getLayoutY() + s * d * Math.sin(a));
                        } else {
                            currentVillain.relocate(currentVillain.getLayoutX() - s * d * Math.cos(a), currentVillain.getLayoutY() - s * d * Math.sin(a));
                        }
                    }
                    else
                    {
                        if (yy < currentVillain.getLayoutY()) {
                            currentVillain.relocate(currentVillain.getLayoutX() + d * Math.cos(a), currentVillain.getLayoutY() + d * Math.sin(a));
                        } else {
                            currentVillain.relocate(currentVillain.getLayoutX() - d * Math.cos(a), currentVillain.getLayoutY() - d * Math.sin(a));
                        }
                    }
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
        Iterator<Background> it=Game.game.backgroundObjects.iterator();
        ImageView test=object.getClass().newInstance();
        test.relocate(x,y);
        while(it.hasNext())
        {
            Background current=it.next();
            if(current.getBoundsInParent().intersects(test.getBoundsInParent()))
            {
                return false;
            }
        }
        return true;
    }
    private static boolean villainBgCheck(Villain object) {
        boolean isBoss=object.isBoss();
        for (Background current : Game.game.backgroundObjects) {
            double dd = Math.sqrt((object.getLayoutX() - current.getLayoutX() - current.getBoundsInLocal().getWidth() / 2) * (object.getLayoutX() - current.getLayoutX() - current.getBoundsInLocal().getWidth() / 2) + (object.getLayoutY() - current.getLayoutY() - current.getBoundsInLocal().getHeight() / 2) * (object.getLayoutY() - current.getLayoutY() - current.getBoundsInLocal().getHeight() / 2));
            if(!isBoss) {
                if (dd <= 60) {
                    curr = current;
                    return false;
                }
            }
            else
            {
                if(dd<30)
                {
                    Game.game.board.getChildren().remove(current);
                }
            }
        }
        return true;
    }
}
