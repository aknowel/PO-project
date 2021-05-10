package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;

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
    static void checkBox(Game game)
    {
        Iterator<Box> x=game.boxes.iterator();
        while(x.hasNext()){
            Box currentBox=x.next();
            if (currentBox.getBoundsInParent().intersects(game.hero.getBoundsInParent())){
                if(currentBox.i==1)
                {
                    game.upgrade=true;
                }
                Game.board.getChildren().remove(currentBox);
                x.remove();
            }
        }
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