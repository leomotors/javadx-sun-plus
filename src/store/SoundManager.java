package store;

import java.io.File;
import java.io.IOException;

import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import logic.core.Chart;
import utils.FileUtil;

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
        fxPlayer.setVolume(Integer
                .parseInt(DataManager.getInstance().get(Setting.FX_VOLUME))
                / 100.00);
        fxPlayer.play();
    }

    public void playPartner(String soundPath) {
        if (partnerPlayer != null)
            partnerPlayer.stop();
        partnerPlayer = new MediaPlayer(new Media(
                SoundManager.class.getResource(this.path + soundPath)
                        .toString()));
        partnerPlayer.setVolume(Integer
                .parseInt(DataManager.getInstance().get(Setting.PARTNER_VOLUME))
                / 100.00);
        partnerPlayer.play();
    }

    public void playBGM(String soundPath) {
        if (bgmPlayer != null)
            bgmPlayer.stop();
        bgmPlayer = new MediaPlayer(new Media(
                SoundManager.class.getResource(this.path + soundPath)
                        .toString()));
        bgmPlayer.setVolume(Integer
                .parseInt(DataManager.getInstance().get(Setting.BGM_VOLUME))
                / 100.00);
        bgmPlayer.play();
    }

    public void playBGM(Chart chart) throws IOException {
        var chartId = chart.id();
        var location = FileUtil.getPathPrefix() + SongManager.SONGS_DIR
                + chartId;
        var url = new File(location + "/music.mp3").toURI().toString();
        if (bgmPlayer != null)
            bgmPlayer.stop();
        bgmPlayer = new MediaPlayer(new Media(url));
        bgmPlayer.setVolume(Integer
                .parseInt(DataManager.getInstance().get(Setting.BGM_VOLUME))
                / 100.00);
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

    public void setBGMVolume(int volume) {
        DataManager.getInstance().set(Setting.BGM_VOLUME,
                String.valueOf(volume));
        bgmPlayer.setVolume(Integer
                .parseInt(DataManager.getInstance().get(Setting.BGM_VOLUME))
                / 100.00);
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
