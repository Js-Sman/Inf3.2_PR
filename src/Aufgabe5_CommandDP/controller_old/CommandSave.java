/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aufgabe5_CommandDP.controller_old;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;
import t4_adressverwaltung.model.AdressverwaltungModel;
import t4_adressverwaltung.view.MainWindow;

/**
 *
 * @author Je-Sc, Ahrens
 * Klasse zur Ausführung des Kommandos "Speichern einer Datei"
 */
public class CommandSave implements CommandInterface{
    private MainWindow view;
    private AdressverwaltungModel model;
    
    public CommandSave(MainWindow view, AdressverwaltungModel model){
        this.view = view;
        this.model = model;
    }
    
  /**
   * Aufrufen des File Choosers in zuletzt verwendeten Ordner, versuchen die 
   * Tabellendatei zu speichern
   */
    @Override
    public void execute() {
      JFileChooser fc = view.getFcTableChooser();
      String lastDirectory = model.getPref();
      if (lastDirectory != null) {
          fc.setCurrentDirectory(new File(lastDirectory));
      }
      int choice = fc.showSaveDialog(view);
      if (choice == JFileChooser.APPROVE_OPTION)
      {
        File selectedFile = fc.getSelectedFile();
        String filename = selectedFile.getAbsolutePath();
        view.getLbStatus().setText(filename);
        model.putPref("lastDirectory", selectedFile.getParent());
        
        try
        {
          model.datenSpeichern(selectedFile);
        }
        catch (FileNotFoundException ex)
        {
          Logger.getLogger(CommandOpen.class.getName()).log(Level.SEVERE, null, ex);
        }
        catch (IOException ex)
        {
          Logger.getLogger(CommandOpen.class.getName()).log(Level.SEVERE, null, ex);
        }
      }
    }
    

    @Override
    public void undo() {
        throw new UnsupportedOperationException("Not undoable."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void redo() {
        throw new UnsupportedOperationException("Not redoable."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public boolean isUndoable() {
        return false;
    }
    
}
