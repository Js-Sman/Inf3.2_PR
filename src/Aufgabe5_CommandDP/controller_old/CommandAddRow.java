/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aufgabe5_CommandDP.controller_old;

import java.util.ArrayList;
import t4_adressverwaltung.model.AdressverwaltungModel;
import t4_adressverwaltung.view.MainWindow;

/**
 *
 * @author Js-Sc, Ahrens
 * Klasse zur Ausführung des Kommandos "Hinzufügen einer Zeile"
 */
public class CommandAddRow implements CommandInterface{
    private MainWindow view;
    private AdressverwaltungModel model;
    private int selectedRow;
    private ArrayList<String> defaultEintrag;
    
    /**
     * Inizialisierung
     * @param view Referenziertes Fenster
     * @param model Referenziertes Model
     */
    public CommandAddRow(MainWindow view, AdressverwaltungModel model){
      this.view = view;
      this.model = model;
      defaultEintrag = new ArrayList<>();
    }
    
    /**
     * Hinzufügen einer Zeile am Ende der Tabelle bzw. bei ausgewählter Zeile, 
     * hinter diese Zeile
     */
    @Override
    public void execute() {
        int selectedRow = view.getTbMainTable().getSelectedRow();
        if (selectedRow == -1){
            model.leerenAdressEintragAnhaengen();
        }
        else{
            defaultEintrag.add("Name Neu");
            defaultEintrag.add("Telefon Neu");

            model.insertRowData(selectedRow+1, defaultEintrag);
        }
        model.setAddedRow(selectedRow);
    }


    /**
     * Löschen der hinzugefügten Zeile
     */
    @Override
    public void undo() {

            if (!model.addedStackIsEmpty()) {
            int lastAddedRow = model.getLastAddedRow();
            if (lastAddedRow == -1) {
                lastAddedRow = model.getRowCount() - 1;
            } else {
                lastAddedRow++;
            }
            model.deleteRowData(lastAddedRow);
        }
    }


    @Override
    public void redo() {
        model.insertRowData(selectedRow, model.getLastDeletedData());
    }

    @Override
    public boolean isUndoable() {
        return true;
    }
    
}
