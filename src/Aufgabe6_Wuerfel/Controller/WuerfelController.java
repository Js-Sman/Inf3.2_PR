package Aufgabe6_Wuerfel.Controller;

import Aufgabe6_Wuerfel.Model.WuerfelModel_Thread;
import Aufgabe6_Wuerfel.Model.WuerfelModel_eService;
import Aufgabe6_Wuerfel.View.WuerfelView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Der Controller organisiert die Aktionen von der View zum Model.
 * <p>
 * Die Aktionen aus der View werden hier als Events registriert und starten bzw stoppen den Thread im Model.
 */
public class WuerfelController implements ActionListener {

    private WuerfelView view;
    private WuerfelModel_eService model;

    /**
     * Der Controller braucht wie der Adapter nur die Referenz auf die View sowie das Model um zwischen beiden Parteien
     * zu interagieren.
     */
    public WuerfelController(WuerfelView view, WuerfelModel_eService model) {
        this.view = view;
        this.model = model;
    }

    public void registerEvents() {
        //Da hier nur zwei Events aus der View verwendet werden, reicht es beide in der actionPerformed Methode abzuhandeln
        view.getBtnStart().addActionListener(this);
        view.getBtnStop().addActionListener(this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //Da hier beide Events ankommen muss die Quelle erfasst werden
        Object src = e.getSource();

        //Mit Start Stop Button den Thread anhalten bzw starten
        if (src == view.getBtnStart()) {
            model.start();
        } else if (src == view.getBtnStop()) {
            model.stop();

        }

    }
}
