package sample;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.Iterator;
import java.util.Random;

public abstract class Box extends ImageView {
    int i;
    static Random randomize=new Random();
    Box(Image img)
    {
        super(img);
    }
    static Box getNewBox(int i)
    {
        return switch(i) {
            case 0 -> new EmptyBox();
            case 1 -> new UpgradeBox();
            case 2 -> new HeartBox();
            default -> new BarrierBox();
        };
    }
    static void checkBox(Game game)
    {
        Iterator<Box> x=game.boxes.iterator();
        while(x.hasNext()){
            Box currentBox=x.next();
            if (currentBox.getBoundsInParent().intersects(game.heroes.get(0).getBoundsInParent())){
                switch(currentBox.i)
                {
                    case 1->game.game.heroes.get(0).upgrade=50;
                    case 2-> {
                        if(game.heroes.get(0).hp<game.heroes.get(0).maxHp) {
                            game.heroes.get(0).hp += 1;
                            game.heroes.get(0).changeHpBar();
                            game.hp_texts.get(game.heroes.get(0)).setText("HP: " + game.heroes.get(0).hp);
                        }
                    }
                    case 3 ->
                            {
                                if(!game.heroes.get(0).barrierCheck)
                                {
                                    game.heroes.get(0).barrierCheck = true;
                                    game.heroes.get(0).barrier = new ImageView(new Image("resources/Images/Boxes/Barrier.png"));
                                    game.heroes.get(0).barrier.setOpacity(0.50);
                                    game.board.getChildren().add(game.heroes.get(0).barrier);
                                }
                                game.heroes.get(0).barrierTime=0;
                            }
                }
                if(randomize.nextInt(3)==1)
                {
                    currentBox.openChest();
                }
                Game.game.board.getChildren().remove(currentBox);
                x.remove();
            }
        }
    }
    public void openChest()
    {
        Sounds sounds=new Sounds();
        sounds.playOpenChest();
    }
    public static EmptyBox newEmptyBox()
    {
        return new EmptyBox();
    }
    public static UpgradeBox newUpgradeBox()
    {
        return new UpgradeBox();
    }
    public static HeartBox newHeartBox()
    {
        return new HeartBox();
    }
    public String toString()
    {
        return this.i + " " + this.getLayoutX()+ " " + this.getLayoutY();
    }
}
class EmptyBox extends Box
{
    EmptyBox()
    {
        super(new Image("/resources/Images/Boxes/Chest.png"));
        i=0;
    }
}
class UpgradeBox extends Box
{
    UpgradeBox()
    {
        super(new Image("/resources/Images/Boxes/Chest.png"));
        i=1;
    }
}
class HeartBox extends Box
{
    HeartBox()
    {
        super(new Image("/resources/Images/Boxes/heart.png"));
        i=2;
    }
}
class BarrierBox extends Box
{
    BarrierBox()
    {
        super(new Image("/resources/Images/Boxes/Shield.png"));
        i=3;
    }
}