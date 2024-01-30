package Aufgabe_Kniffel.Controller.Commands;

import Aufgabe_Kniffel.Controller.ICommand;
import Aufgabe_Kniffel.Kniffel;

public class StartCommand implements ICommand {
    private Kniffel model;

    public StartCommand(Kniffel model){
        this.model = model;
    }

    @Override
    public void execute() {
        this.model.start();
    }
}
