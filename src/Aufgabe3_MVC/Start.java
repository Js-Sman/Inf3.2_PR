package Aufgabe3_MVC;

import Aufgabe3_MVC.Controller.ControllerCmToZoll;
import Aufgabe3_MVC.Controller.ControllerZollToCm;
import Aufgabe3_MVC.Model.DatenModel;
import Aufgabe3_MVC.View.MainWindow;

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
    DatenModel model = new DatenModel();

    //Events werden bei MVC in den Controllern verarbeitet und registriert
    ControllerCmToZoll controllerCmToZoll = new ControllerCmToZoll(view, model);
    controllerCmToZoll.registerEvents();
    ControllerZollToCm controllerZollToCm = new ControllerZollToCm(view, model);
    controllerZollToCm.registerEvents();


    //Fenster Parameter setzen, kann auch im Fenster ctor passieren
    view.pack();
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
