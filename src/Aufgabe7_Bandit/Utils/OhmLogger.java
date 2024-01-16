/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package Aufgabe7_Bandit.Utils;

import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDateTime;
import java.util.Properties;
import java.util.logging.*;


public class OhmLogger
{
  private static final String LOGGER_NAME = "OhmLogger";
  private static Logger lg = null;
  
  /**
   * privater Konstrukter -> Singleton
   */
  private OhmLogger() 
  {
  }
  
  /**
   * Public Method zum Erstellen des Loggers und zum Aufruf der Methode 
   * zum Initialisieren
   * @return Referenz auf Logger
   */
  public static Logger getLogger()
  {
    if (lg == null)
    {
      lg = Logger.getLogger(LOGGER_NAME);
      initLogger();
    }
    return lg;
  }
  
  /**
   * Methode zum Initialisieren, Suchen der Konfigurationsdatei, Anlegen eines
   * neuen Console Handlers, Löschen der bisherigen Standard Handler und 
   * Einfügen des eigenen Handler, Setzen des Levels, je nach Properties
   */
  private static void initLogger()
  {
    try  {
        //Mit einer Properties Klasse können ".properties" Dateien eingelesen werden.
        Properties properties = new Properties();
        //Die Datei als Ressource einlesen → Die Datei muss im selben Verzeichnis wie der OhmLogger sein
        InputStream inputStream = OhmLogger.class.getResourceAsStream("logger.properties");
        //Und als Properties laden
        properties.load(inputStream);

        //Konsolen Handler für den Logger erstellen
        ConsoleHandler ch = new ConsoleHandler();
        ch.setFormatter(new OhmFormatter());
        lg.setUseParentHandlers(false); //Um die zusätzliche Verwendung des default Loggers zu unterbinden
        lg.addHandler(ch);
        lg.setLevel(Level.parse(properties.getProperty("LOG_LEVEL")));  //Das Log Level aus der Properties Datei parsen und verwenden

        //File Handler für den Logger einrichten
        FileHandler fileHandler = new FileHandler(properties.getProperty("LOG_DATEI"), false);
        fileHandler.setFormatter(new OhmFormatter());
        lg.addHandler(fileHandler);
     

    } catch (IOException | SecurityException e) {
      System.err.println("Error configuring OhmLogger: " + e.getMessage());
    }
    
  }
}

/**
 * Klasse zum Setzen des Formates des Auszugebenden Log-Strings
 * @author ahren
 */
class OhmFormatter extends Formatter
{
  @Override
  public String format(LogRecord record)
  {
    String logline = "| ";
    
    LocalDateTime ldt = LocalDateTime.now();
    logline += ldt.toString();
    logline += " | " + record.getLevel();
    logline += " | " + record.getSourceClassName();
    logline += ": " + record.getMessage();
    logline += "\n";
    
    return logline;
  }
}