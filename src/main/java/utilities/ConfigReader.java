package utilities;



import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.Logger;

public class ConfigReader {
    private  static final Properties properties = new Properties();
    private static final Logger logger = Logger.getLogger(ConfigReader.class.getName());

    static {
        try {
            FileInputStream file = new FileInputStream("src/test/resources/config.properties");
             properties.load(file);
        } catch (IOException e) {
             logger.severe("failed to load config.properties" + e.getMessage());
        }
    }

    public static String get(String key){
     return properties.getProperty(key);
    }
}
