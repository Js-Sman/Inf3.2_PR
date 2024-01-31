package Aufgabe_Kniffel.Controller.Commands;

import Aufgabe_Kniffel.Controller.ICommand;
import Aufgabe_Kniffel.Kniffel;

public class TakeCommand implements ICommand {
    private Kniffel model;

    public TakeCommand(Kniffel model){
        this.model = model;
    }

    @Override
    public void execute() {
        this.model.takeLocked();
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
