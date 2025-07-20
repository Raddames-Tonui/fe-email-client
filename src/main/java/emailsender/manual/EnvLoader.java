package emailsender.manual;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * EnvLoader is responsible for reading environment configuration
 * from a `.env` file located at the root of the project.
 *
 */

/**
 *  # SMTP email address used to send emails and the password samples
 *  SENDER_EMAIL=youremail@example.com
 *
 *  SENDER_PASSWORD=yourpassword123
 *  */
public class EnvLoader {

    // Properties object to hold key-value pairs from the .env file
    private static final Properties env = new Properties();

    // Static block executes once when the class is first loaded
    static {
        try (FileInputStream fis = new FileInputStream(".env")) {
            // Load all the key=value pairs into the Properties object
            env.load(fis);
        } catch (IOException e) {
            throw new RuntimeException("Failed to load .env file from project root", e);
        }
    }

    /**
     * Returns the value associated with a given key from the loaded .env file.
     *
     * @param key the property key to look up (e.g., "SENDER_EMAIL")
     * @return the corresponding value, or null if the key is not found
     */
    public static String get(String key) {
        return env.getProperty(key);
    }
}
