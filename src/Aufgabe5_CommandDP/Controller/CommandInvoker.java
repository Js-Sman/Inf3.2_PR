package Aufgabe5_CommandDP.Controller;

import Aufgabe5_CommandDP.Controller.CommandInterface;

import java.awt.*;
import java.util.HashMap;
import java.util.Stack;

/**
 * Der CommandInvoker führt die Kommandos aus, indem er von jedem Controller die execute Methode ausführt.
 * Die Kommandos werden dem Incoker vom Controller gegeben und in einer HashMap gehalten.
 * Für eine undo Möglichkeit, muss hierfür ein undoStack im Invoker realisiert werden.
 * Auf dem undoStack werden die Commands abgelegt die ausgeführt werden. Über diesen Stack kann die
 * Ausführungshistorie rückgängig gemacht werden. Um die Daten wiederherzustellen, müssen diese im Model ebenso gehalten werden.
 */
public class CommandInvoker {
    private HashMap<Component, CommandInterface> commands;
    private Stack<CommandInterface> undoStack;

    public CommandInvoker(){
        commands = new HashMap<>();
        undoStack = new Stack();
    }

    /**
     * Die Komponente aus der View wird als key für das Kommando verwendet.
     * Das Kommando selbst wird als value übergeben und ist eine Klassenreferenz aus das jeweilige Kommando.
     */
    public void addCommand(Component key, CommandInterface value){
        commands.put(key, value);
    }

    /**
     * Der key der hier ankommt, ist die Komponente aus der View.
     * Da die Komponenten in der commands HashMap als key angelegt wurden, können jetzt mit der Komponente
     * die entsprechenden Controller aus der HashMap geholt werden.
     */
    public void executeCommand(Component key){
      CommandInterface command = commands.get(key);
      command.execute();
      if (command.isUndoable())
      {
        undoStack.push(command);
      }
    }

    /**
    * Falls der Stack Elemente enthält, wird das oberste Element geholt
    * und die Methode "undo" des Commands aufgerufen
    */
    public void undoCommand()
    {
      if (!undoStack.isEmpty())
      {
        undoStack.pop().undo();
      }
    }

    /**
     * Löscht den Stack
     */
    public void deleteStack()
    {
      while(!undoStack.isEmpty())
        undoStack.pop();
    }
}
