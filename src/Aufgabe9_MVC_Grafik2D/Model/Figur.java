package Aufgabe9_MVC_Grafik2D.Model;

import java.awt.*;
import java.awt.geom.Line2D;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Eine Figur ist prinzipiell nur eine Datenklasse da sie eine ArrayListe an Punkten hält.
 * Eine Figur zeichnet sich selbst, indem sie alle Punkte in ihrer ArrayListe mit einer Linie verbindet.
 */
public class Figur {

    Logger logger = Logger.getLogger("MVC_Grafik");

    private ArrayList<Point> punkte;
    private Line2D.Float line;
    private BasicStroke stift;

    //Zwei Point variablen um die Punkte Liste verkettet zu zeichnen
    private Point from;
    private Point to;


    public Figur() {
        this.punkte = new ArrayList<>();
        this.line = new Line2D.Float();
        this.stift = new BasicStroke(3);
    }

    public void addPoint(Point point){
        punkte.add(point);
    }

    public ArrayList<Point> getPunkte(){
        return this.punkte;
    }

    /**
     * Diese Methode zeichnen eine Linie zwischen den Punkten in der Punkte ArrayListe.
     * Die Liste wird verkettet durchlaufen → sonst müsste die ArrayListe für jeden punkt komplett neu durchlaufen werden
     */
    public void zeichnen(Graphics2D g2) {
        if (punkte.size() >= 2) {
            //Für verkettete Liste die ersten beiden Punkte aus der Punkte Liste zuweisen
            from = punkte.getFirst();
            to = null;



            punkte.forEach(punkt -> {
                to = punkt; //Immer der aktuelle Punkt
                line.setLine(from,to);
                from = to;  //Für den nächsten Punkt ist das Ziel der aktuelle Punkt

                //Die Linienstärke setzen
                g2.setStroke(stift);

                //Die aufgebaute Linie zeichnen
                g2.draw(line);
            });


            //Da bis hier die Grafikdaten der JKomponente durchgeschleift wurden, müssen diese hier wieder freigegeben werden!
            //g2.dispose();
        }
        else{
            //Wenn es nicht min. 2 Punkt in der Punkte Liste gibt wird nichts gezeichnet und die Grafik Daten werden einfach freigegeben
            g2.dispose();
        }
    }
}
