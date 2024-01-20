package Aufgabe9_MVC_Grafik2D.Model;

import java.awt.*;
import java.awt.geom.Line2D;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Eine Figur ist prinzipiell nur eine Datenklasse da sie eine ArrayListe an Punkten hält.
 * Eine Figur zeichnet sich selbst, indem sie alle Punkte in ihrer ArrayListe mit einer Linie verbindet.
 *
 * Da eine Figur zum Speicher und Öffnen serializiert werden muss, muss die Klasse zwingend das entsprechende Interface implementieren
 */
public class Figur implements Serializable {

    private ArrayList<Point> punkte;
    private Line2D.Float line;


    //Zwei Point variablen um die Punkte Liste verkettet zu zeichnen
    private Point to;
    private Point from;


    public Figur() {
        this.punkte = new ArrayList<>();
        this.line = new Line2D.Float();
    }

    public void addPoint(Point point){
        punkte.add(point);
    }


    /**
     * Diese Methode zeichnen eine Linie zwischen den Punkten in der Punkte ArrayListe.
     * Die Liste wird verkettet durchlaufen → sonst müsste die ArrayListe für jeden punkt komplett neu durchlaufen werden.
     */
    public void zeichnen(Graphics2D g2) {
        if (punkte.size() >= 2) {
            //Für verkettete Liste die ersten beiden Punkte aus der Punkte Liste zuweisen
            to = punkte.getFirst();
            from = null;


            //For Each ist in Java eine Methode einer ArrayListe.
            punkte.forEach(punkt -> {
                //Es wird immer nur ein Segment zwischen zei Punkten gezeichnet

                from = punkt; //Immer der aktuelle Punkt
                line.setLine(from, to);
                to = from;  //Für den nächsten Punkt ist das Ziel der aktuelle Punkt

                //Die Linienstärke setzen
                g2.setStroke(new BasicStroke(3));

                //Die aufgebaute Linie zeichnen
                g2.draw(line);
            });
        }
    }
}
