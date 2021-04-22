package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Weapon extends ImageView {
    double x;
    double y;
    Weapon(double x,double y){
        super("https://icons.iconarchive.com/icons/icons8/windows-8/16/Cultures-Thor-Hammer-icon.png");
        this.x=x;
        this.y=y;
    }

}
