package store;

import java.io.IOException;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import utils.FileUtil;

public final class DataManager {
    private static DataManager instance;

    public static final String MAC_OS_X_PREFIX = "/Library/Application Support/javadx";
    public static final String SETTINGS_FILE = "/settings.txt";

    private static final Map<Setting, String> defaultValue = Collections
            .singletonMap(
                    Setting.PLAYER_NAME, "JavaDX");

    private String pathPrefix;
    private final HashMap<Setting, String> setting = new HashMap<>();

    private DataManager() throws IOException {
        var os = System.getProperty("os.name");

        if (os.startsWith("Mac")) {
            this.pathPrefix = System.getProperty("user.home")
                    + DataManager.MAC_OS_X_PREFIX;
        } else if (os.startsWith("Windows")) {
            this.pathPrefix = Paths.get("").toAbsolutePath().toString();

        } else {
            throw new IOException("Unsupported OS");
        }
        // TODO Windows

        this.readConfig();
        this.writeConfig();
    }

    private void readConfig() {
        var stringToEnum = new HashMap<String, Setting>();
        Arrays.asList(Setting.values()).stream()
                .map(val -> stringToEnum.put(val.name(), val));

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
    }

    public static synchronized DataManager createInstance() throws IOException {
        DataManager.instance = new DataManager();
        return DataManager.instance;
    }

    public static synchronized DataManager getInstance() {
        return DataManager.instance;
    }
}
