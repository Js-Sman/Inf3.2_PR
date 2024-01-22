package Aufgabe10_ChatProgramm.Model.Grafik;

import Aufgabe10_ChatProgramm.View.GrafikFrame;

import java.util.ArrayList;

/**
 * Diese Klasse hält alle relevanten Informationen über eine Figur und über alle Figuren.
 * Eine Figur wird als ArrayListe von Punkten abgespeichert und in einer andern ArrayListe werden alle Figuren gehalten.
 * <p>
 * Hier werden auch die Preferences gehalten und verwaltet.
 */
public class GrafikModel{

    private GrafikFrame frame;  //Diese Referenz wird gebraucht um den Frame mit UI Elementen zu beeinflussen

    private ArrayList<Figure> figuren;   //Hier werden fertige Figuren abgespeichert

    private Figure figure;    //Das ist die aktuelle Figur die gezeichnet wird


    public GrafikModel(GrafikFrame frame) {
        this.frame = frame;
        figure = new Figure();
        figuren = new ArrayList<>();
    }

    public void endFigure() {
        this.figuren.add(figure);
        figure = new Figure();
    }
    public void addFigure(Figure item) {
        //Erhaltene Figur dem GrafikModel hinzufügen
        this.figuren.add(item);

        //Alle aktuellen Figuren nochmal neu Zeichnen
        frame.repaint();
    }

    public Figure getFigur() {
        return this.figure;
    }

    public ArrayList<Figure> getFiguren() {
        return figuren;
    }

    public GrafikFrame getGrafikFrame() {
        return this.frame;
    }
}