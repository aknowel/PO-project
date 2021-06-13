package sample;

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
    public LinkedList<Villain> shootingVillains = new LinkedList<>();
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

    public void writeDynamicElementsToStream(DataOutputStream out) throws IOException {
        out.writeInt(heroes.size());
        for (Hero hero : heroes) {
            out.writeInt(hero.id);
            out.writeDouble(hero.getLayoutX());
            out.writeDouble(hero.getLayoutY());
            out.writeBoolean(hero.barrierCheck);
        }
        out.writeInt(villains.size());
        for (Villain villain : villains) {
            out.writeInt(villain.id);
            out.writeDouble(villain.getLayoutX());
            out.writeDouble(villain.getLayoutY());
        }
        out.writeInt(weaponsHeroes.size());
        for (Weapon weapon : weaponsHeroes) {
            out.writeInt(weapon.id);
            out.writeDouble(weapon.getLayoutX());
            out.writeDouble(weapon.getLayoutY());
        }
        out.writeInt(weaponsVillains.size());
        for (Weapon weapon : weaponsVillains) {
            out.writeInt(weapon.id);
            out.writeDouble(weapon.getLayoutX());
            out.writeDouble(weapon.getLayoutY());
        }
        out.writeInt(specialObjects.size());
        for (SpecialObject specialObject : specialObjects) {
            out.writeInt(specialObject.i);
            out.writeDouble(specialObject.getLayoutX());
            out.writeDouble(specialObject.getLayoutY());
        }
        out.writeInt(boxes.size());
        for (Box box : boxes) {
            out.writeInt(box.i);
            out.writeDouble(box.getLayoutX());
            out.writeDouble(box.getLayoutY());
        }
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

    public void loadDynamicElementsFromStream(DataInputStream in) throws IOException {
        for (Hero hero : heroes) {
            GameClient.game.board.getChildren().remove(hero);
        }
        heroes.clear();
        int heroesSize = in.readInt();
        for (int i = 0; i < heroesSize; i += 1) {
            Hero hero = Hero.getNewHero(0, 0, 10, in.readInt());
            hero.setX(in.readDouble());
            hero.setY(in.readDouble());
            boolean barrierCheck = in.readBoolean();
            heroes.add(hero);
            GameClient.game.board.getChildren().add(hero);
            hero.setBarrier();
        }


        for (Villain villain : villains) {
            GameClient.game.board.getChildren().remove(villain);
        }
        villains.clear();
        int villainsSize = in.readInt();
        for (int i = 0; i < villainsSize; i += 1) {
            Villain villain = Villain.getNewVillain(in.readInt());
            villain.setX(in.readDouble());
            villain.setY(in.readDouble());
            villains.add(villain);
            GameClient.game.board.getChildren().add(villain);
        }

        for (Weapon weapon : weaponsHeroes) {
            GameClient.game.board.getChildren().remove(weapon);
        }
        weaponsHeroes.clear();
        int weaponHeroesSize = in.readInt();
        for (int i = 0; i < weaponHeroesSize; i += 1) {
            Weapon weapon = Weapon.getNewWeapon(in.readInt());
            weapon.setX(in.readDouble());
            weapon.setY(in.readDouble());
            weaponsHeroes.add(weapon);
            GameClient.game.board.getChildren().add(weapon);
        }

        for (Weapon weapon : weaponsVillains) {
            GameClient.game.board.getChildren().remove(weapon);
        }
        weaponsVillains.clear();
        int weaponsVillainsSize = in.readInt();
        for (int i = 0; i < weaponsVillainsSize; i += 1) {
            Weapon weapon = Weapon.getNewWeapon(in.readInt());
            weapon.setX(in.readDouble());
            weapon.setY(in.readDouble());
            weaponsVillains.add(weapon);
            GameClient.game.board.getChildren().add(weapon);
        }

        for (SpecialObject specialObject : specialObjects) {
            GameClient.game.board.getChildren().remove(specialObject);
        }
        specialObjects.clear();
        int specialObjectsSize = in.readInt();
        for (int i = 0; i < specialObjectsSize; i += 1) {
            SpecialObject specialObject = SpecialObject.getNewSpecialObject(in.readInt());
            specialObject.setX(in.readDouble());
            specialObject.setY(in.readDouble());
            specialObjects.add(specialObject);
            GameClient.game.board.getChildren().add(specialObject);
        }

        for (Box box : boxes) {
            GameClient.game.board.getChildren().remove(box);
        }
        boxes.clear();
        int boxesSize = in.readInt();
        for (int i = 0; i < boxesSize; i += 1) {
            Box box = Box.getNewBox(in.readInt());
            box.setX(in.readDouble());
            box.setY(in.readDouble());
            boxes.add(box);
            GameClient.game.board.getChildren().add(box);
        }
    }
}
