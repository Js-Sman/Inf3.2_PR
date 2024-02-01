package Aufgabe_Init.View;

import Aufgabe_Init.Config.Config;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.io.IOException;

public class MainWindow extends JFrame{
    private final GridFrame gridFrame;

    public MainWindow(String name) throws IOException {
        super(name);    //Setzt den Fensternamen
        Container mainWindow = this.getContentPane();
        mainWindow.setLayout(new BorderLayout());
        this.gridFrame = new GridFrame();

        mainWindow.add(gridFrame,BorderLayout.CENTER);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //Beendet das Fenster mit Exit

        setSize(Config.getValue("WIDTH"),Config.getValue("HEIGHT"));
        setVisible(true);
    }

    public GridFrame getGridFrame(){
        return gridFrame;
    }


}
