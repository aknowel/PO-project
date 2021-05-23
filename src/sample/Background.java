package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Background extends ImageView {
    Background(Image img)
    {
        super(img);
    }
}
class Cactus extends Background
{
    Cactus()
    {
        super(new Image("/resources/Images/Background/Cacti.png"));
    }
}
class Barrel extends Background
{
    Barrel()
    {
        super(new Image("/resources/Images/Background/Barrel.png"));
    }
}
class Stone extends Background
{
    Stone()
    {
        super(new Image("/resources/Images/Background/Stone.png"));
    }
}
class Crystal extends Background
{
    Crystal()
    {
        super(new Image("/resources/Images/Background/Crystal.png"));
    }
}