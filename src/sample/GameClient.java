package sample;

import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameClient extends Game {

    public GameClient(Pane pane, GameState gameState, Server server) {
        super(pane);
        this.gameState = gameState;

        round = gameState.map_id;
        backgroundObjects = gameState.backgrounds;
        heroes = gameState.heroes;
        villains = gameState.villains;
        shootingVillains = gameState.shootingVillains;
        boxes = gameState.boxes;
        specialObjects = gameState.specialObjects;
        weaponsHero = gameState.weaponsHeroes;
        weaponsVillain = game.weaponsVillain;

        BackgroundSetter.setBackgroundObjects(gameState.map_id);
        BackgroundSetter.setBackground(gameState.map_id);
    }


    public void play(Stage stage) {
        while (true);
    }
}
