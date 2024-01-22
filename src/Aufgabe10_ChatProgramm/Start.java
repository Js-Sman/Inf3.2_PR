package Aufgabe10_ChatProgramm;

import Aufgabe10_ChatProgramm.Controller.CommandController;
import Aufgabe10_ChatProgramm.Controller.GrafikController;
import Aufgabe10_ChatProgramm.Model.ChatModel;
import Aufgabe10_ChatProgramm.Model.Grafik.GrafikModel;
import Aufgabe10_ChatProgramm.View.GrafikFrame;
import Aufgabe10_ChatProgramm.View.MainWindow;

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
    GrafikFrame frame = view.getGrafikFrame();
    GrafikModel grafikModel = new GrafikModel(frame);
    frame.setModel(grafikModel);

    //ChatModel initialisieren
    ChatModel chatModel = new ChatModel(grafikModel);

    //Kommandos verknüpfen und Kontroller setup
    CommandController controller = new CommandController(view, chatModel);
    controller.registerEvents();
    controller.registerCommands();

    //Grafik Kontroller erstellen
    GrafikController grafikController = new GrafikController(view, grafikModel);
    grafikController.registerEvents();



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
