/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aufgabe5_CommandDP.Controller.Commands;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFileChooser;

import Aufgabe5_CommandDP.Controller.CommandInterface;
import Aufgabe5_CommandDP.Model.AdressverwaltungModel;
import Aufgabe5_CommandDP.View.MainWindow;

/**
 *
 * @author Je-Sc, Ahrens
 * Klasse zur Ausführung des Kommandos "Öffnen einer Datei"
 */
public class CommandOpen implements CommandInterface {
  private MainWindow view;
  private AdressverwaltungModel model;

  public CommandOpen(MainWindow view, AdressverwaltungModel model){
      this.view = view;
      this.model = model;
  }

  /**
   * Aufrufen des File Choosers in zuletzt verwendeten Ordner, versuchen eine
   * Tabellendatei zu öffnen
   */
  @Override
  public void execute() {

    //FileChooser referenz holen
    JFileChooser fc = view.getFcTableChooser();

    //Letztes offenes Systemverzeichnis holen
    String lastDirectory = model.getPref();

    if (lastDirectory != null) {
        //Des FileChooser das ausgewählte Verzeichnis als neuen File hinzufügen
          fc.setCurrentDirectory(new File(lastDirectory));
      }

    //FileChooser Dialog im View anzeigen
    int choice = fc.showOpenDialog(view);

    if (choice == JFileChooser.APPROVE_OPTION)
    {
      //Ressourcen laden
      File selectedFile = fc.getSelectedFile();
      String filename = selectedFile.getAbsolutePath();

      //View und Model updaten
      view.getLblOpenFile().setText(filename);
      model.putPref("lastDirectory", selectedFile.getParent());

      //Daten in der File-Ressource abspeichern mit ErrorHandling
      try
      {
        model.datenLesen(selectedFile);
      }
      catch (FileNotFoundException ex)
      {
        Logger.getLogger(CommandOpen.class.getName()).log(Level.SEVERE, null, ex);
      }
      catch (ClassNotFoundException | IOException ex)
      {
        Logger.getLogger(CommandOpen.class.getName()).log(Level.SEVERE, null, ex);
      }

      //Den Undo Verlauf löschen, wenn die Datei neu aufgemacht wird
      model.deleteStacks();
    }
  }

  @Override
  public void undo() {
      throw new UnsupportedOperationException("Not undoable."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
  }


  @Override
  public boolean isUndoable() {
      return false;
  }

}
