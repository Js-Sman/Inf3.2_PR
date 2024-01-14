package Aufgabe1_FensterUndEvents;

import Aufgabe1_FensterUndEvents.View.MainWindow;

import javax.swing.*;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
    MainWindow view = new MainWindow("Hauptfenster"); //Neues Fenster mit Namen initialisieren
    view.registerEvents();  //Events registrieren

    //Fenster Parameter setzen, kann auch im Fenster ctor passieren
    view.setSize(300,300);

    //Überschreibt die Close Option vom ctor
    view.setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE); //Um selbst auf das schließen zu reagieren → In der WindowClosing Methode
    view.setVisible(true);
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) 
  {

    //Look-and-Feel des Systems übernehmen
    try
    {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    }
    catch (Exception ex)
    {
      //MessageBox in der Mitte vom Anzeigebildschirm → parentComponent: null
      JOptionPane.showMessageDialog(null, ex.toString());
    }
    new Start();
  }
}
