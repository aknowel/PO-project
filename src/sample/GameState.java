package sample;

import javafx.scene.layout.Pane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

public class GameState {

    public int map_id;
    public LinkedList<Background> backgrounds = new LinkedList<>();

    public Vector<Hero> heroes = new Vector<>();
    public LinkedList<Villain> villains = new LinkedList<>();
    public  LinkedList<Villain> shootingVillains = new LinkedList<>();
    public LinkedList<Box> boxes = new LinkedList<>();
    public LinkedList<SpecialObject> specialObjects = new LinkedList<>();
    public LinkedList<Weapon> weaponsHeroes = new LinkedList<>();
    public LinkedList<Weapon> weaponsVillains = new LinkedList<>();


    public void writeStaticElementsToStream(DataOutputStream out) throws IOException {
        out.writeInt(map_id);
        out.writeInt(backgrounds.size());
        for (Background background : backgrounds) {
            out.writeInt(background.id);
            out.writeDouble(background.getLayoutX());
            out.writeDouble(background.getLayoutY());
        }
    }

    public void writeDynamicElementsToStream(DataOutputStream out) {

    }


    public void loadStaticElementsFromStream(DataInputStream in) throws IOException {
        map_id = in.readInt();
        int backgroundsSize = in.readInt();
        for (int i = 0; i < backgroundsSize; i += 1) {
            Background background = Background.newBackground(in.readInt());
            background.setX(in.readDouble());
            background.setY(in.readDouble());
        }
    }

    public void loadDynamicElementsFromStream(DataInputStream in) {

    }

}
