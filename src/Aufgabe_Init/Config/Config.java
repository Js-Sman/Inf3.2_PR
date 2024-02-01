package Aufgabe_Init.Config;

import javax.crypto.spec.IvParameterSpec;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Properties;

public class Config {
    private static final String PATH = "D:\\WorkingDirectory\\RePo_Projects\\Inf3.2_PR\\Inf3.2_PR\\src\\Aufgabe_Init\\Config\\config.properties";

    private Config() {

    }

    public static int getValue(String key) throws IOException {

        Properties properties = new Properties();
        FileInputStream fileInputStream = new FileInputStream(PATH);
        properties.load(fileInputStream);

        return Integer.parseInt(properties.getProperty(key));
    }
}
