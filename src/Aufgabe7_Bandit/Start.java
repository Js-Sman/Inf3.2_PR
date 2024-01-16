package Aufgabe7_Bandit;

import Aufgabe7_Bandit.Controller.WuerfelAdapter;
import Aufgabe7_Bandit.Controller.WuerfelController;
import Aufgabe7_Bandit.Model.Bandit;
import Aufgabe7_Bandit.Model.WuerfelModel;
import Aufgabe7_Bandit.View.WuerfelView;

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
    Bandit bandit = new Bandit(); //Der Bandit ist jetzt das Haupt Model und verwaltet die Würfelmodel

    //Adapter anlegen
    WuerfelAdapter adapter = new WuerfelAdapter(view, bandit);
    bandit.addWuerfelWertSubscriber(adapter);  //Der Adapter muss sich im Model einschreiben

    //Controller anlegen
    WuerfelController controller = new WuerfelController(view, bandit);
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
