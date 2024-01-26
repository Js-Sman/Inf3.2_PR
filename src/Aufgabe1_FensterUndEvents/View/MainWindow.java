package Aufgabe1_FensterUndEvents.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;

public class MainWindow extends JFrame implements MouseListener, WindowListener {

    public MainWindow(String name){
        super(name);    //Setzt den Fensternamen
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //Beendet das Fenster mit Exit
    }

    /**
     * Alle Events müssen hier verknüpft werden.
     * Eigene Methoden zur abarbeitung können hier registriert werden.
     */
    public void registerEvents(){
        this.addMouseListener(this);    //Verknüpft diese Fenster mit einem MouseListener → erst dann reagieren die Funktionen der MouseListener Klasse
        this.addWindowListener(this);   //Um auf WindowFunktionen zu hören
    }


    /**
     * Das Clicked Event wird getriggert, wenn eine Maustaste los gelassen wird
     * @param e the event to be processed
     */
    @Override
    public void mouseClicked(MouseEvent e) {


    }

    /**
     * Das CPressed Event wird getriggert, wenn eine Maustaste gedrückt wird
     * @param e the event to be processed
     */
    @Override
    public void mousePressed(MouseEvent e) {
        int red = getRandomNumber(0, 255);
        int green = getRandomNumber(0, 255);
        int blue = getRandomNumber(0, 255);

        //Neues Color-Objekt erstellen
        Color color = new Color(red,green,blue);

        //Referenz auf die Content Pane des Fensters
        this.getContentPane().setBackground(color);

    }

    /**
     * Generiert eine zufällige Ganzzahl
     * @param from: Untere Grenze (eingeschlossen)
     * @param to: Obere grenze (eingeschlossen)
     */
    public int getRandomNumber(int from, int to){
        int range = to - from + 1;
        return (int)((Math.random() * range) + from);
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }

    @Override
    public void windowOpened(WindowEvent e) {

    }

    @Override
    public void windowClosing(WindowEvent e) {
        //Für Benutzer abfragen immer einen ConfirmDialog verwende
        //Optionen werden als int von der Form zurückgegeben
        int result = JOptionPane.showConfirmDialog(this,
                "Wollen Sie wirklich das Fenster schließen?",
                "Exit",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE);

        //Option Dialogergebnisse immer mit Dialogkonstanten abfragen
        if (result == JOptionPane.YES_OPTION){
            System.exit(0);
        }
    }

    @Override
    public void windowClosed(WindowEvent e) {

    }

    @Override
    public void windowIconified(WindowEvent e) {

    }

    @Override
    public void windowDeiconified(WindowEvent e) {

    }

    @Override
    public void windowActivated(WindowEvent e) {

    }

    @Override
    public void windowDeactivated(WindowEvent e) {

    }
}
