package Aufgabe9_MVC_Grafik2D.View;

import Aufgabe9_MVC_Grafik2D.Model.Figur;
import Aufgabe9_MVC_Grafik2D.Model.GrafikModel;

import javax.swing.*;
import java.awt.*;
import java.awt.print.PageFormat;
import java.awt.print.Printable;
import java.awt.print.PrinterException;
import java.util.logging.Logger;

/**
 * Diese Klasse ist die Zeichenfläche. Um sie im Hauptfenster zu verwenden, muss sie die Klasse JComponent erweitern.
 * Da diese Zeichenfläche ausgedruckt werden können soll muss zusätzlich das Interface Printable implementiert werden.
 * <p>
 * Diese Klasse ist verantwortlich dafür die Figuren zu zeichnen und anzuzeigen.
 * Alle relevanten Daten einer Figur werden im Model gehalten.
 */
public class GrafikFrame extends JComponent implements Printable {

    private GrafikModel model;

    //Um mit dem GUI Builder zu arbeiten, braucht eine Klasse einen Standard Konstruktor → Is einfach so!
    public GrafikFrame() {
    }

    //Konstruktor aufgaben müssen dann anderweitig über nachgelagerte Methoden aufgerufen werden
    public void setModel(GrafikModel model) {
        this.model = model;
    }

    public void addPointToCurrentFigure(Point point) {
        //Eine Referenz vom Model auf die aktuelle figur holen und den Punkt in die Punkte Liste dieser Figur einfügen
        Figur aktuelleFigur = model.getFigur();
        aktuelleFigur.addPoint(point);

        /*
        Da sich die Figur selbst auf den Frame zeichnen soll, müssen ihr die Grafikdaten übergeben werden.
        Jeder JComponent hat Grafikdaten die mat mit getGraphics() referenzieren kann.

        → Diese Grafikdaten müssen wieder freigegeben werden da es sonst früher oder später zu performance Schwierigkeiten kommt.
         */
        Graphics2D g2 = (Graphics2D) this.getGraphics();    //Grafikdaten eines JComponents sind von der alten Graphics Bib → einfach auf neuere Bib casten
        aktuelleFigur.zeichnen(g2);
        g2.dispose();   //Hier wird der Grafikspeicher wieder freigegeben
    }

    public void endFigure() {
        //Das Signal eine Figur zu beenden wir hier nur weitergegeben → Das liegt nur an der Architektur und kann auch anders gelöst werden
        model.endFigure();
    }

    /**
     * Die paintComponent() Methode ist Teil eines JComponents.
     * Sie wird immer dann automatisch aufgerufen, wenn sich der JComponent verändert → z.B. vergrößern oder verkleinern.
     * Diese Methode kann mit repaint() manuell getriggert werden.
     * <p>
     * Hier werden direkt die Grafikdaten der Komponente verwendet und automatisch wieder freigegeben → deswegen muss man hier kein dispose() aufrufen.
     */
    @Override
    public void paintComponent(Graphics g) {
        //Wenn das Fenster größer gezogen wird, sollen alle bereits bestehenden Figuren aktualisiert werden.
        if (model != null) {
            super.paintComponent(g);    //Super Klasse muss initialisiert werden
            Graphics2D g2 = (Graphics2D) g; //Die Grafikdaten der Komponente auf eine neuere Version casten

            //Alle Figuren nochmal Zeichnen
            model.getFiguren().forEach(figur ->
            {
                figur.zeichnen(g2);
            });

        }

    }


    @Override
    public int print(Graphics graphics, PageFormat pageFormat, int pageIndex) throws PrinterException {
        Graphics2D g2p = (Graphics2D) graphics;
        if (pageIndex == 0) {
            g2p.translate(pageFormat.getImageableX(), pageFormat.getImageableY());
            g2p.scale(pageFormat.getImageableWidth() / this.getWidth(),
                    pageFormat.getImageableHeight() / this.getHeight());
            super.print(g2p);
            return Printable.PAGE_EXISTS;
        } else {
            return Printable.NO_SUCH_PAGE; // wichtig sonst Papiervernichtung
        }
    }

}


