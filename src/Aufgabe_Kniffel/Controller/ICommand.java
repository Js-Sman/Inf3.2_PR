package Aufgabe_Kniffel.Controller;

public interface ICommand {
    void execute();

    boolean isUndoable();

    void executeUndo();
}
