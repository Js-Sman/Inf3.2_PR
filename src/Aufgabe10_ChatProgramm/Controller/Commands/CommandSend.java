package Aufgabe10_ChatProgramm.Controller.Commands;

import Aufgabe10_ChatProgramm.Controller.CommandInterface;
import Aufgabe10_ChatProgramm.Model.ChatModel;

import java.io.IOException;

public class CommandSend implements CommandInterface {

    private ChatModel model;

    public CommandSend(ChatModel model) {
        this.model = model;
    }

    @Override
    public void execute() throws IOException {
        model.sendFigure();
    }

    @Override
    public void undo() {

    }

    @Override
    public boolean isUndoable() {
        return false;
    }
}
