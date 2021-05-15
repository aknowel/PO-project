package sample;

import java.util.Arrays;
import java.util.List;

public class Counter {
    public static List<Integer> list;
    static
    {
        list= Arrays.asList(0,0,0,0,0,0,0,0,0);
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
    public static void killedBoss()
    {
        list.set(3,list.get(3)+1);
    }
    public static void thrownWeapon()
    {
        list.set(4,list.get(4)+1);
    }
    public static void deathless()
    {
        list.set(5,list.get(5)+1);
    }
    public static void thorGames()
    {
        list.set(6,list.get(6)+1);
    }
    public static void games()
    {
        list.set(7,list.get(7)+1);
    }
    public static void victories()
    {
        list.set(8,list.get(8)+1);
    }
}
