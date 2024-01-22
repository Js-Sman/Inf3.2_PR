package Aufgabe10_ChatProgram;

import Aufgabe10_ChatProgram.Controller.CommandController;
import Aufgabe10_ChatProgram.View.MainWindow;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
    MainWindow view = new MainWindow(); //Im Gui Designer erstellt

    //Kommandos verknüpfen und Kontroller setup
    CommandController controller = new CommandController(view);
    controller.registerEvents();
    controller.registerCommands();



    //Fenster Parameter setzen, kann auch im Fenster ctor passieren
    view.setSize(600,450);
    view.setVisible(true);

    //Dialog Panel öffnen
    view.getjOptionDialog().setVisible(true);
    //Mit Modal dafür sorgen, dass das MainWindow blockiert ist, solange der Dialog noch offen ist.
    view.getjOptionDialog().setModal(true);
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
