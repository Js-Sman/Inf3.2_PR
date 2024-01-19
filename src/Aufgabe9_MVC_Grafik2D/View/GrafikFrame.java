package Aufgabe9_MVC_Grafik2D.View;

import Aufgabe9_MVC_Grafik2D.Model.Figur;
import Aufgabe9_MVC_Grafik2D.Model.GrafikModel;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.ArrayList;
import java.util.logging.Logger;

/**
 * Diese Klasse ist die Zeichenfläche. Um sie im Hauptfenster zu verwenden, muss sie die Klasse JComponent erweitern.
 * Da diese Zeichenfläche ausgedruckt werden können soll muss zusätzlich das Interface Printable implementiert werden.
 * <p>
 * Diese Klasse ist verantwortlich dafür die Figuren zu zeichnen und anzuzeigen.
 * Alle relevanten Daten einer Figur werden im Model gehalten.
 */
public class GrafikFrame extends JComponent implements Printable {

    Logger logger = Logger.getLogger("MVC_Grafik");
    private GrafikModel model;


    /**
     * Um mit dem GUI Builder zu arbeiten, braucht eine Klasse einen Standard Konstruktor → Is einfach so!
     */
    public GrafikFrame() {
    }

    /**
     * Konstruktor aufgaben müssen dann anderweitig über nachgelagerte Methoden aufgerufen werden
     */
    public void setModel(GrafikModel model) {
        this.model = model;
    }

    public void addPointToFigure(Point point) {
        Figur aktuelleFigur = model.getFigur();
        aktuelleFigur.addPoint(point);
        aktuelleFigur.zeichnen((Graphics2D) this.getGraphics());
    }

    public void endFigure() {
        model.endFigure();
        model.zeichneFiguren((Graphics2D) this.getGraphics());
    }

    @Override
    public void paintComponent(Graphics g) {
        logger.info("Repaint");
        super.paintComponent(g);
        Graphics2D g2 = (Graphics2D) g;

        model.getFiguren().forEach(figur ->
        {
            figur.zeichnen(g2);
        });

    }



    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        return 0;
    }

}
