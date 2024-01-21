package Aufgabe10_ChatProgramm.Controller;

import Aufgabe10_ChatProgramm.Controller.Commands.CommandConnect;
import Aufgabe10_ChatProgramm.Controller.Commands.CommandSend;
import Aufgabe10_ChatProgramm.Model.Transmitter;
import Aufgabe10_ChatProgramm.View.MainWindow;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.logging.Logger;

/**
 * Der Controller verbindet nur die Events aus der View mit den tatsächlichen Aktionen.
 * Die Aktionen werden vom Invoker angestoßen un din den Commands ausgeführt.
 */
public class CommandController implements ActionListener {

    Logger lg = Logger.getLogger("Netz");

    private MainWindow view;
    private CommandInvoker invoker;

    public CommandController(MainWindow view){
        this.view = view;
        this.invoker = new CommandInvoker();
    }

    /**
     * Hier werden alle Events aus der View registriert. Sie werden alle in der default actionPerformed Methode abgehandelt.
     * Dort werden sie dann identifiziert und an den Invoker weiter geleitet.
     */
    public void registerEvents(){
        view.getBtnConnect().addActionListener(this);
        view.getTfChatField().addActionListener(this);  //Jedes jTextField hat eine Action die bei "Enter" feuert
    }

    /**
     * Hier werden die Kommandos für den Invoker verknüpft.
     * Es muss heir immer die auslösende Komponente und das zugehörige Kommando übergeben werden. Dafür muss es ein gemeinsames CommandInterface geben!
     *
     */
    public  void registerCommands(){
        CommandSend commandSend = new CommandSend(view);
        //Beide Commandos interagieren mit der View daher muss diese im Konstrukor mitgegeben werden.
        //Der Transmitter wird erst Initialisiert wenn das Connect Kommando ausgeführt wird → erst dann kann die Referenz für das Senden Kommando gesetzt werden
        invoker.addCommand(view.getBtnConnect(),new CommandConnect(view, commandSend));
        invoker.addCommand(view.getTfChatField(),commandSend);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Alle Events kommen hier an → jedes Event wird von einer Komponente getriggert, die hier aussortiert wird
        Component key = (Component) e.getSource();

        //Die Komponente ist für den Invoker der Key für das Dictionary, indem des Value als Command hinterlegt ist.
        try {
            invoker.executeCommand(key);
        } catch (IOException ex) {
            throw new RuntimeException(ex);
        }

    }
}
