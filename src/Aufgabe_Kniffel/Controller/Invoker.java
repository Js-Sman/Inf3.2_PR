package Aufgabe_Kniffel.Controller;

import java.awt.*;
import java.util.HashMap;
import java.util.Stack;

public class Invoker {

    private final HashMap<Component, ICommand> commands;
    private final Stack<ICommand> undoStack;

    public Invoker(){
        commands = new HashMap<>();
        undoStack = new Stack<>();
    }

    public void addCommand(Component key, ICommand value){
        commands.put(key, value);
    }

    public void execute(Component key){
        ICommand command = commands.get(key);
        command.execute();

        if (command.isUndoable()) {
            undoStack.push(command);
        }
    }

    public void undoCommand(){
        undoStack.pop().executeUndo();

    }
}
