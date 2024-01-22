package Aufgabe10_ChatProgram.Controller.Commands;

import Aufgabe10_ChatProgram.Controller.CommandInterface;
import Aufgabe10_ChatProgram.Model.Transmitter;
import Aufgabe10_ChatProgram.View.MainWindow;

public class CommandSend implements CommandInterface {

    private MainWindow view;
    private Transmitter transmitter;

    public CommandSend(MainWindow view) {
        this.view = view;
        this.transmitter = null;
    }

    public void setTransmitter(Transmitter transmitter) {
        this.transmitter = transmitter;
    }

    @Override
    public void execute() {
        String nachricht = view.getTfChatField().getText();
        if(!nachricht.isEmpty() && transmitter != null){
            transmitter.send(nachricht);
            view.getTfChatField().setText(null);
        }

    }

    @Override
    public void undo() {

    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
