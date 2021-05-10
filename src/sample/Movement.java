package sample;

import java.util.Iterator;

public abstract class Movement {
    static Game game;
    static void moveHeroTo (double x, double y){
        if (x>=0 && x<= Game.W -game.hero.getBoundsInLocal().getWidth() && y>=0 && y<= Game.H -game.hero.getBoundsInLocal().getHeight())
            game.hero.relocate(x, y);
        else if (x>=0 && x<= Game.W -game.hero.getBoundsInLocal().getWidth())
            game.hero.relocate(x, game.hero.getLayoutY());
        else if (y>=0 && y<= Game.H -game.hero.getBoundsInLocal().getHeight())
            game.hero.relocate(game.hero.getLayoutX(), y);
    }

    static void moveVillain (){
        double d;
        Iterator<Villain> it=game.villains.iterator();
        while(it.hasNext()){
            Villain currentvillain=it.next();
            d=currentvillain.getSpeed();
            if(game.hero.getBoundsInParent().intersects(currentvillain.getBoundsInParent())) {
                game.lives--;
                game.livesText.setText("Lives: " + game.lives);
                it.remove();
                game.board.getChildren().remove(currentvillain);
                continue;
            }
            double x=currentvillain.getLayoutX();
            double y=currentvillain.getLayoutY();
            double z=game.hero.getLayoutX();
            double v=game.hero.getLayoutY();
            double dd=Math.sqrt((x-z)*(x-z)+(y-v)*(y-v));
            currentvillain.relocate(currentvillain.getLayoutX() +d*(x-z)/dd, currentvillain.getLayoutY()+d*(y-v)/dd);
        }
    }
    static void throwWeapon(double d){
        Iterator<Weapon> z=game.weaponsHero.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            if (x.getLayoutX()<= Game.W && x.getLayoutX()>=0 && x.getLayoutY()<= Game.H && x.getLayoutY()>=0){
                double dd=Math.sqrt(x.x*x.x+x.y*x.y);
                x.relocate(x.getLayoutX() + d*x.x/dd, x.getLayoutY() + d * x.y / dd);
            }
            else {
                z.remove();
                game.board.getChildren().remove(x);
            }
        }
    }
    public static void enemyWeapon(double d)
    {
        Iterator<Weapon> z=game.weaponsVillain.iterator();
        while(z.hasNext()){
            Weapon x=z.next();
            if (x.getLayoutX()<= Game.W && x.getLayoutX()>=0 && x.getLayoutY()<= Game.H && x.getLayoutY()>=0){
                double dd=Math.sqrt(x.x*x.x+x.y*x.y);
                x.relocate(x.getLayoutX() + d*x.x/dd, x.getLayoutY() + d * x.y / dd);
            }
            else {
                z.remove();
                game.board.getChildren().remove(x);
            }
        }
    }
}
