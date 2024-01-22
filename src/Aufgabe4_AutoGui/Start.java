package Aufgabe4_AutoGui;

import Aufgabe4_AutoGui.View.MainWindow;
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
    MainWindow view = new MainWindow(); //AutoGui Fenster hat einen leeren ctor → Eigenschaften werden im Designer gesetzt

    //Die view muss trotzdem von der Startklasse aus sichtbar gesetzt werden
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
