package Aufgabe10_ChatProgramm.Controller.Commands;

import Aufgabe10_ChatProgramm.Controller.CommandInterface;
import Aufgabe10_ChatProgramm.Model.ChatModel;
import Aufgabe10_ChatProgramm.Model.Com.Client;
import Aufgabe10_ChatProgramm.Model.Com.Server;
import Aufgabe10_ChatProgramm.View.MainWindow;

import java.io.IOException;

public class CommandConnect implements CommandInterface {

    private MainWindow view;
    private ChatModel model;
    public CommandConnect(MainWindow view, ChatModel model) {
        this.view = view;
        this.model = model;
    }

    /**
     * Hier wird jeh nach Auswahl der Radiobutton der Transmitter als Server oder Client initialisiert
     */
    @Override
    public void execute(){

        if(view.getRbtnServer().isSelected()){
            try{
                model.setTransmitter(new Server());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }

        if(view.getRbtnClient().isSelected()){
            try{
                model.setTransmitter(new Client());
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
