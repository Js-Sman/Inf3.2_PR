package Aufgabe9_MVC_Grafik2D.Model;

import Aufgabe9_MVC_Grafik2D.View.GrafikFrame;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.standard.DialogTypeSelection;
import javax.swing.*;
import java.awt.print.PrinterJob;
import java.io.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.prefs.Preferences;

/**
 * Diese Klasse hält alle relevanten Informationen über eine Figur und über alle Figuren.
 * Eine Figur wird als ArrayListe von Punkten abgespeichert und in einer andern ArrayListe werden alle Figuren gehalten.
 * <p>
 * Hier werden auch die Preferences gehalten und verwaltet.
 */
public class GrafikModel{

    private GrafikFrame frame;  //Diese Referenz wird gebraucht um den Frame mit UI Elementen zu beeinflussen

    private ArrayList<Figur> figuren;   //Hier werden fertige Figuren abgespeichert

    private Figur figur;    //Das ist die aktuelle Figur die gezeichnet wird

    private Preferences preferences;    //Preferences um letzten Ordner wieder zu öffnen

    public GrafikModel(GrafikFrame frame) {
        //Um die Preference Klasse zu initialisieren, muss ihr ein default Ordner angegeben werden
        this.preferences = Preferences.userNodeForPackage(getClass());    //Den Projektpfad übergeben
        this.frame = frame;
        figur = new Figur();
        figuren = new ArrayList<>();
    }

    public void endFigure() {
        this.figuren.add(figur);
        figur = new Figur();
    }

    public Figur getFigur() {
        return this.figur;
    }

    public ArrayList<Figur> getFiguren() {
        return figuren;
    }


    public void deleteFigures() {
        figuren.clear();
        frame.repaint();    //Dadurch wird im frame die paintComponent Method getriggert
    }


    /**
     * Preferences werden als Dictionary abgespeichert.
     * Der Key, ist der Name, unter dem der Pfad abgespeichert wird.
     * Wenn das Preference Dictionary ausgelesen wird, will es den Key und einen Default Wert der zurückgegeben werden soll,
     * wenn der Key nicht gefunden wird.
     */
    public String getPreferences() {
        return preferences.get("lastDirectory", null);
    }

    /**
     * Preferences werden als Dictionary abgespeichert.
     * Der Key, ist der Name, unter dem der Pfad abgespeichert wird.
     * Der Value ist der Pfad zum verzeichnis das abgespeichert werden soll.
     */
    public void setPreferences(String key, String path) {
        preferences.put(key, path);
    }

    /**
     * In dieser Methode werden die Figuren Serialisiert und als Objektdatei abgespeichert.
     */
    public void saveFigures(String filename) throws IOException {
        //Neues Stream Objekt mit Zielpfad anlegen
        FileOutputStream fileOutputStream = new FileOutputStream(filename);

        //Für Streams immer einen Buffer anlegen, um ein einziges Objekt zu schreiben anstatt einzelne Bytes
        BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(fileOutputStream); //Den Stream angeben der gepuffert werden soll

        //Die Daten sollen Serialisiert werden → in ein Object gepackt werden
        //Der Stream ist also ein Objekt Stream
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(bufferedOutputStream); //Wieder den Stream angeben der als Object geliefert werden soll

        //Eine ArrayListe kann nicht serialisiert werden → muss in Liste umgewandelt werden
        List<Figur> figurenListe = Collections.unmodifiableList(figuren);   //Wandelt die ArrayList in eine statische Liste um die serialisierbar ist

        //Die serialisierte Liste als Objekt in den Stream schicken
        objectOutputStream.writeObject(figurenListe);

        //Der Stream ist jetzt beschrieben aber muss noch geflushed werden, um die Daten tatsächlich weiterzusenden
        objectOutputStream.flush(); //Dieser aufruf triggert das tatsächliche Schreiben in eine Datei

        //Streams müssen immer geschlossen werden da sie sonst Resourcen blockieren
        objectOutputStream.close();
    }

    /**
     * In dieser Methode werden die serialisierte Objektdateien geladen und in Figuren abgespeichert,
     */
    public void loadFigures(String filename) throws IOException, ClassNotFoundException {
        //Neues Stream Objekt mit Zielpfad anlegen
        FileInputStream fileInputStreamStream = new FileInputStream(filename);

        //Für Streams immer einen Buffer anlegen, um ein einziges Objekt zu schreiben anstatt einzelne Bytes
        BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStreamStream); //Den Stream angeben der gepuffert werden soll

        //Die Daten liegen als serialisiertes Objekt vor.
        //Der Stream ist also ein Objekt Stream
        ObjectInputStream objectInputStream = new ObjectInputStream(bufferedInputStream); //Wieder den Stream angeben der als Object geliefert werden soll

        //Der Dateiname wurde schon mit eingebettet und das Object kann direkt ausgelesen werden.
        Object loadedObject = objectInputStream.readObject();

        //Das Objekt kann direkt in eine Liste gecastet werden da es so auch abgespeichert wurde.
        //Schöner ist es aber das nochmal abzufangen.
        if(loadedObject instanceof List<?>){
            List<Figur> serialisierteListe = (List<Figur>) loadedObject;
            //Der ArrayList Konstruktor kann aus einer Liste eine ArrayList machen
            figuren = new ArrayList<>(serialisierteListe);
        }

        //Streams müssen immer geschlossen werden da sie sonst Resourcen blockieren
        objectInputStream.close();

        //Wenn neue Figuren im Model sind, muss der Frame seine paintComponent Methode wieder aufrufen
        frame.repaint();
    }

    public void printFigures() {
        HashPrintRequestAttributeSet printSet = new HashPrintRequestAttributeSet();
        printSet.add(DialogTypeSelection.NATIVE);
        PrinterJob pj = PrinterJob.getPrinterJob();
        pj.setPrintable(frame);

        //Dialog
        if (pj.printDialog(printSet)) {
            try {
                pj.print(printSet);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(frame, ex.toString());
            }
        }

    }
}
