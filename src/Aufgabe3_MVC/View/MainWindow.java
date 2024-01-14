package Aufgabe3_MVC.View;

import javax.swing.*;

import java.awt.*;



public class MainWindow extends JFrame{

    //Referenzen auf Fenster Elemente
    private JButton btnToCm;
    private JButton btnToZoll;
    private JLabel lbCm;
    private JLabel lbZoll;
    private JTextField txfCm;
    private JTextField txfZoll;

    public MainWindow(String name) {
        super(name);    //Setzt den Fensternamen
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);    //Beendet das Fenster mit Exit

        //Komponenten Initialisieren
        initComponents();
        //Layout zusammenbauen
        buildLayout();
    }

    /**
     * Hier werden die benötigten Komponenten initialisiert
     */
    private void initComponents() {
        //Buttons und Labels
        btnToCm = new JButton(">");
        btnToZoll = new JButton("<");
        lbCm = new JLabel("Cm");
        lbZoll = new JLabel("Zoll");

        //Textfelder
        txfCm = new JTextField(15);
        txfCm.setHorizontalAlignment(JTextField.RIGHT); //Um das Textfeld rechtsbündig zu machen
        txfZoll = new JTextField(15);
        txfZoll.setHorizontalAlignment(JTextField.RIGHT);

    }

    /**
     * Hier wird das Layout des Fensters erstellt und Komponenten werden zugewiesen.
     * Alle Komponenten müssen vor der Zuweisung initialisiert sein!
     */
    private void buildLayout() {
        //Container als Referenz auf das aktuelle Window
        Container layer0 = this.getContentPane();
        layer0.setLayout(new FlowLayout()); //FlowLayout um alle Elemente nebeneinander anzuordnen

        //BoxContainer erzeugen und befüllen
        Container layer1_0 = new Container();   //Neuen leeren Container erzeugen
        layer1_0.setLayout(new BoxLayout(layer1_0, BoxLayout.Y_AXIS));   //BoxLayout für den Container layer1_0 in vertikaler ausrichtung erzeugen und zuweisen
        layer1_0.add(lbZoll);   //Erste Komponente hinzufügen → Landet in der ersten Zeile wegen vertikalem Box Layout
        layer1_0.add(txfZoll);  //Zweite Komponente landet in der zweiten Zeile

        Container layer1_1 = new Container();
        layer1_1.setLayout(new BoxLayout(layer1_1, BoxLayout.Y_AXIS));
        layer1_1.add(btnToCm);
        layer1_1.add(btnToZoll);

        Container layer1_2 = new Container();
        layer1_2.setLayout(new BoxLayout(layer1_2, BoxLayout.Y_AXIS));
        layer1_2.add(lbCm);
        layer1_2.add(txfCm);

        //Alle Container zum Hauptfenster hinzufügen → immer erst die Container bauen und dann zuweisen
        layer0.add(layer1_0);
        layer0.add(layer1_1);
        layer0.add(layer1_2);

    }

    //Die View muss public Methoden bereitstellen, um auf Komponenten zuzugreifen
    public JButton getBtnToCm() {
        return btnToCm;
    }

    public JButton getBtnToZoll() {
        return btnToZoll;
    }

    public JTextField getTxfCm() {
        return txfCm;
    }

    public JTextField getTxfZoll() {
        return txfZoll;
    }
}
