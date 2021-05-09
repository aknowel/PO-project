package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public abstract class Box extends ImageView {
    int i;
    Box(Image img)
    {
        super(img);
    }
    static Box getNewBox(int i)
    {
        return switch(i) {
            case 0 -> new EmptyBox();
            default -> new UpgradeBox();
        };
    }
}
class EmptyBox extends Box
{
    EmptyBox()
    {
        super(new Image("https://icons.iconarchive.com/icons/etherbrian/presto/32/chest-icon.png"));
        i=0;
    }
}
class UpgradeBox extends Box
{
    UpgradeBox()
    {
        super(new Image("https://icons.iconarchive.com/icons/etherbrian/presto/32/chest-icon.png"));
        i=1;
    }
}