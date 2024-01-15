package Aufgabe5_CommandDP;

import Aufgabe5_CommandDP.Controller.CommandController;
import Aufgabe5_CommandDP.View.MainWindow;
import Aufgabe5_CommandDP.Model.AdressverwaltungModel;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
    //Alle Design spezifischen Eigenschaften werden im Designer beschrieben.
    //Für alle Elemente, die ansprechbar sein sollen, müssen getter / setter Methoden bereitgestellt werden.
    //Mit Refactor → encapsulate fields können diese automatisch generiert werden.
    MainWindow view = new MainWindow(); //AutoGui Fenster hat einen leeren ctor → Eigenschaften werden im Designer gesetzt
    AdressverwaltungModel model = new AdressverwaltungModel();

    //Da wir ein eigenes TableModel verwenden, muss es in der allgemeinen jTable gesetzt werden.
    view.getjTable1().setModel(model);

    CommandController controller = new CommandController(view, model);

    
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
