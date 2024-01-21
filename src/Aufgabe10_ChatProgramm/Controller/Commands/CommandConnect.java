package Aufgabe10_ChatProgramm.Controller.Commands;

import Aufgabe10_ChatProgramm.Controller.CommandInterface;
import Aufgabe10_ChatProgramm.Model.Client;
import Aufgabe10_ChatProgramm.Model.Server;
import Aufgabe10_ChatProgramm.Model.Transmitter;
import Aufgabe10_ChatProgramm.View.MainWindow;

import java.io.IOException;

public class CommandConnect implements CommandInterface {

    private MainWindow view;
    private CommandSend commandSend;
    public CommandConnect(MainWindow view, CommandSend command) {
        this.view = view;
        this.commandSend = command;
    }

    /**
     * Hier wird jeh nach Auswahl der Radiobutton der Transmitter als Server oder Client initialisiert
     */
    @Override
    public void execute(){

        if(view.getRbtnServer().isSelected()){
            try{
                commandSend.setTransmitter(new Server(view));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(view.getRbtnClient().isSelected()){
            try{
                commandSend.setTransmitter(new Client(view));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        view.getjOptionDialog().setModal(false);
        view.getjOptionDialog().setVisible(false);

    }

    @Override
    public void undo() {

    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
