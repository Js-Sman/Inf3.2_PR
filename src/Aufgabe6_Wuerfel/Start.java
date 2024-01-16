package Aufgabe6_Wuerfel;

import Aufgabe6_Wuerfel.Controller.WuerfelAdapter;
import Aufgabe6_Wuerfel.Controller.WuerfelController;
import Aufgabe6_Wuerfel.Model.WuerfelModel;
import Aufgabe6_Wuerfel.View.WuerfelView;

import javax.swing.JOptionPane;
import javax.swing.UIManager;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
    WuerfelView view = new WuerfelView(); //Gui im Designer gebaut
    WuerfelModel model = new WuerfelModel();

    //Adapter anlegen
    WuerfelAdapter adapter = new WuerfelAdapter(view, model);
    model.addWuerfelWertSubscriber(adapter);  //Der Adapter muss sich im Model einschreiben

    //Controller anlegen
    WuerfelController controller = new WuerfelController(view, model);
    controller.registerEvents();


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
