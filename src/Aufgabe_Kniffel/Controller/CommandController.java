package Aufgabe_Kniffel.Controller;

import Aufgabe_Kniffel.Controller.Commands.StartCommand;
import Aufgabe_Kniffel.Controller.Commands.StopCommand;
import Aufgabe_Kniffel.Controller.Commands.TakeCommand;
import Aufgabe_Kniffel.Kniffel;
import Aufgabe_Kniffel.Utils.KniffelLogger;
import Aufgabe_Kniffel.View.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.logging.Logger;

public class CommandController implements ActionListener, MouseListener {

    Logger logger = KniffelLogger.getLogger();

    private final MainWindow view;
    private final Kniffel model;

    private final Invoker invoker;

    public CommandController(MainWindow view, Kniffel model) {
        this.view = view;
        this.model = model;

        this.invoker = new Invoker();
    }

    public void registerEvents(){
        this.view.getBtnStart().addActionListener(this);
        this.view.getBtnStop().addActionListener(this);
        this.view.getBtnTake().addActionListener(this);

        for ( JLabel label : this.view.getLblWuerfelArray() ) {
            label.addMouseListener(this);
        }
    }

    public void registerCommands(){
        StartCommand startCommand = new StartCommand(model);
        StopCommand stopCommand = new StopCommand(model);
        TakeCommand takeCommand = new TakeCommand(model);

        invoker.addCommand(view.getBtnStart(), startCommand);
        invoker.addCommand(view.getBtnStop(), stopCommand);
        invoker.addCommand(view.getBtnTake(), takeCommand);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Component source = (Component) e.getSource();
        invoker.execute(source);
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        JLabel label = (JLabel) e.getSource();

        int lblNumber = indexOfElement(view.getLblWuerfelArray(), label);

        model.take(lblNumber);
    }

    // Method to find the index of an element in an array
    private static int indexOfElement(JLabel[] array, JLabel element) {
        for (int i = 0; i < array.length; i++) {
            if (array[i] == element) {
                return i; // Return the index if the element is found
            }
        }
        return -1; // Return -1 if the element is not found
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
}
