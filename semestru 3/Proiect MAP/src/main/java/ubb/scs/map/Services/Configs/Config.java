package ubb.scs.map.Services.Configs;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Config {
    static InputStream CONFIG = Config.class.getClassLoader().getResourceAsStream("application.properties");

    public static Properties getProperties() {
        Properties properties=new Properties();
        try {
            properties.load(CONFIG);
            return properties;
        } catch (IOException e) {
            throw new RuntimeException("Cannot load config properties");

        }
    }
}
