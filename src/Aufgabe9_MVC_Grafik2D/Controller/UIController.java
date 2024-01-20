package Aufgabe9_MVC_Grafik2D.Controller;

import Aufgabe9_MVC_Grafik2D.Model.GrafikModel;
import Aufgabe9_MVC_Grafik2D.View.MainWindow;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.io.File;
import java.io.IOException;

/**
 * Dieser Controller hört auf die Events der UI → Die Buttons.
 * Er ist nur mit der View und dem Model verknüpft.
 * <p>
 * Da in dieser Aufgabe der Fokus auf dem Umgang mit Grafiken liegt, ist hier kein CommandDP verwendet worden.
 * Dieser Kontroller registriert alle events und implementiert eigene Methoden für jedes Event.
 * <p>
 * Da Der Kontroller für jedes Event eine eigene Method bereitstellt, muss hier nicht zwingen das Interface ActionListener umgesetzt werden
 */
public class UIController {

    private GrafikModel model;
    private MainWindow view;

    public UIController(GrafikModel model, MainWindow view) {
        this.model = model;
        this.view = view;
    }

    public void registerEvents() {
        view.getBtnDelete().addActionListener(this::actionDelete);
        view.getBtnSave().addActionListener(this::actionSave);
        view.getBtnOpen().addActionListener(this::actionOpen);
        view.getBtnPrint().addActionListener(this::actionPrint);
    }

    private void actionPrint(ActionEvent actionEvent) {
        model.printFigures();
    }

    private void actionOpen(ActionEvent actionEvent) {
        //Die FileChooser Referenz von der View abholen
        JFileChooser fileChooser = view.getjFileChooser1();
        //Preferences aus dem Model abholen
        String lastDirectory = model.getPreferences();

        //Wenn es Directory in den Preferences hinterlegt ist diesen Ordner öffnen und eine neue Datei darin anlegen
        if (lastDirectory != null) {
            fileChooser.setCurrentDirectory(new File(lastDirectory));
        }

        //Den Save-Dialog im Hauptfenster anzeigen und Auswahl abspeichern
        int result = fileChooser.showOpenDialog(view);

        //Dialog Ergebnisse immer mit Symbolen vergleichen!
        if (result == JFileChooser.APPROVE_OPTION) {
            /*
            Die Approve Option eines FileChoosers ist dann true, wenn eine Datei ausgewählt wurde oder ein Name in
            die Leiste eingegeben wurde.
             */
            File selectedFile = fileChooser.getSelectedFile();  //Als Dateireferenz speichern
            String filename = selectedFile.getAbsolutePath();   //Name der Datei abspeichern
            model.setPreferences("lastDirectory", selectedFile.getParent());   //Den Ordner, indem die Datei liegt in den Preferences abspeichern

            try {
                model.loadFigures(filename);
            } catch (IOException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void actionSave(ActionEvent actionEvent) {
        //Die FileChooser Referenz von der View abholen
        JFileChooser fileChooser = view.getjFileChooser1();
        //Preferences aus dem Model abholen
        String lastDirectory = model.getPreferences();

        //Wenn es Directory in den Preferences hinterlegt ist diesen Ordner öffnen und eine neue Datei darin anlegen
        if (lastDirectory != null) {
            fileChooser.setCurrentDirectory(new File(lastDirectory));
        }

        //Den Save-Dialog im Hauptfenster anzeigen und Auswahl abspeichern
        int result = fileChooser.showSaveDialog(view);

        //Dialog Ergebnisse immer mit Symbolen vergleichen!
        if (result == JFileChooser.APPROVE_OPTION) {
            /*
            Die Approve Option eines FileChoosers ist dann true, wenn eine Datei ausgewählt wurde oder ein Name in
            die Leiste eingegeben wurde.
             */
            File selectedFile = fileChooser.getSelectedFile();  //Als Dateireferenz speichern
            String filename = selectedFile.getAbsolutePath();   //Name der Datei abspeichern
            model.setPreferences("lastDirectory", selectedFile.getParent());   //Den Ordner, indem die Datei liegt in den Preferences abspeichern

            try {
                model.saveFigures(filename);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

        }
    }

    private void actionDelete(ActionEvent actionEvent) {
        model.deleteFigures();
    }

}
