package Aufgabe10_ChatProgramm.Controller;

import Aufgabe10_ChatProgramm.Controller.Commands.CommandConnect;
import Aufgabe10_ChatProgramm.Controller.Commands.CommandSend;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.util.HashMap;

public class CommandInvoker {

    private HashMap<Component,CommandInterface> commandsLookUpTable;

    public CommandInvoker(){
        this.commandsLookUpTable = new HashMap<>();
    }

    /**
     * Hier werden die Kommandos mit einer Komponente in einer HashMap verknüpft → Wie ein Dictionary
     * @param key: jComponent
     * @param value: CommandInterface
     */
    public void addCommand(Component key, CommandInterface value) {
        commandsLookUpTable.put(key, value);
    }

    /**
     * Mit dem Key wird der richtige Command aus dem Kommando-Dictionary (HashMap) ausgewählt und die execute Methode des Kommandos wird ausgeführt.
     */
    public void executeCommand(Component key) throws IOException {
        commandsLookUpTable.get(key).execute();
    }
}
