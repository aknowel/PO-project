package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Weapon extends ImageView {
    double x;
    double y;
    Weapon(Image i,double x,double y){
        super(i);
        this.x=x;
        this.y=y;
    }

}
