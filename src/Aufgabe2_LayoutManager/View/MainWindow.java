package Aufgabe2_LayoutManager.View;

import javax.swing.*;

import java.awt.*;
import java.awt.event.*;
import java.util.Locale;


public class MainWindow extends JFrame implements ActionListener {

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

    /**
     * Alle Events müssen hier verknüpft werden.
     * Eigene Methoden zur abarbeitung können hier registriert werden.
     */
    public void registerEvents() {
        //Die actionPerformed Methode aus der ActionListener Klasse verwenden
        btnToCm.addActionListener(this);    //Damit wird die action des buttons automatisch an die actionPerformed Methode weitergeleitet
        txfCm.addActionListener(this);

        //Eigene Methoden zur verfügung stellen
        btnToZoll.addActionListener(this::action_ToZoll);
        txfZoll.addActionListener(this::action_ToCm);
        //Note: wenn man für jede Aktion eine eigene Methode bereitstellt, muss man die Klasse ActionListener nicht implementieren
    }

    private void action_ToCm(ActionEvent actionEvent) {
        //In eigenen Methoden ist die Source bekannt und muss nicht erst einem Objekt zugewiesen und überprüft werden
        String result = convert_toCm(txfZoll.getText());
        txfCm.setText(result);
    }

    private void action_ToZoll(ActionEvent actionEvent) {
        String result = convert_toZoll(txfCm.getText());
        txfZoll.setText(result);
    }


    @Override
    public void actionPerformed(ActionEvent e) {
        //Wenn man hier alle Aktionen sammelt, muss man sie hier nach source auftrennen
        Object src = e.getSource(); //Gibt den namen des Object welches die Aktion ausgelöst hat

        if (src == btnToCm) {
            String result = convert_toCm(txfZoll.getText());
            txfCm.setText(result);
        } else if (src == txfCm) {
            String result = convert_toZoll(txfCm.getText());
            txfZoll.setText(result);
        }

    }

    private String convert_toCm(String strValue) {
        //Errorhandling, falls umwandlung nicht möglich ist
        try {
            //String format festlegen und Dezimaltrennung anhand System Standort
            String result = String.format(Locale.US, "%.2f", Double.parseDouble(strValue) * 2.54);  //Parsen ist immer eine Funktion des Datentyp-Objekts
            txfCm.setBackground(Color.GREEN);   //User Feedback für erfolgreiches Parsen
            return result;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Umwandlung nicht möglich", "Error", JOptionPane.ERROR_MESSAGE);
            txfZoll.setBackground(Color.RED);
            return strValue;
        }

    }

    private String convert_toZoll(String strValue) {
        try {
            //String format festlegen und Dezimaltrennung anhand System Standort
            String result = String.format(Locale.US, "%.2f", Double.parseDouble(strValue) / 2.54);  //Parsen ist immer eine Funktion des Datentyp-Objekts
            txfZoll.setBackground(Color.GREEN);   //User Feedback für erfolgreiches Parsen
            return result;
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Umwandlung nicht möglich", "Error", JOptionPane.ERROR_MESSAGE);
            txfCm.setBackground(Color.RED);
            return strValue;
        }

    }
}
