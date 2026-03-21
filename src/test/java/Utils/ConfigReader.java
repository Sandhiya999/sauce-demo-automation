package Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {
    private static Properties prop;
    static
    {
        try
        {
            prop = new Properties();
            FileInputStream  fis = new FileInputStream("src/main/resources/config.properties");
            prop.load(fis);
        }
        catch (IOException e)
        {
            throw new RuntimeException("Failed to load config.properties file: ", e);
        }
    }
    public static  String get(String key)
    {
        return prop.getProperty(key);
    }
}

