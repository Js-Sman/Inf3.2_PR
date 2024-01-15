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
 * Klasse zur Ausführung des Kommandos "Löschen einer Zeile"
 */
public class CommandDeleteRow implements CommandInterface{
    private MainWindow view;
    private AdressverwaltungModel model;
    private int selectedRow;
    
    /**
     * Inizialisierung
     * @param view Referenziertes Fenster
     * @param model Referenziertes Model
     */
    public CommandDeleteRow(MainWindow view, AdressverwaltungModel model){
        this.view = view;
        this.model = model;
    }
    
    /**
     * Löschen einer ausgewählten Zeile bzw. der letzten Zeile
     */
    @Override
    public void execute() {
        int selectedRow = view.getTbMainTable().getSelectedRow();
        if (selectedRow == -1) {
            int row = model.getRowCount();
            if (row >= 1) {
                model.pushData(model.getRowData(row-1));
                model.deleteRowData(row - 1);
            }
        }
        else{
            model.pushData(model.getRowData(selectedRow));
            model.deleteRowData(selectedRow);
        }
        model.setDeleteRow(selectedRow-1);
    }

    /**
     * Wiederherstellen der gelöschten Zeile
     */
    @Override
    public void undo() {
        if (!model.deleteStackIsEmpty()) {
            int lastdeletedRow = model.getLastDeletedRow();
            if (lastdeletedRow == -2) {
                lastdeletedRow = model.getRowCount();
            } else {
                lastdeletedRow++;
            }
            model.insertRowData(lastdeletedRow, model.getLastDeletedData());
        }
    }


    @Override
    public void redo() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isUndoable() {
      return true;
    }
    
}
