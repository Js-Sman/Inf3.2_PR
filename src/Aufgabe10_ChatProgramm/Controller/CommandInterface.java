package Aufgabe10_ChatProgramm.Controller;

import java.io.IOException;

/**
 * Für das CommandDP müssen alle Commands ein gemeinsames Interface implementieren.
 */
public interface CommandInterface {
    public void execute() throws IOException;

    public void undo();

    public boolean isUndoable();

}
