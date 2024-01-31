package Aufgabe_Kniffel.Controller.Commands;

import Aufgabe_Kniffel.Controller.ICommand;
import Aufgabe_Kniffel.Kniffel;

import javax.print.attribute.HashPrintJobAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.DialogTypeSelection;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;

public class PrintCommand implements ICommand {
    private Kniffel model;

    public PrintCommand(Kniffel model) {
        this.model = model;
    }

    @Override
    public void execute() {
        HashPrintRequestAttributeSet printSet = new HashPrintRequestAttributeSet();
        printSet.add(DialogTypeSelection.NATIVE);
        PrinterJob printerJob = PrinterJob.getPrinterJob();
        printerJob.setPrintable(model.getDruckerModel());

        if (printerJob.printDialog((printSet))){

            try {
                printerJob.print(printSet);
            } catch (PrinterException e) {
                throw new RuntimeException(e);
            }


        }
    }

    @Override
    public boolean isUndoable() {
        return false;
    }

    @Override
    public void executeUndo() {
        System.out.println("Undo Command: " + this.getClass());
    }
}
