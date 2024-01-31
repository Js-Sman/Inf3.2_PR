package Aufgabe_Kniffel.Utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;
import java.util.logging.*;

public class KniffelLogger {

    private static final String LOGGER_NAME = "Kniffel_Logger";
    //private static final String CONFIG_PATH = "D:\\WorkingDirectory\\RePo_Projects\\Inf3.2_PR\\Inf3.2_PR\\src\\Aufgabe_Kniffel\\Utils\\loggerConfig.properties";
    private static final String CONFIG_PATH = "C:\\WorkingDirectory\\Java\\Inf3.2_PR\\Inf3.2_PR\\src\\Aufgabe_Kniffel\\Utils\\loggerConfig.properties";
    private static Logger lg = null;
    private KniffelLogger() {
    }

    public static Logger getLogger() {

        if (lg == null) {
            lg = Logger.getLogger(LOGGER_NAME);
            initLogger();
        }

        return lg;
    }

    private static void initLogger() {
        try {

            //Properties-Datei von angegebenem Pfad laden.
            //In der Datei stehen Key-Value paare
            Properties properties = new Properties();
            FileInputStream fileInputStream = new FileInputStream(CONFIG_PATH);
            properties.load(fileInputStream);

            //KonsolenHandler anlegen
            ConsoleHandler consoleHandler = new ConsoleHandler();
            consoleHandler.setFormatter(new KniffelFormatter());
            consoleHandler.setLevel(Level.parse(properties.getProperty("LOG_LEVEL")));

            //FileHandler anlegen
            FileHandler fileHandler = new FileHandler(properties.getProperty("LOG_FILE"),false);
            fileHandler.setFormatter(new KniffelFormatter());
            fileHandler.setLevel(Level.parse(properties.getProperty("LOG_LEVEL")));

            //Allgemeine Logger parameter einstellen
            lg.setUseParentHandlers(false);
            lg.setLevel(Level.parse(properties.getProperty("LOG_LEVEL")));

            //Custom Handler dem Logger hinzufügen
            lg.addHandler(consoleHandler);
            lg.addHandler(fileHandler);


        } catch (SecurityException | IOException e) {
            throw new RuntimeException(e);
        }
    }


    static class KniffelFormatter extends java.util.logging.Formatter {


        @Override
        public String format(LogRecord record) {
            String logline = "";

            logline += " | " + record.getLevel();
            logline += " | " + record.getSourceClassName();
            logline += " | " + record.getSourceMethodName();
            logline += ": " + record.getMessage();
            logline += "\n";

            return logline;
        }
    }
}
