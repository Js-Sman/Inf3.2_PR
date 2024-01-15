/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package Aufgabe5_CommandDP.controller_old;


/**
 *
 * @author Js-Sc, Ahrens
 * Interface für die Kommandos
 */
public interface CommandInterface {
    public void execute();
    public void undo();
    public void redo();
    public boolean isUndoable();    
}
