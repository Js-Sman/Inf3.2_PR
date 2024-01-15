/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aufgabe5_CommandDP.controller_old;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.table.TableModel;
import t4_adressverwaltung.model.AdressverwaltungModel;
import t4_adressverwaltung.view.MainWindow;

/**
 *
 * @author Js-Sc, Ahrens
 * Klasse zum Registrieren und Ausführen aller Kommandos
 */
public class CommandController implements ActionListener{
    
    private MainWindow view;
    private AdressverwaltungModel model;
    private CommandInvoker invoker;
    
    public CommandController(MainWindow view, AdressverwaltungModel model){
        this.view = view;
        this.model = model;
        this.invoker = new CommandInvoker();
    }
    
    public void registerEvents(){
        view.getBtnAddRow().addActionListener(this);
        view.getBtnDeleteRow().addActionListener(this);
        view.getBtnOpen().addActionListener(this);
        view.getBtnSave().addActionListener(this);
        view.getMiAddRow().addActionListener(this);
        view.getMiDeleteRow().addActionListener(this);
        view.getMiOpen().addActionListener(this);
        view.getMiSave().addActionListener(this);
        view.getPumiAddRow().addActionListener(this);
        view.getPumiDeleteRow().addActionListener(this);
        view.getMiUndo().addActionListener(this);
        view.getBtnUndo().addActionListener(this);
        
    }
    
    public void registerCommands(){
        CommandOpen cmdOpen = new CommandOpen(view, model);
        invoker.addCommand(view.getBtnOpen(), cmdOpen);
        invoker.addCommand(view.getMiOpen(), cmdOpen);
        
        CommandSave cmdSave = new CommandSave(view, model);
        invoker.addCommand(view.getBtnSave(), cmdSave);
        invoker.addCommand(view.getMiSave(), cmdSave);
        
        CommandAddRow cmdAdd = new CommandAddRow(view,model);  
        invoker.addCommand(view.getBtnAddRow(), cmdAdd);
        invoker.addCommand(view.getPumiAddRow(), cmdAdd);
        invoker.addCommand(view.getMiAddRow(), cmdAdd);  
        
        CommandDeleteRow cmdDelete = new CommandDeleteRow(view,model);
        invoker.addCommand(view.getBtnDeleteRow(), cmdDelete);
        invoker.addCommand(view.getPumiDeleteRow(), cmdDelete);
        invoker.addCommand(view.getMiDeleteRow(), cmdDelete);  
      
    }

    /**
     * Ausführen des jeweiligen Kommandos
     * @param e Referenz auf das Event
     */
    @Override
    public void actionPerformed(ActionEvent e) {
        Component key = (Component)e.getSource();
        if (key == view.getMiUndo() || key == view.getBtnUndo()) {
            invoker.undoCommand();
        } else {
        invoker.executeCommand(key);
        if(key == view.getBtnOpen()|| key==view.getMiOpen())
          invoker.deleteStack();
        }
    }
    
}
