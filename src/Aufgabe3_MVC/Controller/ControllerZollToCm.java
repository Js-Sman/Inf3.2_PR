package Aufgabe3_MVC.Controller;

import Aufgabe3_MVC.Model.DatenModel;
import Aufgabe3_MVC.View.MainWindow;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Locale;

public class ControllerZollToCm implements ActionListener {
    //Controller brauchen referenzen auf view und model um zwischen den beiden zu agieren
    private final MainWindow view;
    private final DatenModel model;

    public ControllerZollToCm(MainWindow view, DatenModel model){
        this.view = view;
        this.model = model;

    }

    public void registerEvents(){
        //Da jetzt für jede Action ein eigener Controller verwendet wird, kann die ActionListener Methode verwendet werden
        view.getBtnToCm().addActionListener(this);
        view.getTxfZoll().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Errorhandling, falls umwandlung nicht möglich ist.
        //Umwandlungen passieren im Controller → Das Model bekommt nur Werte die es verarbeiten kann
        try {
            //String format festlegen und Dezimaltrennung anhand System Standort
            float rawValue = Float.parseFloat(String.format(Locale.US, "%.2f", Double.parseDouble(view.getTxfZoll().getText())));  //Parsen ist immer eine Funktion des Datentyp-Objekts

            //Model operationen durchführen
            model.setRawValue(rawValue);
            model.calcCm();
            String convValue = String.valueOf(model.getConvValue());

            //Ergebnisse an view zurückgeben
            view.getTxfZoll().setBackground(Color.GREEN);   //User Feedback für erfolgreiches Parsen
            view.getTxfCm().setText(convValue);

        } catch (Exception ex) {
            JOptionPane.showMessageDialog(view, "Umwandlung nicht möglich", "Error", JOptionPane.ERROR_MESSAGE);
            view.getTxfCm().setBackground(Color.RED);

        }
    }
}
