package store;

import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import utils.FileUtil;

public final class DataManager {
    private static DataManager instance;

    public static final String SETTINGS_FILE = "/settings.txt";

    private static final Map<Setting, String> defaultValue = Collections
            .unmodifiableMap(
                    Map.of(Setting.PLAYER_NAME, System.getProperty("user.name"),
                            Setting.PARTNER,
                            "CPP", Setting.SPEED, "4",
                            Setting.BGM_VOLUME, "50", Setting.FX_VOLUME,
                            "50", Setting.PARTNER_VOLUME, "50"));

    private String pathPrefix;
    private final HashMap<Setting, String> setting = new HashMap<>();

    private DataManager() throws IOException {
        this.pathPrefix = FileUtil.getPathPrefix();
        this.readConfig();
        this.writeConfig();
    }

    private void readConfig() {
        var stringToEnum = new HashMap<String, Setting>();

        for (var val : Arrays.asList(Setting.values())) {
            stringToEnum.put(val.name(), val);
        }

        // Set config from files
        for (var line : FileUtil
                .readFileAsLines(this.pathPrefix + DataManager.SETTINGS_FILE)) {
            var token = line.split(" ");

            if (!stringToEnum.keySet().contains(token[0])) {
                continue;
            }

            // TODO Handling invalid token[1]
            this.setting.put(stringToEnum.get(token[0]), token[1]);
        }

        // Set remaining config to default value
        for (var key : DataManager.defaultValue.keySet()) {
            if (!this.setting.containsKey(key)) {
                this.setting.put(key, DataManager.defaultValue.get(key));
            }
        }
    }

    /**
     * Save the current {@link #config} to settings file
     *
     * @throws IOException
     */
    private void writeConfig() throws IOException {
        var content = "";

        for (var key : this.setting.keySet()) {
            content += String.format("%s %s\n", key.name(), this.get(key));
        }

        FileUtil.writeToFile(this.pathPrefix + DataManager.SETTINGS_FILE,
                content);
    }

    public String get(Setting key) {
        return this.setting.get(key);
    }

    public void set(Setting key, String value) {
        this.setting.put(key, value);
        try {
            this.writeConfig();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    public static synchronized DataManager createInstance() throws IOException {
        DataManager.instance = new DataManager();
        return DataManager.instance;
    }

    public static synchronized DataManager getInstance() {
        return DataManager.instance;
    }

    public String getPathPrefix() {
        return this.pathPrefix;
    }

}
