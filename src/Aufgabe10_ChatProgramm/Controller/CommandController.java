package Aufgabe10_ChatProgramm.Controller;

import Aufgabe10_ChatProgramm.Controller.Commands.CommandConnect;
import Aufgabe10_ChatProgramm.Controller.Commands.CommandSend;
import Aufgabe10_ChatProgramm.Model.ChatModel;
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
    private ChatModel model;
    private CommandInvoker invoker;

    CommandSend commandSend;
    CommandConnect commandConnect;

    public CommandController(MainWindow view, ChatModel model){
        this.view = view;
        this.model = model;
        this.invoker = new CommandInvoker();

        //Die Kommandos schon im ctor Initialisieren für übersichtlichkeit in der registerCommands Methode
        this.commandSend = new CommandSend(model);
        this.commandConnect = new CommandConnect(view, model);
    }

    /**
     * Hier werden alle Events aus der View registriert. Sie werden alle in der default actionPerformed Methode abgehandelt.
     * Dort werden sie dann identifiziert und an den Invoker weiter geleitet.
     */
    public void registerEvents(){
        view.getBtnConnect().addActionListener(this);
        view.getBtnSend().addActionListener(this);  //Jedes jTextField hat eine Action die bei "Enter" feuert
    }

    /**
     * Hier werden die Kommandos für den Invoker verknüpft.
     * Es muss heir immer die auslösende Komponente und das zugehörige Kommando übergeben werden. Dafür muss es ein gemeinsames CommandInterface geben!
     *
     */
    public  void registerCommands(){
        //Die Kommandos mit den Komponenten aus der view verknüpfen
        invoker.addCommand(view.getBtnConnect(),commandConnect);
        invoker.addCommand(view.getBtnSend(),commandSend);
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
