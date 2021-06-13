package sample;

import javafx.scene.image.ImageView;

import java.util.Iterator;

public abstract class Movement {
    static Background curr=null;
    static int counter=0;
    static void moveHeroTo (Hero hero, double x, double y) throws InstantiationException, IllegalAccessException {
        if (x >= 0 && x <= Game.W - hero.getBoundsInLocal().getWidth() && y >= 0 && y <= Game.H - hero.getBoundsInLocal().getHeight() && backgroundCheck(x,y,hero)) {
               hero.relocate(x, y);
        } else if (x >= 0 && x <= Game.W -hero.getBoundsInLocal().getWidth() && backgroundCheck(x,y,hero)) {
            hero.relocate(x, hero.getLayoutY());
            hero.pos_y -= hero.dy;
        }
        else if (y>=0 && y<= Game.H -hero.getBoundsInLocal().getHeight() && backgroundCheck(x,y,hero)) {
            hero.relocate(hero.getLayoutX(), y);
            hero.pos_x -= hero.dx;
        } else {
            hero.pos_x -= hero.dx;
            hero.pos_y -= hero.dy;
        }
        hero.hpBar.setCoordinates(hero.getLayoutX()-4,hero.getLayoutY()-20);
        if(Hero.isWarrior(hero)) {
            swordMove(hero);
        }
        if(hero.barrierCheck)
        {
            if(hero.barrierTime<600) {
                hero.barrier.relocate(hero.getLayoutX()-4.5,hero.getLayoutY()-4);
                hero.barrierTime++;
            }
            else
            {
                Game.game.board.getChildren().remove(hero.barrier);
                hero.barrierCheck=false;
                hero.barrierTime=0;
            }
            counter++;
        }
    }

    static void moveVillain () throws InstantiationException, IllegalAccessException {
        double d;
        Iterator<Villain> it=Game.game.villains.iterator();
        outer:while(it.hasNext()) {
            Villain currentVillain = it.next();
            d = currentVillain.getSpeed();
            for(Hero hero:Game.game.heroes) {
                if (hero.getBoundsInParent().intersects(currentVillain.getBoundsInParent())) {
                    if (!hero.barrierCheck) {
                        hero.hp--;
                        Game.game.hp_texts.get(hero).setText("HP: " + hero.hp);
                        hero.changeHpBar();
                    } else {
                        hero.barrierCheck = false;
                        hero.barrierTime = 0;
                        Game.game.board.getChildren().remove(hero.barrier);
                    }
                    if (Villain.isShooting(currentVillain)) {
                        Game.game.shootingVillains.remove(currentVillain);
                    }
                    it.remove();
                    Game.game.board.getChildren().remove(currentVillain);
                    Game.game.board.getChildren().remove(currentVillain.hpBar);
                    continue outer;
                }
            }
            double x = currentVillain.getLayoutX();
            double y = currentVillain.getLayoutY();
            int i=Hero.minDistance(currentVillain);
            double z = Game.game.heroes.get(i).getLayoutX();
            double v = Game.game.heroes.get(i).getLayoutY();
            double dd = Math.sqrt((x - z) * (x - z) + (y - v) * (y - v));
            boolean tt = villainBgCheck(currentVillain);
            boolean isBoss = currentVillain.isBoss();
            if (!isBoss) {
                if (tt) {
                    currentVillain.relocate(currentVillain.getLayoutX() + d * (x - z) / dd, currentVillain.getLayoutY() + d * (y - v) / dd);
                } else {
                    double xx = curr.getLayoutX() + curr.getBoundsInLocal().getWidth() / 2;
                    double yy = curr.getLayoutY() + curr.getBoundsInLocal().getHeight() / 2;
                    double b = yy - y;
                    if (b == 0) {
                            if (xx < currentVillain.getLayoutX()) {
                                currentVillain.relocate(currentVillain.getLayoutX(), currentVillain.getLayoutY() - d);
                            } else {
                                currentVillain.relocate(currentVillain.getLayoutX(), currentVillain.getLayoutY() + d);
                            }
                        }
                     else {
                        double a = Math.atan(-(x - xx) / (y - yy));
                            if (yy < currentVillain.getLayoutY()) {
                                currentVillain.relocate(currentVillain.getLayoutX() + d * Math.cos(a), currentVillain.getLayoutY() + d * Math.sin(a));
                            } else {
                                currentVillain.relocate(currentVillain.getLayoutX() - d * Math.cos(a), currentVillain.getLayoutY() - d * Math.sin(a));
                            }
                    }
                }
            } else {
                if (currentVillain.getVillainId() != 12) {
                    bossBgCheck(currentVillain);
                }
                currentVillain.relocate(currentVillain.getLayoutX() + d * (x - z) / dd, currentVillain.getLayoutY() + d * (y - v) / dd);
            }
                currentVillain.hpBar.relocate(x + currentVillain.getBoundsInLocal().getWidth()/2-currentVillain.hpBar.getBoundsInLocal().getWidth()/2, y - currentVillain.hpBar.getBoundsInLocal().getHeight()-3);
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
    private static boolean backgroundCheck(double x, double y, ImageView object) {
        Iterator<Background> it=Game.game.backgroundObjects.iterator();
        ImageView test=new ImageView(object.getImage());
        test.relocate(x, y);
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
        for (Background current : Game.game.backgroundObjects) {
            double dd = Math.sqrt((object.getLayoutX() - current.getLayoutX() - current.getBoundsInLocal().getWidth() / 2) * (object.getLayoutX() - current.getLayoutX() - current.getBoundsInLocal().getWidth() / 2) + (object.getLayoutY() - current.getLayoutY() - current.getBoundsInLocal().getHeight() / 2) * (object.getLayoutY() - current.getLayoutY() - current.getBoundsInLocal().getHeight() / 2));
                if (dd <= 60) {
                    curr = current;
                    return false;
                }
        }
        return true;
    }
    private static void bossBgCheck(Villain boss)
    {
        for (Background current : Game.game.backgroundObjects) {
            if(boss.getBoundsInParent().intersects(current.getBoundsInParent()))
            {
                Game.game.backgroundObjects.remove(current);
                Game.game.board.getChildren().remove(current);
            }
        }
    }
    private static void swordMove(Hero hero)
    {
        if(!hero.side) {
            if(hero.top)
            {
                hero.sword.relocate(hero.getLayoutX() + 4*Hero.counter / 15.0, hero.getLayoutY() +hero.getBoundsInLocal().getHeight());
            }
            else {
                hero.sword.relocate(hero.getLayoutX() + hero.getBoundsInLocal().getWidth(), hero.getLayoutY() + 4 * Hero.counter / 15.0);
            }
        }
        else
        {
            if(hero.top)
            {
                hero.sword.relocate(hero.getLayoutX() - 4*Hero.counter / 15.0, hero.getLayoutY() - hero.sword.getBoundsInLocal().getHeight());
            }
            else {
                hero.sword.relocate(hero.getLayoutX() - hero.sword.getBoundsInLocal().getWidth(), hero.getLayoutY() + 4 * Hero.counter / 15.0);
            }
        }
    }
}
