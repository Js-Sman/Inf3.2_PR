package Aufgabe2_LayoutManager;

import Aufgabe2_LayoutManager.View.MainWindow;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

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
    view.pack();  //Mit pack() wird das Fenster an alle seine Komponenten angepasst
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
