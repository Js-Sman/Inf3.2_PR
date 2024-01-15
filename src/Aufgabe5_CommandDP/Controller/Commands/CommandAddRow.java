/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aufgabe5_CommandDP.Controller.Commands;

import java.util.ArrayList;

import Aufgabe5_CommandDP.Controller.CommandInterface;
import Aufgabe5_CommandDP.Model.AdressverwaltungModel;
import Aufgabe5_CommandDP.View.MainWindow;

/**
 * Jedes Kommando muss das Interface CommandInterface implementieren.
 * Ein Kommando erhält eine Referenz auf das Model und die View
 */
public class CommandAddRow implements CommandInterface {

    private MainWindow view;
    private AdressverwaltungModel model;


    public CommandAddRow(MainWindow view, AdressverwaltungModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Hinzufügen einer Zeile am Ende der Tabelle bzw. bei ausgewählter Zeile,
     * hinter dieser Zeile
     */
    @Override
    public void execute() {
        //Ausgewählte Zeile der Tabelle.
        int selectedRow = view.getjTable1().getSelectedRow();   //Wenn nichts ausgewählt -1

        if (selectedRow == -1) {
            model.leerenAdressEintragAnhaengen();
        } else {
            //Default Eintrag erstellen
            ArrayList<String> defaultEintrag = new ArrayList<>();

            int insertRow = selectedRow + 1;
            defaultEintrag.add("Name");
            defaultEintrag.add("Telefon");


            //In der Tabelle unter der ausgewählten Zeile einfügen
            model.insertRowData(insertRow, defaultEintrag);
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
    public boolean isUndoable() {
        return true;
    }

}
