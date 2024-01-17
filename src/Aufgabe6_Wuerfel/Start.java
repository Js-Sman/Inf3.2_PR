package Aufgabe6_Wuerfel;

import Aufgabe6_Wuerfel.Controller.WuerfelAdapter;
import Aufgabe6_Wuerfel.Controller.WuerfelController;
import Aufgabe6_Wuerfel.Model.WuerfelModel_Thread;
import Aufgabe6_Wuerfel.Model.WuerfelModel_eService;
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

    //Zwei Modelle mit identischer Funktion → nur um unterschiedliche Handhabung von Threads und eService zu untersuchen
    WuerfelModel_Thread model_thread = new WuerfelModel_Thread();
    WuerfelModel_eService model_eService = new WuerfelModel_eService();
    //Je nachdem welches Model man testet müssen die Konstruktoren vom Adapter und Controller entsprechend angepasst werden

    //Adapter anlegen
    WuerfelAdapter adapter = new WuerfelAdapter(view, model_eService);
    model_eService.addWuerfelWertSubscriber(adapter);  //Der Adapter muss sich im Model einschreiben

    //Controller anlegen
    WuerfelController controller = new WuerfelController(view, model_eService);
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
