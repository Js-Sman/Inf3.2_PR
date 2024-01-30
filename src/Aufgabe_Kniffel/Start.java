package Aufgabe_Kniffel;

import Aufgabe_Kniffel.Controller.CommandController;
import Aufgabe_Kniffel.View.MainWindow;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
    MainWindow view = new MainWindow("Kniffel"); //Neues Fenster mit Namen initialisieren
    Kniffel kniffel = new Kniffel(view);
    CommandController commandController = new CommandController(view, kniffel);
    commandController.registerEvents();
    commandController.registerCommands();

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
