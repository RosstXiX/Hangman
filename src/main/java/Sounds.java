import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.util.Duration;

public class Sounds {
    private static final MediaPlayer mainMenuSong = new MediaPlayer(new Media(Sounds.class.getResource("/sounds/mainMenuMusic.mp3").toString()));
    private static final MediaPlayer gameSong = new MediaPlayer(new Media(Sounds.class.getResource("/sounds/gameMusic.mp3").toString()));
    static {
        mainMenuSong.setCycleCount(MediaPlayer.INDEFINITE);
        gameSong.setCycleCount(MediaPlayer.INDEFINITE);
        gameSong.setVolume(0.35);
    }
    private static final Media buttonSound = new Media(Sounds.class.getResource("/sounds/button.mp3").toString());

    public static void playMainMenuMusic() {
        mainMenuSong.play();
    }

    public static void playGameMusic() {
        gameSong.play();
    }

    public static void playButtonSound() {
        new MediaPlayer(buttonSound).play();
    }

    public static void stopMainMenuMusic() {
        mainMenuSong.stop();
        mainMenuSong.seek(Duration.ZERO);
    }

    public static void stopGameMusic() {
        gameSong.stop();
        gameSong.seek(Duration.ZERO);
    }

}