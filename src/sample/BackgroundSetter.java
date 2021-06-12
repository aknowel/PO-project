package sample;

import javafx.scene.image.Image;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;

import java.util.Random;


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
                    case 5->new Round5BackgroundFactory();
                    case 6->new Round6BackgroundFactory();
                    default -> new SurvivalBackGroundFactory();
                };
        for (int j=0; j<2; j++)
            for(int i=0; i<3; i++)
            {
                Background b=factory.produce(i+j);
                double x=(i+1)*Game.W/5+Game.W*Game.randomize.nextDouble()/5;
                double y=(2*j+1)*Game.H/6+Game.H*Game.randomize.nextDouble()/3;
                b.relocate(x, y);
                if(b instanceof SpecialObject)
                {
                    Game.game.specialObjects.add((SpecialObject) b);
                }
                else {
                    Game.game.backgroundObjects.add(b);
                }
                Game.game.board.getChildren().add(b);
            }
    }
    static public void setBackground(int round)
    {
        String imageName;
        imageName=switch (round)
                {
                    case 1->"resources/Images/Background/wild_grass_background.png";
                    case 2->"resources/Images/Background/cave_background.png";
                    case 3->"resources/Images/Background/sand_background.png";
                    case 4->"resources/Images/Background/cave_background2.png";
                    case 5->"resources/Images/Background/grass_background.png";
                    case 6->"resources/Images/Background/sandstone_background.png";
                    default -> "resources/Images/Background/ground_background.png";
                };
        BackgroundImage myBI= new BackgroundImage(new Image(imageName,Game.W,Game.H,false,true),
                BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT, null, null);
        Game.game.board.setBackground(new javafx.scene.layout.Background(myBI));
    }
    private static Background specialObjectsGenerator(Background bg,int i)
    {
        Random random=new Random();
        if(i%2==0) {

            return switch (random.nextInt()%8)
                    {
                        case 1 -> new SpeedUp();
                        case 2 -> new SpiderWeb();
                        case 3 -> new Fire();
                        default -> bg;
                    };
        }
        return switch (random.nextInt()%8)
                {
                    case 1 -> new SpeedUp();
                    case 2 -> new Swamp();
                    case 3 -> new Fire();
                    default -> bg;
                };
    }
    interface BackgroundFactory{
        Background produce (int k);
    }
    private static class Round1BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ?  specialObjectsGenerator(new Barrel(),1) : specialObjectsGenerator(new Gazebo(),1);
        }
    }
    private static class Round2BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? specialObjectsGenerator(new Crystal(),2) : specialObjectsGenerator(new Stone(),2);
        }
    }
    private static class Round3BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? specialObjectsGenerator(new Cactus(),3) : specialObjectsGenerator(new Barrel(),3);
        }
    }
    private static class Round4BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? specialObjectsGenerator(new Stone2(),4) : specialObjectsGenerator(new Crystal(),4);
        }
    }
    private static class Round5BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? specialObjectsGenerator(new Shrub(),5) : specialObjectsGenerator(new Stone2(),5);
        }
    }
    private static class Round6BackgroundFactory implements BackgroundFactory{
        @Override
        public Background produce (int k)
        {
            return (k%2==0) ? specialObjectsGenerator(new MaleBust(),6) : specialObjectsGenerator(new FemaleBust(),6);
        }
    }
    private static class SurvivalBackGroundFactory implements BackgroundFactory{
        @Override
        public Background produce(int k)
        {
            return (k%2==0) ? specialObjectsGenerator(new Stone(),7) : specialObjectsGenerator(new Gazebo(),7);
        }
    }
}
