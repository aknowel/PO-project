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
            case 0 -> new UpgradeBox();
            case 1 -> new UpgradeBox();
            case 2 -> new HeartBox();
            default -> new BarrierBox();
        };
    }
    static void checkBox()
    {
        Iterator<Box> x=Game.game.boxes.iterator();
        while(x.hasNext()){
            Box currentBox=x.next();
            for(Hero hero: Game.game.heroes) {
                if (currentBox.getBoundsInParent().intersects(hero.getBoundsInParent())) {
                    Counter.checkedBoxes();
                    switch (currentBox.i) {
                        case 1 -> {
                            hero.upgrade = 50;
                            hero.changeHpBar();
                        }
                        case 2 -> {
                            if (hero.hp < hero.maxHp) {
                                hero.hp += 1;
                                hero.changeHpBar();
                                Game.game.hp_texts.get(hero).setText("HP: " + hero.hp);
                            }
                        }
                        case 3 -> {
                            if (!hero.barrierCheck) {
                                hero.barrierCheck = true;
                                hero.barrier = new ImageView(new Image("resources/Images/Boxes/Barrier.png"));
                                hero.barrier.setOpacity(0.50);
                                Game.game.board.getChildren().add(hero.barrier);
                            }
                            hero.barrierTime = 0;
                        }
                    }
                    if (randomize.nextInt(3) == 1) {
                        currentBox.openChest();
                    }
                    Game.game.board.getChildren().remove(currentBox);
                    x.remove();
                }
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
    public static BarrierBox newBarrierBox()
    {
        return new BarrierBox();
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