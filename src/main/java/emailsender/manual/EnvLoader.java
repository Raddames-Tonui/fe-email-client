package emailsender.manual;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * Loads key-value pairs from a .env file in the project root.
 */
public class EnvLoader {
    private static final Properties env = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(".env")) {
            env.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file from project root", e);
        }
    }

    public static String get(String key) {
        return env.getProperty(key);
    }
}
