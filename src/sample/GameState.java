package sample;

import javafx.scene.layout.Pane;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.util.LinkedList;
import java.util.Vector;

public class GameState {

    public int map_id;
    public LinkedList<Background> backgrounds;

    public Vector<Hero> heroes;
    public LinkedList<Villain> villains;
    public  LinkedList<Villain> shootingVillains;
    public LinkedList<Box> boxes;
    public LinkedList<SpecialObject> specialObjects;
    public LinkedList<Weapon> weaponsHeroes;
    public LinkedList<Weapon> weaponsVillains;


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
            backgrounds.add(background);
        }
    }

    public void loadDynamicElementsFromStream(DataInputStream in) {

    }

}
