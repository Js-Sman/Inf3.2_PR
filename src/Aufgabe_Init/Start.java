package Aufgabe_Init;

import Aufgabe_Init.Controller.FrameAdapter;
import Aufgabe_Init.Controller.MouseController;
import Aufgabe_Init.Model.TileMapModel;
import Aufgabe_Init.View.MainWindow;

import javax.swing.JOptionPane;
import javax.swing.UIManager;
import java.io.IOException;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start() throws IOException {
    MainWindow view = new MainWindow("Hauptfenster"); //Neues Fenster mit Namen initialisieren
    TileMapModel model = new TileMapModel();

    MouseController controller = new MouseController(model, view);
    controller.registerEvents();

    FrameAdapter adapter = new FrameAdapter(view);
    model.addTileDataSubscriber(adapter);
  }

  /**
   * @param args the command line arguments
   */
  public static void main(String[] args) throws IOException {

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
