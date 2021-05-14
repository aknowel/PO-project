package sample;

import java.util.Iterator;

public abstract class Movement {
    static void moveHeroTo (double x, double y){
        if (x>=0 && x<= Game.W -Game.game.hero.getBoundsInLocal().getWidth() && y>=0 && y<= Game.H -Game.game.hero.getBoundsInLocal().getHeight())
            Game.game.hero.relocate(x, y);
        else if (x>=0 && x<= Game.W -Game.game.hero.getBoundsInLocal().getWidth())
            Game.game.hero.relocate(x, Game.game.hero.getLayoutY());
        else if (y>=0 && y<= Game.H -Game.game.hero.getBoundsInLocal().getHeight())
            Game.game.hero.relocate(Game.game.hero.getLayoutX(), y);
    }

    static void moveVillain (){
        double d;
        Iterator<Villain> it=Game.game.villains.iterator();
        while(it.hasNext()){
            Villain currentvillain=it.next();
            d=currentvillain.getSpeed();
            if(Game.game.hero.getBoundsInParent().intersects(currentvillain.getBoundsInParent())) {
                Game.game.lives--;
                Game.game.livesText.setText("Lives: " + Game.game.lives);
                it.remove();
                Game.game.board.getChildren().remove(currentvillain);
                continue;
            }
            double x=currentvillain.getLayoutX();
            double y=currentvillain.getLayoutY();
            double z=Game.game.hero.getLayoutX();
            double v=Game.game.hero.getLayoutY();
            double dd=Math.sqrt((x-z)*(x-z)+(y-v)*(y-v));
            currentvillain.relocate(currentvillain.getLayoutX() +d*(x-z)/dd, currentvillain.getLayoutY()+d*(y-v)/dd);
        }
    }
    static void throwWeapon(double d){
        Iterator<Weapon> z=Game.game.weaponsHero.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            if (x.getLayoutX()<= Game.W && x.getLayoutX()>=0 && x.getLayoutY()<= Game.H && x.getLayoutY()>=0){
                double dd=Math.sqrt(x.x*x.x+x.y*x.y);
                x.relocate(x.getLayoutX() + d*x.x/dd, x.getLayoutY() + d * x.y / dd);
            }
            else {
                z.remove();
                Game.game.board.getChildren().remove(x);
            }
        }
    }
    public static void enemyWeapon(double d)
    {
        Iterator<Weapon> z=Game.game.weaponsVillain.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            if (x.getLayoutX()<= Game.W && x.getLayoutX()>=0 && x.getLayoutY()<= Game.H && x.getLayoutY()>=0){
                double dd=Math.sqrt(x.x*x.x+x.y*x.y);
                x.relocate(x.getLayoutX() + d*x.x/dd, x.getLayoutY() + d * x.y / dd);
            }
            else {
                z.remove();
                Game.game.board.getChildren().remove(x);
            }
        }
    }
}
