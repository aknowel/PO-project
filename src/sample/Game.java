package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import sample.controllers.ChooseHeroController;
import sample.controllers.MultiplayerController;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;


public class Game {

    static final double W = 1280, H = 720;

    public static boolean isServerRunning = false;


    static final Random randomizer = new Random();

    public LinkedList<Client> clients = new LinkedList<>();

    public Vector<Hero> heroes = new Vector<>();
    public LinkedList<Weapon> weaponsHero = new LinkedList<>();
    public LinkedList<Weapon> weaponsVillain = new LinkedList<>();
    public LinkedList<Villain> villains = new LinkedList<>();
    public LinkedList<Villain> shootingVillains = new LinkedList<>();
    public LinkedList<Background> backgroundObjects = new LinkedList<>();
    public LinkedList<Box> boxes = new LinkedList<>();
    public LinkedList<SpecialObject> specialObjects = new LinkedList<>();
    public Pane board;
    public AnimationTimer timer;
    public Text scoreText;
    public ConcurrentHashMap<Hero, Text> hp_texts = new ConcurrentHashMap<>();
    public final int dWeapon = 10;
    public int modifier = 150, villainCounter = modifier - 1, score = 0, livesMax = 10;
    public boolean isBoss = false;
    public boolean pause = false, stop = false;
    public int time = 0; //upgrade = 0;
    public int round;
    public Double mode;
    AnchorPane root;
    public Boss boss = null;
    public static Game game;
    static Random randomize = new Random();
    public VillainFactory villainFactory;
    public int counter = 0;
    boolean clientResponse = false;

    public GameState gameState = new GameState();

    public Game(Pane board) {
        this.mode = 0D;
        this.board = board;
        this.round = 0;
        game = this;
    }

    public Game(Pane board, Double mode, int round, int hp, int heroId) {
        this.mode = mode;
        this.board = board;
        this.round = round;
        game = this;
        heroes.add(Hero.getNewHero(20, H/2, hp, heroId));
        BackgroundSetter.setBackgroundObjects(round);
        BackgroundSetter.setBackground(round);
        villainFactory = VillainFactory.getVillainFactory(round);

        gameState.map_id = round;
        gameState.backgrounds = backgroundObjects;
        gameState.heroes = heroes;
        gameState.villains = villains;
        gameState.shootingVillains = shootingVillains;
        gameState.boxes = boxes;
        gameState.specialObjects = specialObjects;
        gameState.weaponsHeroes = weaponsHero;
        gameState.weaponsVillains = weaponsVillain;
    }

    public Game(Pane board, Double mode, int round, Hero h) {
        this.mode = mode;
        this.board = board;
        this.round = round;
        game = this;
        heroes.add(h);
        BackgroundSetter.setBackground(round);
        villainFactory = VillainFactory.getVillainFactory(round);

        gameState.map_id = round;
        gameState.backgrounds = backgroundObjects;
        gameState.heroes = heroes;
        gameState.villains = villains;
        gameState.shootingVillains = shootingVillains;
        gameState.boxes = boxes;
        gameState.specialObjects = specialObjects;
        gameState.weaponsHeroes = weaponsHero;
        gameState.weaponsVillains = weaponsVillain;
    }

    public void play(Stage stage) {
        Counter.games();
        Game.game.mode = mode;
        scoreText = new Text(W / 2, 30, "Score: " + score);
        for (Hero hero : heroes) {
            board.getChildren().add(hero);
            hero.setBarrier();
            if(Hero.isWarrior(hero))
            {
                hero.setSword();
            }
        }
        scoreText.setFont(new Font(30));
        scoreText.relocate(W / 2 - scoreText.getBoundsInLocal().getWidth() / 2, 0);


        for (int i = 0; i < heroes.size(); i += 1) {
            hp_texts.put(heroes.get(i), new Text(170, 10, "HP: " + heroes.get(i).hp));
            hp_texts.get(heroes.get(i)).setFont(new Font(30));
            hp_texts.get(heroes.get(i)).setFill(Color.RED);
            hp_texts.get(heroes.get(i)).relocate(10 + 150 * i, 0);
            board.getChildren().add(hp_texts.get(heroes.get(i)));
            board.getChildren().add(heroes.get(i).hpBar);
        }

        for(Villain villain:villains)
        {
            villain.changeHpBar();
        }

        for (SpecialObject specialObject:specialObjects)
        {
            specialObject.setImage();
        }

        board.getChildren().add(scoreText);

        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        stage.setScene(scene);
        stage.setTitle("Ragnarok");
        stage.show();
        scene.setOnMouseMoved(event -> {Mouse.x=event.getSceneX();
        Mouse.y=event.getSceneY();});
        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyBinds.W)) heroes.get(0).goNorth = true;
            else if (event.getCode().equals(KeyBinds.S)) heroes.get(0).goSouth = true;
            else if (event.getCode().equals(KeyBinds.A)) heroes.get(0).goWest = true;
            else if (event.getCode().equals(KeyBinds.D)) heroes.get(0).goEast = true;
            else if(event.getCode().equals(KeyBinds.SPACE) && heroes.get(0).skillCooldown<=0) heroes.get(0).heroSkill = true;
            else if (event.getCode().equals(KeyBinds.P)) {
                if (!pause & !stop & !isServerRunning) {
                    timer.stop();
                    pause();
                    pause = true;
                } else if (!stop & !isServerRunning) {
                    board.getChildren().remove(root);
                    pause = false;
                    timer.start();
                }
            }
        });

        scene.setOnKeyReleased(event -> {
            if (event.getCode().equals(KeyBinds.W)) heroes.get(0).goNorth = false;
            else if (event.getCode().equals(KeyBinds.S)) heroes.get(0).goSouth = false;
            else if (event.getCode().equals(KeyBinds.A)) heroes.get(0).goWest = false;
            else if (event.getCode().equals(KeyBinds.D)) heroes.get(0).goEast = false;
        });
        scene.setOnMouseClicked(event -> {
            if (!pause) {
                heroes.get(0).newWeapon(event);
            }
        });

        timer = new AnimationTimer() {
            int cnt = 0;
            @Override
            public void handle(long now) {
                if (cnt >= Menu.screen_refresh_divisor) {

                    if (ChooseHeroController.clientJoined) {
                        ChooseHeroController.clientJoined = false;
                        hp_texts.put(ChooseHeroController.newHero, new Text(170, 10, "HP: " + ChooseHeroController.newHero.hp));
                        hp_texts.get(ChooseHeroController.newHero).setFont(new Font(30));
                        hp_texts.get(ChooseHeroController.newHero).setFill(Color.RED);
                        hp_texts.get(ChooseHeroController.newHero).relocate(10 + 150, 0);
                        board.getChildren().add(hp_texts.get(ChooseHeroController.newHero));
                    }

                    cnt = 0;
                    Hero h = heroes.get(0);
                    h.dx = 0;
                    h.dy = 0;
                    if (h.goNorth) h.dy -= 1;
                    if (h.goSouth) h.dy += 1;
                    if (h.goEast) h.dx += 1;
                    if (h.goWest) h.dx -= 1;
                    double length = Math.sqrt(Math.pow(h.dx, 2) + Math.pow(h.dy, 2));
                    if (length > 0) {
                        h.dx /= length;
                        h.dy /= length;
                        h.dx *= h.speed;
                        h.dy *= h.speed;
                    }

                    if (counter < 2 || round==0) {
                        villainCounter++;
                        Villain.newVillain(round==0);
                    } else if (villains.size() == 0) {
                        if (!isBoss) {
                            isBoss = true;
                            boss = villainFactory.produceBoss(mode);
                            boss.shout();
                            boss.relocate(W, Math.random() * (H - boss.getBoundsInLocal().getHeight()));
                            villains.add(boss);
                            board.getChildren().add(boss);
                        } else {
                            isBossDefeat(timer);
                        }
                    }
                    if (isBoss) {
                        if (boss.isAlive()) {
                            boss.skill();
                        }
                    }
                    Hero.decreaseVulnerability();
                    Villain.cooldownSODecrease();
                    SpecialObject.specialObjectsSkills();
                    try {
                        for (Hero hero : heroes) {
                            hero.pos_x += hero.dx;
                            hero.pos_y += hero.dy;
                            Movement.moveHeroTo(hero ,hero.pos_x, hero.pos_y);
                        }
                        if(Hero.isWarrior(heroes.get(0))) {
                            Hero.Warrior warrior=(Hero.Warrior) heroes.get(0);
                            warrior.moveSword();
                        }
                        Movement.throwWeapon(dWeapon);
                        Movement.enemyWeapon(5);
                        Movement.moveVillain();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (time == 32 - 8 * mode) {
                        Weapon.newEnemyWeapon();
                        time = 0;
                    } else {
                        time++;
                    }
                    for (Hero hero : heroes) {
                        hero.checkHitHero();
                        hero.skill();
                        if(Hero.isWarrior(hero) && hero.counter%7==0) {
                            Villain.swordCheck();
                        }
                    }
                    Villain.checkHitVillains();
                    Box.checkBox();
                    gameOver(this);
                    if (isServerRunning) {
                        for (Client client : clients) {
                            try {
                                if (!clientResponse) {
                                    gameState.writeDynamicElementsToStream(client.out);
                                    clientResponse = true;
                                } else {
                                    client.hero.dx = client.in.readDouble();
                                    client.hero.dy = client.in.readDouble();
                                    client.hero.dx *= heroes.get(1).speed;
                                    client.hero.dy *= heroes.get(1).speed;
                                    board.getChildren().remove(client.hero);
                                    board.getChildren().remove(client.hero.hpBar);
                                    board.getChildren().add(client.hero);
                                    client.hero.setHpBar();
                                    client.hero.setHpBar();
                                    client.hero.changeHpBar();
                                    board.getChildren().add(client.hero.hpBar);

                                    int size=client.in.readInt();
                                    Weapon newWeapon;
                                    for(int i=0;i<size;i++)
                                    {
                                        switch (heroes.get(1).id)
                                        {
                                            case 1 -> newWeapon=new Axe(client.in.readDouble(),client.in.readDouble());
                                            case 2 ->{
                                                if(heroes.get(1).upgrade>0)
                                                {
                                                    newWeapon=new SuperHammer(client.in.readDouble(),client.in.readDouble());
                                                    heroes.get(1).upgrade--;
                                                }
                                                else
                                                {
                                                    newWeapon=new Hammer(client.in.readDouble(),client.in.readDouble());
                                                }
                                            }
                                            case 3 -> {
                                                if(heroes.get(1).counter==2) {
                                                    newWeapon = new Shuriken(client.in.readDouble(), client.in.readDouble());
                                                    heroes.get(1).counter=0;
                                                }
                                                else
                                                    {
                                                        heroes.get(1).counter++;
                                                        continue;
                                                    }
                                            }
                                            default -> newWeapon=new Hammer(client.in.readDouble(),client.in.readDouble());
                                        }
                                        newWeapon.relocate(
                                                heroes.get(1).getLayoutX() + heroes.get(1).getBoundsInLocal().getWidth(), heroes.get(1).getLayoutY());
                                        weaponsHero.add(newWeapon);
                                        board.getChildren().add(newWeapon);
                                    }

                                    boolean heroSkill=client.in.readBoolean();
                                    if(heroSkill && heroes.get(1).skillCooldown<=0)
                                    {
                                        heroes.get(1).heroSkill=true;
                                    }

                                    clientResponse = false;
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    }
                }
                cnt += 1;
            }
        };
        timer.start();
    }

    void gameOver(AnimationTimer timer) {
        for(Hero hero:Game.game.heroes) {
            if (hero.hp <= 0 && Game.game.heroes.size()==1) {
                timer.stop();
                stop = true;
                Sounds sounds = new Sounds();
                sounds.playGameOver();
                FXMLLoader fxmlLoader = new FXMLLoader();
                if (round > 0) {
                    fxmlLoader.setLocation(getClass().getResource("/resources/fxml/gameOver.fxml"));
                    try {
                        root = fxmlLoader.load();
                        root.setLayoutX(445);
                        root.setLayoutY(193);
                        board.getChildren().add(root);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else {
                    fxmlLoader.setLocation(getClass().getResource("/resources/fxml/gameOverSurvi.fxml"));
                    try {
                        root = fxmlLoader.load();
                        root.setLayoutX(445);
                        root.setLayoutY(193);
                        board.getChildren().add(root);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
            else if(hero.hp<=0 && Game.game.heroes.size()>1)
            {
                Game.game.board.getChildren().remove(hero);
                Game.game.board.getChildren().remove(hero.hpBar);
                Game.game.board.getChildren().remove(hp_texts.get(hero));
                hero.deleted=true;
                //Game.game.heroes.remove(hero);
            }
        }
    }

    private void pause() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        if(round>0) {
            fxmlLoader.setLocation(getClass().getResource("/resources/fxml/pause.fxml"));
            try {
                root = fxmlLoader.load();
                root.setLayoutX(445);
                root.setLayoutY(193);
                board.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else
        {
            fxmlLoader.setLocation(getClass().getResource("/resources/fxml/pauseSurvi.fxml"));
            try {
                root = fxmlLoader.load();
                root.setLayoutX(445);
                root.setLayoutY(193);
                board.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void isBossDefeat(AnimationTimer timer) {
        if (!boss.isAlive()) {
            timer.stop();
            stop = true;
            Counter.victories();
            Counter.killedBoss();
            Sounds sounds = new Sounds();
            sounds.playWonGame();
            if (heroes.get(0).hp == livesMax) {
                Counter.deathless();
            }
            FXMLLoader fxmlLoader = new FXMLLoader();
            if (round < 6) {
                fxmlLoader.setLocation(getClass().getResource("/resources/fxml/bossDefeat.fxml"));
            } else {
                fxmlLoader.setLocation(getClass().getResource("/resources/fxml/gameEnd.fxml"));
            }
            try {
                root = fxmlLoader.load();
                root.setLayoutX(340);
                root.setLayoutY(160);
                board.getChildren().add(root);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            for (Hero hero : heroes) {
                hero.hp=0;
                hp_texts.get(hero).setText("HP: " + hero.hp);
            }
        }
    }
}
