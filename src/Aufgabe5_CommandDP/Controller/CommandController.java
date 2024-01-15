package Aufgabe5_CommandDP.Controller;

import Aufgabe5_CommandDP.Controller.Commands.CommandAddRow;
import Aufgabe5_CommandDP.Controller.Commands.CommandDeleteRow;
import Aufgabe5_CommandDP.View.MainWindow;
import Aufgabe5_CommandDP.Controller.Commands.CommandOpen;
import Aufgabe5_CommandDP.Controller.Commands.CommandSave;
import Aufgabe5_CommandDP.Model.AdressverwaltungModel;

import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Der CommandController koordiniert die einzelnen Kommandos.
 * Dabei lenkt er alle relevanten informationen zwischen der view und dem model.
 * Das Ausführen der Kommandos übernimmt dann der CommandInvoker.
 */
public class CommandController implements ActionListener {
    //Der CommandController braucht eine Referenz auf die View, das Model und einen CommandInvoker
    private MainWindow view;
    private AdressverwaltungModel model;
    private CommandInvoker invoker;

    public CommandController(MainWindow view, AdressverwaltungModel model) {
        this.view = view;
        this.model = model;
        this.invoker = new CommandInvoker();

        registerEvents();
        registerCommands();
    }

    /**
     * Hier werden alle Events registriert die von den View Komponenten ausgelöst werden.
     * Jedes Event wird in der actionPerformed Methode verarbeitet,
     * um die Event-src als key für den Invoker zu verwenden.
     */
    private void registerEvents() {
        view.getBtnAddRow().addActionListener(this);
        view.getBtnDeleteRow().addActionListener(this);
        view.getBtnOpen().addActionListener(this);
        view.getBtnSave().addActionListener(this);
        view.getMiAddRow().addActionListener(this);
        view.getMiDeleteRow().addActionListener(this);
        view.getMiOpen().addActionListener(this);
        view.getMiSave().addActionListener(this);
        view.getBtnUndo().addActionListener(this);
    }

    /**
     * Hier werden alle Kommandos initialisiert.
     * Der Invoker verknüpft dann in der addCommand Methode die Kommandos mit den Events der View Komponenten.
     */
    private void registerCommands() {
        CommandOpen cmdOpen = new CommandOpen(view, model);
        invoker.addCommand(view.getBtnOpen(), cmdOpen);
        invoker.addCommand(view.getMiOpen(), cmdOpen);

        CommandSave cmdSave = new CommandSave(view, model);
        invoker.addCommand(view.getBtnSave(), cmdSave);
        invoker.addCommand(view.getMiSave(), cmdSave);

        CommandAddRow cmdAdd = new CommandAddRow(view, model);
        invoker.addCommand(view.getBtnAddRow(), cmdAdd);
        invoker.addCommand(view.getPumiAddRow(), cmdAdd);
        invoker.addCommand(view.getMiAddRow(), cmdAdd);

        CommandDeleteRow cmdDelete = new CommandDeleteRow(view, model);
        invoker.addCommand(view.getBtnDeleteRow(), cmdDelete);
        invoker.addCommand(view.getPumiDeleteRow(), cmdDelete);
        invoker.addCommand(view.getMiDeleteRow(), cmdDelete);

    }

    /**
     * Alle registrierten Events werden in der actionPerformed Methode abgehandelt.
     * Die src des Events ist direkt eine Komponente aus der View → diese an den Invoker weiterleiten
     *
     * @param e the event to be processed
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Component key = (Component) e.getSource();

        //Abfangen, wenn die Komponente das Undo Event auslösen soll
        if (key == view.getBtnUndo()){
            //In dem Fall wird die undoCommand Routine vom Invoker ausgeführt
            invoker.undoCommand();
        } else {
            //Ansonsten führt der Invoker das Event der jeweiligen Komponente aus
            invoker.executeCommand(key);
        }

        //Wenn die das Event eine neue Datei öffnet, muss der undoStack gelöscht werden
        if(key == view.getBtnOpen()|| key==view.getMiOpen())
          invoker.deleteStack();
        }
    }


