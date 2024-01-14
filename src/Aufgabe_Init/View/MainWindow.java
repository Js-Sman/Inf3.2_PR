package Aufgabe_Init.View;

import javax.swing.*;

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


    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {

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
