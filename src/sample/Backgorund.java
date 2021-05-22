package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Backgorund extends ImageView {
    Backgorund(Image img)
    {
        super(img);
    }
}
class Cactus extends Backgorund
{
    Cactus()
    {
        super(new Image("/resources/Images/Cacti.png"));
    }
}
class Barrel extends Backgorund
{
    Barrel()
    {
        super(new Image("/resources/Images/Barrel.png"));
    }
}