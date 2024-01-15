package Aufgabe5_CommandDP.Controller;

/**
 * Da der CommandInvoker eine Referenz auf jedes Kommando hält, bietet sich
 * ein gemeinsames Interface für alle Kommandos an.
 * Die Kommandos sind die tatsächlichen Aktionen die passieren, wenn eine Komponente aus der View aktiviert wird.
 */
public interface CommandInterface {
    //Typische Methoden für ein Kommando

    /**
     * In dieser Methode werden die Aktionen ausgeführt.
     */
    public void execute();

    /**
     * In dieser Methode werden die Daten die beim letzten execute entstanden sind rückgängig gemacht
     */
    public void undo();

    /**
     * Diese Methode bietet eine Abfrage an das Kommando, ob es irreversibel ist.
     */
    public boolean isUndoable();
}
