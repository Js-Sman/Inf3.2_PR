package Aufgabe9_MVC_Grafik2D.Model;

import java.awt.*;
import java.util.ArrayList;
import java.util.prefs.Preferences;

/**
 * Diese Klasse hält alle relevanten Informationen über eine Figur und über alle Figuren.
 * Eine Figur wird als ArrayListe von Punkten abgespeichert und in einer weitern ArrayListe werden alle Figuren gehalten.
 *
 * Hier werden auch die Preferences gehalten und verwaltet.
 */
public class GrafikModel {

    private ArrayList<Figur> figuren;   //Hier werden fertige Figuren abgespeichert

    private Figur figur;    //Das ist die aktuelle Figur die gezeichnet wird

    private Preferences preferences;    //Preferences um letzten Ordner wieder zu öffnen

    public GrafikModel(){
        figur = new Figur();
        figuren = new ArrayList<>();
    }
    public void endFigure() {
        this.figuren.add(figur);
        figur = new Figur();
    }

    public Figur getFigur(){
        return this.figur;
    }

    public ArrayList<Figur> getFiguren(){
        return figuren;
    }

    public void zeichneFiguren(Graphics2D g2) {
        //Die Java-Methode einer foreach Schleife.
        //Für jede Figur in der Figuren ArrayListe wird die zeichnen Funktion der Figur aufgerufen.
        //Die Grafikdaten werden hier weiter gegeben → müssen immer noch disposed werden!
        figuren.forEach(figur->{
            figur.zeichnen(g2);
        });
    }

}
