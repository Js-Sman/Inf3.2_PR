package Aufgabe4_AutoGui;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
//    MainWindow view = new MainWindow("Hauptfenster"); //Neues Fenster mit Namen initialisieren
//    view.registerEvents();  //Events registrieren
//
//    //Fenster Parameter setzen, kann auch im Fenster ctor passieren
//    view.setSize(300,300);
//    view.setVisible(true);
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
