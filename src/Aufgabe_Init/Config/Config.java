package Aufgabe_Init.Config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.Objects;
import java.util.Properties;

public class Config {
    private static final String PATH = "D:\\WorkingDirectory\\RePo_Projects\\Inf3.2_PR\\Inf3.2_PR\\src\\Aufgabe_Init\\Config\\config.properties";


    private static HashMap<String,Integer> propertiesDictionary;
    private Config() {


    }

    public static int getValue(String key) throws IOException {


        if(propertiesDictionary == null) {
            System.out.println(PATH);
            propertiesDictionary = new HashMap<>();
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(PATH);
            properties.load(fileInputStream);


            for(String propKey : properties.stringPropertyNames()) {
                if (Objects.equals(propKey, "SIZE")) {
                    propertiesDictionary.put(propKey,
                            Integer.parseInt(properties.getProperty("COLUMNS"))
                                    *
                                    Integer.parseInt(properties.getProperty("ROWS")));
                }
                else {
                    propertiesDictionary.put(propKey,
                            Integer.parseInt(properties.getProperty(propKey)));
                }


            }

        }

        return propertiesDictionary.get(key);
    }
}
