package sample;

import javafx.animation.AnimationTimer;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Random;
import java.util.Vector;


public class Game {

    static final double W = 1280, H = 720;


    static final Random randomizer = new Random();

    public final Vector<Hero> heroes = new Vector<>();
    public final LinkedList<Weapon> weaponsHero = new LinkedList<>();
    public final LinkedList<Weapon> weaponsVillain = new LinkedList<>();
    public final LinkedList<Villain> villains = new LinkedList<>();
    public final LinkedList<Villain> shootingVillains = new LinkedList<>();
    public final LinkedList<Background> backgroundObjects = new LinkedList<>();
    public final LinkedList<Box> boxes = new LinkedList<>();
    public final LinkedList<SpecialObject> specialObjects = new LinkedList<>();
    public Pane board;
    public AnimationTimer timer;
    public Text scoreText;
    public HashMap<Hero, Text> hp_texts = new HashMap<>();
    public final int dWeapon = 10;
    public int modifier = 150, villainCounter = modifier - 1, score = 0, livesMax = 10;
    public boolean isBoss = false;
    public boolean pause = false, stop = false;
    public int time = 0, upgrade = 0;
    public final int round;
    public Double mode;
    AnchorPane root;
    public Boss boss = null;
    public static Game game;
    static Random randomize = new Random();
    public VillainFactory villainFactory;
    public int counter = 0;
    public boolean sword=false;

    public Game(Pane board, Double mode, int round, int hp, int heroId) {
        this.mode = mode;
        this.board = board;
        this.round = round;
        game = this;
        heroes.add(Hero.getNewHero(20, H/2, hp, heroId));
        BackgroundSetter.setBackgroundObjects(round);
        BackgroundSetter.setBackground(round);
        villainFactory = VillainFactory.getVillainFactory(round);

    }

    public Game(Pane board, Double mode, int round, Hero h) {
        this.mode = mode;
        this.board = board;
        this.round = round;
        game = this;
        heroes.add(h);
        BackgroundSetter.setBackground(round);
        villainFactory = VillainFactory.getVillainFactory(round);
    }

    public void play(Stage stage) {
        Hero.counter=0;
        Counter.games();
        Counter.thorGames();
        Game.game.mode = mode;
        scoreText = new Text(W / 2, 30, "Score: " + score);
        for (Hero hero : heroes) {
            board.getChildren().add(hero);
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

        board.getChildren().add(scoreText);

        Scene scene = new Scene(board, W, H, Color.POWDERBLUE);
        stage.setScene(scene);
        stage.setTitle("Ragnarok");
        stage.show();

        scene.setOnKeyPressed(event -> {
            if (event.getCode().equals(KeyBinds.W)) heroes.get(0).goNorth = true;
            else if (event.getCode().equals(KeyBinds.S)) heroes.get(0).goSouth = true;
            else if (event.getCode().equals(KeyBinds.A)) heroes.get(0).goWest = true;
            else if (event.getCode().equals(KeyBinds.D)) heroes.get(0).goEast = true;
            else if (event.getCode().equals(KeyBinds.UP)) heroes.get(1).goNorth = true;
            else if (event.getCode().equals(KeyBinds.DOWN)) heroes.get(1).goSouth = true;
            else if (event.getCode().equals(KeyBinds.LEFT)) heroes.get(1).goWest = true;
            else if (event.getCode().equals(KeyBinds.RIGHT)) heroes.get(1).goEast = true;
            else if (event.getCode().equals(KeyBinds.P)) {
                if (!pause & !stop) {
                    timer.stop();
                    pause();
                    pause = true;
                } else if (!stop) {
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
            else if (event.getCode().equals(KeyBinds.UP)) heroes.get(1).goNorth = false;
            else if (event.getCode().equals(KeyBinds.DOWN)) heroes.get(1).goSouth = false;
            else if (event.getCode().equals(KeyBinds.LEFT)) heroes.get(1).goWest = false;
            else if (event.getCode().equals(KeyBinds.RIGHT)) heroes.get(1).goEast = false;
        });
        scene.setOnMouseClicked(event -> {
            if (!pause) {
                heroes.get(0).weapon(event);
            }
        });

        timer = new AnimationTimer() {
            int cnt = 0;
            @Override
            public void handle(long now) {
                if (cnt >= Menu.screen_refresh_divisor) {
                    cnt = 0;
                    for (Hero hero : heroes) {
                        hero.dx = 0;
                        hero.dy = 0;
                        if (hero.goNorth) hero.dy -= 1;
                        if (hero.goSouth) hero.dy += 1;
                        if (hero.goEast) hero.dx += 1;
                        if (hero.goWest) hero.dx -= 1;
                        double length = Math.sqrt(Math.pow(hero.dx, 2) + Math.pow(hero.dy, 2));
                        if (length > 0) {
                            hero.dx /= length;
                            hero.dy /= length;
                            hero.dx *= 3;
                            hero.dy *= 3;
                        }
                    }

                    if (counter < 50 || round==0) {
                        villainCounter++;
                        Villain.newVillain(game, round==0);
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
                    try {
                        for (Hero hero : heroes) {
                            hero.pos_x += hero.dx;
                            hero.pos_y += hero.dy;
                            Movement.moveHeroTo(hero ,hero.pos_x, hero.pos_y);
                        }
                        Movement.throwWeapon(dWeapon);
                        Movement.enemyWeapon(5);
                        Movement.moveVillain();
                    } catch (InstantiationException | IllegalAccessException e) {
                        e.printStackTrace();
                    }
                    if (time == 32 - 8 * mode) {
                        Weapon.newEnemyWeapon(game);
                        time = 0;
                    } else {
                        time++;
                    }
                    for (Hero hero : heroes) {
                        hero.checkHitHero(game);
                        hero.skill();
                    }
                    Villain.checkHitVillain(game);
                    if(Hero.isWarrior(heroes.get(0)) && Hero.counter%7==0) {
                        Villain.swordCheck();
                    }
                    Box.checkBox(game);
                    gameOver(this);
                }
                cnt += 1;
            }
        };
        timer.start();
    }

    void gameOver(AnimationTimer timer) {
        if (heroes.get(0).hp <= 0) {
            timer.stop();
            stop = true;
            Sounds sounds = new Sounds();
            sounds.playGameOver();
            FXMLLoader fxmlLoader = new FXMLLoader();
            if(round>0) {
                fxmlLoader.setLocation(getClass().getResource("/resources/fxml/gameOver.fxml"));
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
            if (round < 4) {
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
