package Aufgabe_Kniffel.Controller;

import java.awt.*;
import java.util.HashMap;

public class Invoker {

    private HashMap<Component, ICommand> commands;

    public Invoker(){
        commands = new HashMap<>();
    }

    public void addCommand(Component key, ICommand value){
        commands.put(key, value);
    }

    public void execute(Component key){
        commands.get(key).execute();
    }
}
