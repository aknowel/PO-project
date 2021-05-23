package sample;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;


public class Sounds {
    Sounds()
    {

    }
    public void playHuh()
    {
        Media sound = new Media(getClass().getResource("/resources/sounds/huh.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public void playCritic()
    {
        Media sound = new Media(getClass().getResource("/resources/sounds/critic_shout.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public void playOpenChest()
    {
        Media sound = new Media(getClass().getResource("/resources/sounds/chest_open.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public void playGameOver()
    {
        Media sound = new Media(getClass().getResource("/resources/sounds/gameOver.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public void playBossIntro()
    {
        Media sound = new Media(getClass().getResource("/resources/sounds/bossIntro.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public void playBossLaugh()
    {
        Media sound = new Media(getClass().getResource("/resources/sounds/bossLaugh.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public void playExplosion()
    {
        Media sound = new Media(getClass().getResource("/resources/sounds/explosion.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
    public void playWonGame()
    {
        Media sound = new Media(getClass().getResource("/resources/sounds/wonGame.mp3").toExternalForm());
        MediaPlayer mediaPlayer = new MediaPlayer(sound);
        mediaPlayer.play();
    }
}
