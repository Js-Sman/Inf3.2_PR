package Aufgabe8_AnalogUhr;



import javax.swing.*;
import java.awt.*;

public class Start
{
  /**
   * Start initialisiert alle Komponenten
   */
  private Start()
  {
    //Für diese Aufgabe wird nur ein einfacher JFrame erstellt
    JFrame frame = new JFrame("Analog Uhr");
    frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

    //Eine Referenz auf den Content des Frames erhalten
    Container hauptContainer = frame.getContentPane(); //Hier werden die Zeiger eingefügt
    hauptContainer.setLayout(new OverlayLayout(hauptContainer));  //Dieses Layout wird benötigt, wenn Teile übereinander dargestellt werden sollen

    //Drei Zeiger anlegen → Die zeiten so anpassen, dass eine Uhr simuliert wird
    Zeiger sekundenZeiger = new Zeiger(50000/(60*60), 50, 2);
    Zeiger minutenZeiger = new Zeiger(50000/60, 100, 3);
    Zeiger stundenZeiger = new Zeiger(50000, 150, 4);

    //Man muss opaque auf false setzen, um ein Object durchsichtig zu machen,
    //Der Zeiger ist ein JComponent und füllt den gesamten Hauptcontainer aus.
    //Daher muss der JComponent durchsichtig sein, um die Hintergrundfarbe des Containers und die anderen Objekte zu sehen.
    sekundenZeiger.setOpaque(false);
    minutenZeiger.setOpaque(false);
    stundenZeiger.setOpaque(false);

    //Die Zeiger zum Hauptkontainer hinzufügen
    hauptContainer.add(sekundenZeiger);
    hauptContainer.add(minutenZeiger);
    hauptContainer.add(stundenZeiger);

    //Alle Zeiger starten
    sekundenZeiger.start();
    minutenZeiger.start();
    stundenZeiger.start();


    //Fenster Parameter setzen, kann auch im Fenster ctor passieren
    frame.setSize(400,400);
    hauptContainer.setBackground(Color.BLACK);
    frame.setVisible(true);
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
