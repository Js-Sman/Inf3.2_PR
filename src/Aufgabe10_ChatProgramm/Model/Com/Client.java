package Aufgabe10_ChatProgramm.Model.Com;

import Aufgabe10_ChatProgramm.Model.ChatModel;
import Aufgabe10_ChatProgramm.View.MainWindow;

import java.io.IOException;
import java.net.Socket;
import java.util.logging.Logger;

public class Client extends Transmitter {
    private static Logger lg = Logger.getLogger("Netz");

    public Client() throws IOException {
        connect();
        initIO();
    }

    public void connect() throws IOException {
        try {
            lg.info("Client: Verbindung wird aufgebaut");
            socket = new Socket(IP, PORT);
            lg.info("Client: Verbindung aufgebaut");
        } catch (java.io.InterruptedIOException e) {
            lg.warning("Timeout" + "(" + timeout / 1000 + "s)");
        }
    }

}
