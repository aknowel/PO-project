package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;


abstract public class BackgroundSetter {
    static public void setBackgroundObjects(int round)
    {
        BackgroundFactory factory=null;
        if(round==1)
            factory=new Round1BackgroundFactory();
        else if (round==2)
            factory=new Round2BackgroundFactory();
        for (int j=0; j<2; j++)
            for(int i=0; i<3; i++)
            {
                Background b=factory.produce(i+j);
                double x=(i+1)*Game.W/5+Game.W*Game.randomize.nextDouble()/5;
                double y=(2*j+1)*Game.H/6+Game.H*Game.randomize.nextDouble()/3;
                b.relocate(x, y);
                Game.game.backgroundObjects.add(b);
                Game.game.board.getChildren().add(b);
            }
    }
    static public void setBackground(int round)
    {
        String imageName="";
        if(round==1)
            imageName="resources/Images/Background/sand_background.png";
        else if (round==2)
            imageName="resources/Images/Background/cave_background.png";
        BackgroundImage myBI= new BackgroundImage(new Image(imageName,Game.W,Game.H,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
        Game.game.board.setBackground(new javafx.scene.layout.Background(myBI));
    }
    interface BackgroundFactory{
        Background produce (int k);
    }
    private static class Round1BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? new Cactus() : new Barrel();
        }
    }
    private static class Round2BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? new Crystal() : new Stone();
        }
    }
}
