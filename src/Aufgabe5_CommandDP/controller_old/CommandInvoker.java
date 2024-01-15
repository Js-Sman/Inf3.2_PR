/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Aufgabe5_CommandDP.controller_old;

import java.awt.Component;
import java.util.HashMap;
import java.util.Stack;


/**
 * Dient zur Realisierung des Polymorphismus
 * für das Command-Design-Pattern
 * Die Commands werden in einer HashMap gespeichert
 * @author Js-Sc, Ahrens
 * @see CommandInterface
 */
public class CommandInvoker {
    
    private HashMap<Component, CommandInterface> commands;
    private Stack <CommandInterface> undoStack;
    
    public CommandInvoker(){
        commands = new HashMap<>();
        undoStack = new Stack();  
    }
    
    /**
    * Fügt ein Kommando zur Kommando-"Datenbank" = HashMap hinzu
    * @param key Quelle des Events
    * @param value Referenz auf das zugehörige Kommando-Objekt
    */
    public void addCommand(Component key, CommandInterface value){
        commands.put(key, value);
    }
    
    /**
    * Führt Kommando der Eventquelle "key" aus und legt die Referenz
    * des Kommando in den Undo-Stack
    * @param key Referenz auf die Eventquelle
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
     * Löscht bei Öffnen einer neuen Datei den Stack
     */
    public void deleteStack()
    {
      while(!undoStack.isEmpty())
        undoStack.pop();
    }
    
}
