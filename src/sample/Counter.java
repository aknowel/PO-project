package sample;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Counter {
    public static List<Integer> list;
    public static List<Limits> limits;
    static
    {
        list= Arrays.asList(0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0);
        limits=new ArrayList<>();
        limits.add(new Limits(100,500,1000,5000));
        limits.add(new Limits(50,250,500,2500));
        limits.add(new Limits(100,500,1000,5000));
        limits.add(new Limits(100,500,1000,5000));
        limits.add(new Limits(50,250,500,2500));
        limits.add(new Limits(100,500,1000,5000));
        limits.add(new Limits(100,500,1000,5000));
        limits.add(new Limits(50,250,500,2500));
        limits.add(new Limits(50,250,500,2500));
        limits.add(new Limits(50,250,500,2500));
        limits.add(new Limits(10,50,100,500));
        limits.add(new Limits(500,2500,5000,25000));
        limits.add(new Limits(500,2500,5000,25000));
        limits.add(new Limits(250,1250,2500,12500));
        limits.add(new Limits(25,125,250,1250));
        limits.add(new Limits(25,125,250,1250));
        limits.add(new Limits(25,125,250,1250));
        limits.add(new Limits(1,5,10,50));
        limits.add(new Limits(50,250,500,2500));
        limits.add(new Limits(50,250,500,2500));
        limits.add(new Limits(10,50,100,500));
    }
    public static void killedSpider()
    {
        list.set(0,list.get(0)+1);
    }
    public static void killedPredator()
    {
        list.set(1,list.get(1)+1);
    }
    public static void killedSkull()
    {
        list.set(2,list.get(2)+1);
    }
    public static void killedBat()
    {
        list.set(3,list.get(3)+1);
    }
    public static void killedMummy()
    {
        list.set(4,list.get(4)+1);
    }
    public static void killedOgre()
    {
        list.set(5,list.get(5)+1);
    }
    public static void killedOrc()
    {
        list.set(6,list.get(6)+1);
    }
    public static void killedVampire()
    {
        list.set(7,list.get(7)+1);
    }
    public static void killedWizard()
    {
        list.set(8,list.get(8)+1);
    }
    public static void killedZombie()
    {
        list.set(9,list.get(9)+1);
    }
    public static void killedBoss()
    {
        list.set(10,list.get(10)+1);
    }
    public static void thrownWeapon()
    {
        list.set(11,list.get(11)+1);
    }
    public static void useSword()
    {
        list.set(12,list.get(12)+1);
    }
    public static void thrownShuriken()
    {
        list.set(13,list.get(13)+1);
    }
    public static void thorGames()
    {
        list.set(14,list.get(14)+1);
    }
    public static void warriorGames()
    {
        list.set(15,list.get(15)+1);
    }
    public static void assassinGames()
    {
        list.set(16,list.get(16)+1);
    }
    public static void deathless()
    {
        list.set(17,list.get(17)+1);
    }
    public static void games()
    {
        list.set(18,list.get(18)+1);
    }
    public static void checkedBoxes()
    {
        list.set(19,list.get(19)+1);
    }
    public static void victories()
    {
        list.set(20,list.get(20)+1);
    }
    public static class Limits
    {
        public int first,second,third,fourth;
        Limits(int first,int second,int third,int fourth)
        {
            this.first=first;
            this.second=second;
            this.third=third;
            this.fourth=fourth;
        }
    }
}
