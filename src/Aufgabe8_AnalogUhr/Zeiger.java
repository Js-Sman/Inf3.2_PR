package Aufgabe8_AnalogUhr;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;

import static java.lang.Math.PI;

/**
 * Ein Zeiger soll ein darstellbares Objekt in einem JFrame sein, deshalb muss die Klasse JComponent erweitern.
 * Für die Nebenläufigkeit muss ein Zeiger Runnable implementieren.
 */
public class Zeiger extends JComponent implements Runnable {

    private final long delay; //Die Zeit, die der Thread wartet, um alpha zu erhöhen.
    private final int laenge; //Die Zeigerlänge


    private double alpha;   //Drehwinkel des Zeigers
    private final Thread thd;


    //Grafische Komponenten
    private final Line2D.Float zeiger;
    private final BasicStroke stift;

    public Zeiger(long delay, int laenge, float dicke){
        this.delay = delay;
        this.laenge = laenge;
        this.alpha = 0;

        this.thd = new Thread(this);

        zeiger = new Line2D.Float();
        stift = new BasicStroke(dicke);
    }

    //Diese Methode startet nur den nach außen privaten Thread
    public void start(){
        thd.start();
    }

    /**
     * Der Zeiger wird immer um einen gewissen Winkel alpha gedreht. Alpha wird in der Nebenläufigen run Methode erhöht
     */
    @Override
    public void run() {
        //In dieser Methode soll der Drehwinkel alpha kontinuierlich erhöht werden
        while (true){

            //Da alpha Nebenläufig verwendet, muss es in einem Synchronisierten Block stehen.
            //So wird verhindert, dass setzen und lesen der variable immer Coherent sind
            synchronized (this){
                alpha += 2*PI/60;   //Immer um 1/60tel weiter drehen
            }

            //Mit repaint wird die paintComponent Methode aufgerufen.
            //Keinen direkten aufruf machen, da die Klasse JComponent einige interne Prozesse beim repainten verarbeitet
            this.repaint();

            //Die entsprechende Zeit für diesen Zeiger warten
            try {
                Thread.sleep(delay);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

        }

    }

    /**
     * In dieser Methode wird der Zeiger einfach nur gezeichnet.
     * Die Methode wird jedes Mal aus der run Methode aufgerufen.
     */
    @Override
    public void paintComponent(Graphics g){
        super.paintComponent(g);    //Die Superklasse muss Initialisiert werden
        Graphics2D g2 = (Graphics2D) g; //Die modernere Klasse Graphics2D verwenden → kann einfach gecastet werden

        //Die Höhe und Breite des JComponents laden → Weil einzige Komponente im Frame ist das die Höhe und Breite des Frames
        float frameBreite = this.getWidth();
        float frameHoehe = this.getHeight();

        //Mit Translate wird der Ursprung um die angegebenen Werte verschoben
        //Default ist (0,0) in der oberen linken Ecke eines Frames
        g2.translate(frameBreite/2, frameHoehe/2);

        //Die ganze Komponente wird jetzt um den Ursprung gedreht → nach d translate ist der Ursprung in der Mitte
        g2.rotate(alpha);


        //Der Zeiger wird jetzt einfach als vertikaler Strich gezeichnet.
        //Auf der JComponent ist der Strich vertikal nach oben → die Komponente dreht sich → der Strich dreht sich mit
        zeiger.setLine(0,0, 0, -laenge);

        //Zeicheneinstellungen und zeichnen
        g2.setStroke(stift);
        g2.setPaint(Color.RED);
        g2.draw(zeiger);


    }
}
