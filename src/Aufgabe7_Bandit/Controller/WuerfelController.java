package Aufgabe7_Bandit.Controller;

import Aufgabe7_Bandit.Model.Bandit;
import Aufgabe7_Bandit.Model.WuerfelModel;
import Aufgabe7_Bandit.View.WuerfelView;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * Der Controller organisiert die Aktionen von der View zum Model.
 * <p>
 * Die Aktionen aus der View werden hier als Events registriert und starten bzw stoppen den Thread im Model.
 */
public class WuerfelController implements ActionListener {

    private WuerfelView view;
    private Bandit bandit;

    /**
     * Der Controller braucht wie der Adapter nur die Referenz auf die View sowie das Model um zwischen beiden Parteien
     * zu interagieren.
     */
    public WuerfelController(WuerfelView view, Bandit bandit) {
        this.view = view;
        this.bandit = bandit;
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

        //Mit Start Stop Button den Banditen starten bzw stoppen → Im Bandit werden die WuerfelModele geschaltet
        if (src == view.getBtnStart()) {
            bandit.start();
        } else if (src == view.getBtnStop()) {
            bandit.stop();

        }

    }
}
