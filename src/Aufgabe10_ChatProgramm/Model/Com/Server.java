package Aufgabe10_ChatProgramm.Model.Com;

import Aufgabe10_ChatProgramm.Model.ChatModel;
import Aufgabe10_ChatProgramm.View.MainWindow;

import java.io.IOException;
import java.net.ServerSocket;
import java.util.logging.Logger;

public class Server extends Transmitter {

    Logger lg = Logger.getLogger("Netz");

    public Server() throws IOException {
        connect();
        initIO();
    }

    public void connect() throws IOException {
        try {
            ServerSocket sSocket = new ServerSocket(PORT);
            sSocket.setSoTimeout(timeout);
            lg.info("Server: warte auf Verbindung");
            socket = sSocket.accept();
            lg.info("Server: Verbindung akzeptiert");
        } catch (java.io.InterruptedIOException e) {
            lg.warning("Timeout" + "(" + timeout / 1000 + "s)");
        }
    }

}
