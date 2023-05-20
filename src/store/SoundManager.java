package store;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;

public final class SoundManager {

    private static SoundManager instance;
    private static MediaPlayer fxPlayer;
    private static MediaPlayer partnerPlayer;
    private static MediaPlayer bgmPlayer;
    private String path;

    public SoundManager() {
        this.path = "/sounds/";
    }

    public void playFx(String soundPath) {
        if (fxPlayer != null)
            fxPlayer.stop();
        fxPlayer = new MediaPlayer(new Media(
                SoundManager.class.getResource(this.path + soundPath)
                        .toString()));
        fxPlayer.play();
    }

    public void playPartner(String soundPath) {
        if (partnerPlayer != null)
            partnerPlayer.stop();
        partnerPlayer = new MediaPlayer(new Media(
                SoundManager.class.getResource(this.path + soundPath)
                        .toString()));
        partnerPlayer.play();
    }

    public void playBGM(String soundPath) {
        if (bgmPlayer != null)
            bgmPlayer.stop();
        bgmPlayer = new MediaPlayer(new Media(
                SoundManager.class.getResource(this.path + soundPath)
                        .toString()));
        bgmPlayer.play();
    }

    public void stopBGM() {
        if (bgmPlayer != null)
            bgmPlayer.stop();
    }

    public void playBGM() {
        if (bgmPlayer != null)
            bgmPlayer.play();
    }

    public int getTime() {
        return (int) bgmPlayer.getCurrentTime().toMillis();
    }

    public static synchronized SoundManager createInstance() {
        instance = new SoundManager();
        return instance;
    }

    public static synchronized SoundManager getInstance() {
        return instance;
    }
}
