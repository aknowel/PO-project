package sample;

import javafx.scene.image.ImageView;

public class HpBar extends ImageView {
    double x;
    double y;
    public void setCoordinates(double x,double y)
    {
        this.x=x;
        this.y=y;
        this.relocate(x,y);
    }
    HpBar()
    {
        super();
    }
}
