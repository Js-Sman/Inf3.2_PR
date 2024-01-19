package Aufgabe9_MVC_Grafik2D;

import Aufgabe9_MVC_Grafik2D.Controller.GrafikController;
import Aufgabe9_MVC_Grafik2D.Model.GrafikModel;
import Aufgabe9_MVC_Grafik2D.View.GrafikFrame;
import Aufgabe9_MVC_Grafik2D.View.MainWindow;
import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
    MainWindow view = new MainWindow(); //Neues Fenster mit Namen initialisieren
    GrafikModel model = new GrafikModel();
    GrafikFrame frame = view.getZeichenflaeche();
    frame.setModel(model);

    GrafikController grafikController = new GrafikController(frame);
    grafikController.registerEvents();


    //Fenster Parameter setzen, kann auch im Fenster ctor passieren
    view.setSize(500,500);
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
