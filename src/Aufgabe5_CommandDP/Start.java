package Aufgabe5_CommandDP;

import Aufgabe5_CommandDP.View.MainWindow;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
    //Alle Design spezifischen Eigenschaften werden im Designer beschrieben 
    MainWindow view = new MainWindow(); //AutoGui Fenster hat einen leeren ctor -> Eigenschafften werden im Designer gesetzt
    
    //view.registerEvents();  //Events registrieren
    
    //Die view muss trotzdem von der Start Klasse aus sichtbar gesetzt werden
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
