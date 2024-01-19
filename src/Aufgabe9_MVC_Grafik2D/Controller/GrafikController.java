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

    Logger logger = Logger.getLogger("MVC_Grafik");
    private GrafikFrame frame;

    public GrafikController(GrafikFrame frame) {
        this.frame = frame;
    }

    public void registerEvents() {
        //Es werden zwei Events registriert und beide werden mit der default Methode der Klasse abgehandelt
        frame.addMouseMotionListener(this);     //Für das mouseDragged Event
        frame.addMouseListener(this);   //Für das mouseReleased Event
    }

    @Override
    public void mouseDragged(MouseEvent evt){
        Point point = evt.getPoint();
        frame.addPointToFigure(point);
    }

    @Override
    public void mouseReleased(MouseEvent evt){
        frame.endFigure();
    }

}
