package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;


abstract public class BackgroundSetter {
    static public void setBackgroundObjects(int round)
    {
        BackgroundFactory factory;
        factory=switch (round)
                {
                    case 1->new Round1BackgroundFactory();
                    case 2->new Round2BackgroundFactory();
                    case 3->new Round3BackgroundFactory();
                    case 4->new Round4BackgroundFactory();
                    default -> new SurvivalBackGroundFactory();
                };
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
        String imageName;
        imageName=switch (round)
                {
                    case 1->"resources/Images/Background/sand_background.png";
                    case 2->"resources/Images/Background/cave_background.png";
                    case 3->"resources/Images/Background/grass_background.png";
                    case 4->"resources/Images/Background/sandstone_background.png";
                    default -> "resources/Images/Background/ground_background.png";
                };
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
    private static class Round3BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? new Shrub() : new Stone2();
        }
    }
    private static class Round4BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? new MaleBust() : new FemaleBust();
        }
    }
    private static class SurvivalBackGroundFactory implements BackgroundFactory{
        @Override
        public Background produce(int k)
        {
            return (k%2==0) ? new Stone() : new Gazebo();
        }
    }
}
