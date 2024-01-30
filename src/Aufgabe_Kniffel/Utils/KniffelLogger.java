package Aufgabe_Kniffel.Utils;

import java.util.Formatter;
import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

public class KniffelLogger {

    private static final String LOGGER_NAME = "Kniffel_Logger";

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

            ConsoleHandler ch = new ConsoleHandler();
            ch.setFormatter(new KniffelFormatter());
            ch.setLevel(Level.ALL);
            lg.setUseParentHandlers(false);
            lg.setLevel(Level.ALL);
            lg.addHandler(ch);

        } catch (SecurityException e) {
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
