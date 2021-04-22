package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class Weapon extends ImageView {
    enum Directions{
        N, S, W, E, NW, NE, SW, SE
    }
    public Directions dir;
    Weapon(Directions dir, Image i){
        super(i);
        this.dir=dir;
    }
}
