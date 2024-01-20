package Aufgabe9_MVC_Grafik2D.Controller;

import Aufgabe9_MVC_Grafik2D.View.GrafikFrame;

import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.util.logging.Logger;

/**
 * Dieser Controller hört auf die Maus Aktionen innerhalb des GrafikFrames.
 * Er ist nur mit dem Frame verbunden und übermittelt die Maus Aktionen.
 */
public class GrafikController extends MouseAdapter implements MouseMotionListener {

    private GrafikFrame frame;

    public GrafikController(GrafikFrame frame) {
        this.frame = frame;
    }

    public void registerEvents() {
        //Es werden zwei Events registriert und beide werden mit der default Methode der Klasse abgehandelt
        frame.addMouseMotionListener(this);     //Für das mouseDragged Event
        frame.addMouseListener(this);   //Für das mouseReleased Event
    }

    /**
     * Dieses Event wird gefeuert, sobald die Maus geklickt wird. Danach wird die Funktion
     * automatisch so lange im Loop aufgerufen, bis die Maus losgelassen wird.
     */
    @Override
    public void mouseDragged(MouseEvent evt){
        //Immer die aktuelle Maus position abfragen
        Point point = evt.getPoint();
        frame.addPointToCurrentFigure(point);
    }

    @Override
    public void mouseReleased(MouseEvent evt){
        /*
        Beim Los lassen der Maus wird die Figur beendet → Führt dazu, dass im Model die Figur
        in die Figuren Liste übernommen wird und eine neue Figur angelegt wird
         */
        frame.endFigure();
    }

}
